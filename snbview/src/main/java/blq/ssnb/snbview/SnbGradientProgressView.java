package blq.ssnb.snbview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Locale;

import blq.ssnb.snbutil.SnbCheckUtil;
import blq.ssnb.snbutil.SnbDisplayUtil;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2016/8/30
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 渐变进度条
 * ================================================
 * </pre>
 */

public class SnbGradientProgressView extends View {

    /**
     * 进度条变化监听
     */
    public interface ProgressChangeListener {
        /**
         * 当前进度
         *
         * @param progress 当前进度
         */
        void onProgress(int progress);
    }

    private ProgressChangeListener changeListener;

    public void setProgressChangeListener(ProgressChangeListener listener) {
        this.changeListener = listener;
    }

    private void callBack() {
        if (changeListener != null) {
            changeListener.onProgress(getProgress());
        }
    }

    /**
     * 获得最大的刻度值
     *
     * @return 最大刻度值，未设置默认为100
     */
    public int getMaxCount() {
        return maxCount;
    }

    /**
     * 设置view的最大刻度值
     * 如果currentCount大于新设置的maxCount的话那么就直接返回
     * 反之会设置新的maxCount并且比例会发生变化（currentCount不会发生变化）
     *
     * @param maxCount 最大进度
     * @return true 设置成功 false 设置失败
     */
    public boolean setMaxCount(int maxCount) {
        if (maxCount < currentCount) {
            return false;
        }
        this.maxCount = maxCount;
        progress = currentCount * 1f / this.maxCount;
        callBack();
        refresh();
        return true;
    }

    /**
     * 获得当前的刻度值
     *
     * @return 当前刻度值
     */
    public int getCurrentCount() {
        return currentCount;
    }

    /**
     * 设置当前刻度值，如果超过最大刻度值就为最大刻度值
     *
     * @param currentCount 当前刻度值
     */
    public void setCurrentCount(int currentCount) {
        //如果currentCount >0 判断是否大于maxCount
        this.currentCount = (0 <= currentCount ? (currentCount <= maxCount ? currentCount : maxCount) : 0);
        this.progress = this.currentCount * 1f / maxCount;
        callBack();
        refresh();
    }

    /**
     * 获得当前进度百分比
     *
     * @return float 0-100
     */
    public int getProgress() {
        return (int) (progress * 100);
    }

    /**
     * 设置当前进度的百分比
     *
     * @param progress 0&lt;= progress &lt;=100 超出范围都会变成临界值
     */
    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > 100) {
            progress = 100;
        }
        this.progress = progress / 100f;
        currentCount = (int) (this.progress * maxCount);
        callBack();
        refresh();
    }

    /**
     * 设置文字颜色是否随进度条的颜色的变化而变化
     *
     * @param isTrue true 会随着进度条的颜色的变化而变化 会忽略{@link #setTextColor(int)}设置的颜色
     *               false 不会变化会显示{@link #setTextColor(int)}的颜色
     */
    public void setTextColorWithProgress(boolean isTrue) {
        isTextColorWithProgress = isTrue;
        refresh();
    }

    private int textColor = Color.BLACK;

    /**
     * 设置中间文字的颜色
     *
     * @param textColor 颜色值
     *                  如果设置了{@link #setTextColorWithProgress(boolean)}为true的话该属性就会被忽略
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    private static float TEXT_SIZE_PERCENT = 0.2F;
    private static float PADDING_PERCENT = 0.1F;
    private static float STROKE_WIDTH_PERCENT = 0.15F;

    //最大刻度
    private int maxCount;
    //当前刻度
    private int currentCount;
    //当前百分比
    private float progress;
    //用于画图的画笔
    private Paint picturePaint;
    //用于画文字的画笔
    private Paint textPaint;
    //view的宽高
    private int mWidth, mHeight;
    //画图中间刻度，宽高中最小值
    private int drawW;

    //宽>高 IntervalH=0 IntervalW=(宽-高)/2
    private int IntervalW, IntervalH;

    private boolean isTextColorWithProgress = false;

    public SnbGradientProgressView(Context context) {
        this(context, null);
    }

    public SnbGradientProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.SnbGradientProgressViewStyle);
    }

    public SnbGradientProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.DefSnbGradientProgressViewStyle);
    }

    public SnbGradientProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        init(context, attrs, defStyleAttr, defStyleRes);

    }

    private void init() {
        picturePaint = new Paint();
        textPaint = new Paint();
        maxCount = 100;
        currentCount = 0;
        progress = 0;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SnbGradientProgressView, defStyleAttr, defStyleRes);

        maxCount = typedArray.getInt(R.styleable.SnbGradientProgressView_snb_max, 100);
        float mProgress = typedArray.getFloat(R.styleable.SnbGradientProgressView_snb_progress, 50f);
//        setProgress((int) mProgress);
        progress = mProgress / 100f;
        if (progress > 1) {
            progress = 1f;
        } else if (progress < 0) {
            progress = 0f;
        }
        typedArray.recycle();
        currentCount = (int) progress * maxCount;
    }

    //对界面大小设计
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽高定义类型
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取宽高大小
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //如果宽度是充满或者精确值的话就获得該值
        if (widthSpecMode == MeasureSpec.EXACTLY) {
            mWidth = widthSpecSize;
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = SnbDisplayUtil.dp2Px(getContext(), 100f);
        } else {
            //否者就是自适应wrap_content ,那么久设置默认宽度为100dip；
            mWidth = SnbDisplayUtil.dp2Px(getContext(), 100f);
        }

        //如果高度是充满或者精确值的话就获得該值
        if (heightSpecMode == MeasureSpec.EXACTLY) {
            mHeight = heightSpecSize;
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            //否者就是自适应wrap_content ,那么久设置默认高度为100dip；
            mHeight = SnbDisplayUtil.dp2Px(getContext(), 100f);
        } else {
            mHeight = SnbDisplayUtil.dp2Px(getContext(), 100f);

        }
        drawW = mHeight > mWidth ? mWidth : mHeight;
        IntervalW = (mWidth - drawW) / 2;
        IntervalH = (mHeight - drawW) / 2;
        setMeasuredDimension(mWidth, mHeight);
    }


    private RectF rectBlackBG;
    private Rect mBound;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        if (rectBlackBG == null) {
            rectBlackBG = new RectF(IntervalW + drawW * PADDING_PERCENT,
                    IntervalH + drawW * PADDING_PERCENT,
                    mWidth - IntervalW - drawW * PADDING_PERCENT,
                    mHeight - IntervalH - drawW * PADDING_PERCENT);
        }

        //获得当前进度
        float Proportion = progress;

        float df = 4.0f;
        //不同进度话不同的颜色
        if (Proportion <= 1.0f / df) {//0,0,255->0,255,255
            if (Proportion != 0.0f) {
                picturePaint.setColor(Color.rgb(0, (int) (255 * Proportion * df), 255));
            } else {
                picturePaint.setColor(Color.TRANSPARENT);
            }
        } else if (Proportion <= 2.0f / df) {//0,255,255->0,255,0
            picturePaint.setColor(Color.rgb(0, 255, (int) (255 * (2 - Proportion * df))));
        } else if (Proportion <= 3.0f / df) {//0,255,0->255,255,0
            picturePaint.setColor(Color.rgb((int) (255 * (Proportion * df - 2)), 255, 0));
        } else if (Proportion <= 4.0f / df) {//255,255,0->255,0,0
            picturePaint.setColor(Color.rgb(255, (int) (255 * (4 - Proportion * df)), 0));
        }
        if (isTextColorWithProgress) {
            int color = picturePaint.getColor();
            textPaint.setColor(color == 0 ? Color.BLACK : color);
        } else {
            textPaint.setColor(textColor);
        }

        //画进度
        canvas.drawArc(rectBlackBG, 135, Proportion * 360, false, picturePaint);
        String showText = String.format(Locale.getDefault(), "%d%%", (int) (progress * 100));
        if (mBound == null) {
            mBound = new Rect();
        }
        textPaint.getTextBounds(showText, 0, showText.length(), mBound);
        //这里是画中间显示的数值
        canvas.drawText(showText,
                getWidth() / 2.0f,
                mHeight / 2.0f + mBound.height() / 2.0f,
                textPaint);
    }

    private void initPaint() {
        picturePaint.setAntiAlias(true);//抗锯齿
        picturePaint.setStrokeWidth(drawW * STROKE_WIDTH_PERCENT);//设置空心的边框宽度
        picturePaint.setStyle(Paint.Style.STROKE);//设置画笔为空心
        picturePaint.setStrokeCap(Paint.Cap.ROUND);//设置圆形样式
        picturePaint.setColor(Color.TRANSPARENT);//设置画笔颜色为无色
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth((float) 4.0);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(drawW * TEXT_SIZE_PERCENT);
        textPaint.setColor(Color.BLACK);

    }

    private void refresh() {
        if (SnbCheckUtil.isMainThread()) {
            invalidate();//实在主线程中刷新ui
        } else {
            postInvalidate();
        }
    }
}

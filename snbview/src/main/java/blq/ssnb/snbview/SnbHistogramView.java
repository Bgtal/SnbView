package blq.ssnb.snbview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import blq.ssnb.snbutil.SnbLog;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019-08-11
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 类似下面这样的柱状图
 *       __
 *      |  |
 *      |  |
 *      |--|
 *      |--|
 *      |__|
 *
 *
 * ================================================
 * </pre>
 */
public class SnbHistogramView extends View {

    private static final int LEFT = 1;
    private static final int TOP = 2;
    private static final int RIGHT = 4;
    private static final int BOTTOM = 8;

    public SnbHistogramView(Context context) {
        this(context, null);
    }

    public SnbHistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.SnbHistogramViewStyle);
    }

    public SnbHistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.DefSnbHistogramViewStyle);
    }

    public SnbHistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData();
        initAttrs(context, attrs, defStyleAttr, defStyleRes);
    }

    private Paint mPaint;

    private void initData() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    private int mMax = 100;
    private int mProgress = 50;
    private int mGravity = BOTTOM;
    private boolean isKeepMin = true;
    private int[] mGradientColors;
    private boolean isSelect;
    private int mSelectColor = 0xFF7543FA;


    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SnbHistogramView, defStyleAttr, defStyleRes);
        int max = typedArray.getInt(R.styleable.SnbHistogramView_snb_max, mMax);
        int progress = typedArray.getInt(R.styleable.SnbHistogramView_snb_progress, mProgress);
        int gravity = typedArray.getInt(R.styleable.SnbHistogramView_snb_gravity, mGravity);
        boolean isKeep = typedArray.getBoolean(R.styleable.SnbHistogramView_snb_keep_min, isKeepMin);
        int bgColorArray = typedArray.getResourceId(R.styleable.SnbHistogramView_snb_progress_background, R.array.snb_histogram_color_def);
        int[] bgColors = getResources().getIntArray(bgColorArray);
        int selectColor = typedArray.getColor(R.styleable.SnbHistogramView_snb_high_light_color, mSelectColor);
        typedArray.recycle();
        setMax(max);
        setProgress(progress);
        setGravity(gravity);
        setKeepMinEnable(isKeep);
        setGradientColors(bgColors);
        setSelectColor(selectColor);
    }

    private int mRealHeight = 0;
    private int mRealWidth = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽高定义类型
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取宽高大小
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int mHeight = 0;
        int mWidth = 0;

        //如果宽度是充满或者精确值的话就获得該值
        if (widthSpecMode == MeasureSpec.EXACTLY) {
            mWidth = widthSpecSize;
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        }
        mRealWidth = mWidth - getPaddingStart() - getPaddingEnd();

        //如果高度是充满或者精确值的话就获得該值
        if (heightSpecMode == MeasureSpec.EXACTLY) {
            mHeight = heightSpecSize;
        } else {
            mHeight = heightSpecSize;
        }
        mRealHeight = mHeight - getPaddingTop() - getPaddingBottom();
        setMeasuredDimension(mWidth, mHeight);
    }


    private Path mPath = new Path();
    private LinearGradient mGradient;
    private int lastDrawLength = -1;


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //计算得到需要画图的高度
        int drawHeight = (mRealHeight * mProgress) / mMax;

        //计算得到顶部的圆弧的半径是多少
        float radius = mRealWidth / 2.0f;//直径/2= 半径

        if (mGravity == LEFT || mGravity == RIGHT) {
            drawHeight = (mRealWidth * mProgress) / mMax;
            radius = mRealHeight / 2.0f;
        }

        if (drawHeight == 0 && !isKeepMin) {
            lastDrawLength = 0;
            return;//如果要画图的高度为0，表示不用画了，直接返回
        }
        mPath.reset();
        if (drawHeight > radius) {//如果画图高度大于半径，说明图形是 矩形+ 半圆弧的结构
            updatePoint(drawHeight, (int) radius);
            mPath.moveTo(getAPoint().x, getAPoint().y);
            mPath.lineTo(getBPoint().x, getBPoint().y);
            mPath.lineTo(getCPoint().x, getCPoint().y);
            mPath.lineTo(getDPoint().x, getDPoint().y);
        }

        drawArc(mPath, drawHeight, (int) radius);
        mPath.close();
        if (isSelect) {
            mPaint.setColor(mSelectColor);
            mPaint.setShader(null);
        } else {
            if (mGradient == null || isGradientChanged || lastDrawLength != drawHeight) {
                calculateGradientRect(drawHeight, (int) radius);
                mGradient = new LinearGradient(getGradientRect().left,
                        getGradientRect().top,
                        getGradientRect().right,
                        getGradientRect().bottom,
                        mGradientColors, null,
                        Shader.TileMode.REPEAT);
                isGradientChanged = false;
                lastDrawLength = drawHeight;
            }
            mPaint.setShader(mGradient);
        }
        canvas.drawPath(mPath, mPaint);
    }

    /* 这里画图因为涉及到方向的改变，所以，将画图用到的几个关键点(下半部分的矩形4个点位)定位为ABCD
     * 那么些代码只要按逻辑从A连线到B->C->D 连线，关键点如何计算就由各自的方法自己去计算
     * */

    private void updatePoint(int drawLength, int radius) {
        int aX, aY, bX, bY, cX, cY, dX, dY;
        aX = aY = bX = bY = cX = cY = dX = dY = 0;
        switch (mGravity) {
            case LEFT:
                aX = getPaddingStart() + drawLength - radius;
                aY = getPaddingTop();
                bX = getPaddingStart();
                bY = getPaddingTop();
                cX = getPaddingStart();
                cY = getPaddingTop() + mRealHeight;
                dX = getPaddingStart() + drawLength - radius;
                dY = getPaddingTop() + mRealHeight;
                break;
            case TOP:
                aX = getPaddingStart() + mRealWidth;
                aY = getPaddingTop() + drawLength - radius;
                bX = getPaddingStart() + mRealWidth;
                bY = getPaddingTop();
                cX = getPaddingStart();
                cY = getPaddingTop();
                dX = getPaddingStart();
                dY = getPaddingTop() + drawLength - radius;
                break;
            case RIGHT:
                aX = getPaddingStart() + mRealWidth - drawLength + radius;
                aY = getPaddingTop() + mRealHeight;
                bX = getPaddingStart() + mRealWidth;
                bY = getPaddingTop() + mRealHeight;
                cX = getPaddingStart() + mRealWidth;
                cY = getPaddingTop();
                dX = getPaddingStart() + mRealWidth - drawLength + radius;
                dY = getPaddingTop();
                break;
            case BOTTOM:
                aX = getPaddingStart();
                aY = getPaddingTop() + mRealHeight - drawLength + radius;
                bX = getPaddingStart();
                bY = getPaddingTop() + mRealHeight;
                cX = getPaddingStart() + mRealWidth;
                cY = getPaddingTop() + mRealHeight;
                dX = getPaddingStart() + mRealWidth;
                dY = getPaddingTop() + mRealHeight - drawLength + radius;
                break;
        }
        getAPoint().set(aX, aY);
        getBPoint().set(bX, bY);
        getCPoint().set(cX, cY);
        getDPoint().set(dX, dY);
    }

    private void drawArc(Path path, int drawLength, int radius) {
        float sX = 0;
        float sY = 0;
        float eX = 0;
        float eY = 0;
        float startAngle = 0;
        float sweepAngle = 180;
        float diff = 0;
        if (isKeepMin) {
            if (radius > drawLength) {
                drawLength = radius;
            }
        } else {
            diff = getSweepDiff(radius, drawLength);
        }
        switch (mGravity) {
            case LEFT:
                sX = getPaddingStart() + drawLength - mRealHeight;
                sY = getPaddingTop();
                eX = getPaddingStart() + drawLength;
                eY = getPaddingTop() + mRealHeight;
                startAngle = 270;
                break;
            case TOP:
                sX = getPaddingStart();
                sY = getPaddingTop() + drawLength - mRealWidth;
                eX = getPaddingStart() + mRealWidth;
                eY = getPaddingTop() + drawLength;
                startAngle = 0;
                break;
            case RIGHT:
                sX = getPaddingStart() + mRealWidth - drawLength;
                sY = getPaddingTop();
                eX = getPaddingStart() + mRealWidth - drawLength + mRealHeight;
                eY = getPaddingTop() + mRealHeight;
                startAngle = 90;
                break;
            case BOTTOM:
                sX = getPaddingStart();
                sY = getPaddingTop() + mRealHeight - drawLength;
                eX = getPaddingStart() + mRealWidth;
                eY = getPaddingTop() + mRealHeight - drawLength + mRealWidth;
                startAngle = 180;
                break;
        }

        startAngle = startAngle + diff;
        sweepAngle = sweepAngle - 2 * diff;
        SnbLog.e(">>>> 开始角度:" + startAngle + ": 划过角度:" + sweepAngle);

        path.addArc(sX, sY, eX, eY, startAngle, sweepAngle);

    }

    private float getSweepDiff(int radius, int drawLength) {
        if (drawLength >= radius) {
            return 0;
        }
        return (float) Math.toDegrees(Math.asin((radius - drawLength) / (float) radius));
    }

    private void calculateGradientRect(int drawLength, int radius) {

        float sX = 0;
        float sY = 0;
        float eX = 0;
        float eY = 0;
        if (isKeepMin) {
            if (radius > drawLength) {
                drawLength = radius;
            }
        }
        switch (mGravity) {
            case LEFT:
                sX = getPaddingStart() + drawLength;
                sY = getPaddingTop() + mRealHeight / 2;
                eX = getPaddingStart();
                eY = getPaddingTop() + mRealHeight / 2;
                break;
            case TOP:
                sX = getPaddingStart() + mRealWidth / 2;
                sY = getPaddingTop() + drawLength;
                eX = getPaddingStart() + mRealWidth / 2;
                eY = getPaddingTop();

                break;
            case RIGHT:
                sX = getPaddingStart() + mRealWidth - drawLength;
                sY = getPaddingTop() + mRealHeight / 2;
                eX = getPaddingStart() + mRealWidth;
                eY = getPaddingTop() + mRealHeight / 2;
                break;
            case BOTTOM:
                sX = getPaddingStart() + mRealWidth / 2;
                sY = getPaddingTop() + mRealHeight - drawLength;
                eX = getPaddingStart() + mRealWidth / 2;
                eY = getPaddingTop() + mRealHeight;
                break;
        }
        getGradientRect().set(sX, sY, eX, eY);
    }

    private Point aPoint;

    private Point getAPoint() {
        if (aPoint == null) {
            aPoint = new Point();
        }
        return aPoint;
    }

    private Point bPoint;

    private Point getBPoint() {
        if (bPoint == null) {
            bPoint = new Point();
        }
        return bPoint;
    }

    private Point cPoint;

    private Point getCPoint() {
        if (cPoint == null) {
            cPoint = new Point();
        }
        return cPoint;
    }

    private Point dPoint;

    private Point getDPoint() {
        if (dPoint == null) {
            dPoint = new Point();
        }
        return dPoint;
    }

    private RectF mGradientRect;

    private RectF getGradientRect() {
        if (mGradientRect == null) {
            mGradientRect = new RectF();
        }
        return mGradientRect;
    }

    /**
     * 设置画图方向
     *
     * @param gravity
     */
    public void setGravity(int gravity) {
        if (this.mGravity != gravity) {
            this.mGravity = gravity;
            isGradientChanged = true;
            invalidate();
        }
    }

    public int getGravity() {
        return mGravity;
    }

    public void setMax(int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("最大值不能小于0");
        }
        if (this.mMax != max) {
            this.mMax = max;
            //设置完后需要设置下进度值，万一进度值大于最大值，需要更改
            if (isExceedMax(mProgress)) {
                setProgress(mProgress);
            } else {
                invalidate();
            }
        }
    }

    public int getMax() {
        return mMax;
    }

    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        } else if (isExceedMax(progress)) {
            progress = mMax;
        }

        if (progress != this.mProgress) {
            this.mProgress = progress;
            invalidate();
        }
    }

    public int getProgress() {
        return mProgress;
    }

    private boolean isExceedMax(int num) {
        return mMax < num;
    }

    public boolean isKeepMin() {
        return isKeepMin;
    }

    public void setKeepMinEnable(boolean isEnable) {
        if (isKeepMin != isEnable) {
            isKeepMin = isEnable;
            isGradientChanged = true;
            invalidate();
        }
    }


    private boolean isGradientChanged = false;

    public void setGradientColors(int[] gradientColors) {
        isGradientChanged = true;
        this.mGradientColors = gradientColors;
        invalidate();
    }

    public void setSelectColor(int color) {
        if (this.mSelectColor != color) {
            this.mSelectColor = color;
            if (isSelect) {
                invalidate();
            }
        }
    }


    public void showHighLight(boolean isShow) {
        if (this.isSelect != isShow) {
            this.isSelect = isShow;
            invalidate();
        }
    }
}

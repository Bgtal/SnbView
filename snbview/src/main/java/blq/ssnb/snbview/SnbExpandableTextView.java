package blq.ssnb.snbview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import blq.ssnb.snbutil.SnbDisplayUtil;

/**
 * <pre>
 * ================================================
 * 作者: SSNB
 * 日期: 2017年3月30日
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 可以折叠收缩的TextView
 *
 * <attr name="snb_text"/>
 * <attr name="snb_foldShowLine" format="integer"/>
 * <attr name="snb_textSize"/>
 * <attr name="snb_foldStatusSrc" format="reference"/>
 * <attr name="snb_expandStatusSrc" format="reference"/>
 * <attr name="snb_animDuration"/>
 * <attr name="snb_isFold" format="boolean"/>
 * <attr name="snb_textColor"/>
 *
 * ================================================
 * </pre>
 */

public class SnbExpandableTextView extends LinearLayout {

    /**
     * 默认的折叠展开动画时间
     */
    private static final int DEFAULT_ANIMATION_DURATION = 300;
    /**
     * 默认显示的行数
     */
    private static final int DEFAULT_SHOW_TEXT_LINE = 3;

    public SnbExpandableTextView(Context context) {
        this(context, null);
    }

    public SnbExpandableTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.SnbExpandableTextViewStyle);
    }

    public SnbExpandableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.DefSnbExpandableTextViewStyle);
    }

    public SnbExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs, defStyleAttr, defStyleRes);
        View.inflate(context, R.layout.snb_expandable_text_view, this);
    }


    /*外部传入参数*/
    /**
     * 文本内容
     */
    private String mText;
    /**
     * 文字大小
     */
    private float textSize;
    /**
     * 折叠状态下图标
     */
    private Drawable foldStatusDrawable;
    /**
     * 展开状态下图标
     */
    private Drawable expandStatusDrawable;
    /**
     * 最少显示几行
     */
    private int foldShowLine;
    /**
     * 动画持续时间
     */
    private int animDuration;
    /**
     * 是否折叠状态
     */
    private boolean isFold;
    /**
     * 文字颜色
     */
    private ColorStateList textColor;


    /* 状态参数 */
    /**
     * 是否在动画状态下
     */
    private boolean isAnimating = false;

    /**
     * 是否需要重新计算大小
     */
    private boolean needReMeasure = true;

    /**
     * 强行调用动画
     */
    private boolean forceRunAnimation = false;

    /* 用于计算参数 */
    /**
     * 文字最高高度
     */
    private int textMaxHeight;
    /**
     * 文字最低高度
     */
    private int textMinHeight;

    /**
     * 最后一个动画的对象
     */
    private Animation mAnimation;


    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SnbExpandableTextView, defStyleAttr, defStyleRes);
            mText = typedArray.getString(R.styleable.SnbExpandableTextView_snb_text);
            foldShowLine = typedArray.getInt(R.styleable.SnbExpandableTextView_snb_foldShowLine, DEFAULT_SHOW_TEXT_LINE);
            foldStatusDrawable = typedArray.getDrawable(R.styleable.SnbExpandableTextView_snb_foldStatusSrc);
            expandStatusDrawable = typedArray.getDrawable(R.styleable.SnbExpandableTextView_snb_expandStatusSrc);
            animDuration = typedArray.getInt(R.styleable.SnbExpandableTextView_snb_animDuration, DEFAULT_ANIMATION_DURATION);
            textSize = typedArray.getDimensionPixelSize(R.styleable.SnbExpandableTextView_snb_textSize, SnbDisplayUtil.dp2Px(context, 14));
            isFold = typedArray.getBoolean(R.styleable.SnbExpandableTextView_snb_isFold, true);
            textColor = typedArray.getColorStateList(R.styleable.SnbExpandableTextView_snb_textColor);
            typedArray.recycle();
        }

        foldStatusDrawable = getStatusDrawable(foldStatusDrawable, R.drawable.snb_ic_expand_more_black_24dp);
        expandStatusDrawable = getStatusDrawable(expandStatusDrawable, R.drawable.snb_ic_expand_less_black_24dp);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //初始化界面
        initView();
        initEvent();
    }

    private TextView contentView;
    private ImageView expandBtn;

    private void initView() {
        expandBtn = findViewById(R.id.bsn_expandable_expand_btn);
        contentView = findViewById(R.id.bsn_expandable_content);
        contentView.setText(mText);
        contentView.getPaint().setTextSize(textSize);
        contentView.setTextColor(textColor != null ? textColor : ColorStateList.valueOf(0xFF000000));
        expandBtn.setImageDrawable(isFold ? foldStatusDrawable : expandStatusDrawable);
    }

    private void initEvent() {
        expandBtn.setOnClickListener(v -> changeExpandStatus(!isFold, true));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("onMeasure", "onMeasure1");
        //如果需要更新并且界面为显示
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!needReMeasure || getVisibility() == GONE) {
            return;
        }
        Log.e("onMeasure", "onMeasure2");
        needReMeasure = false;
        expandBtn.setVisibility(GONE);
        //如果文本的行数小于最小显示行数 直接返回
        int p = contentView.getCompoundPaddingTop() + contentView.getCompoundPaddingBottom();
        textMaxHeight = p + contentView.getLayout().getHeight();
        textMinHeight = textMaxHeight;
        if (contentView.getLineCount() <= foldShowLine) {
            contentView.setHeight(textMaxHeight);
            changeExpandStatus(isFold, true);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        Log.e("onMeasure", "onMeasure3");
        //获得文本的最小高度
        textMinHeight = contentView.getLayout().getLineTop(foldShowLine) + p;
        if (!forceRunAnimation) {//如果当前状态不是强制动画状态，那么就设置高度，否者就先不设置
            if (isFold) {//如果是缩起状态那么就设置最小高度
                contentView.setHeight(textMinHeight);
            } else {
                contentView.setHeight(textMaxHeight);
            }
        }
        //显示图标
        expandBtn.setVisibility(VISIBLE);
        changeExpandStatus(isFold, true);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setOrientation(@LinearLayoutCompat.OrientationMode int orientation) {
        if (orientation != LinearLayout.VERTICAL) {
            throw new IllegalArgumentException("只允许使用LinearLayout.VERTICAL");
        }
        super.setOrientation(orientation);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isAnimating;
    }

    private class MAnimation extends Animation {

        private TextView targetView;
        private int startHeight;
        private int endHeight;

        MAnimation(TextView targetView, int startHeight, int endHeight) {
            this.targetView = targetView;
            this.startHeight = startHeight;
            this.endHeight = endHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final int newHeight = (int) ((endHeight - startHeight) * interpolatedTime + startHeight);
            targetView.setHeight(newHeight);
        }
    }

    /**
     * 设置显示内容
     *
     * @param text 文本内容
     */
    public void setText(String text) {
        needReMeasure = true;//需要重写计算大小
        checkForceRunAnimation();//检测是否需要强制运行动画
        mText = text == null ? "" : text;
        contentView.setText(mText);
        requestLayout();
    }

    public CharSequence getText() {
        return contentView.getText();
    }

    /**
     * 设置字体大小
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        needReMeasure = true;//需要重写计算大小
        checkForceRunAnimation();//检测是否需要强制运行动画
        contentView.setTextSize(textSize);
        requestLayout();
    }

    public float getTextSize() {
        return contentView.getTextSize();
    }

    /**
     * 设置缩起状态下的指示图片
     *
     * @param id 资源id
     */
    public void setFoldStatusDrawable(@DrawableRes int id) {
        if (moreLOLLIPOP()) {
            setFoldStatusDrawable(getResources().getDrawable(id, getContext().getTheme()));
        } else {
            setFoldStatusDrawable(getResources().getDrawable(id));
        }
    }

    /**
     * 设置缩起状态下的指视图片
     *
     * @param drawable 图片对象
     */
    public void setFoldStatusDrawable(Drawable drawable) {
        foldStatusDrawable = getStatusDrawable(drawable, R.drawable.snb_ic_expand_more_black_24dp);
        expandBtn.setImageDrawable(isFold ? foldStatusDrawable : expandStatusDrawable);
    }

    public Drawable getFoldStatusDrawable() {
        return foldStatusDrawable;
    }

    /**
     * 设置展开状态下的指示图片
     *
     * @param id 资源id
     */
    public void setExpandStatusDrawable(@DrawableRes int id) {
        if (moreLOLLIPOP()) {
            setExpandStatusDrawable(getResources().getDrawable(id, getContext().getTheme()));
        } else {
            setExpandStatusDrawable(getResources().getDrawable(id));
        }
    }

    /**
     * 设置展开状态下的指视图片
     *
     * @param drawable 图片对象
     */
    public void setExpandStatusDrawable(Drawable drawable) {
        expandStatusDrawable = getStatusDrawable(drawable, R.drawable.snb_ic_expand_less_black_24dp);
        expandBtn.setImageDrawable(isFold ? foldStatusDrawable : expandStatusDrawable);
    }

    public Drawable getExpandStatusDrawable() {
        return expandStatusDrawable;
    }

    /**
     * 设置缩起状态下显示的行数
     *
     * @param foldShowLine 缩起状态下的行数
     */
    public void setFoldShowLine(int foldShowLine) {
        checkForceRunAnimation();
        this.foldShowLine = foldShowLine;
        if (isFold) {
            needReMeasure = true;
            requestLayout();
        }
    }

    public int getFoldShowLine() {
        return foldShowLine;
    }

    /**
     * 设置动画时间
     *
     * @param animDuration
     */
    public void setAnimDuration(int animDuration) {
        if (animDuration < 0) {
            throw new IllegalArgumentException("animDuration 必须大于等于0");
        }
        this.animDuration = animDuration;
    }

    public long getAnimDuration() {
        return animDuration;
    }

    /**
     * 文本内容字体颜色
     *
     * @param color 字体颜色
     */
    public void setTextColor(@ColorInt int color) {
        textColor = ColorStateList.valueOf(color);
        setTextColor(textColor);
    }

    /**
     * 文本内容字体颜色
     *
     * @param color 字体颜色
     */
    public void setTextColor(ColorStateList color) {
        contentView.setTextColor(color);
    }

    public ColorStateList getTextColor() {
        return contentView.getTextColors();
    }

    /**
     * 关闭展开项，默认播放动画
     */
    public void foldContent() {
        foldContent(true);
    }

    /**
     * 关闭展开项
     *
     * @param playAnim 是否需要动画
     */
    public void foldContent(boolean playAnim) {
        changeExpandStatus(true, playAnim);
    }

    /**
     * 打开展开项，默认播放动画
     */
    public void expandContent() {
        expandContent(true);
    }

    /**
     * 打开展开项
     *
     * @param playAnim 是否需要动画
     */
    public void expandContent(boolean playAnim) {
        changeExpandStatus(false, playAnim);
    }

    public boolean isFold() {
        return isFold;
    }

    /**
     * 改变展开状态，
     *
     * @param toFold   是否变成折叠状态 ，true 表示折叠，false 展开
     * @param playAnim 是否播放动画
     */
    private void changeExpandStatus(boolean toFold, boolean playAnim) {
        //如果行数小于最小显示行数或者当前状态已经是目标状态了
        if ((contentView.getLineCount() <= foldShowLine || this.isFold == toFold) && !forceRunAnimation) {
            return;
        }
        this.isFold = toFold;//当前状态变成目标状态
        //清除前面的动画
        contentView.clearAnimation();
        //改变图标
        expandBtn.setImageDrawable(isFold ? foldStatusDrawable : expandStatusDrawable);
        //当前状态变为是否播放动画中
        isAnimating = playAnim;

        if (playAnim) {//如果需要播放动画
            Animation animation;
            if (toFold) {//如果变为折叠的
                animation = new MAnimation(contentView, contentView.getHeight(), textMinHeight);
            } else {
                animation = new MAnimation(contentView, contentView.getHeight(), textMaxHeight);
            }
            mAnimation = animation;
            animation.setDuration(animDuration);
            animation.setFillAfter(true);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mAnimation.equals(animation)) {
                        //如果这个动画是最后一次设置的动画，那么就执行 clean 方法，否者认为这个动画是过期的，不执行任何事
                        contentView.clearAnimation();
                        isAnimating = false;
                        forceRunAnimation = false;
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            contentView.startAnimation(animation);
        } else {//否者的就直接设置
            if (toFold) {//true
                contentView.setHeight(textMinHeight);
            } else {
                contentView.setHeight(textMaxHeight);
            }
            forceRunAnimation = false;
        }
    }

    /**
     * 版本大于LOLLIPOP(21)
     *
     * @return true 版本大于等于21
     */
    private boolean moreLOLLIPOP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 获取Drawable对象
     *
     * @param drawable    要判断的对象
     * @param defaultDraw 如果drawable为空获取的对象资源id
     * @return drawable 不为空返回 drawable 否者就返回 defaultDraw 的资源对象
     */
    private Drawable getStatusDrawable(Drawable drawable, @DrawableRes int defaultDraw) {
        Drawable returnDraw = drawable;
        if (returnDraw == null) {
            if (moreLOLLIPOP()) {
                returnDraw = getResources().getDrawable(defaultDraw, getContext().getTheme());
            } else {
                returnDraw = getResources().getDrawable(defaultDraw);
            }
        }
        return returnDraw;
    }

    /**
     * 检查是否需要强制运行动画
     */
    private void checkForceRunAnimation() {
        if (isAnimating) {
            forceRunAnimation = true;
        }
    }
}

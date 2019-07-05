package blq.ssnb.snbview.shape;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * ================================================
 * 作者: SSNB
 * 日期: 2016/9/20
 * 描述:
 * Shape的基类
 * BaseShape 和 Builder 的所有参数方法 都是 protected 这样是为了
 * 方便子类有选择性的向外抛出需要设置和可以得到的属性
 * ================================================
 */
public abstract class BaseShape {
    protected static final int INVALID_VALUE = -1;
    protected static final float DEFAULT_VALUE_ZERO = 0.0F;

    public enum Shape {
        /**
         * 矩形
         */
        RECTANGLE(0),
        /**
         * 椭圆形
         */
        OVAL(1),
        /**
         * 线性
         */
        LINE(2),
        /**
         * 圆环形
         */
        RING(3);
        private int value;

        Shape(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }
    }

    public enum GradientType {
        LINEAR(GradientDrawable.LINEAR_GRADIENT),
        RADIAL(GradientDrawable.RADIAL_GRADIENT),
        SWEEP(GradientDrawable.SWEEP_GRADIENT);
        int value;

        GradientType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    //矩形，椭圆，线，环形
    private Shape shape;

    //线框
    private int strokeColor;//颜色
    private int strokeWidth;//宽度（虚线的高度）
    private float strokeDashWidth;//虚线宽度 （每条虚线的长度）
    private float strokeDashGap;//虚线间隔 两条虚线之间的间隔

    //四个角的元弧度
    private float radius;//四个角的半径
    private float topLeftRadius;//左上角半径
    private float topRightRadius;//右上角半径
    private float bottomLeftRadius;//左下角半径
    private float bottomRightRadius;//右下角半径

    //渐变色
    private int[] gradientColors;//渐变色的梯度
    private float centerX;//中间颜色的相对x坐标（0-1）
    private float centerY;//中间颜色的相对y坐标（0-1）
    private boolean useLevel;//是否用做LevelListDrawable
    private GradientDrawable.Orientation orientation;// 渐变方向
    private GradientType type;//渐变模式 默认线性linear, radial（径向渐变） sweep （雷达一样的渐变）
    private float gradientRadius;//只有设置 gradientType 为 radial 的时候才会有效果

    //内边距
    private int left;//左边距
    private int top;//上边距
    private int right;//右边距
    private int bottom;//下边距

    //宽高
    private int width;//宽
    private int height;//高

    //填充色
    private int color;

    //透明度
    private int alpha;


    protected BaseShape(@NonNull Builder builder) {
        shape = builder.shape;
        //线框
        strokeColor = builder.strokeColor;//颜色
        strokeWidth = builder.strokeWidth;//宽度（虚线的高度）
        strokeDashWidth = builder.strokeDashWidth;//虚线宽度 （每条虚线的长度）
        strokeDashGap = builder.strokeDashGap;//虚线间隔 两条虚线之间的间隔

        //四个角的元弧度
        radius = builder.radius;//四个角的半径
        topLeftRadius = builder.topLeftRadius;//左上角半径
        topRightRadius = builder.topRightRadius;//右上角半径
        bottomLeftRadius = builder.bottomLeftRadius;//左下角半径
        bottomRightRadius = builder.bottomRightRadius;//右下角半径

        //渐变色
        gradientColors = builder.gradientColors;//渐变色的数组
        orientation = builder.orientation;
        centerX = builder.centerX;//中间颜色的相对x坐标（0-1）
        centerY = builder.centerY;//中间颜色的相对y坐标（0-1）
        useLevel = builder.useLevel;//是否用做LevelListDrawable
        type = builder.type;//渐变模式 默认线性linear, radial（径向渐变） sweep （雷达一样的渐变）
        gradientRadius = builder.gradientRadius;//

        //内边距
        left = builder.left;//左边距
        top = builder.top;//上边距
        right = builder.right;//右边距
        bottom = builder.bottom;//下边距

        //宽高
        width = builder.width;//宽
        height = builder.height;//高
        //填充色
        color = builder.color;
        //透明度
        alpha = builder.alpha;

        //设置四个角的弧度
        if (topLeftRadius < 0) {
            topLeftRadius = radius;
        }
        if (topRightRadius < 0) {
            topRightRadius = radius;
        }
        if (bottomRightRadius < 0) {
            bottomRightRadius = radius;
        }
        if (bottomLeftRadius < 0) {
            bottomLeftRadius = radius;
        }
        if (gradientColors != null) {
            if (gradientColors.length == 1) {
                gradientColors = new int[]{gradientColors[0], 0};
            }
        }
    }

    public int getAlpha() {
        return alpha;
    }

    protected int getMColor() {
        return color;
    }

    protected int getMHeight() {
        return height;
    }

    protected int getMWidth() {
        return width;
    }

    protected int getMBottom() {
        return bottom;
    }

    protected int getMRight() {
        return right;
    }

    protected int getMTop() {
        return top;
    }

    protected int getMLeft() {
        return left;
    }

    protected float getMGradientRadius() {
        return gradientRadius;
    }

    protected GradientType getMGradientType() {
        return type;
    }

    protected boolean isMUseLevel() {
        return useLevel;
    }

    protected float getMCenterY() {
        return centerY;
    }

    protected float getMCenterX() {
        return centerX;
    }

    protected GradientDrawable.Orientation getMGradientOrientation() {
        return orientation;
    }

    protected float getMBottomRightRadius() {
        return bottomRightRadius;
    }

    protected float getMBottomLeftRadius() {
        return bottomLeftRadius;
    }

    protected float getMTopRightRadius() {
        return topRightRadius;
    }

    protected float getMTopLeftRadius() {
        return topLeftRadius;
    }

    protected float getMRadius() {
        return radius;
    }

    protected float getMStrokeDashGap() {
        return strokeDashGap;
    }

    protected float getMStrokeDashWidth() {
        return strokeDashWidth;
    }

    protected int getMStrokeWidth() {
        return strokeWidth;
    }

    protected int getMStrokeColor() {
        return strokeColor;
    }

    protected int[] getMGradientColors() {
        return gradientColors;
    }

    protected Shape getMShape() {
        return shape;
    }

    private GradientDrawable gradientDrawable;

    /**
     * 返回创建的Shape Drawable
     *
     * @return Drawable
     */

    public Drawable getDrawable() {
        if (gradientDrawable == null) {
            gradientDrawable = getGradientDrawable();
        }
        return gradientDrawable;
    }

    /**
     * 设置背景
     *
     * @param view 待设置的view
     */

    public void setBackground(View view) {
        if(view != null){
            view.setBackground(getDrawable());
        }
    }


    /**
     * 通过参数创建GradientDrawable
     *
     * @return GradientDrawable
     */

    protected abstract GradientDrawable getGradientDrawable();
    /*
    {
        GradientDrawable gd = new GradientDrawable();

        //设置类型
        gd.setShape(shape.getValue());

        //设置线框
        gd.setStroke(strokeWidth,strokeColor, strokeDashWidth, strokeDashGap);

        //设置四个角的弧度
        if(topLeftRadius<0){
            topLeftRadius = radius;
        }
        if(topRightRadius<0){
            topRightRadius = radius;
        }
        if(bottomRightRadius<0){
            bottomRightRadius = radius;
        }
        if(bottomLeftRadius<0){
            bottomLeftRadius = radius;
        }
        gd.setCornerRadii(new float[]{topLeftRadius,topLeftRadius,
                topRightRadius,topRightRadius,
                bottomRightRadius,bottomRightRadius,
                bottomLeftRadius,bottomLeftRadius});
        //如果为null，或者长度为0就不设置渐变，直接设置填充色
        if(gradientColors!=null&&gradientColors.length>0){
            if(gradientColors.length==1){
                gradientColors = new int[]{gradientColors[0],0};
            }
            //设置渐变色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                gd.setColors(gradientColors);
            }

            gd.setUseLevel(useLevel);
            gd.setGradientType(type.getValue());
            gd.setGradientCenter(centerX,centerY);
            gd.setGradientRadius(gradientRadius);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                gd.setOrientation(orientation);
            }
        }else{
            //设置填充色
            gd.setColor(color);
        }

        //设置宽高
        gd.setSize(width,height);
        gd.setAlpha(alpha);
        return gd;}*/

    public static abstract class Builder<T extends Builder> {
        //矩形，椭圆，线，环形
        private Shape shape = Shape.RECTANGLE;

        //线框
        private int strokeColor = Color.BLACK;//颜色
        private int strokeWidth = INVALID_VALUE;//宽度（虚线的高度）
        private float strokeDashWidth = DEFAULT_VALUE_ZERO;//虚线宽度 （每条虚线的长度）
        private float strokeDashGap = DEFAULT_VALUE_ZERO;//虚线间隔 两条虚线之间的间隔

        //四个角的元弧度
        private float radius = DEFAULT_VALUE_ZERO;//四个角的半径
        private float topLeftRadius = INVALID_VALUE;//左上角半径
        private float topRightRadius = INVALID_VALUE;//右上角半径
        private float bottomLeftRadius = INVALID_VALUE;//左下角半径
        private float bottomRightRadius = INVALID_VALUE;//右下角半径

        //渐变色
        private int[] gradientColors;//渐变色的梯度
        private float centerX = 0.5f;//中间颜色的相对x坐标（0-1）
        private float centerY = 0.5f;//中间颜色的相对y坐标（0-1）
        private boolean useLevel = false;//是否用做LevelListDrawable
        private GradientDrawable.Orientation orientation = GradientDrawable.Orientation.LEFT_RIGHT;// 渐变角度 45度的整数倍
        private GradientType type = GradientType.LINEAR;//渐变模式 默认线性linear, radial（径向渐变） sweep （雷达一样的渐变）
        private float gradientRadius = 0.5f;//

        //内边距
        private int left;//左边距
        private int top;//上边距
        private int right;//右边距
        private int bottom;//下边距

        //宽高
        private int width = INVALID_VALUE;//宽
        private int height = INVALID_VALUE;//高

        //填充色
        private int color = Color.TRANSPARENT;

        //透明度
        private int alpha = 0xFF;

        /**
         * 设置 shape的样式{@link Shape}
         *
         * @param shape 样式
         * @return T
         */
        protected T shape(Shape shape) {
            this.shape = shape;
            return (T) this;
        }

        /**
         * 设置外边框的宽和颜色
         *
         * @param strokeWidth 宽度小于0表示不显示
         * @param strokeColor 颜色
         * @return T
         */
        protected T stroke(int strokeWidth, @ColorInt int strokeColor) {
            this.strokeWidth = strokeWidth;
            this.strokeColor = strokeColor;
            return (T) this;
        }

        /**
         * 设置外边框虚线属性
         *
         * @param strokeDashWidth 虚线长度
         * @param strokeDashGap   虚线间隔
         * @return T
         */
        protected T strokeDash(float strokeDashWidth, float strokeDashGap) {
            if (strokeDashWidth < 0) {
                strokeDashWidth = 0.0f;
            }
            if (strokeDashGap < 0) {
                strokeDashGap = 0.0f;
            }
            this.strokeDashWidth = strokeDashWidth;
            this.strokeDashGap = strokeDashGap;
            return (T) this;
        }

        /**
         * 设置4个角的圆角半径
         *
         * @param radius 半径
         * @return T
         */
        protected T radius(float radius) {
            if (radius < 0) {
                radius = 0.0f;
            }
            this.radius = radius;
            return (T) this;
        }

        /**
         * 左上角的圆角半径
         *
         * @param topLeftRadius 半径
         * @return T
         */
        protected T topLeftRadius(float topLeftRadius) {
            this.topLeftRadius = topLeftRadius;
            return (T) this;
        }

        /**
         * 右上角的圆角半径
         *
         * @param topRightRadius 半径
         * @return T
         */
        protected T topRightRadius(float topRightRadius) {
            this.topRightRadius = topRightRadius;
            return (T) this;
        }

        /**
         * 左下角的圆角半径
         *
         * @param bottomLeftRadius 半径
         * @return T
         */
        protected T bottomLeftRadius(float bottomLeftRadius) {
            this.bottomLeftRadius = bottomLeftRadius;
            return (T) this;
        }

        /**
         * 右下角的圆角半径
         *
         * @param bottomRightRadius 半径
         * @return T
         */
        protected T bottomRightRadius(float bottomRightRadius) {
            this.bottomRightRadius = bottomRightRadius;
            return (T) this;
        }

        /**
         * 设置渐变色数组
         *
         * @param gradientColors 渐变色数组
         * @return T
         */
        protected T gradientColors(@ColorInt int[] gradientColors) {
            this.gradientColors = gradientColors;
            return (T) this;
        }

        /**
         * 渐变色的中心X位置
         *
         * @param centerX 中心X
         * @return T
         */
        protected T gradientCenterX(float centerX) {
            centerX = 0 < centerX ? centerX : 0;
            centerX = centerX < 1 ? centerX : 1;
            this.centerX = centerX;
            return (T) this;
        }

        /**
         * 渐变色的中心Y位置
         *
         * @param centerY 中心Y
         * @return T
         */
        protected T gradientCenterY(float centerY) {
            centerY = 0 < centerY ? centerY : 0;
            centerY = centerY < 1 ? centerY : 1;
            this.centerY = centerY;
            return (T) this;
        }

        /**
         * 设置是否使用LevelListDrawable
         *
         * @param useLevel true，false
         * @return T
         */
        protected T useLevel(boolean useLevel) {
            this.useLevel = useLevel;
            return (T) this;
        }

        /**
         * 设置渐变方向
         *
         * @param orientation 方向
         * @return T
         */
        protected T gradientOrientation(GradientDrawable.Orientation orientation) {
            this.orientation = orientation;
            return (T) this;
        }

        /**
         * 渐变色的类型，线性，径向辐射，扫描式（雷达扫描）
         *
         * @param type {@link GradientType}
         * @return T
         */
        protected T gradientType(GradientType type) {
            if (type == GradientType.RADIAL) {
                gradientRadius(1);
            }
            this.type = type;
            return (T) this;
        }

        /**
         * 渐变半径，只有渐变为径向辐射的时候才有效
         *
         * @param gradientRadius 辐射半径
         * @return T
         */
        protected T gradientRadius(float gradientRadius) {
            this.gradientRadius = gradientRadius;
            return (T) this;
        }

        /**
         * 设置内边距
         *
         * @param left   距离左边部分
         * @param top    距离下部分
         * @param right  距离右边部分
         * @param bottom 距离下边部分
         * @return T
         * @hide 暂时不用
         */
        protected T padding(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            return (T) this;
        }

        /**
         * 设置宽高
         *
         * @param width  宽度 如果不想设置就设置-1
         * @param height 高度 如果不想设置就设置-1
         * @return T
         */
        protected T size(int width, int height) {
            if (width < 0) {
                width = INVALID_VALUE;
            }
            if (height < 0) {
                height = INVALID_VALUE;
            }
            this.width = width;
            this.height = height;
            return (T) this;
        }

        /**
         * 填充色,同时设置填充色和渐变色如果渐变色长度不为0就会用渐变色，填充色失效
         *
         * @param color 填充颜色
         * @return T
         */
        protected T color(@ColorInt int color) {
            this.color = color;
            return (T) this;
        }

        /**
         * 背景透明度
         *
         * @param alpha 透明度 0~255（0x0~0xFF）
         * @return T
         */
        public T alpha(int alpha) {
            alpha = alpha < 0 ? 0 : alpha;
            alpha = alpha > 0xFF ? 0xFF : alpha;
            this.alpha = alpha;
            return (T) this;
        }

        public abstract BaseShape build();
    }
}

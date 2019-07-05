package blq.ssnb.snbview.shape;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;

import java.lang.reflect.Field;

/**
 * ================================================
 * 作者: SSNB
 * 日期: 2016/9/20
 * 描述:
 * 环形Shape
 * 使用这个Shape需要注意的是
 * 1.如果不设置 {@link Builder#useLevelForShape(boolean)} 为false
 * 或者{@link Builder#radian(float)} 不设置 弧度 那么就不会显示圆环
 * 2.如果view的高度小于宽度，那么默认情况下会显示不全
 * ================================================
 */
public final class RingShape extends BaseShape {

    private int innerRadius;
    private float innerRadiusRatio ;
    private boolean useLevelForShape ;
    private float thicknessRatio ;
    private int thickness ;
    private int radian;

    public int getInnerRadius(){
        return innerRadius;
    }

    public float getInnerRadiusRatio(){
        return innerRadiusRatio;
    }

    public boolean isUseLevelForShape(){
        return useLevelForShape;
    }

    public float getThicknessRatio(){
        return thicknessRatio;
    }
    public int getThickness(){
        return thickness;
    }

    public float getRadian(){
        return radian*360.0f/10000;
    }

    public int getHeight(){
        return getMHeight();
    }

    public int getWidth(){
        return getMWidth();
    }

    public float getStrokeDashGap(){
        return getMStrokeDashGap();
    }

    public float getStrokeDashWidth(){
        return getMStrokeDashWidth();
    }

    public int getStrokeColor(){
        return getMStrokeColor();
    }

    public int getStrokeWidth(){
        return getMStrokeWidth();
    }

    public Shape getShape() {
        return getMShape();
    }

    public boolean isUseLevel() {
        return super.isMUseLevel();
    }

    public GradientType getGradientType(){
        return getMGradientType();
    }

    public int[] getGradientColors(){
        return getMGradientColors();
    }

    public GradientDrawable.Orientation getGradientOrientation(){return getMGradientOrientation();}

    public float getGradientCenterX(){
        return getMCenterX();
    }

    public float getGradientCenterY(){
        return getMCenterY();
    }

    public float getGradientRadius(){
        return getMGradientRadius();
    }

    public int getColor(){
        return getMColor();
    }

    private RingShape(Builder builder) {
        super(builder);
        this.innerRadiusRatio = builder.innerRadiusRatio;
        this.innerRadius = builder.innerRadius;
        this.useLevelForShape = builder.useLevelForShape;
        this.thickness = builder.thickness;
        this.thicknessRatio = builder.thicknessRatio;
        this.radian = builder.radian;
    }

    @SuppressWarnings("WrongConstant")
    @Override
    protected GradientDrawable getGradientDrawable() {
        GradientDrawable gd = new GradientDrawable();
        //如果为null，或者长度为0就不设置渐变，直接设置填充色
        if(getMGradientColors()!=null&&getMGradientColors().length>0){
            //设置渐变色
            if(Build.VERSION.SDK_INT> Build.VERSION_CODES.JELLY_BEAN){//如果是16以上的直接用前面实例化的
                gd.setColors(getMGradientColors());
                gd.setOrientation(getMGradientOrientation());
            }else{//否者就重新实例化
                gd = new GradientDrawable(getMGradientOrientation(),getMGradientColors());
            }

            gd.setUseLevel(isMUseLevel());
            gd.setGradientType(getMGradientType().getValue());
            gd.setGradientCenter(getMCenterX(),getMCenterY());
            gd.setGradientRadius(getMGradientRadius());

        }else{
            //设置填充色
            gd.setColor(getMColor());
        }
        reflection(gd);
        gd.setLevel(radian);

        //设置类型
        gd.setShape(getMShape().getValue());
        //设置线框
        gd.setStroke(getMStrokeWidth(), getMStrokeColor(), getMStrokeDashWidth(), getMStrokeDashGap());

        //设置宽高
        gd.setSize(getMWidth(),getMHeight());
        gd.setAlpha(getAlpha());
        return gd;
    }

    /**
     * 反射添加属性
     * @param gd GradientDrawable
     */
    private void reflection(GradientDrawable gd) {
        Field gdGradientState;
        Object mGradientState;
        Class mGradientStateClass;
        //如果获取对象失败的话直接退出
        try{
            //获得GradientDrawable 中mGradientState字段
            gdGradientState=gd.getClass().getDeclaredField("mGradientState");
            gdGradientState.setAccessible(true);
            //实例化GradientState对象
            mGradientState=gdGradientState.get(gd);
            //得到GradientState对象对应类的Class
            mGradientStateClass = Class.forName(mGradientState.getClass().getName());
            //反射出该Class类中的参数方法
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        //如果只是获取和设置属性出错的话就跳过继续设置下一个值
        try {
            Field mUseLevelForShape = mGradientStateClass.getDeclaredField("mUseLevelForShape");
            //取消访问私有方法的合法性检查
            mUseLevelForShape.setAccessible(true);
            //调用设置参数值方法
            mUseLevelForShape.set(mGradientState, useLevelForShape);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            Field mInnerRadius = mGradientStateClass.getDeclaredField("mInnerRadius");
            mInnerRadius.setAccessible(true);
            mInnerRadius.set(mGradientState, innerRadius);
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            Field mInnerRadiusRatio = mGradientStateClass.getDeclaredField("mInnerRadiusRatio");
            mInnerRadiusRatio.setAccessible(true);
            mInnerRadiusRatio.set(mGradientState, innerRadiusRatio);
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {

            Field mThickness = mGradientStateClass.getDeclaredField("mThickness");
            mThickness.setAccessible(true);
            mThickness.set(mGradientState, thickness);
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {

            Field mThicknessRatio = mGradientStateClass.getDeclaredField("mThicknessRatio");
            mThicknessRatio.setAccessible(true);
            mThicknessRatio.set(mGradientState, thicknessRatio);
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static final class Builder extends BaseShape.Builder<Builder>{

        //内半径
        private int innerRadius=INVALID_VALUE;
        //内半径比例
        private float innerRadiusRatio = 3.0f;
        //圆环厚度
        private int thickness = INVALID_VALUE;
        //圆环厚度比例
        private float thicknessRatio = 9.0f;
        //是否是List
        private boolean useLevelForShape = true;

        private int radian=0;
        public Builder(){
            shape(Shape.RING);
        }

        /**
         *  设置环形的度数，默认是0，如果设置{@link #useLevelForShape(boolean)}为false的话
         *  那么会显示360，不会显示设置的度数
         * @param radian 环形的度数（0~360）
         * @return Builder
         */
        public Builder radian(float radian) {
            radian = radian<0?0:(radian<360?radian:360);
            this.radian = (int)(radian*10000/360);
            return this;
        }

        //渐变色
        @Override
        public Builder gradientOrientation(GradientDrawable.Orientation orientation) {
            return super.gradientOrientation(orientation);
        }

        @Override
        public Builder gradientType(GradientType type) {
            return super.gradientType(type);
        }

        @Override
        public Builder gradientColors(@ColorInt int[] gradientColors) {
            return super.gradientColors(gradientColors);
        }

        @Override
        public Builder gradientRadius(float gradientRadius) {
            return super.gradientRadius(gradientRadius);
        }

        @Override
        public Builder gradientCenterX(float centerX) {
            return super.gradientCenterX(centerX);
        }

        @Override
        public Builder gradientCenterY(float centerY) {
            return super.gradientCenterY(centerY);
        }

        @Override
        public Builder useLevel(boolean useLevel) {
            return super.useLevel(useLevel);
        }
        //渐变色结束--------

        @Override
        public Builder stroke(int strokeWidth, @ColorInt int strokeColor) {
            return super.stroke(strokeWidth, strokeColor);
        }

        @Override
        public Builder strokeDash(float strokeDashWidth, float strokeDashGap) {
            return super.strokeDash(strokeDashWidth, strokeDashGap);
        }

        @Override
        public Builder size(int width, int height) {
            return super.size(width, height);
        }

        @Override
        public Builder color(@ColorInt int color) {
            return super.color(color);
        }

        /**
         * Ring 作为LevelListDrawable时为true
         * 否者一般设置为false，单独使用的话设置为false否者需要设置{@link #radian(float)}
         * 不然不会显示
         * 默认是true
         * @param useLevelForShape 作为LevelListDrawable时为true
         * @return Builder
         */
        public Builder useLevelForShape(boolean useLevelForShape) {
            this.useLevelForShape = useLevelForShape;
            return this;
        }

        /**
         * 环形的厚度比例，环的厚度=环的宽度/thicknessRatio
         * @param thicknessRatio 厚度比例
         * @return Builder
         */
        public Builder thicknessRatio(float thicknessRatio) {
            this.thicknessRatio = thicknessRatio;
            return this;
        }

        /**
         * 环形的厚度如果设置了那么{@link #thicknessRatio(float)}设置就会失效
         * @param thickness 厚度比例
         * @return Builder
         */
        public Builder thickness(int thickness) {
            this.thickness = thickness;
            return this;
        }

        /**
         * 圆环内部半径比例,内环半径=环的宽度/innerRadiusRatio
         * @param innerRadiusRatio 内环半径比例
         * @return Builder
         */
        public Builder innerRadiusRatio(float innerRadiusRatio) {
            this.innerRadiusRatio = innerRadiusRatio;
            return this;
        }

        /**
         * 圆环的内半径如果设置那么{@link #innerRadiusRatio(float)}就会失效
         * @param innerRadius 圆环内部半径
         * @return Builder
         */
        public Builder innerRadius(int innerRadius) {
            this.innerRadius = innerRadius;
            return this;
        }


        @Override
        public RingShape build() {
            return new RingShape(this);
        }
    }

}

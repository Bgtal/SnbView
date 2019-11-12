package blq.ssnb.snbview.shape;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import androidx.annotation.ColorInt;

/**
 * ================================================
 * 作者: SSNB
 * 日期: 2016/9/20
 * 描述:
 * 椭圆Shape
 * 要想设置为圆形就将view的宽高设置一样高
 * ================================================
 */
public final class OvalShape extends BaseShape {
    private OvalShape(Builder builder) {
        super(builder);
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

    public int[] getGradientColors(){
        return getMGradientColors();
    }

    public boolean isUseLevel() {
        return super.isMUseLevel();
    }

    public GradientType getGradientType(){
        return getMGradientType();
    }

    public GradientDrawable.Orientation getGradientOrientation(){
        return getMGradientOrientation();
    }

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
        //设置类型
        gd.setShape(getMShape().getValue());
        //设置线框
        gd.setStroke(getMStrokeWidth(), getMStrokeColor(), getMStrokeDashWidth(), getMStrokeDashGap());

        //设置宽高
        gd.setSize(getMWidth(),getMHeight());
        gd.setAlpha(getAlpha());
        return gd;
    }

    public static final class Builder extends BaseShape.Builder<Builder> {

        public Builder(){
            shape(Shape.OVAL);
        }

        //渐变色相关-----------

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

        @Override
        public OvalShape build() {
            return new OvalShape(this);
        }
    }
}

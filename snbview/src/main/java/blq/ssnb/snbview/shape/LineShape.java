package blq.ssnb.snbview.shape;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;

/**
 * ================================================
 * 作者: SSNB
 * 日期: 2016/9/20
 * 描述:
 * 线形的Shape
 * 如果要让虚线显示的话必须在
 * AndroidManifest.xml中 添加
 * &lt;application
 *      android:hardwareAccelerated="false"
 *      ...
 *      &gt;
 *      ...
 * &lt;/application&gt;
 * 或者在要显示的view中添加
 * view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
 *（子view也会显示）
 * ================================================
 */
public final class LineShape extends BaseShape {


    private LineShape(BaseShape.Builder builder) {
        super(builder);
    }

    public int getStrokeWidth(){
        return getMStrokeWidth();
    }

    public int getStrokeColor(){
        return getMStrokeColor();
    }

    public float getStrokeDashWidth(){
        return getMStrokeDashWidth();
    }

    public float getStrokeDashGap(){
        return getMStrokeDashGap();
    }

    @SuppressWarnings("WrongConstant")
    @Override
    protected GradientDrawable getGradientDrawable() {

        GradientDrawable gd = new GradientDrawable();
        //设置类型
        gd.setShape(getMShape().getValue());

        //设置线框
        gd.setStroke(getMStrokeWidth(), getMStrokeColor(), getMStrokeDashWidth(), getMStrokeDashGap());
        return gd;
    }

    public static final class Builder extends BaseShape.Builder<Builder>{

        public Builder(){
            shape(Shape.LINE);
        }

        @Override
        public Builder stroke(int strokeWidth, @ColorInt int strokeColor) {
            return super.stroke(strokeWidth, strokeColor);
        }

        /**
         * 如果要让虚线显示的话必须在
         * AndroidManifest.xml中 添加
         * &#60;application
         *      android:hardwareAccelerated="false"
         *      ...
         *      &#62;
         *      ...
         * &#60;/application&#62;
         * 或者在要显示的view中添加
         * view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
         *（子view也会显示）
         * @param strokeDashWidth 虚线长度
         * @param strokeDashGap 虚线间隔
         * @return Builder
         */
        @Override
        public Builder strokeDash(float strokeDashWidth, float strokeDashGap) {
            return super.strokeDash(strokeDashWidth, strokeDashGap);
        }

        @Override
        public BaseShape build() {
            return new LineShape(this);
        }
    }
}

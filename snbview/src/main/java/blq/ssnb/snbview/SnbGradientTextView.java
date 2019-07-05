package blq.ssnb.snbview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2016/8/29
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 横向渐变色TextView
 * 默认从红--绿--蓝
 * ================================================
 * </pre>
 */


public class SnbGradientTextView extends AppCompatTextView {
    private static final String TAG = "GradientTextView";

    private LinearGradient mGradient;
    private int[] mGradientColors;
    private boolean isGradientChanged = false;

    public SnbGradientTextView(Context context) {
        this(context, null);
    }

    public SnbGradientTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SnbGradientTextViewStyle);
    }

    public SnbGradientTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
        init();
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.SnbGradientTextView,
                defStyle, R.style.DefSnbGradientTextViewStyle);

        int arrayID = ta.getResourceId(R.styleable.SnbGradientTextView_snb_color, R.array.snb_gradient_text_color_def);

        try {
            int[] colorArray = getResources().getIntArray(arrayID);
            setGradientColors(colorArray);
        } catch (Exception e) {
            setGradientColors(new int[]{arrayID});
        }

        ta.recycle();
    }


    private void init() {
        setWillNotDraw(false);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = getPaint();
        setLayerType(LAYER_TYPE_NONE, paint);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        if (mGradient == null || isGradientChanged) {
//            paint.measureText()
            mGradient = new LinearGradient(0, 0, getWidth(), getMeasuredHeight(),
                    mGradientColors, null, Shader.TileMode.REPEAT);
            isGradientChanged = false;
        }
        paint.setShader(mGradient);
        super.onDraw(canvas);
    }

    /**
     * 设置从左到右渐变的颜色
     *
     * @param mGradientColors 颜色数组
     *                        显示颜色按数组顺序从左到右渐变
     */
    public void setGradientColors(int[] mGradientColors) {
        isGradientChanged = true;
        this.mGradientColors = mGradientColors;
        invalidate();
    }

    public int[] getGradientColors() {
        return mGradientColors;
    }
}



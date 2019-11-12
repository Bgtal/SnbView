package blq.ssnb.snbview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019-07-04
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 一个分割线布局
 *
 * ================================================
 * </pre>
 */
public class SnbLineView extends View {

    private int mOrientation = 0;

    public SnbLineView(Context context) {
        this(context, null);
    }

    public SnbLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.SnbLineViewStyle);
    }

    public SnbLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.DefSnbLineViewStyle);
    }

    public SnbLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SnbLineView, defStyleAttr, defStyleRes);
        Drawable drawable = typedArray.getDrawable(R.styleable.SnbLineView_snb_line_color);
        int orientation = typedArray.getInt(R.styleable.SnbLineView_snb_orientation, -1);
        typedArray.recycle();

        setBackground(drawable);
        setOrientation(orientation == LinearLayout.HORIZONTAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpechMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpechMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthSpechMode == MeasureSpec.UNSPECIFIED || widthSpechMode == MeasureSpec.AT_MOST) {
            if (mOrientation == LinearLayout.VERTICAL) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(1, MeasureSpec.EXACTLY);
            }
        }

        if (heightSpechMode == MeasureSpec.UNSPECIFIED || heightSpechMode == MeasureSpec.AT_MOST) {
            if (mOrientation == LinearLayout.HORIZONTAL) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(1, MeasureSpec.EXACTLY);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public void setOrientation(boolean isHorizontal) {
        int orientation = LinearLayout.HORIZONTAL;
        if (!isHorizontal) {
            orientation = LinearLayout.VERTICAL;
        }

        if (mOrientation != orientation) {
            mOrientation = orientation;
            requestLayout();
        }
    }

    public boolean isHorizontal() {
        return mOrientation == LinearLayout.HORIZONTAL;
    }
}

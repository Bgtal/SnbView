package blq.ssnb.snbview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import blq.ssnb.snbutil.SnbDisplayUtil;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019-07-04
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public class SnbDrawableTextView extends AppCompatTextView {

    private CompoundDrawableCache mLeftDrawableCompound;
    private CompoundDrawableCache mRightDrawableCompound;
    private CompoundDrawableCache mTopDrawableCompound;
    private CompoundDrawableCache mBottomDrawableCompound;

    public SnbDrawableTextView(Context context) {
        this(context, null);
    }

    public SnbDrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SnbDrawableTextViewStyle);
    }

    public SnbDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SnbDrawableTextView, defStyleAttr, R.style.DefSnbDrawableTextViewStyle);
        getLeftDrawableCompound().mWidth = typedArray.getDimensionPixelSize(R.styleable.SnbDrawableTextView_snb_left_width, -1);
        getLeftDrawableCompound().mHeight = typedArray.getDimensionPixelSize(R.styleable.SnbDrawableTextView_snb_left_height, -1);
        getTopDrawableCompound().mWidth = typedArray.getDimensionPixelSize(R.styleable.SnbDrawableTextView_snb_top_width, -1);
        getTopDrawableCompound().mHeight = typedArray.getDimensionPixelSize(R.styleable.SnbDrawableTextView_snb_top_height, -1);
        getRightDrawableCompound().mWidth = typedArray.getDimensionPixelSize(R.styleable.SnbDrawableTextView_snb_right_width, -1);
        getRightDrawableCompound().mHeight = typedArray.getDimensionPixelSize(R.styleable.SnbDrawableTextView_snb_right_height, -1);
        getBottomDrawableCompound().mWidth = typedArray.getDimensionPixelSize(R.styleable.SnbDrawableTextView_snb_bottom_width, -1);
        getBottomDrawableCompound().mHeight = typedArray.getDimensionPixelSize(R.styleable.SnbDrawableTextView_snb_bottom_height, -1);
        typedArray.recycle();
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        updateCompoundDrawable(getLeftDrawableCompound(), left);
        updateCompoundDrawable(getTopDrawableCompound(), top);
        updateCompoundDrawable(getRightDrawableCompound(), right);
        updateCompoundDrawable(getBottomDrawableCompound(), bottom);

        if (left != null) {
            calculateAndSetDrawableSize(left, Gravity.LEFT, getLeftDrawableCompound());
        }
        if (top != null) {
            calculateAndSetDrawableSize(top, Gravity.TOP, getTopDrawableCompound());
        }
        if (right != null) {
            calculateAndSetDrawableSize(right, Gravity.RIGHT, getRightDrawableCompound());
        }
        if (bottom != null) {
            calculateAndSetDrawableSize(bottom, Gravity.BOTTOM, getBottomDrawableCompound());
        }

        super.setCompoundDrawables(left, top, right, bottom);
    }

    public void setDrawableSize(int gravity, int widthDp, int heightDp) {
        CompoundDrawableCache setCompound = null;
        switch (gravity) {
            case Gravity.LEFT:
                setCompound = mLeftDrawableCompound;
                break;
            case Gravity.TOP:
                setCompound = mTopDrawableCompound;
                break;
            case Gravity.RIGHT:
                setCompound = mRightDrawableCompound;
                break;
            case Gravity.BOTTOM:
                setCompound = mBottomDrawableCompound;
                break;
        }

        if (setCompound != null) {
            setCompound.mWidth = SnbDisplayUtil.dp2Px(getContext(), widthDp);
            setCompound.mHeight = SnbDisplayUtil.dp2Px(getContext(), heightDp);
            setCompoundDrawables(getLeftDrawableCompound().mDrawable,
                    getTopDrawableCompound().mDrawable,
                    getRightDrawableCompound().mDrawable,
                    getBottomDrawableCompound().mDrawable);
        }
    }

    private void updateCompoundDrawable(CompoundDrawableCache Compound, Drawable drawable) {
        if (Compound.mDrawable != drawable) {
            Compound.mDrawable = drawable;
        }
    }

    private void calculateAndSetDrawableSize(Drawable drawable, int gravity, CompoundDrawableCache compound) {

        if (drawable != null && compound != null) {
            int width = compound.mWidth > 0 ? compound.mWidth : drawable.getIntrinsicWidth();
            int height = compound.mHeight > 0 ? compound.mHeight : drawable.getIntrinsicHeight();
            drawable.setBounds(0, 0, width, height);
        }
    }

    private CompoundDrawableCache getLeftDrawableCompound() {
        if (mLeftDrawableCompound == null) {
            mLeftDrawableCompound = new CompoundDrawableCache();
        }
        return mLeftDrawableCompound;
    }

    private CompoundDrawableCache getTopDrawableCompound() {
        if (mTopDrawableCompound == null) {
            mTopDrawableCompound = new CompoundDrawableCache();
        }
        return mTopDrawableCompound;
    }

    private CompoundDrawableCache getRightDrawableCompound() {
        if (mRightDrawableCompound == null) {
            mRightDrawableCompound = new CompoundDrawableCache();
        }
        return mRightDrawableCompound;
    }

    private CompoundDrawableCache getBottomDrawableCompound() {
        if (mBottomDrawableCompound == null) {
            mBottomDrawableCompound = new CompoundDrawableCache();
        }
        return mBottomDrawableCompound;
    }


    private static class CompoundDrawableCache {
        int mWidth = -1;
        int mHeight = -1;

        Drawable mDrawable;
    }
}

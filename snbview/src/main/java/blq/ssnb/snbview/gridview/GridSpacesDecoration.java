package blq.ssnb.snbview.gridview;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2020-04-02
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
class GridSpacesDecoration extends RecyclerView.ItemDecoration {

    private Rect mRect;

    public GridSpacesDecoration(int space) {
        space = space / 2;
        mRect = new Rect(space, space, space, space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = mRect.left;
        outRect.right = mRect.right;
        outRect.top = mRect.top;
        outRect.bottom = mRect.bottom;
    }

    public void setSpace(int space) {
        space = space / 2;
        mRect.set(space, space, space, space);
    }
}

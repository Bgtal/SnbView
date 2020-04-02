package blq.ssnb.snbview.gridview;

import android.net.Uri;
import android.widget.ImageView;

import blq.ssnb.snbview.R;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2020-03-24
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public class SnbGridViewOption {
    //最大数量
    private int mMaxSize = 9;
    //列数
    private int mColumn = 3;
    //是否只是显示，false 会出现加减按钮
    private boolean isJustShow = false;
    //是否可以拖拽
    private boolean canDrag = true;
    //删除图片
    private int delBtnID = R.drawable.snb_ic_clear_black_24dp;
    //添加图片
    private int addImgID = R.drawable.snb_ic_grid_add_btn;
    //动作回调
    private SnbGridView.ActionListener mActionListener;
    //图片加载器
    private ImageLoader mImageLoader;
    //图片之间的边距
    private int spaces;

    public int getMaxSize() {
        return mMaxSize;
    }

    public void setMaxSize(int maxSize) {
        mMaxSize = maxSize;
    }

    public int getColumn() {
        return mColumn;
    }

    public void setColumn(int column) {
        mColumn = column;
    }

    public boolean isJustShow() {
        return isJustShow;
    }

    public void setJustShow(boolean justShow) {
        isJustShow = justShow;
    }

    public boolean isCanDrag() {
        return canDrag;
    }

    public void setCanDrag(boolean canDrag) {
        this.canDrag = canDrag;
    }

    public SnbGridView.ActionListener getActionListener() {
        return mActionListener;
    }

    public void setActionListener(SnbGridView.ActionListener actionListener) {
        mActionListener = actionListener;
    }

    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = (uri, imgPic) -> {
                imgPic.setImageURI(Uri.parse(uri));
            };
        }
        return mImageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    public int getDelBtnID() {
        return delBtnID;
    }

    public void setDelBtnID(int delBtnID) {
        this.delBtnID = delBtnID;
    }

    public int getAddImgID() {
        return addImgID;
    }

    public void setAddImgID(int addImgID) {
        this.addImgID = addImgID;
    }

    public int getSpaces() {
        return spaces;
    }

    public void setSpaces(int spaces) {
        this.spaces = spaces;
    }
}

package blq.ssnb.snbview.gridview;

import android.net.Uri;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
    /**
     * 纯展示
     */
    public static final int PICTURE_MODEL_SHOW = 0;
    /**
     * 从现有图片挑选
     */
    public static final int PICTURE_MODEL_SELECT = 1;
    /**
     * 从图片库选着
     */
    public static final int PICTURE_MODEL_CHOOSE = 2;


    @IntDef({PICTURE_MODEL_SHOW, PICTURE_MODEL_SELECT, PICTURE_MODEL_CHOOSE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GridModel {
    }

    //展示模式
    private int mGridModel = PICTURE_MODEL_SHOW;
    //最大数量
    private int mMaxSize = 9;
    //列数
    private int mColumn = 3;
    //图片显示比例
    private String mRatio = "1:1";

    //是否可以拖拽
    private boolean canDrag = true;
    //图片加载器
    private ImageLoader mImageLoader;
    //图片之间的边距
    private int spaces;
    //动作回调
    private SnbGridView.ActionListener mActionListener;

    // <editor-fold defaultstate="collapsed" desc="图片选择模式">
    //删除图片
    private int deleteIcon = R.drawable.snb_ic_clear_black_24dp;
    //添加图片
    private int addBtnIcon = R.drawable.snb_ic_grid_add_btn;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="图片挑选模式">
    private int mSelectedIcon = R.drawable.snb_icon_selected;
    private int mUnselectedIcon = R.drawable.snb_icon_unselected;
    // </editor-fold>


    public int getSelectedIcon() {
        return mSelectedIcon;
    }

    public void setSelectedIcon(int selectedIcon) {
        this.mSelectedIcon = selectedIcon;
    }

    public int getUnselectedIcon() {
        return mUnselectedIcon;
    }

    public void setUnselectedIcon(int unselectedIcon) {
        this.mUnselectedIcon = unselectedIcon;
    }

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
        return mGridModel == PICTURE_MODEL_SHOW || mGridModel == PICTURE_MODEL_SELECT;
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

    public int getDeleteIcon() {
        return deleteIcon;
    }

    public void setDeleteIcon(int deleteIcon) {
        this.deleteIcon = deleteIcon;
    }

    public int getAddBtnIcon() {
        return addBtnIcon;
    }

    public void setAddBtnIcon(int addBtnIcon) {
        this.addBtnIcon = addBtnIcon;
    }

    public int getSpaces() {
        return spaces;
    }

    public void setSpaces(int spaces) {
        this.spaces = spaces;
    }

    public int getGridModel() {
        return mGridModel;
    }

    public void setGridModel(@GridModel int model) {
        mGridModel = model;
    }

    public String getRatio() {
        return mRatio;
    }

    public void setRatio(String ratio) {
        mRatio = ratio;
    }

}

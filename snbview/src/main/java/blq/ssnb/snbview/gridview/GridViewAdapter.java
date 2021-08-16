package blq.ssnb.snbview.gridview;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import blq.ssnb.snbutil.SnbLog;
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
class GridViewAdapter<Bean extends IGridItemBean> extends RecyclerView.Adapter<GridViewAdapter.MViewHolder> {

    private SnbGridViewOption mOption;
    private List<Bean> mGridItemBeans;
    private IGridItemBean addBtnBean;

    public GridViewAdapter(Context context, SnbGridViewOption option) {
        if (option == null) {
            throw new NullPointerException("传入的 SnbGridViewOption 对线为空");
        }
        this.mOption = option;
        addBtnBean = new IGridItemBean() {

            @Override
            public int getFlag() {
                return IGridItemBean.FLAG_IMG_BTN;
            }

            @Override
            public String getUrl() {
                return "android.resource://" + context.getPackageName() + "/" + mOption.getAddBtnIcon();
            }

            @Override
            public boolean isSelect() {
                return false;
            }

            @Override
            public void setSelect(boolean isSelect) {

            }

            @NonNull
            @Override
            public String toString() {
                return getUrl();
            }
        };
        mGridItemBeans = new ArrayList<>();
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.snb_item_grid_view, viewGroup, false);
        MViewHolder mViewHolder = new MViewHolder(view);
        mViewHolder.setActionBtn(mOption.getDeleteIcon());
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MViewHolder mViewHolder, int position) {
        IGridItemBean bean = getBean(position);
        Log.e(">>>>", position + ":" + bean.toString());
        //设置图标
        initAction(mViewHolder, bean);
        //设置ation 监听
        mViewHolder.actionBtn.setOnClickListener(v -> {
            doAction(mViewHolder, bean);
        });

        //判断下是否为特殊图标
        if (bean.getFlag() == IGridItemBean.FLAG_IMG_BTN) {
            //如果是特殊图标
            mViewHolder.actionBtn.setVisibility(View.GONE);
            mViewHolder.picImageView.setOnClickListener(v -> {
                if (mOption.getActionListener() != null) {
                    mOption.getActionListener().onAddBtnClick(v);
                }
            });
            mViewHolder.picImageView.setImageURI(Uri.parse(bean.getUrl()));
        } else {
//            if(mOption.getGridModel() == SnbGridViewOption.PICTURE_MODEL_SHOW){
//
//            }else{
//                mViewHolder.actionBtn.setVisibility(View.VISIBLE);
//            }
            mViewHolder.picImageView.setOnClickListener(v -> {
                if (mOption.getActionListener() != null) {
                    mOption.getActionListener().onItemClick(v, mViewHolder.getAdapterPosition());
                }
            });
            if (mOption.getImageLoader() != null) {
                //加载图片
                mOption.getImageLoader().display(bean.getUrl(), mViewHolder.picImageView);
            }
        }
        mViewHolder.setRation(mOption.getRatio());
    }

    private void initAction(MViewHolder viewHolder, IGridItemBean bean) {
        switch (mOption.getGridModel()) {
            case SnbGridViewOption.PICTURE_MODEL_SHOW:
                viewHolder.actionBtn.setVisibility(View.GONE);
                break;
            case SnbGridViewOption.PICTURE_MODEL_SELECT:
                viewHolder.actionBtn.setVisibility(View.VISIBLE);
                if (bean.isSelect()) {
                    viewHolder.setActionBtn(mOption.getSelectedIcon());
                } else {
                    viewHolder.setActionBtn(mOption.getUnselectedIcon());
                }
                break;
            case SnbGridViewOption.PICTURE_MODEL_CHOOSE:
                viewHolder.actionBtn.setVisibility(View.VISIBLE);
                viewHolder.setActionBtn(mOption.getDeleteIcon());
                break;

        }
    }

    private void doAction(MViewHolder mViewHolder, IGridItemBean bean) {
        switch (mOption.getGridModel()) {
            case SnbGridViewOption.PICTURE_MODEL_SHOW:
                //什么都不干
                break;
            case SnbGridViewOption.PICTURE_MODEL_SELECT:
                bean.setSelect(!bean.isSelect());
                notifyItemChanged(mViewHolder.getAdapterPosition());
                break;
            case SnbGridViewOption.PICTURE_MODEL_CHOOSE:
                removeItem(mViewHolder.getAdapterPosition());
                break;

        }
    }

    @Override
    public int getItemCount() {
        int size;
        switch (mOption.getGridModel()) {
            case SnbGridViewOption.PICTURE_MODEL_CHOOSE:
                if (mGridItemBeans.size() < mOption.getMaxSize()) {
                    size = mGridItemBeans.size() + 1;
                } else {
                    size = mOption.getMaxSize();
                }
                break;
            case SnbGridViewOption.PICTURE_MODEL_SHOW:
            case SnbGridViewOption.PICTURE_MODEL_SELECT:
            default:
                size = Math.min(mGridItemBeans.size(), mOption.getMaxSize());
        }
        return size;
    }

    /**
     * 获取add的坐标
     *
     * @return
     */
    public int getAddIndex() {
        int index = -1;
        switch (mOption.getGridModel()) {
            case SnbGridViewOption.PICTURE_MODEL_CHOOSE:
                if (!isFull()) {
                    index = getItemCount() - 1;
                }
                break;
            case SnbGridViewOption.PICTURE_MODEL_SELECT:
            case SnbGridViewOption.PICTURE_MODEL_SHOW:
            default:

        }
        return index;
    }

    private IGridItemBean getBean(int index) {
        if (index < mGridItemBeans.size()) {
            return mGridItemBeans.get(index);
        } else {
            return addBtnBean;
        }
    }

    public List<Bean> getGridItemBeans() {
        return mGridItemBeans;
    }

    private boolean isFull() {
        if (mOption.getMaxSize() == mGridItemBeans.size()) {
            return true;
        }
        if (mOption.getMaxSize() < mGridItemBeans.size()) {
            mGridItemBeans.subList(mOption.getMaxSize(), mGridItemBeans.size()).clear();
//            for (int i = mGridItemBeans.size() - 1; i >= mOption.getMaxSize(); i--) {
//                mGridItemBeans.remove(i);//方法同上，不过没验证过，到时候验证下
//            }
            return true;
        }
        return false;
    }

    public void clear() {
        mGridItemBeans.clear();
        notifyDataSetChanged();
    }


    public void addItem(Bean bean) {
        if (isFull()) {//如果超出了就什么都不做
            return;
        }
        //否则就加入
        mGridItemBeans.add(bean);
        if (mGridItemBeans.size() < mOption.getMaxSize()) {
            notifyItemInserted(mGridItemBeans.size() - 1);
        } else {
            notifyDataSetChanged();
        }
    }


    public void addItems(List<Bean> beans) {
        if (beans == null || isFull()) {
            //为空或者满了就不加了
            return;
        }

        //没满
        //当前数量
        int size = mGridItemBeans.size();
        //添加的数量
        int newSize = beans.size();
        //最终数量
        int sumSize = size + newSize;
        //如果原有数据+现有数据大于最大的值 ，那么只需要添加其中一部分
        if (sumSize > mOption.getMaxSize()) {
            List<Bean> normalList = new ArrayList<>();
            for (int i = 0; i < mOption.getMaxSize() - size; i++) {
                normalList.add(beans.get(i));
            }
            beans = normalList;
        }
        mGridItemBeans.addAll(beans);
        if (sumSize < mOption.getMaxSize()) {
            notifyItemRangeInserted(size, beans.size());
        } else {
            notifyDataSetChanged();
        }
    }

    public void removeItem(int index) {
        mGridItemBeans.remove(index);//移除
        notifyItemRemoved(index);
        switch (mOption.getGridModel()) {
            case SnbGridViewOption.PICTURE_MODEL_CHOOSE:
                //有加图选项
                if (getItemCount() == mOption.getMaxSize()) { //如果移除后获得个数任然为9 ，那么就刷新所有布局
//                    notifyItemRemoved(index);//移除后
                    notifyItemChanged(mOption.getMaxSize() - 1);//要更新最后的加号
//                } else {
//                    notifyItemRemoved(index);//否者就刷新移除内容
                }
                break;
            case SnbGridViewOption.PICTURE_MODEL_SELECT:
            case SnbGridViewOption.PICTURE_MODEL_SHOW:
            default:
//                notifyItemRemoved(index);
        }
    }

    public void replace(List<Bean> beans) {
        if (beans == null) {
            mGridItemBeans.clear();
            notifyDataSetChanged();
            return;
        }
        if (this.mGridItemBeans != beans) {
            mGridItemBeans.clear();
            mGridItemBeans.addAll(beans);
            isFull();
            notifyDataSetChanged();
        }
    }

    public void maxSizeChange() {
        isFull();
        notifyDataSetChanged();
    }

    public static class MViewHolder extends RecyclerView.ViewHolder {

        private ImageView picImageView;
        private ImageView actionBtn;

        private int actionBtnDrawable;

        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            picImageView = itemView.findViewById(R.id.img_pic_view);
            actionBtn = itemView.findViewById(R.id.img_pic_close_btn);
        }

        public void setActionBtn(@DrawableRes int resID) {
            if (resID != actionBtnDrawable) {
                actionBtnDrawable = resID;
                actionBtn.setImageResource(resID);
            }
        }

        public void setRation(String ratio) {

            if (picImageView.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) picImageView.getLayoutParams();
                if (!params.dimensionRatio.equals(ratio)) {
                    SnbLog.e(">>>>>>:刷新比例：" + ratio);
                    params.dimensionRatio = ratio;
                    picImageView.setLayoutParams(params);
                }
            }
        }
    }
}

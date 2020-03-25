package blq.ssnb.snbview.gridview;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
        this.mOption = option == null ? new SnbGridViewOption() : option;
        addBtnBean = new IGridItemBean() {

            @Override
            public int getFlag() {
                return IGridItemBean.FLAG_IMG_BTN;
            }

            @Override
            public String getUrl() {
                return "android.resource://" + context.getPackageName() + "/" + mOption.getAddImgID();
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
        mViewHolder.setDeleteBtn(mOption.getDelBtnID());
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MViewHolder mViewHolder, int position) {
        IGridItemBean bean = getBean(position);
        Log.e(">>>>", position + ":" + bean.toString());
        mViewHolder.deleteBtn.setOnClickListener(v -> removeItem(mViewHolder.getAdapterPosition()));
        mViewHolder.updateDeleteView(mOption.getDelBtnID());

        if (bean.getFlag() == IGridItemBean.FLAG_IMG_BTN) {
            mViewHolder.deleteBtn.setVisibility(View.GONE);
            mViewHolder.picImageView.setOnClickListener(v -> {
                if (mOption.getActionListener() != null) {
                    mOption.getActionListener().onAddBtnClick(v);
                }
            });
            mViewHolder.picImageView.setImageURI(Uri.parse(bean.getUrl()));
        } else {
            mViewHolder.deleteBtn.setVisibility(View.VISIBLE);
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
        if (mOption.isJustShow()) {
            mViewHolder.deleteBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        //如果获得 8个大小， 小于9个
        if (mOption.isJustShow()) {
            return mGridItemBeans.size();
        } else {
            if (mGridItemBeans.size() < mOption.getMaxSize()) {
                return mGridItemBeans.size() + 1;
            } else {
                return mOption.getMaxSize();
            }
        }
    }

    /**
     * 获取add的坐标
     *
     * @return
     */
    public int getAddIndex() {
        if (mOption.isJustShow()) {
            return -1;
        } else {
            if (isFull()) {
                return -1;
            }
            return getItemCount() - 1;
        }
    }

    /**
     * 获取实际数据个数
     *
     * @return
     */
    public int getDataCount() {
        return mGridItemBeans.size();
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
            if (mGridItemBeans.size() > mOption.getMaxSize()) {
                mGridItemBeans.subList(mOption.getMaxSize(), mGridItemBeans.size()).clear();
            }
//            for (int i = mGridItemBeans.size() - 1; i >= mOption.getMaxSize(); i--) {
//                mGridItemBeans.remove(i);//方法同上，不过没验证过，到时候验证下
//            }
            notifyDataSetChanged();
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
        if (mOption.isJustShow()) {//如果只是显示就移除就够了
            notifyItemRemoved(index);
        } else {
            //有加图选项
            if (getItemCount() == mOption.getMaxSize()) { //如果移除后获得个数任然为9 ，那么就刷新所有布局
                notifyItemRemoved(index);//移除后
                notifyItemChanged(mOption.getMaxSize() - 1);//要更新最后的加号
            } else {
                notifyItemRemoved(index);//否者就刷新移除内容
            }
        }
    }


    public static class MViewHolder extends RecyclerView.ViewHolder {

        private ImageView picImageView;
        private ImageView deleteBtn;

        private int deleteBtnID;

        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            picImageView = itemView.findViewById(R.id.img_pic_view);
            deleteBtn = itemView.findViewById(R.id.img_pic_close_btn);
        }

        public void setDeleteBtn(@DrawableRes int resID) {
            deleteBtnID = resID;
            deleteBtn.setImageResource(resID);
        }

        public void updateDeleteView(int resID) {
            if (deleteBtnID != resID) {
                setDeleteBtn(resID);
            }
        }
    }
}

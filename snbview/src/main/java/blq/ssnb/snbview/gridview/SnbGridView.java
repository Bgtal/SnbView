package blq.ssnb.snbview.gridview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import blq.ssnb.snbview.R;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019-05-18
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      用RecyclerView 写的网格图片展示器
 *      .设置最大显示数量
 *      .设置列数
 *      .设置是否显示加减按钮
 *      .设置是否可以拖拽
 * ================================================
 * </pre>
 */
public class SnbGridView<Bean extends IGridItemBean> extends RecyclerView {

    /**
     * 用于存所有的参数
     */
    private SnbGridViewOption mOption;

    private GridViewAdapter<Bean> mGridAdapter;
    private GridLayoutManager mGridLayoutManager;
    private ItemTouchHelper mItemTouchHelper;
    private OnItemTouchListener mItemTouchListener;

    public SnbGridView(@NonNull Context context) {
        this(context, null);
    }

    public SnbGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnbGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttr(context, attrs, defStyle);
        initData(context);
        bindData(context);
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyle) {
        mOption = new SnbGridViewOption();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SnbGridView, defStyle, R.style.DefSnbGridViewStyle);
        mOption.setMaxSize(typedArray.getInt(R.styleable.SnbGridView_snb_max, mOption.getMaxSize()));
        mOption.setColumn(typedArray.getInt(R.styleable.SnbGridView_snb_column, mOption.getColumn()));
        mOption.setCanDrag(typedArray.getBoolean(R.styleable.SnbGridView_snb_can_drag, mOption.isCanDrag()));
        mOption.setJustShow(typedArray.getBoolean(R.styleable.SnbGridView_snb_show_only, mOption.isJustShow()));
        mOption.setDelBtnID(typedArray.getResourceId(R.styleable.SnbGridView_snb_close_draw_id, mOption.getDelBtnID()));
        mOption.setAddImgID(typedArray.getResourceId(R.styleable.SnbGridView_snb_add_draw_id, mOption.getAddImgID()));
        typedArray.recycle();
    }

    private void initData(Context context) {
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder, @NonNull ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();//得到item原来的position
                int toPosition = target.getAdapterPosition();//得到目标position
                if (mOption.isCanDrag()) {
                    if (!mOption.isJustShow()) {
                        if ((toPosition == mGridAdapter.getAddIndex() || mGridAdapter.getAddIndex() == fromPosition)) {
                            return true;
                        }
                    }
                }
                //滑动事件
                mGridAdapter.getGridItemBeans().add(toPosition, mGridAdapter.getGridItemBeans().remove(fromPosition));
                mGridAdapter.notifyItemMoved(fromPosition, toPosition);
                return false;
            }

            @Override
            public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setScaleX(1.1f);
                    viewHolder.itemView.setScaleY(1.1f);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                if (!recyclerView.isComputingLayout()) {
                    //拖拽结束后恢复view的状态
                    viewHolder.itemView.setScaleX(1.0f);
                    viewHolder.itemView.setScaleY(1.0f);
                }
            }
        });
        mGridLayoutManager = new GridLayoutManager(context, mOption.getColumn());
        mGridAdapter = new GridViewAdapter<>(context, mOption);
        mItemTouchListener = new OnItemTouchListener() {
            private GestureDetectorCompat mGestureDetector;

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                getGestureDetector().onTouchEvent(e);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                getGestureDetector().onTouchEvent(e);
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

            private GestureDetectorCompat getGestureDetector() {
                if (mGestureDetector == null) {
                    mGestureDetector = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public void onLongPress(MotionEvent e) {
                            View child = findChildViewUnder(e.getX(), e.getY());
                            if (child != null) {
                                RecyclerView.ViewHolder vh = getChildViewHolder(child);
                                if (mOption.isCanDrag()) {
                                    if (mOption.isJustShow()) {
                                        mItemTouchHelper.startDrag(vh);
                                    } else if (vh.getLayoutPosition()
                                            != mGridAdapter.getAddIndex()) {
                                        mItemTouchHelper.startDrag(vh);
                                    }
                                }
                            }
                        }
                    });
                }
                return mGestureDetector;
            }
        };
    }

    private void bindData(Context context) {
        mItemTouchHelper.attachToRecyclerView(this);
        setLayoutManager(mGridLayoutManager);
        setAdapter(mGridAdapter);
        addOnItemTouchListener(mItemTouchListener);
    }

    public void setColumn(int columns) {
        if (mOption.getColumn() != columns) {
            mOption.setColumn(columns);
            mGridLayoutManager.setSpanCount(columns);
        }
    }

    public int getColumn() {
        return mOption.getColumn();
    }

    public void setMaxSize(int maxSize) {
        //如果数量与最后一次不一样就要改变
        if (mOption.getMaxSize() != maxSize) {
            //如果设置的最大数小于当前实际数量，说明需要移除多余的内容
            int diff = mGridAdapter.getDataCount() - maxSize;
            if (diff > 0) {
                mGridAdapter.getGridItemBeans().subList(maxSize, mGridAdapter.getDataCount()).clear();
            }
            mOption.setMaxSize(maxSize);
            mGridAdapter.notifyDataSetChanged();
        }
    }

    public int getMaxSize() {
        return mOption.getMaxSize();
    }

    public void addImage(Bean bean) {
        mGridAdapter.addItem(bean);
    }

    public void addImage(List<Bean> beans) {
        mGridAdapter.addItems(beans);
    }

    public void clear() {
        mGridAdapter.clear();
    }

    public List<Bean> getAllItems() {
        return mGridAdapter.getGridItemBeans();
    }

    public void setDragEnable(boolean enable) {
        mOption.setCanDrag(enable);
    }

    public boolean isDragEnable() {
        return mOption.isCanDrag();
    }

    public void setShowOnly(boolean yes) {
        if (this.mOption.isJustShow() != yes) {
            this.mOption.setJustShow(yes);
            mGridAdapter.notifyDataSetChanged();
        }
    }

    public boolean isShowOnly() {
        return mOption.isJustShow();
    }

    public ActionListener getActionListener() {
        return this.mOption.getActionListener();
    }

    public void setActionListener(ActionListener actionListener) {
        this.mOption.setActionListener(actionListener);
    }

    public ImageLoader getImageLoader() {
        return mOption.getImageLoader();
    }

    public void setImageLoader(ImageLoader imageLoader) {
        mOption.setImageLoader(imageLoader);
    }

    public int getDelBtnID() {
        return mOption.getDelBtnID();
    }

    public void setDelBtnID(int delBtnID) {
        if (mOption.getDelBtnID() != delBtnID) {
            mOption.setDelBtnID(delBtnID);
            mGridAdapter.notifyDataSetChanged();
        }
    }


    public int getAddImgID() {
        return mOption.getAddImgID();
    }

    public void setAddImgID(int addImgID) {
        if (mOption.getAddImgID() != addImgID) {
            mOption.setAddImgID(addImgID);
            mGridAdapter.notifyDataSetChanged();
        }
    }

    public interface ActionListener {

        void onItemClick(View view, int index);

        void onAddBtnClick(View view);

    }


}

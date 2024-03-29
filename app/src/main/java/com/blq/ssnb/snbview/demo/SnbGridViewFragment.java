package com.blq.ssnb.snbview.demo;

import android.view.View;
import android.widget.TextView;

import com.blq.ssnb.snbview.R;

import java.util.ArrayList;
import java.util.List;

import blq.ssnb.baseconfigure.BaseFragment;
import blq.ssnb.snbutil.SnbLog;
import blq.ssnb.snbutil.SnbToast;
import blq.ssnb.snbview.gridview.IGridItemBean;
import blq.ssnb.snbview.gridview.SnbGridView;
import blq.ssnb.snbview.gridview.SnbGridViewOption;

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
public class SnbGridViewFragment extends BaseFragment {
    public static class GridItemBean implements IGridItemBean {

        private int flag;
        private String imgUrl;
        private String imgName;
        private boolean isSelect;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgName() {
            return imgName;
        }

        public void setImgName(String imgName) {
            this.imgName = imgName;
        }

        public int getFlag() {
            return flag;
        }

        @Override
        public String getUrl() {
            return imgUrl;
        }

        @Override
        public boolean isSelect() {
            return isSelect;
        }

        @Override
        public void setSelect(boolean isSelect) {
            this.isSelect = isSelect;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        @Override
        public String toString() {
            return "GridItemBean{" +
                    "flag=" + flag +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", imgName='" + imgName + '\'' +
                    '}';
        }
    }

    private SnbGridView<GridItemBean> mSnbGridView;
    private View btn1;
    private View btn2;
    private View btn3;
    private View btn4;
    private TextView btn5;
    private TextView btn6;
    private View btn7;
    private View btn8;

    private View btn11;
    private View btn12;
    private View btn13;
    private View btn14;

    private View btn21;
    private View btn22;

    private View btn31;
    private View btn32;
    private View btn33;


    @Override
    protected int rootLayout() {
        return R.layout.fragment_snb_grid_view;
    }

    @Override
    protected void initView(View view) {
        mSnbGridView = view.findViewById(R.id.sgv_grid);
        mSnbGridView.addImage(getBean(R.drawable.vateral_1));
        mSnbGridView.addImage(getBean(R.drawable.vateral_2));
        mSnbGridView.addImage(getBean(R.drawable.vateral_3));
        btn1 = view.findViewById(R.id.tv_btn_1);
        btn2 = view.findViewById(R.id.tv_btn_2);
        btn3 = view.findViewById(R.id.tv_btn_3);
        btn4 = view.findViewById(R.id.tv_btn_4);
        btn5 = view.findViewById(R.id.tv_btn_5);
        btn6 = view.findViewById(R.id.tv_btn_6);
        btn7 = view.findViewById(R.id.tv_btn_7);
        btn8 = view.findViewById(R.id.tv_btn_8);


        btn11 = view.findViewById(R.id.tv_btn_11);
        btn12 = view.findViewById(R.id.tv_btn_12);
        btn13 = view.findViewById(R.id.tv_btn_13);
        btn14 = view.findViewById(R.id.tv_btn_14);

        btn21 = view.findViewById(R.id.tv_btn_21);
        btn22 = view.findViewById(R.id.tv_btn_22);

        btn31 = view.findViewById(R.id.tv_btn_31);
        btn32 = view.findViewById(R.id.tv_btn_32);
        btn33 = view.findViewById(R.id.tv_btn_33);
    }

    private GridItemBean getBean(int id) {
        GridItemBean bean = new GridItemBean();
        bean.setImgUrl("android.resource://" + getContext().getPackageName() + "/" + id);
        return bean;
    }

    @Override
    protected void initData() {
        btn1.setOnClickListener(v -> {
            mSnbGridView.addImage(getBean(R.drawable.vateral_4));
        });
        btn2.setOnClickListener(v -> {
            List<GridItemBean> beans = new ArrayList<>();
            beans.add(getBean(R.drawable.ic_looks_one_black_24dp));
            beans.add(getBean(R.drawable.ic_looks_two_black_24dp));
            beans.add(getBean(R.drawable.ic_looks_3_black_24dp));
            beans.add(getBean(R.drawable.ic_looks_4_black_24dp));
            beans.add(getBean(R.drawable.ic_looks_5_black_24dp));
            beans.add(getBean(R.drawable.ic_looks_6_black_24dp));
            beans.add(getBean(R.drawable.ic_filter_7_black_24dp));
            beans.add(getBean(R.drawable.ic_filter_8_black_24dp));
            beans.add(getBean(R.drawable.ic_filter_9_black_24dp));
            mSnbGridView.addImage(beans);
        });
        btn3.setOnClickListener(v -> mSnbGridView.setMaxSize(5));
        btn4.setOnClickListener(v -> mSnbGridView.setMaxSize(9));
        btn5.setOnClickListener(v -> {
            mSnbGridView.setDragEnable(!mSnbGridView.isDragEnable());
            status();
        });
        btn6.setOnClickListener(v -> mSnbGridView.setGridStyle(SnbGridViewOption.PICTURE_MODEL_SHOW));
        btn7.setOnClickListener(v -> mSnbGridView.setGridStyle(SnbGridViewOption.PICTURE_MODEL_CHOOSE));
        btn8.setOnClickListener(v -> mSnbGridView.setGridStyle(SnbGridViewOption.PICTURE_MODEL_SELECT));

        btn11.setOnClickListener(v -> mSnbGridView.setDelBtnID(R.drawable.snb_ic_clear_black_24dp));

        btn12.setOnClickListener(v -> mSnbGridView.setDelBtnID(R.drawable.ic_navigation_close_btn));

        btn13.setOnClickListener(v -> mSnbGridView.setAddImgID(R.drawable.snb_ic_grid_add_btn));

        btn14.setOnClickListener(v -> mSnbGridView.setAddImgID(R.drawable.ic_navigation_back_btn));
        btn21.setOnClickListener(v -> mSnbGridView.setImgSpace(8));
        btn22.setOnClickListener(v -> mSnbGridView.setImgSpace(16));
        status();

        btn31.setOnClickListener(v -> mSnbGridView.setRatio("2:1"));
        btn32.setOnClickListener(v -> mSnbGridView.setColumn(3));
        btn33.setOnClickListener(v -> mSnbGridView.setColumnAndRatio(1, "4:1"));


    }

    private void status() {
        btn5.setText("可以拖动(" + mSnbGridView.isDragEnable() + ")");
    }

    @Override
    protected void bindEvent() {
        mSnbGridView.setActionListener(new SnbGridView.ActionListener() {
            @Override
            public void onItemClick(View view, int index) {
                SnbToast.showSmart("item被点击了");
            }

            @Override
            public void onAddBtnClick(View view) {
                SnbToast.showSmart("添加被点击了");
            }
        });
    }
}

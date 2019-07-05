package com.blq.ssnb.snbview.demo;

import android.view.View;

import com.blq.ssnb.snbview.R;

import blq.ssnb.baseconfigure.BaseFragment;
import blq.ssnb.snbview.SnbCountDownView;

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
public class SnbCountDownViewFragment extends BaseFragment {

    SnbCountDownView mSnbCountDownView;

    @Override
    protected int rootLayout() {
        return R.layout.fragment_snb_count_down_view;
    }

    @Override
    protected void initView(View view) {
        mSnbCountDownView = view.findViewById(R.id.snb_count_view_btn);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void bindEvent() {
        mSnbCountDownView.setOnBtnClickListener(e -> mSnbCountDownView.start());
    }
}

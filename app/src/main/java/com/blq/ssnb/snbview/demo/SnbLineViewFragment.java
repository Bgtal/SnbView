package com.blq.ssnb.snbview.demo;

import android.view.View;
import android.widget.TextView;

import com.blq.ssnb.snbview.R;

import blq.ssnb.baseconfigure.BaseFragment;
import blq.ssnb.snbview.SnbLineView;

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
public class SnbLineViewFragment extends BaseFragment {

    SnbLineView mSnbLineView;
    TextView mTextView;
    @Override
    protected int rootLayout() {
        return R.layout.fragment_snb_line_view;
    }

    @Override
    protected void initView(View view) {
        mTextView = view.findViewById(R.id.tv_change_orientation_btn);
        mSnbLineView = view.findViewById(R.id.tv_line);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void bindEvent() {
        mTextView.setOnClickListener(v->{
            mSnbLineView.setOrientation(!mSnbLineView.isHorizontal());
        });
    }
}

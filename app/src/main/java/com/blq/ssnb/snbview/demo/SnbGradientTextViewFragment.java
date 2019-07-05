package com.blq.ssnb.snbview.demo;

import android.view.View;
import android.widget.TextView;

import com.blq.ssnb.snbview.R;

import blq.ssnb.baseconfigure.BaseFragment;
import blq.ssnb.snbview.SnbGradientTextView;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019-07-05
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public class SnbGradientTextViewFragment extends BaseFragment {
    SnbGradientTextView testView1;

    SnbGradientTextView testView2;

    SnbGradientTextView setView1;

    TextView setView2;


    private int[] gradientColor = {0xff949494, 0xff5D478B, 0xff121212, 0xff0000EE,
            0xffB8860B, 0xffEEB4B4, 0xffFFC125, 0xffEE3A8C, 0xffCAFF70};

    @Override
    protected int rootLayout() {
        return R.layout.fragment_snb_gradient_text;
    }

    @Override
    protected void initView(View view) {
        testView1 = view.findViewById(R.id.gradient_view1);
        testView2 = view.findViewById(R.id.gradient_view2);
        setView1 = view.findViewById(R.id.gradient_set1);
        setView2 = view.findViewById(R.id.gradient_set2);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void bindEvent() {
        setView1.setOnClickListener(v -> {
            testView1.setGradientColors(setView1.getGradientColors());
            testView2.setGradientColors(setView1.getGradientColors());
        });
        setView2.setOnClickListener(v -> {
            testView1.setGradientColors(gradientColor);
            testView2.setGradientColors(gradientColor);
        });
    }
}

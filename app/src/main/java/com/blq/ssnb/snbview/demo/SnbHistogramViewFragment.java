package com.blq.ssnb.snbview.demo;

import android.view.View;
import android.widget.SeekBar;


import com.blq.ssnb.snbview.R;

import blq.ssnb.baseconfigure.BaseFragment;
import blq.ssnb.snbutil.SnbLog;
import blq.ssnb.snbview.SnbHistogramView;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2020-03-26
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public class SnbHistogramViewFragment extends BaseFragment {

    private View btn1;
    private View btn2;
    private View btn3;
    private SeekBar mSeekBar;
    private SnbHistogramView mSnbHistogramView;
    private SnbHistogramView mSnbHistogramView2;

    @Override
    protected int rootLayout() {
        return R.layout.fragment_snb_histogram_view;
    }

    @Override
    protected void initView(View view) {
        btn1 = view.findViewById(R.id.tv_btn_1);
        btn2 = view.findViewById(R.id.tv_btn_2);
        btn3 = view.findViewById(R.id.tv_btn_3);
        mSeekBar = view.findViewById(R.id.seekBar);
        mSnbHistogramView = view.findViewById(R.id.shv_view);
        mSnbHistogramView2 = view.findViewById(R.id.shv_view_2);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void bindEvent() {
        btn1.setOnClickListener(v -> {
            mSnbHistogramView.setMax(300);
            upSeekBar();
        });
        btn2.setOnClickListener(v -> {
            mSnbHistogramView.setMax(100);
            upSeekBar();
        });
        btn3.setOnClickListener(v -> {
            mSnbHistogramView.setProgress(90);
            upSeekBar();
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SnbLog.e(">>>>progress:" + progress);
                    mSnbHistogramView.setProgress(progress);
                    mSnbHistogramView2.setProgress(progress * mSnbHistogramView2.getMax() / mSeekBar.getMax());
                    if(progress >= 90){
                        mSnbHistogramView2.showHighLight(true);
                    }else{
                        mSnbHistogramView2.showHighLight(false);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSnbHistogramView.setOnClickListener(v -> {
            int gravity = mSnbHistogramView.getGravity() << 1;
            if (gravity > 8) {
                gravity = 1;
            }
            mSnbHistogramView.setGravity(gravity);
        });
    }

    private void upSeekBar() {
        mSeekBar.setMax(mSnbHistogramView.getMax());
        mSeekBar.setProgress(mSnbHistogramView.getProgress());
    }
}

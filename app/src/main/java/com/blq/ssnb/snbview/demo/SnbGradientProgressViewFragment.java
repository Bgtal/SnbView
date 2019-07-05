package com.blq.ssnb.snbview.demo;

import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blq.ssnb.snbview.R;

import blq.ssnb.baseconfigure.BaseFragment;
import blq.ssnb.snbutil.SnbLog;
import blq.ssnb.snbview.SnbGradientProgressView;

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
public class SnbGradientProgressViewFragment extends BaseFragment {


    SeekBar seekBar;

    SnbGradientProgressView progressView1;

    TextView progressThreadBtn;

    SnbGradientProgressView progressThreadView;

    EditText maxEdit;

    EditText curEdit;

    EditText proEdit;

    TextView changeBtn;

    SnbGradientProgressView progressChangeView;

    @Override
    protected int rootLayout() {
        return R.layout.fragment_snb_gradient_progress_view;
    }

    @Override
    protected void initView(View view) {

        seekBar = view.findViewById(R.id.progress_seekBar);

        progressView1 = view.findViewById(R.id.progress_progress_1);

        progressThreadBtn = view.findViewById(R.id.progress_btn_thread);

        progressThreadView = view.findViewById(R.id.progress_progress_thread);

        maxEdit = view.findViewById(R.id.ll_ll_edt_max);

        curEdit = view.findViewById(R.id.ll_ll_edt_cur);

        proEdit = view.findViewById(R.id.ll_ll_edt_progress);

        changeBtn = view.findViewById(R.id.progress_btn_change);

        progressChangeView = view.findViewById(R.id.progress_progress_change);
    }

    @Override
    protected void initData() {
        seekBar.setProgress(progressView1.getProgress());

    }

    private boolean isThreadRunn = false;

    private Thread progressThread;
    @Override
    protected void bindEvent() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressView1.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        progressThreadBtn.setOnClickListener(v -> {
            if (!isThreadRunn) {
                progressThread = new Thread(() -> {
                    isThreadRunn = true;
                    for (int i = 0; i <= 100; i++) {
                        progressThreadView.setProgress(i);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    isThreadRunn = false;
                });
                progressThread.start();
            }
        });

        changeBtn.setOnClickListener(v -> {
            int mMax = Integer.valueOf(maxEdit.getText().toString());
            int mCur = Integer.valueOf(curEdit.getText().toString());
            int mPro = Integer.valueOf(proEdit.getText().toString());
            if (mMax != max) {
                progressChangeView.setMaxCount(mMax);
            } else if (mCur != cur) {
                progressChangeView.setCurrentCount(mCur);
            } else if (mPro != pro) {
                progressChangeView.setProgress(mPro);
            }
        });

        progressChangeView.setProgressChangeListener(progress -> {
            max = progressChangeView.getMaxCount();
            cur = progressChangeView.getCurrentCount();
            pro = progress;
            SnbLog.e("tag", "max:" + max + ";cur:" + cur + ";pro:" + pro);
            maxEdit.setText(max + "");
            curEdit.setText(cur + "");
            proEdit.setText(pro + "");
        });
    }

    int max = 100;
    int cur = 100;
    int pro = 100;

    @Override
    protected void operation() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        progressThread.interrupt();
    }

}

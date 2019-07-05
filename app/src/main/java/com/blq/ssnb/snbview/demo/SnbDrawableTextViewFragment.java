package com.blq.ssnb.snbview.demo;

import android.view.Gravity;
import android.view.View;

import com.blq.ssnb.snbview.R;

import blq.ssnb.baseconfigure.BaseFragment;
import blq.ssnb.snbutil.SnbLog;
import blq.ssnb.snbview.SnbDrawableTextView;

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
public class SnbDrawableTextViewFragment extends BaseFragment {

    SnbDrawableTextView mDrawableTextView;

    @Override
    protected int rootLayout() {
        return R.layout.fragment_snb_drawable_text;
    }

    @Override
    protected void initView(View view) {
        mDrawableTextView = view.findViewById(R.id.sdtv_text_view);
    }

    @Override
    protected void initData() {

    }

    int i = 0;
    int size1w = 50;
    int size2w = 100;

    int size1h = 50;
    int size2h = 100;

    @Override
    protected void bindEvent() {
        mDrawableTextView.setOnClickListener(v -> {
            SnbLog.e(">>>>>i" + i);
            i++;
            switch (i % 8) {
                case 0:
                    mDrawableTextView.setDrawableSize(Gravity.LEFT, size1w, size1h);
                    break;
                case 1:
                    mDrawableTextView.setDrawableSize(Gravity.TOP, size1w, size1h);
                    break;
                case 2:
                    mDrawableTextView.setDrawableSize(Gravity.RIGHT, size1w, size1h);
                    break;
                case 3:
                    mDrawableTextView.setDrawableSize(Gravity.BOTTOM, size1w, size1h);
                    break;
                case 4:
                    mDrawableTextView.setDrawableSize(Gravity.LEFT, size2w, size2h);
                    break;
                case 5:
                    mDrawableTextView.setDrawableSize(Gravity.TOP, size2w, size2h);
                    break;
                case 6:
                    mDrawableTextView.setDrawableSize(Gravity.RIGHT, size2w, size2h);
                    break;
                case 7:
                    mDrawableTextView.setDrawableSize(Gravity.BOTTOM, size2w, size2h);
                    break;
            }
        });
    }
}

package com.blq.ssnb.snbview.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blq.ssnb.snbview.MainActivity;
import com.blq.ssnb.snbview.R;

import blq.ssnb.baseconfigure.BaseFragment;
import blq.ssnb.snbutil.SnbLog;
import blq.ssnb.snbutil.SnbToast;
import blq.ssnb.snbview.addsub.ActionCallBack;
import blq.ssnb.snbview.addsub.SnbAddSubLogicHelper;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019-08-01
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public class SnbAddSubLogicHelperFragment extends BaseFragment {

    private SnbAddSubLogicHelper mSnbAddSubLogicHelper;

    private TextView subView;
    private EditText mEditText;
    private TextView addView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSnbAddSubLogicHelper = new SnbAddSubLogicHelper();
    }

    @Override
    protected int rootLayout() {
        return R.layout.fragment_snb_add_sub_logic_helper;
    }

    @Override
    protected void initView(View view) {
        subView = view.findViewById(R.id.tv_sub_btn);
        mEditText = view.findViewById(R.id.edit_msg);
        addView = view.findViewById(R.id.tv_add_btn);

    }

    @Override
    protected void initData() {
        mSnbAddSubLogicHelper.attachView(subView, mEditText, addView);
        mSnbAddSubLogicHelper.setActionCallBack(new ActionCallBack() {
            @Override
            public void onTopLimit(int topLimit) {
                SnbToast.showSmart("到达上限了:" + topLimit);
            }

            @Override
            public void onBottomLimit(int bottomLimit) {
                SnbToast.showSmart("到达下限了:" + bottomLimit);
            }

            @Override
            public void onNumberChange(int number) {
                SnbToast.showSmart("数字改变了:" + number);
            }

            @Override
            public boolean typeError(String msg) {
                return true;
            }
        });
        mSnbAddSubLogicHelper.setOption(new SnbAddSubLogicHelper.Option().setTopLimit(10).setBottomLimit(-20).setStep(2));
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSnbAddSubLogicHelper.detach();
    }
}

package com.blq.ssnb.snbview.demo;

import android.app.ActionBar;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.blq.ssnb.snbview.R;

import blq.ssnb.baseconfigure.BaseFragment;
import blq.ssnb.snbview.shape.LineShape;
import blq.ssnb.snbview.shape.OvalShape;
import blq.ssnb.snbview.shape.RectangleShape;
import blq.ssnb.snbview.shape.RingShape;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2020-01-10
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public class SnbShapeFragment extends BaseFragment {

    private View oneView;

    private View twoView;

    private View threeView;

    private View fourView;


    @Override
    protected int rootLayout() {
        return R.layout.fragment_snb_shape;
    }

    @Override
    protected void initView(View view) {
        oneView = view.findViewById(R.id.tv_btn_one);
        twoView = view.findViewById(R.id.tv_btn_two);
        threeView = view.findViewById(R.id.tv_btn_three);
        fourView = view.findViewById(R.id.tv_btn_four);
    }

    @Override
    protected void initData() {
        new LineShape
                .Builder()
                .stroke(12, Color.BLUE)
                .strokeDash(20, 5)
                .build()
                .setBackground(oneView);
        new OvalShape
                .Builder()
                .color(Color.BLUE)
                .stroke(10, Color.YELLOW)
                .build()
                .setBackground(twoView);
        new RectangleShape
                .Builder()
                .bottomLeftRadius(10)
                .bottomRightRadius(20)
                .topLeftRadius(20)
                .topRightRadius(10)
                .color(Color.BLUE)
                .stroke(10, Color.YELLOW)
                .build()
                .setBackground(threeView);
        new RingShape
                .Builder()
                .color(Color.BLUE)
                .stroke(10, Color.YELLOW)
                .radian(270)
                .innerRadius(12)
                .build()
                .setBackground(fourView);

    }

    @Override
    protected void bindEvent() {

    }
}

package com.blq.ssnb.snbview.demo;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.blq.ssnb.snbview.R;

import blq.ssnb.baseconfigure.BaseFragment;
import blq.ssnb.snbview.SnbExpandableTextView;

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
public class SnbExpandableTextViewFragment extends BaseFragment {

    SnbExpandableTextView epViewOne;
    SnbExpandableTextView epViewTwo;
    TextView changeContentBtn;
    TextView expandFoldBtn;
    TextView changeLineBtn;
    TextView changeExpandIconBtn;
    TextView changeFoldIconBtn;
    TextView changeAnimDurationBtn;

    private String changhenge;
    private String chunjianghuayueye;

    private Drawable expandOneIcon;
    private Drawable expandTwoIcon;

    private Drawable foldOneIcon;
    private Drawable foldTwoIcon;


    @Override
    protected int rootLayout() {
        return R.layout.fragment_snb_expandable_text_view;
    }

    @Override
    protected void initView(View view) {
        epViewOne = view.findViewById(R.id.etv_expand_1);
        epViewTwo = view.findViewById(R.id.etv_expand_2);
        changeContentBtn = view.findViewById(R.id.tv_expand_change_content_btn);
        expandFoldBtn = view.findViewById(R.id.tv_expand_change_expand_fold_btn);
        changeLineBtn = view.findViewById(R.id.tv_expand_change_flod_show_line_btn);
        changeExpandIconBtn = view.findViewById(R.id.tv_expand_change_expand_icon_btn);
        changeFoldIconBtn = view.findViewById(R.id.tv_expand_change_flod_icon_btn);
        changeAnimDurationBtn = view.findViewById(R.id.tv_expand_change_anim_duration_btn);
    }

    @Override
    protected void initData() {
        changhenge = getString(R.string.expandable_text_content_chg);
        chunjianghuayueye = getString(R.string.expandable_text_content_cjhyy);
        expandOneIcon = getResources().getDrawable(R.drawable.ic_arrow_top_black_24dp);
        expandTwoIcon = getResources().getDrawable(R.drawable.ic_arrow_top_black_24dp);
        expandOneIcon.setBounds(0, 0, 32, 32);
        expandTwoIcon.setBounds(0, 0, 32, 32);

        foldOneIcon = getResources().getDrawable(R.drawable.ic_arrow_bottom_black_24dp);
        foldTwoIcon = getResources().getDrawable(R.drawable.ic_arrow_bottom_black_24dp);
        foldOneIcon.setBounds(0, 0, 32, 32);
        foldTwoIcon.setBounds(0, 0, 32, 32);

        contentOne = true;
        changeContent(contentOne);
        expandFoldBtn.setText("开关:" + (epViewOne.isFold() ? "折叠" : "展开"));
        changeLineBtn.setText("显示行数：" + epViewOne.getFoldShowLine());
        expandOne = true;
        changeExpandIcon(expandOne);
        foldOne = true;
        changeFoldIcon(foldOne);

        changeAnimDurationBtn.setText("动画持续时间:" + epViewOne.getAnimDuration());
    }

    @Override
    protected void bindEvent() {
        changeContentBtn.setOnClickListener(v -> {
            contentOne = !contentOne;
            changeContent(contentOne);
        });
        expandFoldBtn.setOnClickListener(v -> {
            boolean isFold = epViewOne.isFold();
            if (isFold) {
                epViewOne.expandContent();
            } else {
                epViewOne.foldContent();
            }
            expandFoldBtn.setText("开关:" + (epViewOne.isFold() ? "折叠" : "展开"));

        });
        changeLineBtn.setOnClickListener(v -> {
            int line = epViewOne.getFoldShowLine();
            if (line == 5) {
                epViewOne.setFoldShowLine(3);
            } else {
                epViewOne.setFoldShowLine(5);
            }
            changeLineBtn.setText("显示行数：" + epViewOne.getFoldShowLine());
        });

        changeExpandIconBtn.setOnClickListener(v -> {
            expandOne = !expandOne;
            changeExpandIcon(expandOne);
        });
        changeFoldIconBtn.setOnClickListener(v -> {
            foldOne = !foldOne;
            changeFoldIcon(foldOne);
        });
        changeAnimDurationBtn.setOnClickListener(v -> {
            long duration = epViewOne.getAnimDuration();
            if (duration == 1000) {
                epViewOne.setAnimDuration(300);
            } else {
                epViewOne.setAnimDuration(1000);
            }
            changeAnimDurationBtn.setText("动画持续时间:" + epViewOne.getAnimDuration());
        });
    }



    private boolean contentOne = false;

    private void changeContent(boolean contentOne) {
        if (contentOne) {
            epViewOne.setText(changhenge);
        } else {
            epViewOne.setText(chunjianghuayueye);
        }
        changeContentBtn.setText("修改内容:" + (contentOne ? "长恨歌" : "春江花月夜"));
    }

    private boolean expandOne = false;

    private void changeExpandIcon(boolean expandOne) {
        if (expandOne) {
            epViewOne.setExpandStatusDrawable(expandOneIcon);
            changeExpandIconBtn.setCompoundDrawables(null, null, expandOneIcon, null);
        } else {
            epViewOne.setExpandStatusDrawable(expandTwoIcon);
            changeExpandIconBtn.setCompoundDrawables(null, null, expandTwoIcon, null);
        }
    }

    private boolean foldOne = false;

    private void changeFoldIcon(boolean foldOne) {
        if (foldOne) {
            epViewOne.setFoldStatusDrawable(foldOneIcon);
            changeFoldIconBtn.setCompoundDrawables(null, null, foldOneIcon, null);
        } else {
            epViewOne.setFoldStatusDrawable(foldTwoIcon);
            changeFoldIconBtn.setCompoundDrawables(null, null, foldTwoIcon, null);
        }
    }
}

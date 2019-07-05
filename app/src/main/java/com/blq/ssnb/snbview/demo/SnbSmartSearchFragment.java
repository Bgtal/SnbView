package com.blq.ssnb.snbview.demo;

import android.view.View;
import android.widget.TextView;

import com.blq.ssnb.snbview.R;

import blq.ssnb.baseconfigure.BaseFragment;
import blq.ssnb.snbview.SnbSmartSearchEdit;

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
public class SnbSmartSearchFragment extends BaseFragment {

    SnbSmartSearchEdit smartSearchEdit;

    TextView infoShowView;

    private StringBuilder infoBuilder;


    @Override
    protected int rootLayout() {
        return R.layout.fragment_snb_smart_search;
    }

    @Override
    protected void initView(View view) {
        smartSearchEdit = view.findViewById(R.id.smart_search_edit);
        infoShowView = view.findViewById(R.id.tv_info_show_view);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void bindEvent() {
        smartSearchEdit.setOnSearchActionListener(new SnbSmartSearchEdit.OnSearchActionListener() {
            @Override
            public void onSoftInputSearch(String searchKey) {
                showSearchInfo("软键盘搜索:" + searchKey);
            }

            @Override
            public void onLostFocusSearch(String searchKey) {
                showSearchInfo("丢失焦点搜索:" + searchKey);
            }

            @Override
            public void onAutoSearch(String searchKey) {
                showSearchInfo("自动搜索:" + searchKey);
            }
        });
    }

    @Override
    protected void operation() {
        infoBuilder = new StringBuilder();
        infoBuilder.append("搜索历史：\n");
        infoShowView.setText(infoBuilder);
    }

    private void showSearchInfo(String searchKey) {
        infoBuilder.append(searchKey).append("\n");
        infoShowView.setText(infoBuilder);
    }
}

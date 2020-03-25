package com.blq.ssnb.snbview;

import androidx.fragment.app.Fragment;

import com.blq.ssnb.snbview.demo.SnbAddSubLogicHelperFragment;
import com.blq.ssnb.snbview.demo.SnbCountDownViewFragment;
import com.blq.ssnb.snbview.demo.SnbDrawableTextViewFragment;
import com.blq.ssnb.snbview.demo.SnbExpandableTextViewFragment;
import com.blq.ssnb.snbview.demo.SnbGradientProgressViewFragment;
import com.blq.ssnb.snbview.demo.SnbGradientTextViewFragment;
import com.blq.ssnb.snbview.demo.SnbGridViewFragment;
import com.blq.ssnb.snbview.demo.SnbLineViewFragment;
import com.blq.ssnb.snbview.demo.SnbShapeFragment;
import com.blq.ssnb.snbview.demo.SnbSmartSearchFragment;

import java.util.ArrayList;
import java.util.List;

import blq.ssnb.baseconfigure.BaseFragmentContainerActivity;
import blq.ssnb.baseconfigure.simple.MenuBean;
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity;

public class MainActivity extends SimpleMenuActivity {

    @Override
    protected String navigationTitle() {
        return "main";
    }

    @Override
    protected List<MenuBean> getMenuBeans() {
        List<MenuBean> beanList = new ArrayList<>();
        beanList.add(new MenuBean()
                .setMenuTitle("SnbLineView")
                .setOnClickListener(v -> startToFragment(SnbLineViewFragment.class)));
        beanList.add(new MenuBean()
                .setMenuTitle("SnbDrawableTextView")
                .setOnClickListener(v -> startToFragment(SnbDrawableTextViewFragment.class)));
        beanList.add(new MenuBean()
                .setMenuTitle("SnbSmartSearchEdit")
                .setOnClickListener(v -> startToFragment(SnbSmartSearchFragment.class)));
        beanList.add(new MenuBean()
                .setMenuTitle("SnbExpandableTextView")
                .setOnClickListener(v -> startToFragment(SnbExpandableTextViewFragment.class)));
        beanList.add(new MenuBean()
                .setMenuTitle("SnbCountDownView")
                .setOnClickListener(v -> startToFragment(SnbCountDownViewFragment.class)));
        beanList.add(new MenuBean()
                .setMenuTitle("SnbGradientProgressView")
                .setOnClickListener(v -> startToFragment(SnbGradientProgressViewFragment.class)));
        beanList.add(new MenuBean()
                .setMenuTitle("SnbGradientTextView")
                .setOnClickListener(v -> startToFragment(SnbGradientTextViewFragment.class)));
        beanList.add(new MenuBean()
                .setMenuTitle("SnbAddSubLogicHelper")
                .setOnClickListener(v -> startToFragment(SnbAddSubLogicHelperFragment.class)));
        beanList.add(new MenuBean()
                .setMenuTitle("SnbShape")
                .setOnClickListener(v -> startToFragment(SnbShapeFragment.class)));
        beanList.add(new MenuBean()
                .setMenuTitle("SnbGridView")
                .setOnClickListener(v -> startToFragment(SnbGridViewFragment.class)));
        return beanList;
    }

    private void startToFragment(Class<? extends Fragment> fragmentClass) {
        startActivity(BaseFragmentContainerActivity.newIntent(getContext(), fragmentClass, null));
    }
}

package com.jerry.jingdong.fragment.tab;

import android.view.View;
import android.widget.TextView;

import com.jerry.jingdong.activity.MainUI;
import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.utils.UIUtils;

/**
 * 搜索
 */

public class SearchFragment extends BaseFragment {
    @Override
    public void initTitle() {
        ((MainUI) getActivity()).mMainTitleview.setTvTitle("搜索");
    }
    @Override
    public LoadingPager.LoadResultState initData() {


        return LoadingPager.LoadResultState.SUCCESS;
    }

    @Override
    public View initSuccessView() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(getClass().getSimpleName());
        return tv;
    }
}

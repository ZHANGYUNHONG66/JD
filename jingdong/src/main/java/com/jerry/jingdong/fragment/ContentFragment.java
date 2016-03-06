package com.jerry.jingdong.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RadioGroup;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.factory.TabFragmentFactory;
import com.jerry.jingdong.utils.UIUtils;
import com.jerry.jingdong.views.LazyViewPager;
import com.jerry.jingdong.views.NoScrollViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContentFragment extends Fragment implements LazyViewPager.OnPageChangeListener {

    @Bind(R.id.content_rg)
    RadioGroup        mContentRg;
    @Bind(R.id.content_viewPager)
    NoScrollViewPager mContentViewPager;

    private int mCurrRbIndex = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(UIUtils.getContext(),
                R.layout.fragment_content, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();

        initListener();
    }

    public void initData() {
        // 设置预加载的页面数
        mContentViewPager.setOffscreenPageLimit(0);

        // 给ViePager设置适配器
        mContentViewPager.setAdapter(
                new MyAdapter(getActivity().getSupportFragmentManager()));

        // 初始化选中首页
        mContentRg.check(R.id.content_rb_home);

    }

    /**
     * 初始化监听
     */
    public void initListener() {

        mContentRg.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId) {
                        switch (checkedId) {// 首页
                            case R.id.content_rb_home:
                                mCurrRbIndex = 0;
                                break;
                            case R.id.content_rb_search:// 搜索
                                mCurrRbIndex = 1;
                                break;
                            case R.id.content_rb_comment:// 品牌
                                mCurrRbIndex = 2;
                                break;
                            case R.id.content_rb_shopping:// 购物车
                                mCurrRbIndex = 3;
                                break;
                            case R.id.content_rb_mine:// 我的
                                mCurrRbIndex = 4;
                                break;
                        }
                        mContentViewPager.setCurrentItem(mCurrRbIndex);
                    }
                });

        mContentViewPager.setOnPageChangeListener(this);

        mContentViewPager.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ContentFragment.this.onPageSelected(0);
                        mContentViewPager.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        BaseFragment fragment = TabFragmentFactory.createFragment(position);
        if (fragment != null && fragment.mLoadingPager != null) {
            Log.d("0000", "3333333" );
            fragment.mLoadingPager.triggerLoadData();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {

            return mContentRg.getChildCount();
        }

        @Override
        public Fragment getItem(int position) {

            BaseFragment fragment = TabFragmentFactory.createFragment(position);

            return fragment;
        }

    }

}

package com.jerry.jingdong.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewTreeObserver;
import android.widget.RadioGroup;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.factory.TabFragmentFactory;
import com.jerry.jingdong.fragment.tab.HomeFragment;
import com.jerry.jingdong.views.LazyViewPager;
import com.jerry.jingdong.views.NoScrollViewPager;
import com.jerry.jingdong.views.TitleView;

public class MainUI extends FragmentActivity
        implements LazyViewPager.OnPageChangeListener {

    public RadioGroup        mContentRg;
    public NoScrollViewPager mContentViewPager;
    public TitleView         mMainTitleview;

    public int mCurrRbIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        // 告诉它内容的区域
        setContentView(R.layout.fragment_main_content);

        initView();

        initData();

        initListener();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mContentRg = (RadioGroup) findViewById(R.id.main_radiogroup);
        mContentViewPager = (NoScrollViewPager) findViewById(
                R.id.main_viewPager);
        mMainTitleview = (TitleView) findViewById(R.id.main_titleview);

    }

    private void initData() {
        // 设置预加载的页面数
        mContentViewPager.setOffscreenPageLimit(0);

        // 给ViePager设置适配器
        FragmentManager fragmentManager = getSupportFragmentManager();

        mContentViewPager.setAdapter(new MyAdapter(fragmentManager));

        // 初始化选中首页
        mContentRg.check(R.id.content_rb_home);
    }

    /**
     * 初始化监听
     */
    public void initListener() {

        mContentRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                changeGroupAndView(checkedId, 0);
            }
        });

        mContentViewPager.setOnPageChangeListener(this);

        mContentViewPager.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        MainUI.this.onPageSelected(0);

                        mContentViewPager.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
    }

    public void changeGroupAndView(int checkedId, int curposition) {
        if (checkedId != 0) {
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
        } else {
            //肯定，就是根据position来改变，viewpager的位置了
            int rbId = 0;
            switch (curposition) {
                case 0:
                    rbId = R.id.content_rb_home;
                    break;
                case 1:
                    rbId = R.id.content_rb_search;
                    break;
                case 2:
                    rbId = R.id.content_rb_comment;
                    break;
                case 3:
                    rbId = R.id.content_rb_shopping;
                    break;
                case 4:
                    rbId = R.id.content_rb_mine;
                    break;
            }
            mContentRg.check(rbId);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        BaseFragment fragment = TabFragmentFactory.createFragment(position);
        if (fragment instanceof HomeFragment) {
            mMainTitleview.isTitleDaohang(false);
        } else {
            mMainTitleview.isTitleDaohang(true);
        }

        if (fragment != null && fragment.mLoadingPager != null) {
            fragment.mLoadingPager.triggerLoadData();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyAdapter extends FragmentPagerAdapter {
        FragmentManager mManager;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mManager = fm;
        }

        @Override
        public int getCount() {

            return mContentRg.getChildCount();
        }

        @Override
        public Fragment getItem(int position) {

            BaseFragment fragment = TabFragmentFactory.createFragment(position);
            System.out.println(fragment);

            return fragment;
        }
    }

    /**
     * 不保存实例状态
     *
     * @param outState
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        /*super.onSaveInstanceState(outState, outPersistentState);*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
       /* super.onSaveInstanceState(outState);*/
    }
}

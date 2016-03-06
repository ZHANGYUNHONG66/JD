package com.jerry.jingdong.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.controller.CommentFragment;
import com.jerry.jingdong.controller.HomeFragment;
import com.jerry.jingdong.controller.MineFragment;
import com.jerry.jingdong.controller.SearchFragment;
import com.jerry.jingdong.controller.ShoppingFragment;
import com.jerry.jingdong.utils.UIUtils;
import com.jerry.jingdong.views.NoScrollViewPager;

public class ContentFragment extends Fragment {

    @Bind(R.id.content_rg)
    RadioGroup                 mContentRg;
    @Bind(R.id.content_viewPager)
    NoScrollViewPager          mContentViewPager;

    private int                mCurrRbIndex   = -1;
    private List<BaseFragment> mBaseFragments = new ArrayList<>();


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
    }

    public void initData() {

        mBaseFragments.add(new HomeFragment());
         mBaseFragments.add(new SearchFragment());
         mBaseFragments.add(new CommentFragment());
         mBaseFragments.add(new ShoppingFragment());
         mBaseFragments.add(new MineFragment());

        // 设置预加载的页面数
        mContentViewPager.setOffscreenPageLimit(0);

        // 给ViePager设置适配器
        mContentViewPager.setAdapter(new MyAdapter());

        // 初始化选中首页
        mContentRg.check(R.id.content_rb_home);
    }

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
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }

}

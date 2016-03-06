package com.jerry.jingdong.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.controller.CommentController;
import com.jerry.jingdong.controller.HomeController;
import com.jerry.jingdong.controller.MineController;
import com.jerry.jingdong.controller.SearchController;
import com.jerry.jingdong.controller.ShoppingController;
import com.jerry.jingdong.utils.UIUtils;
import com.jerry.jingdong.views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContentFragment extends BaseFragment {

    @Bind(R.id.content_rg)
    RadioGroup                   mContentRg;
    @Bind(R.id.content_viewPager)
    NoScrollViewPager            mContentViewPager;

    private int                  mCurrRbIndex     = -1;
    private List<BaseController> mBaseControllers = new ArrayList<>();


    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_content, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        mBaseControllers.add(new HomeController(getActivity()));
        mBaseControllers.add(new SearchController(getActivity()));
        mBaseControllers.add(new CommentController(getActivity()));
        mBaseControllers.add(new ShoppingController(getActivity()));
        mBaseControllers.add(new MineController(getActivity()));

        // 设置预加载的页面数
        mContentViewPager.setOffscreenPageLimit(0);

        // 给ViePager设置适配器
        mContentViewPager.setAdapter(new MyAdapter());

        // 初始化选中首页
        mContentRg.check(R.id.content_rb_home);
    }

    @Override
    public void initListener() {
        super.initListener();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container,
                savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mBaseControllers != null)
                return mBaseControllers.size();
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BaseController controller = mBaseControllers.get(position);
            View rootView = controller.mRootView;

            // 添加到容器
            container.addView(rootView);

            controller.mLoadingPager.triggerLoadData();


            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                Object object) {
            container.removeView((View) object);
        }
    }

}

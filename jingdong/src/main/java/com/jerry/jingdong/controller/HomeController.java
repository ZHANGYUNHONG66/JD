package com.jerry.jingdong.controller;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.FrameLayout;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.holder.home.DownHolder;
import com.jerry.jingdong.holder.home.MiddleHolder;
import com.jerry.jingdong.holder.home.UpHolder;
import com.jerry.jingdong.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeController extends BaseController {

    @Bind(R.id.home_content_up_container)
    FrameLayout mHomeContentUpContainer;
    @Bind(R.id.home_content_middle_container)
    FrameLayout mHomeContentMiddleContainer;
    @Bind(R.id.home_content_down_container)
    FrameLayout mHomeContentDownContainer;

    public HomeController(Context context) {
        super(context);
    }

    /**
     * 初始化导航栏,使用本页面的搜索导航栏，将原来的gone掉
     */
    @Override
    public void initTitle() {
        // 隐藏原来的导航栏
        mRlDaohangTitleContainer.setVisibility(View.GONE);

        mLlDaohangSearchContainer.setVisibility(View.VISIBLE);

    }

    /**
     * 在子线程中加载数据
     *
     * @return
     */
    @Override
    public LoadingPager.LoadResultState initData() {
        SystemClock.sleep(2000);
        return LoadingPager.LoadResultState.SUCCESS;
    }

    /**
     * 初始化请求成功后的内容视图
     */
    @Override
    protected View initSuccessView() {
        View homeView = View.inflate(UIUtils.getContext(),
                R.layout.home_content_view, null);
        ButterKnife.bind(this, homeView);

        // TODO：三个页面需要设置数据
        UpHolder upHolder = new UpHolder();
        mHomeContentUpContainer.addView(upHolder.mRootView);
        upHolder.setDataAndRefreshView(null);

        MiddleHolder middleHolder = new MiddleHolder();
        mHomeContentMiddleContainer.addView(middleHolder.mRootView);
        middleHolder.setDataAndRefreshView(null);

        DownHolder downHolder = new DownHolder();
        mHomeContentDownContainer.addView(downHolder.mRootView);
        downHolder.setDataAndRefreshView(null);

        return homeView;
    }

}

package com.jerry.jingdong.controller;

import android.os.SystemClock;
import android.view.View;

import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.holder.mine.AccountCentreHolder;

/**
 * 我的
 */

public class MineFragment extends BaseFragment {

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
    public View initSuccessView() {
        /**
         * 临时显示
         */
        // LoginHolder holder = new LoginHolder();
        AccountCentreHolder holder = new AccountCentreHolder();
        return holder.mRootView;

    }
}

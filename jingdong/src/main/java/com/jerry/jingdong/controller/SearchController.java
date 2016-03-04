package com.jerry.jingdong.controller;/*
                                      * @创建者     Jerry
                                      * @创建时间   2016/2/5 21:58
                                      * @描述      分类
                                      */

import android.content.Context;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.utils.UIUtils;

/**
 * 搜索
 */

public class SearchController extends BaseController {

    public SearchController(Context context) {
        super(context);
    }

    /**
     * 初始化导航栏
     */
    @Override
    public void initDaoHang() {

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
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(getClass().getSimpleName());
        tv.setTextSize(20);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}

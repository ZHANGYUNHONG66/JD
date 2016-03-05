package com.jerry.jingdong.base;

import android.view.View;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 12:33
 * @包名: com.jerry.jingdong.base
 * @工程名: JingDong
 * @描述: TODO
 */
public abstract class BaseHolder<HOLDERBEANTYPE> {
    // 持有一个根布局
    public View           mRootView;

    public HOLDERBEANTYPE mData;

    /**
     * 使用构造方法初始化视图，并进行tag绑定
     */
    public BaseHolder() {
        mRootView = initRootView();

        mRootView.setTag(this);
    }

    /**
     * 接收数据，进行数据和视图的绑定
     *
     * @param data
     */
    public void setDataAndRefreshView(HOLDERBEANTYPE data) {
        mData = data;

        refreshView(data);
    }

    /**
     * 初始化根布局，交给子类去具体实现
     *
     * @return
     */
    public abstract View initRootView();

    /**
     * 数据和视图的绑定，更新UI，交给子类去具体实现
     *
     * @param data
     */
    protected abstract void refreshView(HOLDERBEANTYPE data);
}

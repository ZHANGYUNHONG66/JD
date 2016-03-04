package com.jerry.jingdong.base;

import android.content.Context;
import android.view.View;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/4 20:05
 * @包名: com.jerry.jingdong.base
 * @工程名: JingDong
 * @描述: TODO
 */
public abstract class BaseController {
    public Context     mContext;
    public View        mRootView;

    public BaseController(Context context) {
        this.mContext = context;
        this.mRootView = initView();
    }

    /**
     * 初始化View
     */
    public abstract View initView();


    /**
     * 初始化加载数据，子类选择性实现这个方法
     */
    public void initData() {

    }

    /**
     * 基类不知道谁要切换到哪一个视图，但是子类自己知道，让子类自己实现
     * @param position
     */
    public void switchContent(int position) {

    }
}

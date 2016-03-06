package com.jerry.jingdong.base;

import android.content.Context;
import android.view.View;

import com.jerry.jingdong.utils.UIUtils;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/4 20:05
 * @包名: com.jerry.jingdong.base
 * @工程名: JingDong
 * @描述: ViewPager五个页面的基类
 */
public abstract class BaseController {
    public Context      mContext;
    public View         mRootView;

    public LoadingPager mLoadingPager;

    public BaseController(Context context) {
        this.mContext = context;
        this.mRootView = initContentView();
    }

    public View initContentView() {
        if (mLoadingPager == null) {
            mLoadingPager = new LoadingPager(UIUtils.getContext()) {
                @Override
                public LoadResultState initData() {
                    return BaseController.this.initData();
                }

                @Override
                public View initSuccessView() {
                    return BaseController.this.initSuccessView();
                }
            };
        }

        return mLoadingPager;
    }

    /**
     * 在子线程中加载数据，子类必须实现
     */
    public abstract LoadingPager.LoadResultState initData();

    /**
     * 初始化成功视图，子类必须实现
     */
    protected abstract View initSuccessView();

}

package com.jerry.jingdong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.utils.UIUtils;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 20:35
 * @包名: com.jerry.jingdong.fragment
 * @工程名: JingDong
 * @描述: TODO
 */
public abstract class OrderBaseFragment extends Fragment {
    public LoadingPager mLoadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // 优化LoadingPager重复创建
        if (mLoadingPager == null) {
            mLoadingPager = new LoadingPager(UIUtils.getContext()) {
                /**
                 * 同样交给BaseFragment的子类去完成
                 */
                @Override
                public LoadResultState initData() {
                    return OrderBaseFragment.this.initData();
                }

                /**
                 * 同样交给BaseFragment的子类去完成
                 */
                @Override
                public View initSuccessView() {
                    return OrderBaseFragment.this.initSuccessView();
                }
            };
        }

        mLoadingPager.triggerLoadData();

        return mLoadingPager;
    }

    /**
     * @des 在子线程中加载数据的方法
     * @des 不知道每个视图需要展现什么样的数据，所以定义成抽象方法让子类去实现
     *
     * @return 加载数据后呈现视图的视图状态码
     */
    public abstract LoadingPager.LoadResultState initData();

    /**
     * @des 成功视图
     * @des 不知道每个Fragment的成功视图需要展现什么样的数据，所以定义成抽象方法让子类去实现
     *
     * @return 加载数据成功后的成功视图
     */
    public abstract View initSuccessView();
}

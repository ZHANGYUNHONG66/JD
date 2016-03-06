package com.jerry.jingdong.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.jingdong.utils.UIUtils;

import java.util.List;
import java.util.Map;

public abstract class BaseFragment extends Fragment {

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
                    return BaseFragment.this.initData();
                }

                /**
                 * 同样交给BaseFragment的子类去完成
                 */
                @Override
                public View initSuccessView() {
                    return BaseFragment.this.initSuccessView();
                }
            };
        }

        return mLoadingPager;
    }

    /**
     * 校验请求网络返回的数据的状态
     *
     * @param resObj
     * @return
     */
    public LoadingPager.LoadResultState checkState(Object resObj) {
        if (resObj == null) {
            return LoadingPager.LoadResultState.EMPTY;
        }

        if (resObj instanceof List) {
            if (((List) resObj).size() == 0) {
                return LoadingPager.LoadResultState.EMPTY;
            }
        }

        if (resObj instanceof Map) {
            if (((Map) resObj).size() == 0) {
                return LoadingPager.LoadResultState.EMPTY;
            }
        }

        return LoadingPager.LoadResultState.SUCCESS;
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

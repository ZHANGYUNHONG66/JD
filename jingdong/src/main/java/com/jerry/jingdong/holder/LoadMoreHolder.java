package com.jerry.jingdong.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 12:33
 * @包名: com.jerry.jingdong
 * @工程名: JingDong
 * @描述: TODO
 */
public class LoadMoreHolder extends BaseHolder<Integer> {
    public static final int LOADDATA_LOADING = 0;         // 正在加载更多的状态
    public static final int LOADDATA_RETRY   = 1;         // 加载更多失败，点击重试的状态
    public static final int LOADDATA_NONE    = 2;         // 没有加载更多的状态

    @Bind(R.id.item_loadmore_container_loading)
    LinearLayout            mItemLoadmoreContainerLoading;
    @Bind(R.id.item_loadmore_container_retry)
    LinearLayout            mItemLoadmoreContainerRetry;


    @Override
    protected void refreshView(Integer data) {
        // 先隐藏两个控件
        mItemLoadmoreContainerLoading.setVisibility(View.GONE);
        mItemLoadmoreContainerRetry.setVisibility(View.GONE);

        System.out.println(data);

        // 根据状态显示控件
        switch (data) {
        case LOADDATA_LOADING:
            mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
            break;
        case LOADDATA_RETRY:
            mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
            break;
        case LOADDATA_NONE:
            break;
        }

    }

    @Override
    public View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(),
                R.layout.item_loadmore_view, null);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

}

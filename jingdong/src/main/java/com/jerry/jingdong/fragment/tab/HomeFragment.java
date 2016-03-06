package com.jerry.jingdong.fragment.tab;

import android.os.SystemClock;
import android.view.View;
import android.widget.FrameLayout;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.holder.home.DownHolder;
import com.jerry.jingdong.holder.home.MiddleHolder;
import com.jerry.jingdong.holder.home.UpHolder;
import com.jerry.jingdong.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment {

    @Bind(R.id.home_content_up_container)
    FrameLayout mHomeContentUpContainer;
    @Bind(R.id.home_content_middle_container)
    FrameLayout mHomeContentMiddleContainer;
    @Bind(R.id.home_content_down_container)
    FrameLayout mHomeContentDownContainer;

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

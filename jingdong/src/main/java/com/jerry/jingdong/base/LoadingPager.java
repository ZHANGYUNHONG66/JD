package com.jerry.jingdong.base;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.jerry.jingdong.R;
import com.jerry.jingdong.factory.ThreadPoolProxyFactory;
import com.jerry.jingdong.utils.UIUtils;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/4 19:14
 * @包名: com.jerry.jingdong.base
 * @工程名: JingDong
 * @描述: TODO
 */
public abstract class LoadingPager extends FrameLayout {
    /** 错误视图 */
    public View             mErrorView;
    /** 空视图 */
    public View             mEmptyView;
    /** 加载中视图 */
    public View             mLoadingView;
    /** 成功视图 */
    public View             mSuccessView;

    /** 错误视图状态码 */
    public static final int STATE_ERROR   = 0;
    /** 空视图状态码 */
    public static final int STATE_EMPTY   = 1;
    /** 加载中视图状态码 */
    public static final int STATE_LOADING = 2;
    /** 成功视图状态码 */
    public static final int STATE_SUCCESS = 3;

    /** 当前视图状态，默认为加载中视图状态 */
    public int              mCurrState    = STATE_LOADING;

    private Button          mErrorBtnRetry;

    private LoadDataTask    mLoadDataTask;

    public LoadingPager(Context context) {
        super(context);

        initCommonView();
    }

    /**
     * 初始化基本的三种视图：错误、加载中、空
     */
    public void initCommonView() {
        /** 初始化错误视图 */
        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error,
                null);
        mErrorBtnRetry = (Button) mErrorView.findViewById(R.id.error_btn_retry);
        mErrorBtnRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 当点击错误视图的重试按钮时重新加载数据
                triggerLoadData();
            }
        });

        /** 初始化空视图 */
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty,
                null);

        /** 初始化加载中视图 */
        mLoadingView = View.inflate(UIUtils.getContext(),
                R.layout.pager_loading, null);

        // 添加到本类这个容器中
        addView(mErrorView);
        addView(mEmptyView);
        addView(mLoadingView);

        refreshViewByState();
    }

    /**
     * @des 根据状态刷新视图
     * @called 初始化LoadingPager时调用
     * @called 当数据加载完成需要更新UI界面时调用
     */
    public void refreshViewByState() {
        /** 控制错误视图的显示和隐藏 */
        mErrorView.setVisibility(
                mCurrState == STATE_ERROR ? View.VISIBLE : View.GONE);

        /** 控制空视图的显示和隐藏 */
        mEmptyView.setVisibility(
                mCurrState == STATE_EMPTY ? View.VISIBLE : View.GONE);

        /** 控制加载中视图的显示和隐藏 */
        mLoadingView.setVisibility(
                mCurrState == STATE_LOADING ? View.VISIBLE : View.GONE);

        /** 当状态时成功并且成功视图为空的时候再初始化成功视图 */
        if (mCurrState == STATE_SUCCESS && mSuccessView == null) {
            mSuccessView = initSuccessView();
            addView(mSuccessView);
        }

        /** 控制成功视图的显示和隐藏 */
        if (mSuccessView != null)
            mSuccessView.setVisibility(
                    mCurrState == STATE_SUCCESS ? View.VISIBLE : View.GONE);

    }

    /**
     * @des 触发加载数据
     * @called 当外界需要LoadingPager加载数据的时候调用
     */
    public void triggerLoadData() {
        // 优化加载成功和当前页面正在加载中的时候无需再次加载,也就是状态码等于成功和加载数据的任务没有结束的时候
        if (mCurrState != STATE_SUCCESS && mLoadDataTask == null) {

            // 优化开始加载数据的时候应该显示加载中视图
            mCurrState = STATE_LOADING;
            // 更改状态后刷新视图
            refreshViewByState();

            // 异步加载数据
            mLoadDataTask = new LoadDataTask();

            // new Thread(mLoadDataTask).start();

            // 使用线程池执行异步加载数据任务
            ThreadPoolProxyFactory.createNormalThreadPool()
                    .execute(mLoadDataTask);
        }
    }

    /**
     * 异步加载数据的线程类
     */
    private class LoadDataTask implements Runnable {
        @Override
        public void run() {
            // 加载数据，得到数据，更改状态码
            LoadResultState loadResultState = initData();
            mCurrState = loadResultState.getState();

            // 根据状态重新更新UI，这里是子线程，所以要用Handler的post方法放到主线程中执行
            MyApplication.getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    refreshViewByState();
                }
            });

            // 在加载数据的任务结束后将任务对象置空
            mLoadDataTask = null;
        }
    }

    public enum LoadResultState {
        SUCCESS(STATE_SUCCESS), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY);

        public int getState() {
            return state;
        }

        int state;

        LoadResultState(int state) {
            this.state = state;
        }
    }

    /**
     * @des 在子线程中加载数据的方法
     * @des 不知道每个视图需要展现什么样的数据，所以定义成抽象方法让子类去实现
     * @called 当外界需要LoadingPager加载数据的时候调用
     *
     * @return 加载数据后呈现视图的视图状态码
     */
    public abstract LoadResultState initData();

    /**
     * @des 初始化成功视图
     * @des 不知道每个Fragment的成功视图需要展现什么样的数据，所以定义成抽象方法让子类去实现
     * @called 当外界需要LoadingPager加载数据，加载完成，并且加载成功的时候调用
     *
     * @return 加载数据成功后的成功视图
     */
    public abstract View initSuccessView();

}

package com.jerry.jingdong.base;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.utils.UIUtils;

import java.util.List;
import java.util.Map;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/4 20:05
 * @包名: com.jerry.jingdong.base
 * @工程名: JingDong
 * @描述: ViewPager五个页面的基类
 */
public abstract class BaseController {
    public Context       mContext;
    public View          mRootView;

    public TextView      mTvTitle;
    public Button        mBtnLeft, mBtnRight;
    private FrameLayout  mFlContentContainer;
    public LoadingPager mLoadingPager;

    public BaseController(Context context) {
        this.mContext = context;
        this.mRootView = initCommonView();
    }

    /**
     * 初始化标题栏
     */
    public View initCommonView() {
        View view = View.inflate(UIUtils.getContext(),
                R.layout.base_controller_view, null);

        // 找孩子,因为子类无法继承使用注解方式的成员变量，所以使用findview
        mTvTitle = (TextView) view.findViewById(R.id.base_controller_tv_title);
        mBtnLeft = (Button) view.findViewById(R.id.base_controller_btn_left);
        mBtnRight = (Button) view.findViewById(R.id.base_controller_btn_right);
        mFlContentContainer = (FrameLayout) view
                .findViewById(R.id.base_controller_fl_content);

        mFlContentContainer.addView(initContentView());

        mBtnLeft.setVisibility(isLeftBtnVisible() ? View.VISIBLE : View.GONE);
        mBtnRight.setVisibility(isRightBtnVisible() ? View.VISIBLE : View.GONE);

        mBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 告诉外界，让外界处理
                onLeftBtnClick();
            }
        });

        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 告诉外界，让外界处理
                onRightBtnClick();
            }
        });

        initTitle();

        return view;
    }

    /**
     * 初始化导航栏：文字或者左右按钮，每个页面都不同，子类必须实现
     */
    public abstract void initTitle();

    /**
     * 当右边按钮被点击
     */
    public void onRightBtnClick() {

    }

    /**
     * 当返回按钮被点击
     */
    public void onLeftBtnClick() {

    }

    /**
     * 设置左边按钮是否可见，有的页面没有按钮，通过该方法设置,默认不可见
     */
    public boolean isLeftBtnVisible() {
        return false;
    }

    /**
     * 设置右边按钮是否可见，有的页面有按钮，通过该方法设置，默认不可见
     */
    public boolean isRightBtnVisible() {
        return false;
    }

    /**
     * 初始化内容区域,子类必须实现
     */
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
     * 校验请求网络返回的数据的状态
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
     * 在子线程中加载数据，子类必须实现
     */
    public abstract LoadingPager.LoadResultState initData();

    /**
     * 初始化成功视图，子类必须实现
     */
    protected abstract View initSuccessView();

}

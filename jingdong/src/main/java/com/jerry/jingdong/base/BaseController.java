package com.jerry.jingdong.base;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.utils.UIUtils;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/4 20:05
 * @包名: com.jerry.jingdong.base
 * @工程名: JingDong
 * @描述: ViewPager五个页面的基类
 */
public abstract class BaseController {
    public Context mContext;
    public View    mRootView;

    public TextView mTvTitle;
    public Button   mBtnLeft, mBtnRight;
    public LoadingPager mLoadingPager;

    // 普通标题栏容器
    public RelativeLayout mRlDaohangTitleContainer;
    // 首页搜索标题栏容器
    public LinearLayout   mLlDaohangSearchContainer;
    // 内容区域容器
    public FrameLayout    mFlContentContainer;

    // 首页标题搜索栏的两个子View
    public EditText    mHomeSearchEdit;
    public ImageButton mHomeSearchBtn;
    public View        mBaseView;

    public BaseController(Context context) {
        this.mContext = context;
        this.mRootView = initCommonView();
    }

    /**
     * 初始化标题栏
     */
    public View initCommonView() {
        mBaseView = View.inflate(UIUtils.getContext(),
                R.layout.base_controller_view, null);

        // 找孩子,因为子类无法继承使用注解方式的成员变量，所以使用findview
        mTvTitle = (TextView) mBaseView.findViewById(R.id.base_controller_tv_title);
        mBtnLeft = (Button) mBaseView.findViewById(R.id.base_controller_btn_left);
        mBtnRight = (Button) mBaseView.findViewById(R.id.base_controller_btn_right);
        mFlContentContainer = (FrameLayout) mBaseView
                .findViewById(R.id.base_controller_fl_content);


        // 普通标题栏容器
        mRlDaohangTitleContainer = (RelativeLayout) mBaseView
                .findViewById(R.id.base_controller_rl_daohang_titlecontainer);
        // 首页搜索栏容器
        mLlDaohangSearchContainer = (LinearLayout) mBaseView
                .findViewById(R.id.base_controller_ll_daohang_searchcontainer);

        mHomeSearchEdit = (EditText) mBaseView
                .findViewById(R.id.home_daohang_search_tv);
        mHomeSearchBtn = (ImageButton) mBaseView
                .findViewById(R.id.home_daohang_search_btn);

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

        return mBaseView;
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
     * 在子线程中加载数据，子类必须实现
     */
    public abstract LoadingPager.LoadResultState initData();

    /**
     * 初始化成功视图，子类必须实现
     */
    protected abstract View initSuccessView();

}

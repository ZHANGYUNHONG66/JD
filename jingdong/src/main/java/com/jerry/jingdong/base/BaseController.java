package com.jerry.jingdong.base;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.utils.UIUtils;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/4 20:05
 * @包名: com.jerry.jingdong.base
 * @工程名: JingDong
 * @描述: TODO
 */
public abstract class BaseController {
    public Context      mContext;
    public View         mRootView;

    public TextView     mTvTitle;
    public Button       mBtnLeft, mBtnRight;
    private FrameLayout mFlContentContainer;

    private boolean     isLeftBtnVisible  = true;
    private boolean     isRightBtnVisible = false;

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

        initDaoHang();

        return view;
    }

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
    public abstract View initContentView();

    /**
     * 初始化导航栏：文字或者左右按钮，每个页面都不同，子类必须实现
     */
    public abstract void initDaoHang();

    /**
     * 初始化加载数据，子类选择性实现这个方法
     */
    public void initData() {

    }

}

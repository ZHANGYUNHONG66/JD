package com.jerry.jingdong.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.view.View;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.holder.mine.LoginHolder;
import com.jerry.jingdong.utils.UIUtils;
import com.jerry.jingdong.holder.mine.AccountCentreHolder;

/**
 * 我的
 */

public class MineController extends BaseController {

    SharedPreferences mSp;
    private Boolean mIsLogin;

	public MineController(Context context) {
		super(context);
	}

    public MineController(Context context) {
        super(context);
    }


    /**
     * 初始化导航栏
     */
    @Override
    public void initTitle() {
        mTvTitle.setText("登录");
        mBtnLeft.setText("返回");
    }

    /**
     * 当返回按钮被点击
     */
    public void onLeftBtnClick() {
        Toast.makeText(UIUtils.getContext(), "返回已点击", Toast.LENGTH_SHORT).show();
    }
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
     * 设置左边按钮是否可见，有的页面没有按钮，通过该方法设置,默认不可见
     */
    public boolean isLeftBtnVisible() {
        return true;
    }

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
    protected View initSuccessView() {
        //创建SharedPreferences对象
        mSp = UIUtils.getContext().getSharedPreferences("data", 0);
        //获取当前是否已登录的标记
        mIsLogin = mSp.getBoolean("isLogin", false);
        if (mIsLogin) {
            //如果已登录,就跳转到用户中心的页面
            View view = View.inflate(UIUtils.getContext(), R.layout.account_centre_view, null);
            return view;
        } else {
            //如果没登录,就跳转到登录的页面
            LoginHolder holder = new LoginHolder();
            return holder.mRootView;
        }
//        View view = null;
//        if(mIsLogin){
//            view = View.inflate(UIUtils.getContext(), R.layout.account_centre_view, null);
//        }else{
//            view = View.inflate(UIUtils.getContext(), R.layout.login_view, null);
//        }
//        return view;

    }
}

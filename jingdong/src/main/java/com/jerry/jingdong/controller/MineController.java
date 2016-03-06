package com.jerry.jingdong.controller;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.holder.mine.AccountCentreHolder;
import com.jerry.jingdong.utils.UIUtils;

/**
 * 我的
 */

public class MineController extends BaseController {


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
		/**
		 * 临时显示
		 */
//		LoginHolder holder = new LoginHolder();
		AccountCentreHolder holder = new AccountCentreHolder();
		return holder.mRootView;

	}
}

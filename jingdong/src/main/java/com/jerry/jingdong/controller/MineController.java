package com.jerry.jingdong.controller;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;

import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.holder.item.LoginHolder;

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
		LoginHolder holder = new LoginHolder();
		return holder.mRootView;

	}
}

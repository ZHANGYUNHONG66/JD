package com.jerry.jingdong.controller;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.utils.UIUtils;

/**
 * 品牌
 */

public class CommentController extends BaseController {


	public CommentController(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		TextView tv = new TextView(UIUtils.getContext());
		tv.setText(getClass().getSimpleName());
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(20);
		return tv;
	}
}

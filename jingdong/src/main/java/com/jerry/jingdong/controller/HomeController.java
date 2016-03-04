package com.jerry.jingdong.controller;/*
                                      			* @创建者     Jerry
                                      			* @创建时间   2016/2/5 21:58
                                      			* @描述      主页
                                      			*/

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.utils.UIUtils;

public class HomeController extends BaseController {

    public HomeController(Context context) {
        super(context);
    }


    @Override
    public View initContentView() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(getClass().getSimpleName());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);
        return tv;
    }

    @Override
    public void initDaoHang() {

    }

}

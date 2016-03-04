package com.jerry.jingdong.controller;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.utils.UIUtils;

/**
 * 购物
 */

public class ShoppingController extends BaseController {


    public ShoppingController(Context context) {
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

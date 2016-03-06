package com.jerry.jingdong.controller;

import android.content.Context;
import android.view.View;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.utils.UIUtils;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/6 17:33
 * @包名: com.jerry.jingdong.controller
 * @工程名: JingDong
 * @描述: TODO
 */
public class OrderController extends BaseController {
    public OrderController(Context context) {
        super(context);
    }

    @Override
    public LoadingPager.LoadResultState initData() {

        return LoadingPager.LoadResultState.SUCCESS;
    }

    @Override
    protected View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.bb, null);
        return view;
    }
}

package com.jerry.jingdong.controller;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jerry.jingdong.R;
import com.jerry.jingdong.activity.MyOrderActivity;
import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.utils.ActivityUtils;
import com.jerry.jingdong.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeController extends BaseController {

    @Bind(R.id.btn_toMyOrder)
    Button mBtnToMyOrder;

    public HomeController(Context context) {
        super(context);
    }

    /**
     * 初始化导航栏
     */
    @Override
    public void initTitle() {
        mTvTitle.setText("首页");
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
        View view = View.inflate(UIUtils.getContext(), R.layout.aa, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_toMyOrder)
    public void toMyOrder(View view) {
        Log.d("11111", "11111111111111111111");
        ActivityUtils.notActivity2Activity(MyOrderActivity.class);
    }

}

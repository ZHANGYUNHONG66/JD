package com.jerry.jingdong.controller;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.utils.UIUtils;
import com.jerry.jingdong.views.TitleView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索
 */

public class SearchController extends BaseController {

    @Bind(R.id.title_daohang)
    TitleView mTitleDaohang;
    @Bind(R.id.btn_toMyOrder)
    Button    mBtnToMyOrder;

    public SearchController(Context context) {
        super(context);
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

        mTitleDaohang.getTvTitle().setText("搜索");
        return view;
    }

    @OnClick(R.id.btn_toMyOrder)
    public void toMyOrder(View v) {
        OrderController controller = new OrderController(UIUtils.getContext());
        controller.mLoadingPager.triggerLoadData();
        initContentView();
    }

}

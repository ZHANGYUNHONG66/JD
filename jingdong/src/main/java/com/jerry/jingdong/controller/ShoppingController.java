package com.jerry.jingdong.controller;

import android.content.Context;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jerry.jingdong.application.MyApplication;
import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.entity.CartInfoBean;
import com.jerry.jingdong.protocol.ProductProtocol;
import com.jerry.jingdong.utils.CartParamsUtils;
import com.jerry.jingdong.utils.UIUtils;

import org.xutils.http.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class ShoppingController extends BaseController {

    private ProductProtocol                  mCartProtocol;
    private List<CartInfoBean.ProductEntity> mProductEntities;//商品集合
    private boolean                          mIsLogin;
    private CartParamsUtils mCartParamsUtils;

    public ShoppingController(Context context) {
        super(context);
    }

    /**
     * 初始化导航栏
     */
    @Override
    public void initTitle() {
        mTvTitle.setText("杨哥的购物车");
    }

    /**
     * 在子线程中加载数据
     *
     * @return
     */
    @Override
    public LoadingPager.LoadResultState initData() {
        SystemClock.sleep(2000);

        mCartProtocol = new ProductProtocol();

        mProductEntities = new ArrayList<CartInfoBean.ProductEntity>();

        try {
            for (int i = 0; i < 40; i++) {
                HashMap<String, String> map = new HashMap<>();
                map.put("pId", i + "");
                CartInfoBean cartInfoBean = mCartProtocol.loadData(HttpMethod.GET, map, null);
                mProductEntities.add(cartInfoBean.product);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadResultState.ERROR;
        }

        mIsLogin = MyApplication.isLogin();//拿到当前登录状态

        if(mIsLogin){//已登录状态
            mCartParamsUtils = new CartParamsUtils();



        }

        return LoadingPager.LoadResultState.SUCCESS;
    }

    /**
     * 初始化请求成功后的内容视图
     */
    @Override
    protected View initSuccessView() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(getClass().getSimpleName());
        tv.setTextSize(20);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}

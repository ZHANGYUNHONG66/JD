package com.jerry.jingdong.protocol;

import com.google.gson.Gson;
import com.jerry.jingdong.entity.CartNewBean;

import okhttp3.OkHttpClient;

/**
 * Created by MiKoKatie on 2016/3/5 14:36.
 *
 * @ 描述  ${TODO}
 * @ 版本  $Rev$
 * @ 更新者  $Author$
 * @ 更新时间  $Date$
 */
public class ProductProtocol extends BasePlusProtocol<CartNewBean> {

    @Override
    public String getInterfaceKey() {
        OkHttpClient okHttpClient=new OkHttpClient();

        return "product";
    }

    @Override
    public CartNewBean parseJsonString(String resultJsonString) {
        return new Gson().fromJson(resultJsonString, CartNewBean.class);
    }

}

package com.jerry.jingdong.protocol;

import com.google.gson.Gson;
import com.jerry.jingdong.entity.BrandListBean;


public class BrandProtocal extends BaseProtocol<BrandListBean> {

    @Override
    public String getInterfaceKey() {
        return "brand";
    }

    @Override
    public BrandListBean parseJsonString(String jsonString) {
        Gson gson = new Gson();
        BrandListBean brandListBean = gson.fromJson(jsonString, BrandListBean.class);
        return brandListBean;
    }
}

package com.jerry.jingdong.protocol;

import com.google.gson.Gson;
import com.jerry.jingdong.entity.HomeInfoBean;


public class HomeProtocal extends BasePlusProtocol<HomeInfoBean> {

    @Override
    public String getInterfaceKey() {
        return "home";
    }

    @Override
    public HomeInfoBean parseJsonString(String jsonString) {
        Gson gson = new Gson();
        HomeInfoBean homeBean = gson.fromJson(jsonString, HomeInfoBean.class);
        return homeBean;
    }
}

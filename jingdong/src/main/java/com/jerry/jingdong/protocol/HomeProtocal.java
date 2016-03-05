package com.jerry.jingdong.protocol;

import com.google.gson.Gson;
import com.jerry.jingdong.entity.HomeInfoBean;


public class HomeProtocal extends BaseProtocol<HomeInfoBean> {

    @Override
    public String getInterfaceKey() {
        return "home";
    }

    @Override
    public HomeInfoBean parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
        HomeInfoBean homeInfoBean = gson.fromJson(resultJsonString, HomeInfoBean.class);
        return homeInfoBean;
    }
}

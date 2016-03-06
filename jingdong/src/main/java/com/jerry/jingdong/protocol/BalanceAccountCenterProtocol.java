package com.jerry.jingdong.protocol;

import com.google.gson.Gson;
import com.jerry.jingdong.entity.BalanceAccountCenterInfo;

/**
 * 项目名:    JingDong
 * 包名:      com.jerry.jingdong.protocol
 * 文件名:    BalanceAccountCenterProtocol
 * 创建者:    任洛仟
 * 创建时间:  2016/03/05 下午 2:44
 * 描述:      TODO
 */
public class BalanceAccountCenterProtocol extends BasePlusProtocol {
    @Override
    public String getInterfaceKey() {
        return "checkout";
    }

    @Override
    public Object parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(resultJsonString, BalanceAccountCenterInfo.class);
    }
}

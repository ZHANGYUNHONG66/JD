package com.jerry.jingdong.protocol;

import com.jerry.jingdong.entity.OrderInfo;

import java.util.List;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 14:30
 * @包名: com.jerry.jingdong.protocol
 * @工程名: JingDong
 * @描述: 我的订单页面请求数据Protocol
 */
public class MyOrderProtocol extends BaseProtocol<List<OrderInfo>> {
    @Override
    public String getInterfaceKey() {
        // TODO: 请求我的订单数据
        return null;
    }

    @Override
    public List<OrderInfo> parseJsonString(String resultJsonString) {
        return null;
    }
}

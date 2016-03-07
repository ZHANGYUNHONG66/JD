package com.jerry.jingdong.entity;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 10:17
 * @包名: com.jerry.jingdong.entity
 * @工程名: JingDong
 * @描述: 订单列表显示数据
 */

public class OrderInfo{

    public String orderId; // 订单ID
    public String status;  // 订单显示状态
    public String time;    // 下单时间
    public String price;   // 订单金额
    public int flag;    // 订单标识，1.可删除可修改，2.不可修改，3.已完成


    // TODO:模拟数据
    public OrderInfo(String orderId, String status, String time, String price,
            int flag) {
        this.orderId = orderId;
        this.status = status;
        this.time = time;
        this.price = price;
        this.flag = flag;
    }
}

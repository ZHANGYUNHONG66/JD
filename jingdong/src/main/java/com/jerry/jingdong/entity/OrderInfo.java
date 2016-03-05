package com.jerry.jingdong.entity;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 10:17
 * @包名: com.jerry.jingdong.entity
 * @工程名: JingDong
 * @描述: 订单列表显示数据
 */

public class OrderInfo {
    private String orderId; // 订单ID
    private String status;  // 订单显示状态
    private String time;    // 下单时间
    private String price;   // 订单金额
    private String flag;    // 订单标识，1.可删除可修改，2.不可修改，3.已完成
}

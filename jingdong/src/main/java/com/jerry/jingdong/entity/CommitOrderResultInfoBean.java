package com.jerry.jingdong.entity;

import java.io.Serializable;

/**
 * 项目名:    JingDong
 * 包名:      com.jerry.jingdong.entity
 * 文件名:    CommitOrderResultInfoBean
 * 创建者:    任洛仟
 * 创建时间:  2016/03/07 下午 12:30
 * 描述:      TODO
 */
public class CommitOrderResultInfoBean{
    public Order orderInfo;//	Object
    public String response;//	orderSumbit

    public class Order  implements Serializable {
       public String orderId	;//    032096
       public String paymentType;//	1
       public String price	    ;//   450
    }
}

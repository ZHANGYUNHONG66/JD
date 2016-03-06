package com.jerry.jingdong.entity;

import java.util.List;

/**
 * 项目名:    JingDong
 * 包名:      com.jerry.jingdong.entity
 * 文件名:    BillInfoBean
 * 创建者:    任洛仟
 * 创建时间:  2016/03/06 上午 11:22
 * 描述:      TODO
 */
public class BillInfoBean {
    public List<Invoice> invoice;//	Array
    public String response;//	invoice

    public class Invoice {
        public String content;//	图书
        public int id;//1
    }
}

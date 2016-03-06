package com.jerry.jingdong.protocol;

import com.google.gson.Gson;
import com.jerry.jingdong.entity.SaleProductBean;

/*
 * @创建者     Administrator
 * @创建时间   2016/3/5 0005 15:05
 * @描述	      ${TODO}
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */
public class HotProductProtocol extends BaseProtocol<SaleProductBean> {

    private String interfaceKey;
    public HotProductProtocol(String interfaceKey) {
        this.interfaceKey = interfaceKey;
    }

    @Override
    public String getInterfaceKey() {
        return interfaceKey;
    }

    @Override
    public SaleProductBean parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
        return  gson.fromJson(resultJsonString, SaleProductBean.class);
    }
}

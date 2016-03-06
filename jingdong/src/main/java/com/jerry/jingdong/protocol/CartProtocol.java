package com.jerry.jingdong.protocol;

import com.google.gson.Gson;
import com.jerry.jingdong.entity.CartInfoBean;

/**
 * Created by MiKoKatie on 2016/3/5 20:36.
 *
 * @ 描述  ${TODO}
 * @ 版本  $Rev$
 * @ 更新者  $Author$
 * @ 更新时间  $Date$
 */
public class CartProtocol extends BasePlusProtocol<CartInfoBean> {
    @Override
    public String getInterfaceKey() {
        return "cart";
    }

    @Override
    public CartInfoBean parseJsonString(String resultJsonString) {
        return new Gson().fromJson(resultJsonString, CartInfoBean.class);
    }
}

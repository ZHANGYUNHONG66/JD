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
    @Override
    public String getInterfaceKey() {
        return "hotproduct";
    }

    @Override
    public SaleProductBean parseJsonString(String resultJsonString) {
        /*String TextJson = "{\"listCount\":15,\"productList\":[{\"id\":18,\"marketPrice\":200,\"name\":\"短裙\"," +
                "\"pic\":\"/images/product/detail/q16.jpg\",\"price\":160},{\"id\":22,\"marketPrice\":200," +
                "\"name\":\"新款毛衣\",\"pic\":\"/images/product/detail/q20.jpg\",\"price\":160},{\"id\":25," +
                "\"marketPrice\":150,\"name\":\"新款秋装\",\"pic\":\"/images/product/detail/q23.jpg\",\"price\":160}," +
                "{\"id\":26,\"marketPrice\":200,\"name\":\"粉色系暖心套装\",\"pic\":\"/images/product/detail/q24.jpg\"," +
                "\"price\":200},{\"id\":27,\"marketPrice\":150,\"name\":\"韩版粉嫩外套\"," +
                "\"pic\":\"/images/product/detail/q25.jpg\",\"price\":160},{\"id\":28,\"marketPrice\":300," +
                "\"name\":\"春装新款\",\"pic\":\"/images/product/detail/q26.jpg\",\"price\":200},{\"id\":29," +
                "\"marketPrice\":180,\"name\":\"日本奶粉\",\"pic\":\"/images/product/detail/q26.jpg\",\"price\":160}," +
                "{\"id\":30,\"marketPrice\":200,\"name\":\"超凡奶粉\",\"pic\":\"/images/product/detail/q26.jpg\"," +
                "\"price\":160},{\"id\":31,\"marketPrice\":260,\"name\":\"天籁牧羊奶粉\"," +
                "\"pic\":\"/images/product/detail/q26.jpg\",\"price\":200},{\"id\":32,\"marketPrice\":300," +
                "\"name\":\"fullcare奶粉\",\"pic\":\"/images/product/detail/q26.jpg\",\"price\":300},{\"id\":33," +
                "\"marketPrice\":300,\"name\":\"雀巢奶粉\",\"pic\":\"/images/product/detail/q26.jpg\",\"price\":200}," +
                "{\"id\":34,\"marketPrice\":200,\"name\":\"安婴儿奶粉\",\"pic\":\"/images/product/detail/q26.jpg\"," +
                "\"price\":200},{\"id\":35,\"marketPrice\":200,\"name\":\"贝贝羊金装奶粉\"," +
                "\"pic\":\"/images/product/detail/q26.jpg\",\"price\":160},{\"id\":36,\"marketPrice\":200," +
                "\"name\":\"飞雀奶粉\",\"pic\":\"/images/product/detail/q26.jpg\",\"price\":160},{\"id\":37," +
                "\"marketPrice\":200,\"name\":\"智力圆环\",\"pic\":\"/images/product/detail/q26.jpg\",\"price\":200}," +
                "{\"id\":38,\"marketPrice\":180,\"name\":\"音乐小天才\",\"pic\":\"/images/product/detail/q26.jpg\"," +
                "\"price\":160},{\"id\":39,\"marketPrice\":180,\"name\":\"小叮当\",\"pic\":\"/images/product/detail/q26" +
                ".jpg\",\"price\":160},{\"id\":40,\"marketPrice\":180,\"name\":\"智力图形记忆起\"," +
                "\"pic\":\"/images/product/detail/q26.jpg\",\"price\":160},{\"id\":1,\"marketPrice\":500," +
                "\"name\":\"韩版时尚蕾丝裙\",\"pic\":\"/images/product/detail/c3.jpg\",\"price\":350}]," +
                "\"response\":\"hotProduct\"}";*/
        Gson gson = new Gson();
        return  gson.fromJson(resultJsonString, SaleProductBean.class);
    }
}

package com.jerry.jingdong.entity;

import java.util.List;

/*
 * @创建者     Administrator
 * @创建时间   2016/3/5 0005 10:18
 * @描述	      ${TODO}
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */
public class SaleProductBean {
    public int                listCount;//15
    public List<SaleInfoBean> productList;//	Array
    public String             response;// hotProduct

    public class SaleInfoBean {
        public int    id;//	18
        public float  marketPrice;//	200
        public String name;//	短裙
        public String pic;//	/images/product/detail/q16.jpg
        public float  price;//	160
    }
}

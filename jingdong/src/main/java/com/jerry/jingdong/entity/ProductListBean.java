package com.jerry.jingdong.entity;

import java.util.List;

/**
 * Created by yip on 2016/3/7 15:04.
 *
 * @ 版本  $Rev$
 * @ 更新者  $Author$ yip
 */
public class ProductListBean {

    public int    listCount;
    public String response;

    public List<ListFilterEntity> listFilter;


    public List<ProductListEntity> productList;

    public static class ListFilterEntity {
        public String                key;


        public List<ValueListEntity> valueList;

        public static class ValueListEntity {
            public String id;
            public String name;
        }
    }

    public static class ProductListEntity {
        public int    commentCount;
        public int    id;
        public int    marketPrice;
        public String name;
        public String pic;
        public int    price;
    }
}

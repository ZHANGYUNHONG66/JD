package com.jerry.jingdong.entity;

import java.util.List;

/**
 * Created by MiKoKatie on 2016/3/5 20:31.
 *
 * @ 描述  ${TODO}
 * @ 版本  $Rev$
 * @ 更新者  $Author$
 * @ 更新时间  $Date$
 */
public class CartInfoBean {

    public String response;
    public int    totalCount;
    public int    totalPoint;
    public int    totalPrice;

    public List<CartEntity> cart;
    public List<String>     prom;

    public static class CartEntity {
        public int prodNum;
        public boolean isSeleced = true;

        public boolean isSeleced() {
            return isSeleced;
        }

        public void setSeleced(boolean seleced) {
            isSeleced = seleced;
        }

        public ProductEntity product;

        public static class ProductEntity {
            public int    buyLimit;
            public int    id;
            public String name;
            public String number;
            public String pic;
            public int    price;

            public List<ProductPropertyEntity> productProperty;

            public static class ProductPropertyEntity {
                public int    id;
                public String k;
                public String v;
            }
        }
    }
}

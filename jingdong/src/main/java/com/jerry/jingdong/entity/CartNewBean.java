package com.jerry.jingdong.entity;

import java.util.List;

/**
 * Created by MiKoKatie on 2016/3/5 14:30.
 *
 * @ 描述  ${TODO}
 * @ 版本  $Rev$
 * @ 更新者  $Author$
 * @ 更新时间  $Date$
 */
public class CartNewBean {

    public ProductEntity product;

    public String        response;

    public static class ProductEntity {
        public boolean      available;
        public int          buyLimit;
        public int          commentCount;
        public int          id;
        public String       inventoryArea;
        public int          leftTime;
        public int          limitPrice;
        public int          marketPrice;
        public String       name;
        public int          price;
        public int          score;
        public List<String> bigPic;
        public List<String> pics;

        public List<ProductBeanEntity> productProperty;

        public static class ProductBeanEntity {
            public int    id;
            public String k;
            public String v;
        }
    }
}

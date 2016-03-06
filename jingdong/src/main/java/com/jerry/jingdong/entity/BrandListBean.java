package com.jerry.jingdong.entity;

import java.util.List;


public class BrandListBean {


    public String response;

    public List<BrandAreaBean> brand;

    public static class BrandAreaBean {
        public int    id;
        public String key;

        public List<BrandBean> value;

        public static class BrandBean {
            public int    id;
            public String name;
            public String pic;
        }
    }
}

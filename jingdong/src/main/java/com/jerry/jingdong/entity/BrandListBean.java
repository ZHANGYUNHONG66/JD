package com.jerry.jingdong.entity;

import java.util.List;

/**
 * Created by MiKoKatie on 2016/3/5 19:12.
 *
 * @ 描述  ${TODO}
 * @ 版本  $Rev$
 * @ 更新者  $Author$
 * @ 更新时间  $Date$
 */
public class BrandListBean {


    public String response;

    public List<BrandEntity> brand;

    public static class BrandEntity {
        public int    id;
        public String key;

        public List<ValueEntity> value;

        public static class ValueEntity {
            public int    id;
            public String name;
            public String pic;
        }
    }
}

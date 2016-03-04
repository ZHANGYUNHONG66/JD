package com.jerry.jingdong.entity;

import java.util.List;

/**
 * Created by MiKoKatie on 2016/3/4 21:30.
 *
 * @ 描述  ${TODO}
 * @ 版本  $Rev$
 * @ 更新者  $Author$
 * @ 更新时间  $Date$
 */
public class HomeInfoBean {

    public String                response;

    public List<HomeTopicEntity> homeTopic;

    public static class HomeTopicEntity {
        public int    id;
        public String pic;
        public String title;
    }
}

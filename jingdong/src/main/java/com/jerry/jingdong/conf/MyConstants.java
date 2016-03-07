package com.jerry.jingdong.conf;

import com.jerry.jingdong.utils.LogUtils;

/**
 * @创建者: Jerry
 * @创建时间: 2016/2/24 14:15
 * @包名: com.jerry.googleplay.conf
 * @工程名: GooglePlay
 * @描述: TODO
 */
public interface MyConstants {
    /**
     * LEVEL_ALL:打开应用程序里面所有输入的日志 7 LEVEL_OFF:关闭应用程序里面所有输入的日志 0
     */
    int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    public static final long PROTOCOLTIMEOUT = 5 * 60 * 1000; // 协议缓存时间为5分钟

    public static final class URL {
//        		public static final String BASEURL = "http://192.168.165.1:8080/Market/";
        public static final String BASEURL = "http://188.188.5.1:8080/market/";


        public static final String PRODUCT = "";
        public static final String DETAIL  = BASEURL + "/product";
    }
}

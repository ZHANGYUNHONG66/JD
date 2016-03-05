package com.jerry.jingdong.utils;

import java.util.Map;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/3 18:33
 * @包名: com.jerry.googleplay.utils
 * @工程名: GooglePlay
 * @描述: 与网络相关的工具类
 */
public class HttpsUtils {
    /**
     * 将map转换成url
     *
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}

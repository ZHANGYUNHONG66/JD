package com.jerry.jingdong.utils;

import com.jerry.jingdong.base.LoadingPager;

import java.util.List;
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

    /**
     * 校验请求网络返回的数据的状态
     */
    public LoadingPager.LoadResultState checkState(Object resObj) {
        if (resObj == null) {
            return LoadingPager.LoadResultState.EMPTY;
        }

        if (resObj instanceof List) {
            if (((List) resObj).size() == 0) {
                return LoadingPager.LoadResultState.EMPTY;
            }
        }

        if (resObj instanceof Map) {
            if (((Map) resObj).size() == 0) {
                return LoadingPager.LoadResultState.EMPTY;
            }
        }

        return LoadingPager.LoadResultState.SUCCESS;
    }
}

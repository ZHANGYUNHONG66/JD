package com.jerry.jingdong.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.jerry.jingdong.base.MyApplication;


/**
 * @创建者: Jerry
 * @创建时间: 2016/2/24 14:12
 * @包名: com.jerry.googleplay.utils
 * @工程名: GooglePlay
 * @描述: UI的一些工具类
 */
public class UIUtils {
    /** 得到上下文 */
    public static Context getContext() {
        return MyApplication.getContext();
    }

    /** 得到Resource对象 */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /** 得到string.xml中的字符 */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /** 得到string.xml中的字符数组 */
    public static String[] getStringArr(int resId) {
        return getResources().getStringArray(resId);
    }

    /** 得到color.xml中的颜色值 */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /** 得到应用程序的包名 */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /** dp转px */
    public static int dp2px(int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp,
                getResources().getDisplayMetrics()) + .5f);
    }

    /** px转dp */
    public static int px2dp(int px) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px,
                getResources().getDisplayMetrics()) + .5f);
    }
}

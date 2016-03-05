package com.jerry.jingdong.utils;

import android.content.Intent;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 2:31
 * @包名: com.jerry.jingdong.utils
 * @工程名: JingDong
 * @描述: Activity工具类
 */
public class ActivityUtils {
    /**
     * 从不是Activity的上下文中跳转到Activity页面
     * 
     * @param type
     */
    public static void notActivity2Activity(Class type) {
        Intent intent = new Intent(UIUtils.getContext(), type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIUtils.getContext().startActivity(intent);
    }
}

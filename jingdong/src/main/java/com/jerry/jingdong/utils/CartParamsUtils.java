package com.jerry.jingdong.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.jerry.jingdong.application.MyApplication;

/**
 * Created by MiKoKatie on 2016/3/5 15:01.
 *
 * @ 描述  ${动态改变购物车的URL参数}
 * @ 版本  $Rev$
 * @ 更新者  $Author$
 * @ 更新时间  $Date$
 */
public class CartParamsUtils {

    SPUtils mSPUtils = new SPUtils(UIUtils.getContext(), "cart");

    /**
     * @param pId
     * @param number
     * @param color
     * @param size
     * @des 添加购物车
     */
    public void addCart(String pId, String number, String color, String size) {
        if (!MyApplication.isLogin()) {
            Toast.makeText(UIUtils.getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        int userId = MyApplication.getmUserId();
        String good = mSPUtils.getString(userId + "", "");
        String value = pId + ":" + number + ":" + color + "," + size;
        if (good.equals("")) {
            mSPUtils.putString(userId + "", value);
        } else {
            mSPUtils.putString(userId + "", good + "|" + value);
        }
    }

    /**
     * @param pId
     * @param number
     * @param color
     * @param size
     * @des 清空购物车
     */
    public void deleteCart(String pId, String number, String color, String size) {
        if (!MyApplication.isLogin()) {
            Toast.makeText(UIUtils.getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        int userId = MyApplication.getmUserId();
        String good = mSPUtils.getString(userId + "", "");
        String value = pId + ":" + number + ":" + color + "," + size;
        String[] split = good.split("|");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            int temp = 0;
            if (value.equals(split[i])) {
                split[i] = "";
            } else {
                temp++;
                if (temp == 1) {
                    sb.append(split[i]);
                } else {
                    sb.append("|" + split[i]);
                }
            }
            mSPUtils.putString(userId + "", sb.toString());
        }
    }

    public String getString(String userId) {
        return mSPUtils.getString(userId, "");
    }


    public void clearData(String userId) {
        String string = mSPUtils.getString(userId, "");
        if (TextUtils.isEmpty(string)) {
            return;
        }
        mSPUtils.putString(userId, "");
    }
}

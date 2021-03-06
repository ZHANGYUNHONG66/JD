package com.jerry.jingdong.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.jerry.jingdong.activity.MainUI;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过application拿到全局唯一对象
 */
public class MyApplication extends Application {
    public static MainUI  sMainActivity;
    public static Context mContext;
    public static int     mMainThreadId;
    public static Handler mMainThreadHandler;
    public static boolean isLogin = false;//默认状态未登录
    public static  Activity mMainActivity;
    private static int      mUserId;//存放UserId

    public Map<String, String> mProtocolMap = new HashMap<>();//保存协议缓存

    public static void setmUserId(int mUserId) {
        MyApplication.mUserId = mUserId;
    }

    public static int getmUserId() {

        return mUserId;
    }


    public static boolean isLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        MyApplication.isLogin = isLogin;
    }

    public Map<String, String> getProtocolMap() {
        return mProtocolMap;
    }

    /**
     * 得到上下文
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 得到主线程id
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 得到主线程hanlder
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    @Override
    public void onCreate() {// 程序的入口方法
        super.onCreate();

        // 上下文
        mContext = getApplicationContext();

        // 主线程的Id
        mMainThreadId = android.os.Process.myTid();

        // 主线程的Handler
        mMainThreadHandler = new Handler();
    }

    /**
     * 获取MainActivity的对象
     *
     * @param mainActivity
     */
    public static void getMainActivity(MainUI mainActivity) {
        sMainActivity = mainActivity;
    }

}

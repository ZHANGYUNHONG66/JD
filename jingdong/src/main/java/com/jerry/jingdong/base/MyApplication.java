package com.jerry.jingdong.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * @创建者: Jerry
 * @创建时间: 2016/2/24 14:13
 * @包名: com.jerry.googleplay.base
 * @工程名: GooglePlay
 * @描述: 全局,单例,放置一些全局的方法,属性
 */
public class MyApplication extends Application {
    private static Context            mContext;
    private static Handler            mHandler;
    private static long               mMainThreadId;

    // 保存在内存中的协议缓存集合
    public static Map<String, String> mCacheMaps = new HashMap<>();

    /** 得到上下文 */
    public static Context getContext() {
        return mContext;
    }

    /** 得到主线程的handler */
    public static Handler getMainHandler() {
        return mHandler;
    }

    /** 得到主线程的线程id */
    public static long getMainThreadId() {
        return mMainThreadId;
    }

    @Override
    public void onCreate() {// 程序的入口方法

        /*--------------- 创建应用里面需要用到的一些共有的属性 ---------------*/
        // 1.上下文
        mContext = getApplicationContext();

        // 2.主线程handler
        mHandler = new Handler();

        // 3.主线程的id
        /**
         * Tid: thread Pid: process Uid: user
         */
        mMainThreadId = android.os.Process.myTid();

        super.onCreate();
    }
}

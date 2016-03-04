package com.jerry.jingdong.factory;


import com.jerry.jingdong.manager.ThreadPoolProxy;

/**
 * @创建�? Jerry
 * @创建时间: 2016/2/27 18:42
 * @包名: com.jerry.googleplay.factory
 * @工程�? GooglePlay
 * @描述: TODO
 */
public class ThreadPoolProxyFactory {

    private static ThreadPoolProxy mNormalThreadPool;
    private static ThreadPoolProxy mDownLoadThreadPool;

    /**
     * 创建普�?线程�?
     *
     * @return ThreadPoolProxy
     */
    public static ThreadPoolProxy createNormalThreadPool() {
        if (mNormalThreadPool == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPool == null) {
                    mNormalThreadPool = new ThreadPoolProxy(5, 5);
                }
            }
        }
        return mNormalThreadPool;
    }

    /**
     * 创建下载线程�?
     * 
     * @return ThreadPoolProxy
     */
    public static ThreadPoolProxy createDownLoadThreadPool() {
        if (mDownLoadThreadPool == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mDownLoadThreadPool == null) {
                    mDownLoadThreadPool = new ThreadPoolProxy(3, 3);
                }
            }
        }
        return mDownLoadThreadPool;
    }
}

package com.jerry.jingdong.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPoolProxy {

    private int        mCorePoolSize;
    private int        mMaximumExecutorSize;
    ThreadPoolExecutor mExecutor;

    public ThreadPoolProxy(int corePoolSize, int maximumExecutorSize) {
        mCorePoolSize = corePoolSize;
        mMaximumExecutorSize = maximumExecutorSize;
    }

    /**
     * 初始化线程池
     */
    public void initThreadPoolExecutor() {
        if (mExecutor == null || mExecutor.isShutdown()
                || mExecutor.isTerminated()) {
            synchronized (ThreadPoolProxy.class) {
                if (mExecutor == null || mExecutor.isShutdown()
                        || mExecutor.isTerminated()) {
                    long keepAliveTime = 5000;
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

                    mExecutor = new ThreadPoolExecutor(mCorePoolSize, // 核心线程�?
                            mMaximumExecutorSize, // 线程池最大线程数
                            keepAliveTime, // 保持时间
                            unit, // 保持时间的单�?
                            workQueue);// 任务队列
                }
            }
        }
    }

    /*
     * 提交任务和执行任务的区别�?
     * 返回值：提交任务有返回�?，执行任务没�?
     * 返回值Future的作用，执行任务的结果，其中包含get和cancel方法，get是一个阻塞方法，会一直等到执行结果返�?
     */
    /**
     * 提交任务
     */
    public Future<?> submit(Runnable task) {
        initThreadPoolExecutor();
        return mExecutor.submit(task);
    }

    /**
     * 执行任务
     */
    public void execute(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }

    /**
     * 移除任务
     */
    public void remove(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }
}

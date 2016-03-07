package com.jerry.jingdong.views;
/**
 * @创建者     Jerry
 * @创建时间   2016/2/12 15:25
 * @描述      自定义一个不可以滚动的ViewPager,这个类最终的处理是只要对onTouchEvent方法进行处理就可以达到让ViewPager不滚动
 * 			  而子孩子中又可以对点击事件进行处理的效果//
 * 			  继承自LazyViewPager,实现ViewPager的懒加载，每次只加载当前显示的界面//
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends LazyViewPager {

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 一般情况下不需要处理这个方法，无论返回什么结果都不会分发事件
     * 
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否拦截事件 拦截：会走到自己的onTouchEvent方法中 不拦截：将事件传递给子孩子
     * 
     * @param ev
     * @return 这里返回super和false都可以达到孩子能获取事件，但是让ViewPager不滚动的效果
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
        // return false;
    }

    /**
     * 是否自己消费事件 消费：事件到此为止 不消费：将事件回传给父容器处理
     * 
     * @param ev
     * @return false,代表这个ViewPager不能滚动，因为不会走父容器中的move处理，ViewPager是靠对move的处理完成滚动
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
        // return true;//返回true也可行，因为我消费掉事件，但是没有对事件的三个分支进行处理
    }
}

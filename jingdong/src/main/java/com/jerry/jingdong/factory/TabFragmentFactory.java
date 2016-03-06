package com.jerry.jingdong.factory;

import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.controller.CommentFragment;
import com.jerry.jingdong.controller.HomeFragment;
import com.jerry.jingdong.controller.MineFragment;
import com.jerry.jingdong.controller.SearchFragment;
import com.jerry.jingdong.controller.ShoppingFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/6 22:13
 * @包名: com.jerry.jingdong.factory
 * @工程名: JingDong
 * @描述: TODO
 */
public class TabFragmentFactory {
    /**
     * 根据不同的值创建不同的Fragment
     */
    public static final int                  FRAGMENT_HOME     = 0;
    public static final int                  FRAGMENT_SEARCH   = 1;
    public static final int                  FRAGMENT_COMMENT  = 2;
    public static final int                  FRAGMENT_SHOPPING = 3;
    public static final int                  FRAGMENT_MINE     = 4;

    // 定义一个内存中的集合，保存创建过的Fragment实例
    public static Map<Integer, BaseFragment> mFragments        = new HashMap<>();

    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = null;
        // 判断内存中是否有存在的fragment实例，有就直接从内存中拿
        if (mFragments.containsKey(position)) {
            fragment = mFragments.get(position);
            return fragment;
        }

        switch (position) {
        case FRAGMENT_HOME:
            fragment = new HomeFragment();
            break;
        case FRAGMENT_SEARCH:
            fragment = new SearchFragment();
            break;
        case FRAGMENT_COMMENT:
            fragment = new CommentFragment();
            break;
        case FRAGMENT_SHOPPING:
            fragment = new ShoppingFragment();
            break;
        case FRAGMENT_MINE:
            fragment = new MineFragment();
            break;
        }

        // 添加到集合
        mFragments.put(position, fragment);

        return fragment;
    }
}

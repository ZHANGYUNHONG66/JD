package com.jerry.jingdong.factory;

import com.jerry.jingdong.fragment.CancelOrderFragment;
import com.jerry.jingdong.fragment.OrderBaseFragment;
import com.jerry.jingdong.fragment.TenMinuteAgoOrderFragment;
import com.jerry.jingdong.fragment.TenMinuteOrderFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 20:31
 * @包名: com.jerry.jingdong.factory
 * @工程名: JingDong
 * @描述: TODO
 */
public class FragmentFactory {
    /**
     * 根据不同的值创建不同的Fragment
     */
    public static final  int    FRAGMENT_TENMINUTE    = 0;
    public static final  int    FRAGMENT_TENMINUTEAGO = 1;
    public static final  int    FRAGMENT_CANCEL       = 2;

    // 定义一个内存中的集合，保存创建过的Fragment实例
    public static Map<Integer, OrderBaseFragment> mFragments = new HashMap<>();

    public static OrderBaseFragment createFragment(int position) {
        OrderBaseFragment fragment = null;
//        // 判断内存中是否有存在的fragment实例，有就直接从内存中拿
//        if (mFragments.containsKey(position)) {
//            fragment = mFragments.get(position);
//            return fragment;
//        }

        switch (position) {
            case FRAGMENT_TENMINUTE:
                fragment = new TenMinuteOrderFragment();
                break;
            case FRAGMENT_TENMINUTEAGO:
                fragment = new TenMinuteAgoOrderFragment();
                break;
            case FRAGMENT_CANCEL:
                fragment = new CancelOrderFragment();
                break;
        }

        // 添加到集合
        mFragments.put(position, fragment);

        return fragment;
    }
}

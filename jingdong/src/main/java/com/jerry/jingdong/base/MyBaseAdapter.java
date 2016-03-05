package com.jerry.jingdong.base;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/4 22:40
 * @包名: com.jerry.jingdong.base
 * @工程名: JingDong
 * @描述: 继承BaseAdapter的ListView、GridView的基类
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private List<T> mDatas;

    public MyBaseAdapter(List<T> datas) {
        mDatas = datas;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDatas != null) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

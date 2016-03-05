package com.jerry.jingdong.adapter;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.base.SuperBaseAdapter;
import com.jerry.jingdong.entity.OrderInfo;
import com.jerry.jingdong.holder.item.OrderListItemHolder;

import java.util.List;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 20:55
 * @包名: com.jerry.jingdong.adapter
 * @工程名: JingDong
 * @描述: TODO
 */
public class OrderItemAdapter extends SuperBaseAdapter<OrderInfo> {

    public OrderItemAdapter(AbsListView absListView, List datas) {
        super(absListView, datas);
    }

    /**
     * 是否有加载更多数据，由子类覆写
     *
     * @return
     */
    @Override
    public boolean haseMoreData() {
        return true;
    }

    @Override
    public BaseHolder getSpacialHolder(int position) {
        OrderListItemHolder holder = new OrderListItemHolder();
        return holder;
    }

    /**
     * 从基类中获取条目的点击事件
     */
    @Override
    public void onNormalItenClick(AdapterView<?> parent, View view,
            int position, long id) {

    }
}

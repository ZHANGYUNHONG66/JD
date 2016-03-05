package com.jerry.jingdong.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.MyBaseAdapter;
import com.jerry.jingdong.entity.OrderListInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/4 23:03
 * @包名: com.jerry.jingdong.activity
 * @工程名: JingDong
 * @描述: 我的订单页面,跳转到这个页面需要传递订单列表的数据
 */
public class MyOrderFormActivity extends Activity {
    @Bind(R.id.myorder_btn_back)
    Button       mMyorderBtnBack;
    @Bind(R.id.myorder_rg)
    RadioGroup   mMyorderRg;
    @Bind(R.id.myorder_listview)
    ListView     mMyorderListview;
    @Bind(R.id.myorder_empty_container)
    LinearLayout mMyorderEmptyContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_form);
        ButterKnife.bind(this);

        initData();


    }

    private void initData() {
        mMyorderListview.setAdapter(null);
    }

    private class OrderListAdapter extends MyBaseAdapter<List<OrderListInfo>> {

        public OrderListAdapter(List<List<OrderListInfo>> datas) {
            super(datas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}

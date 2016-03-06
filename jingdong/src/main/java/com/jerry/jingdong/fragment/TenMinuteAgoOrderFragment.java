package com.jerry.jingdong.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.jerry.jingdong.adapter.OrderItemAdapter;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.entity.OrderInfo;
import com.jerry.jingdong.factory.ListViewFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 20:33
 * @包名: com.jerry.jingdong.fragment
 * @工程名: JingDong
 * @描述: TODO
 */
public class TenMinuteAgoOrderFragment extends OrderBaseFragment {

    private List<OrderInfo> mDatas;

    @Override
    public LoadingPager.LoadResultState initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            OrderInfo data = new OrderInfo("12312", getClass().getSimpleName(), "1439528260115",
                    "208", 1);
            mDatas.add(data);
        }

        return LoadingPager.LoadResultState.SUCCESS;
    }

    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.createListView();
        listView.setAdapter(new TenMinuteOrderAdapter(listView, mDatas));
        return listView;
    }

    private class TenMinuteOrderAdapter extends OrderItemAdapter {

        public TenMinuteOrderAdapter(AbsListView absListView, List datas) {
            super(absListView, datas);
        }

        /**
         * 在子线程中更新数据
         */
        @Override
        public List<OrderInfo> loadMoreData() throws IOException {
            mDatas = new ArrayList();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy/MM/dd HH:mm:ss");
            for (int i = 0; i < 20; i++) {
                String time = String.valueOf(new Random().nextInt());

                OrderInfo data = new OrderInfo("1231" + i, getClass().getSimpleName(),
                        dateFormat.format(new Date(Long.parseLong(time))), "20" + i, 1);

                mDatas.add(data);
            }
            return mDatas;
        }
    }
}

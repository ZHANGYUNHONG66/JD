package com.jerry.jingdong.holder.item;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.entity.OrderInfo;
import com.jerry.jingdong.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 16:35
 * @包名: com.jerry.jingdong.holder.item
 * @工程名: JingDong
 * @描述: TODO
 */
public class OrderListItemHolder extends BaseHolder<OrderInfo> {
    @Bind(R.id.item_order_tv_id)
    TextView mItemOrderTvId;
    @Bind(R.id.item_order_tv_price)
    TextView mItemOrderTvPrice;
    @Bind(R.id.item_order_tv_state)
    TextView mItemOrderTvState;
    @Bind(R.id.item_order_tv_time)
    TextView mItemOrderTvTime;

    @Override
    public View initRootView() {
        View itemView = View.inflate(UIUtils.getContext(),
                R.layout.item_order_state, null);
        ButterKnife.bind(this, itemView);
        return itemView;
    }

    @Override
    protected void refreshView(OrderInfo data) {
        Log.d("11111", "刷新数据");
        mItemOrderTvId.setText("订单编号：" + data.orderId);
        mItemOrderTvPrice.setText("订单总额：" + data.price);
        mItemOrderTvState.setText("状态：" + data.status);
        mItemOrderTvTime.setText(data.time);
    }
}

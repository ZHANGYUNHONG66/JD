package com.jerry.jingdong.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.entity.CommitOrderResultInfoBean;
import com.jerry.jingdong.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名:    JingDong
 * 包名:      com.jerry.jingdong.activity
 * 文件名:    CommitOrderActivity
 * 创建者:    任洛仟
 * 创建时间:  2016/03/05 下午 6:15
 * 描述:      订单提交成功
 */
public class CommitOrderActivity extends Activity {
    @Bind(R.id.order_num)
    TextView mOrderNum;
    @Bind(R.id.order_result_tv_money)
    TextView mOrderResultTvMoney;
    @Bind(R.id.order_result_tv_pay_way)
    TextView mOrderResultTvPayWay;
    @Bind(R.id.order_result_btn_keepon_shopping)
    Button mOrderResultBtnKeeponShopping;
    @Bind(R.id.order_result_btn_look_orderlist)
    Button mOrderResultBtnLookOrderlist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        CommitOrderResultInfoBean.Order orderInfo = (CommitOrderResultInfoBean.Order) getIntent().getSerializableExtra("commitResult");

        if (orderInfo == null) {
            return;
        }
        String orderNum = UIUtils.getResources().getString(R.string.order_num, orderInfo.orderId);//订单编号
        String orderPrice = UIUtils.getResources().getString(R.string.order_pay_money, orderInfo.price);//订单金额
        String orderPayWay = orderInfo.paymentType;//支付方式
        mOrderNum.setText(orderNum);
        mOrderResultTvMoney.setText(orderPrice);
        String payWay = null;
        switch (orderPayWay) {
            case "0":
                payWay = "到付-现金";
                break;
            case "1":
                payWay = "到付-POS机";
                break;
            case "2":
                payWay = "支付宝";
                break;
            default:
                break;
        }
        orderPayWay = UIUtils.getResources().getString(R.string.order_pay_way, payWay);
        mOrderResultTvPayWay.setText(orderPayWay);
    }

    private void initView() {
        setContentView(R.layout.activity_commit_order);
        ButterKnife.bind(this);
    }
}
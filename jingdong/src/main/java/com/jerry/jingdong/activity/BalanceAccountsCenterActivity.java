package com.jerry.jingdong.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jerry.jingdong.R;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.entity.BalanceAccountCenterInfo;
import com.jerry.jingdong.utils.UIUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 项目名:    JingDong
 * 包名:      com.jerry.jingdong.activity
 * 文件名:    BalanceAccountsCenterActivity
 * 创建者:    任洛仟
 * 创建时间:  2016/03/05 上午 8:41
 * 描述:      结算中心
 */
public class BalanceAccountsCenterActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "Activity";
    @Bind(R.id.balance_account_tv_back)
    TextView mBalanceAccountTvBack;
    @Bind(R.id.balance_account_tv_name)
    TextView mBalanceAccountTvName;
    @Bind(R.id.balance_account_tv_phone)
    TextView mBalanceAccountTvPhone;
    @Bind(R.id.balance_account_tv_addres)
    TextView mBalanceAccountTvAddres;
    @Bind(R.id.balance_account_rl_addres)
    RelativeLayout mBalanceAccountRlAddres;
    @Bind(R.id.balance_account_tv_pay_way)
    TextView mBalanceAccountTvPayWay;
    @Bind(R.id.balance_account_rl_pay_way)
    RelativeLayout mBalanceAccountRlPay;
    @Bind(R.id.balance_account_tv_time)
    TextView mBalanceAccountTvTime;
    @Bind(R.id.balance_account_tv_deliver_goods_way)
    TextView mBalanceAccountTvDeliverGoodsWay;
    @Bind(R.id.balance_account_rl_time_way)
    RelativeLayout mBalanceAccountRlTimeWay;
    @Bind(R.id.balance_account_tv_bill_type)
    TextView mBalanceAccountTvBillType;
    @Bind(R.id.balance_account_rl_bill)
    RelativeLayout mBalanceAccountRlBill;
    @Bind(R.id.balance_account_ll_iv_product_container)
    LinearLayout mBalanceAccountLlIvProductContainer;
    @Bind(R.id.balance_account_rl_product_detial)
    RelativeLayout mBalanceAccountRlProductDetial;
    @Bind(R.id.balance_account_tv_product_money)
    TextView mBalanceAccountTvProductMoney;
    @Bind(R.id.balance_account_tv_freight)
    TextView mBalanceAccountTvFreight;
    @Bind(R.id.balance_account_rl_pay_money)
    RelativeLayout mBalanceAccountRlPayMoney;
    @Bind(R.id.balance_account_ll_enjoy_container)
    LinearLayout mBalanceAccountLlEnjoyContainer;
    @Bind(R.id.balance_account_btn_commit)
    Button mBalanceAccountBtnCommit;
    private BalanceAccountCenterInfo mDatas;
    private BalanceAccountCenterInfo mAccountCenterInfo;
    private List<BalanceAccountCenterInfo.PaymentList> mPaymentList;
    private AlertDialog mPayDialog;
    private List<BalanceAccountCenterInfo.DelveryList> mDeliveryList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
        initData();
    }

    /**
     * 初始化界面数据
     */
    private void initData() {
        //子线程中请求数据
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder().add("sku", "1:3:1,2,3,4|2:2:2,3").build();
                    Request request = new Request.Builder().url(MyConstants.URL.BASEURL + "/checkout").header("userId", "154636").post(formBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String resJson = response.body().string();
                        parseJson(resJson);
                    } else {
//                        Toast.makeText(BalanceAccountsCenterActivity.this, "数据请求失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
//                    Toast.makeText(BalanceAccountsCenterActivity.this, "数据请求失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.start();

    }

    /**
     * 当数据加载成功的时候就去给界面设置数据
     */
    private void setDataRefreshView() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<BalanceAccountCenterInfo.ProductList> productList = mAccountCenterInfo.productList;
                for (int i = 0; i < productList.size(); i++) {
                    ImageView iv = new ImageView(UIUtils.getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80, 80);
                    if (i != 0) {
                        params.leftMargin = 5;
                    }
                    String url = MyConstants.URL.BASEURL + productList.get(i).product.pic;
                    Picasso.with(UIUtils.getContext()).load(url).into(iv);
                    mBalanceAccountLlIvProductContainer.addView(iv, params);
                }

                TextView tv = new TextView(UIUtils.getContext());
                tv.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tvParams.gravity = Gravity.CENTER_VERTICAL;
                tv.setText("共" + mAccountCenterInfo.checkoutAddup.totalCount + "件");
                mBalanceAccountLlIvProductContainer.addView(tv, tvParams);
                mBalanceAccountTvProductMoney.setText("￥" + mAccountCenterInfo.checkoutAddup.totalPrice);
                mBalanceAccountTvFreight.setText("+￥" + mAccountCenterInfo.checkoutAddup.freight);
                List<String> checkoutProm = mAccountCenterInfo.checkoutProm;
                for (int i = 0; i < checkoutProm.size(); i++) {
                    TextView tvEnjoy = new TextView(UIUtils.getContext());
                    tvEnjoy.setTextColor(Color.parseColor("#000000"));
                    tvEnjoy.setText(checkoutProm.get(i));
                    mBalanceAccountLlEnjoyContainer.addView(tvEnjoy);
                }

            }
        });

    }

    /**
     * 解析数据
     *
     * @param resJson
     */
    private void parseJson(String resJson) {
        Gson gson = new Gson();
        mAccountCenterInfo = gson.fromJson(resJson, BalanceAccountCenterInfo.class);
        if (mAccountCenterInfo != null) {
            setDataRefreshView();
        }
    }


    private void initEvent() {
        mBalanceAccountRlAddres.setOnClickListener(this);
        mBalanceAccountRlPay.setOnClickListener(this);
        mBalanceAccountRlTimeWay.setOnClickListener(this);
        mBalanceAccountRlBill.setOnClickListener(this);
        mBalanceAccountRlProductDetial.setOnClickListener(this);
        mBalanceAccountRlPayMoney.setOnClickListener(this);
        mBalanceAccountBtnCommit.setOnClickListener(this);
        mBalanceAccountTvBack.setOnClickListener(this);
    }

    private void initView() {
        setContentView(R.layout.activity_balance_account_center);
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.balance_account_tv_back://返回购物车
                Toast.makeText(this, "返回购物车", Toast.LENGTH_SHORT).show();
                break;
            case R.id.balance_account_rl_addres://添加地址
                Toast.makeText(this, "添加地址", Toast.LENGTH_SHORT).show();
                break;
            case R.id.balance_account_rl_pay_way://选择支付方式
//                Toast.makeText(this, "支付方式", Toast.LENGTH_SHORT).show();
                createDialog2PayWay();
                break;
            case R.id.balance_account_rl_time_way://选择送货方式和送货时间
                Toast.makeText(this, "送货方式及送货时间", Toast.LENGTH_SHORT).show();
                createDialog2TimeAndWay();
                break;
            case R.id.balance_account_rl_bill://索要发票
                Toast.makeText(this, "发票", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, BillInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.balance_account_rl_product_detial://商品明细
                Toast.makeText(this, "商品明细", Toast.LENGTH_SHORT).show();
                break;
            case R.id.balance_account_btn_commit://提交按钮
//                Toast.makeText(this, "提交订单", Toast.LENGTH_SHORT).show();
                commitData();//提交订单
                break;
        }
    }

    /**
     * 提交订单
     */
    private void commitData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder().add("sku", "1:3:1,2,3,4|2:2:2,3").add("addressId", "139").add("paymentType", "1").add("deliveryType", "1")
                            .add("invoiceType", "1").add("invoiceTitle", "传智慧播客教育科技有限公司").add("invoiceContent", "1").build();
                    Request request = new Request.Builder().url(MyConstants.URL.BASEURL + "/ordersumbit").header("userId", "154636").post(formBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String resJson = response.body().string();
                        Log.d(TAG, resJson);
//                        parseJson(resJson);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BalanceAccountsCenterActivity.this, "订单提交成功", Toast.LENGTH_SHORT).show();
                                Intent commitIntent = new Intent(UIUtils.getContext(), CommitOrderActivity.class);
                                startActivity(commitIntent);
                            }
                        });


                    } else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BalanceAccountsCenterActivity.this, "订单提交失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BalanceAccountsCenterActivity.this, "订单提交失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }.start();
    }

    /**
     * 创建选择时间和送货方式的对话框
     */
    private void createDialog2TimeAndWay() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.time_way_dialog_view, null);
        builder.setCustomTitle(view);
        AlertDialog dialog = builder.create();
        mDeliveryList = mAccountCenterInfo.deliveryList;
        builder.setSingleChoiceItems(new TimeAndWayAdapter(), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }

    /**
     * 创建选择支付方式的对话框
     */
    private void createDialog2PayWay() {

        final AlertDialog.Builder payBuilder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.pay_dialog_view, null);
        payBuilder.setCustomTitle(view);
        //支付方式列表
        mPaymentList = mAccountCenterInfo.paymentList;
        payBuilder.setSingleChoiceItems(new PayAdapter(), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        payBuilder.show();
    }

    class PayAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mPaymentList != null) {
                return mPaymentList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mPaymentList != null) {
                return mPaymentList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(UIUtils.getContext(), R.layout.item_balanceaccount_center_pay_way, null);
            TextView tv_pay_way = (TextView) view.findViewById(R.id.item_balanceaccount_tv_pay_way);
            final ImageButton ivIsSelect = (ImageButton) view.findViewById(R.id.item_balanceaccount_iv_isSelect);
            tv_pay_way.setText(mPaymentList.get(position).des);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBalanceAccountTvPayWay.setText(mPaymentList.get(position).des);

                }
            });
            return view;
        }
    }

    class TimeAndWayAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mDeliveryList != null) {
                return mDeliveryList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mDeliveryList != null) {
                return mDeliveryList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(UIUtils.getContext(), R.layout.item_balanceaccount_center_pay_way, null);
            TextView tv_pay_way = (TextView) view.findViewById(R.id.item_balanceaccount_tv_pay_way);
            tv_pay_way.setText(mDeliveryList.get(position).des);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBalanceAccountTvTime.setText(mDeliveryList.get(position).des);
                }
            });
            return view;
        }
    }
}
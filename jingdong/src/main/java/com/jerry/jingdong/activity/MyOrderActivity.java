package com.jerry.jingdong.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.jerry.jingdong.R;
import com.jerry.jingdong.factory.FragmentFactory;
import com.jerry.jingdong.fragment.CancelOrderFragment;
import com.jerry.jingdong.fragment.TenMinuteAgoOrderFragment;
import com.jerry.jingdong.fragment.TenMinuteOrderFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 18:30
 * @包名: com.jerry.jingdong.activity
 * @工程名: JingDong
 * @描述: TODO
 */
public class MyOrderActivity extends FragmentActivity
        implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.myorder_btn_back)
    Button                            mMyorderBtnBack;
    @Bind(R.id.myorder_rg)
    RadioGroup                        mMyorderRg;

    @Bind(R.id.myorder_empty_container)
    LinearLayout                      mMyorderEmptyContainer;

    public static final int           TYPE_TENMINUTE     = 1;
    public static final int           TYPE_TENMINUTE_AGO = 2;
    public static final int           TYPE_CANCEL        = 3;

    private int                       mType              = TYPE_TENMINUTE;
    private TenMinuteOrderFragment    mTenMinuteOrderFragment;
    private TenMinuteAgoOrderFragment mTenMinuteAgoOrderFragment;
    private CancelOrderFragment       mCancelOrderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_form);
        ButterKnife.bind(this);

        initView();

        initListener();
    }

    private void initView() {
        mTenMinuteOrderFragment = (TenMinuteOrderFragment) FragmentFactory
                .createFragment(FragmentFactory.FRAGMENT_TENMINUTE);
        mTenMinuteAgoOrderFragment = (TenMinuteAgoOrderFragment) FragmentFactory
                .createFragment(FragmentFactory.FRAGMENT_TENMINUTEAGO);
        mCancelOrderFragment = (CancelOrderFragment) FragmentFactory
                .createFragment(FragmentFactory.FRAGMENT_CANCEL);

        changeFragment(1);
    }

    private void initListener() {
        mMyorderBtnBack.setOnClickListener(this);

        mMyorderRg.setOnCheckedChangeListener(this);
    }

    /**
     * RadioGroup选择事件
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
        case R.id.myorder_rb_tenminute:// 近十分钟订单
            mTenMinuteOrderFragment = (TenMinuteOrderFragment) FragmentFactory
                    .createFragment(FragmentFactory.FRAGMENT_TENMINUTE);
            mType = TYPE_TENMINUTE;
            break;
        case R.id.myorder_rb_tenminuteago:// 十分钟前订单
            mTenMinuteAgoOrderFragment = (TenMinuteAgoOrderFragment) FragmentFactory
                    .createFragment(FragmentFactory.FRAGMENT_TENMINUTEAGO);
            mType = TYPE_TENMINUTE_AGO;
            break;
        case R.id.myorder_rb_cancel:// 取消订单
            mCancelOrderFragment = (CancelOrderFragment) FragmentFactory
                    .createFragment(FragmentFactory.FRAGMENT_CANCEL);
            mType = TYPE_CANCEL;
            break;
        default:
            break;
        }
        changeFragment(mType);
    }

    private void changeFragment(int type) {
        // 1.获得fragment管理器
        FragmentManager fm = getSupportFragmentManager();

        // 2.开启事务
        FragmentTransaction transaction = fm.beginTransaction();

        // 3.替换内容
        if (type == TYPE_TENMINUTE) {
            transaction.replace(R.id.myorder_frame_container,
                    mTenMinuteOrderFragment);
        } else if (type == TYPE_TENMINUTE_AGO) {
            transaction.replace(R.id.myorder_frame_container,
                    mTenMinuteAgoOrderFragment);
        } else if (type == TYPE_CANCEL) {
            transaction.replace(R.id.myorder_frame_container,
                    mCancelOrderFragment);
        }

        // 4.提交事务
        transaction.commit();
    }

    /**
     * 点击了返回按钮
     */
    @OnClick(R.id.myorder_btn_back)
    public void onClick(View v) {

    }

}

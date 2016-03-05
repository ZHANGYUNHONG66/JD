package com.jerry.jingdong.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.jerry.jingdong.R;
import com.jerry.jingdong.factory.FragmentFactory;
import com.jerry.jingdong.fragment.OrderBaseFragment;
import com.jerry.jingdong.views.LazyViewPager;
import com.jerry.jingdong.views.NoScrollViewPager;

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
public class MyOrderActivity extends AppCompatActivity
        implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.myorder_btn_back)
    Button                         mMyorderBtnBack;
    @Bind(R.id.myorder_rg)
    RadioGroup                     mMyorderRg;
    @Bind(R.id.myorder_viewpager)
    NoScrollViewPager              mMyorderViewpager;
    @Bind(R.id.myorder_empty_container)
    LinearLayout                   mMyorderEmptyContainer;

    public static final int        TYPE_TENMINUTE     = 1;
    public static final int        TYPE_TENMINUTE_AGO = 2;
    public static final int        TYPE_CANCEL        = 3;

    private int                    mType              = TYPE_TENMINUTE;

    private MyPageAdapter          mPagerAdapter;
    private MyOnPageChangeListener mListener;

    private String[]               mTabTitles         = { "近十分钟订单", "10分钟前订单",
            "已取消的订单" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_form);
        ButterKnife.bind(this);

        initData();

        initListener();
    }

    private void initData() {
        mPagerAdapter = new MyPageAdapter(getSupportFragmentManager());

        mMyorderViewpager.setAdapter(mPagerAdapter);
    }

    private void initListener() {
        mMyorderBtnBack.setOnClickListener(this);

        mMyorderRg.setOnCheckedChangeListener(this);
        mListener = new MyOnPageChangeListener();
        mMyorderViewpager.setOnPageChangeListener(mListener);

        // 手动选中首页让首页在一进入应用就加载数据，添加ViewPager的布局监听，当布局完成后再加载数据
        mMyorderViewpager.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // 优化LoadingPager首页未加载
                        mListener.onPageSelected(0);

                        mMyorderViewpager.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
    }

    /**
     * RadioGroup选择事件
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
        case R.id.myorder_rb_tenminute:// 近十分钟订单
            mType = TYPE_TENMINUTE;
            break;
        case R.id.myorder_rb_tenminuteago:// 十分钟前订单
            mType = TYPE_TENMINUTE_AGO;
            break;
        case R.id.myorder_rb_cancel:// 取消订单
            mType = TYPE_CANCEL;
            break;
        default:
            break;
        }

        mListener.onPageSelected(mType);
    }

    /**
     * 点击了返回按钮
     */
    @OnClick(R.id.myorder_btn_back)
    public void onClick(View v) {

    }

    private class MyOnPageChangeListener
            implements LazyViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset,
                int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // 优化数据触发加载时机，当对应的tab页被选中时再初始化数据
            OrderBaseFragment fragment = FragmentFactory
                    .createFragment(position);
            if (fragment != null && fragment.mLoadingPager != null) {
                fragment.mLoadingPager.triggerLoadData();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = FragmentFactory.createFragment(position);

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }
}

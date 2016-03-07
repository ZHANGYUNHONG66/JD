package com.jerry.jingdong.fragment;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.activity.ProductListActivity;
import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.base.MyBaseAdapter;
import com.jerry.jingdong.entity.ProductListBean;
import com.jerry.jingdong.utils.UIUtils;
import com.jerry.jingdong.views.FlowLayout;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProductListFilterFragment extends BaseFragment<ProductListActivity> {

    @Bind(R.id.productlist_filter_tv_bond)
    TextView mProductlistFilterTvBond;
    @Bind(R.id.productlist_filter_tv_color)
    TextView mProductlistFilterTvColor;
    @Bind(R.id.productlist_filter_price_lv)
    TextView mProductlistFilterTvPrice;

    @Bind(R.id.productlist_filter_color_gv)
    GridView mProductlistFilterGvColor;

    @Bind(R.id.productlist_filter_price_gv)
    GridView mProductlistFilterGvPrice;

    @Bind(R.id.productlist_filter_ll_bond)
    LinearLayout mProductlistFilterLlBond;


    List<ProductListBean.ListFilterEntity> mDatas;
    private List<ProductListBean.ListFilterEntity.ValueListEntity> mBondList;
    private List<ProductListBean.ListFilterEntity.ValueListEntity> mColorList;
    private List<ProductListBean.ListFilterEntity.ValueListEntity> mPriceList;

    private static final int BOND  = 858;
    private static final int PRICE = 325;
    private static final int COLOR = 234;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_productlist_filter, null);
        ButterKnife.bind(this, view);

        onClickReset(null);

        mProductlistFilterTvColor.setPadding(10, 3, 10, 3);

        mProductlistFilterGvColor.setAdapter(new ProductListFilterGridViewAdapter(mColorList, COLOR));

        mProductlistFilterGvPrice.setAdapter(new ProductListFilterGridViewAdapter(mPriceList, PRICE));

        FlowLayout fl = new FlowLayout(UIUtils.getContext());

        for (ProductListBean.ListFilterEntity.ValueListEntity entity : mBondList) {
            String id = entity.id;
            String name = entity.name;
            TextView tv = new TextView(UIUtils.getContext());

            tv.setTextColor(Color.WHITE);

            Random random = new Random();
            int alpha = 255;
            int red = random.nextInt(180) + 30;// 30-210
            int green = random.nextInt(180) + 30;// 30-210
            int blue = random.nextInt(180) + 30;// 30-210
            int argb = Color.argb(alpha, red, green, blue);
            GradientDrawable normalBg = new GradientDrawable();
            normalBg.setColor(argb);
            normalBg.setCornerRadius(10);

            GradientDrawable pressedBg = new GradientDrawable();
            pressedBg.setCornerRadius(10);
            pressedBg.setColor(Color.GRAY);


            StateListDrawable selectorBg = new StateListDrawable();
            selectorBg.addState(new int[]{android.R.attr.state_pressed}, pressedBg);
            selectorBg.addState(new int[]{}, normalBg);

            tv.setClickable(true);

            // 设置带有按下状态的drawable
            tv.setBackground(selectorBg);

            tv.setGravity(Gravity.CENTER);

            tv.setText(name);
            tv.setTag(id);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProductlistFilterTvBond.setText(((TextView) v).getText());
                    mProductlistFilterTvBond.setTag(v.getTag());
                    mProductlistFilterTvBond.setVisibility(View.VISIBLE);
                }
            });

            fl.addView(tv);
        }

        mProductlistFilterLlBond.addView(fl);
        return view;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public LoadingPager.LoadResultState initData() {

        List<ProductListBean.ListFilterEntity> listFilter = mContext.mProductListBean.listFilter;
        for (ProductListBean.ListFilterEntity filterEntity : listFilter) {
            if ("品牌".equals(filterEntity.key)) {
                mBondList = filterEntity.valueList;
            } else if ("价格".equals(filterEntity.key)) {
                mPriceList = filterEntity.valueList;
            } else if ("颜色".equals(filterEntity.key)) {
                mColorList = filterEntity.valueList;
            }
        }
        return LoadingPager.LoadResultState.SUCCESS;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class ProductListFilterGridViewAdapter extends MyBaseAdapter<ProductListBean.ListFilterEntity.ValueListEntity> {

        private final int mArea;

        public ProductListFilterGridViewAdapter(List dataSource, int area) {
            super(dataSource);
            mArea = area;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ProductListBean.ListFilterEntity.ValueListEntity valueListEntity = mDatas.get(position);

            LinearLayout view = new LinearLayout(UIUtils.getContext());

            switch (mArea) {
                case COLOR:
                    view.addView(getView(valueListEntity, convertView));
                    break;
                case BOND:
                    view.addView(getTextView(valueListEntity, convertView));
                    break;
                case PRICE:
                    view.addView(getNoteView(valueListEntity, convertView));
                    break;
            }
            return view;
        }

        private View getNoteView(ProductListBean.ListFilterEntity.ValueListEntity valueListEntity, View convertView) {
            final View view = View.inflate(UIUtils.getContext(), R.layout.view_noteview, null);
            TextView tvContext = (TextView) view.findViewById(R.id.tv_context);

            String price = valueListEntity.name;
            tvContext.setText(price);

            String priceId = valueListEntity.id;

            view.setTag(price + "=" + priceId);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag = (String) v.getTag();
                    String[] strings = tag.split("=");
                    int price = Integer.valueOf(strings[0]);
                    String priceId = strings[1];
                    mProductlistFilterTvPrice.setText("价格区间:0" + " --- " + (price - 1) + "元");
                    mProductlistFilterTvPrice.setTag(priceId);
                    mProductlistFilterTvPrice.setVisibility(View.VISIBLE);
                }
            });

            return view;

        }

        private View getView(ProductListBean.ListFilterEntity.ValueListEntity valueListEntity, View convertView) {

            View view = View.inflate(UIUtils.getContext(), R.layout.view_textview, null);
            TextView tvColorName = (TextView) view.findViewById(R.id.tv_color_name);
            ImageView ivColorShow = (ImageView) view.findViewById(R.id.iv_color_show);

            String colorName = valueListEntity.name;
            tvColorName.setText(colorName);
            String colorId = valueListEntity.id;
            int colorRes;
            if ("t1".equals(colorId)) {//红色
                colorRes = R.drawable.shape_red;
            } else if ("t2".equals(colorId)) {//粉色
                colorRes = R.drawable.shape_pink;

            } else if ("t3".equals(colorId)) {//黑色
                colorRes = R.drawable.shape_black;
            } else if ("t4".equals(colorId)) {//深色
                colorRes = R.drawable.shape_dark;
            } else if ("t5".equals(colorId)) {//浅色
                colorRes = R.drawable.shape_tint;
            } else if ("t6".equals(colorId)) {//白色
                colorRes = R.drawable.shape_white;
            } else {//其他
                colorRes = R.drawable.shape_edt_bg_unfocused;
            }

            ivColorShow.setImageResource(colorRes);
            view.setTag(colorName + "=" + colorRes);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag = (String) v.getTag();
                    String[] strings = tag.split("=");
                    String colorName = strings[0];
                    int colorRes = Integer.valueOf(strings[1]);

                    mProductlistFilterTvColor.setText(colorName);
                    mProductlistFilterTvColor.setBackgroundResource(colorRes);
                    if ("白色".equals(colorName) || "浅色".equals(colorName)) {
                        mProductlistFilterTvColor.setTextColor(Color.BLACK);
                    } else {
                        mProductlistFilterTvColor.setTextColor(Color.WHITE);
                    }
                    mProductlistFilterTvColor.setPadding(10, 3, 10, 3);
                    mProductlistFilterTvColor.setVisibility(View.VISIBLE);
                }
            });

            return view;
        }

        private TextView getTextView(ProductListBean.ListFilterEntity.ValueListEntity valueListEntity, View convertView) {
            TextView tv = null;
            if (true || convertView == null) {

                tv = new TextView(UIUtils.getContext());
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.selector_productlist_filter_tv_bond);
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(10, 3, 10, 3);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
                params.bottomMargin = 4;
                params.topMargin = 4;
                tv.setLayoutParams(params);
                tv.setTextSize(12);
            } else {
                tv = (TextView) convertView;
            }
            String name = valueListEntity.name;
            String id = valueListEntity.id;
            tv.setText(name);
            tv.setTag(id);
            //            tv.setId(position);
            return tv;

        }

        @NonNull
        private RadioButton getRadioButton(ProductListBean.ListFilterEntity.ValueListEntity valueListEntity, View convertView) {
            RadioButton rb = null;
            if (true || convertView == null) {
                rb = new RadioButton(UIUtils.getContext());
            } else {
                rb = (RadioButton) convertView;
            }

            String name = valueListEntity.name;
            String id = valueListEntity.id;
            rb.setText(name);
            rb.setTextColor(Color.BLACK);
            rb.setTag(id);
            //            rb.setId(position);
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            rb.setButtonDrawable(colorDrawable);
            return rb;
        }

        @Override
        public int getCount() {
            return super.getCount() - 1;
        }
    }

    @OnClick(R.id.productlist_filter_tv_bond)
    public void onClickBond(View v) {
        disableView(v);
    }

    @OnClick(R.id.productlist_filter_tv_color)
    public void onClickColor(View v) {
        disableView(v);
    }

    @OnClick(R.id.productlist_filter_price_lv)
    public void onClickPrice(View v) {
        disableView(v);
    }


    @OnClick(R.id.productlist_filter_btn_reset)
    public void onClickReset(View v) {
        disableView(mProductlistFilterTvBond);
        disableView(mProductlistFilterTvColor);
        disableView(mProductlistFilterTvPrice);
    }

    @OnClick(R.id.productlist_filter_btn_commit)
    public void onClickCommit(View v) {
        String orderBy = "";
        Object tagBond = mProductlistFilterTvBond.getTag();
        if (tagBond != null) {
            mContext.mFilterBond = (String) tagBond;
        } else {
            mContext.mFilterBond = null;
        }
        Object tagColor = mProductlistFilterTvColor.getTag();
        if (tagColor != null) {
            mContext.mFilterColor = (String) tagColor;
        } else {
            mContext.mFilterColor = null;
        }
        Object tagPrice = mProductlistFilterTvPrice.getTag();
        if (tagPrice != null) {
            mContext.mFilterPrice = (String) tagPrice;
        } else {
            mContext.mFilterPrice = null;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                mContext.requestDataAndRefreshUiInNewThread(ProductListActivity.WITH_KEY + ProductListActivity.WITH_ORDER + ProductListActivity.WITH_FILTER);
            }
        }).start();
        mContext.mSlidingMenu.toggle(true);

    }


    private void disableView(View v) {
        v.setVisibility(View.GONE);
        v.setTag(null);
    }
}

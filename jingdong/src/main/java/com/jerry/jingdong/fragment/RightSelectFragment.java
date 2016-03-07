package com.jerry.jingdong.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.jingdong.R;
import com.jerry.jingdong.activity.GoodsDetailUI;
import com.jerry.jingdong.application.MyApplication;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.entity.CartNewBean;
import com.jerry.jingdong.utils.CartParamsUtils;
import com.jerry.jingdong.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 滑动fragment
 */
public class RightSelectFragment extends Fragment {
    private static final String TAG = "RightSelectFragment";

    private final CartNewBean mGoodsDeanBean;
    private final int         mPid;
    private final Context     mSildContext;

    @Bind(R.id.ll_color)
    LinearLayout mLlColor;
    @Bind(R.id.ll_size)
    LinearLayout mLlSize;
    @Bind(R.id.iv_goods_pic)
    ImageView    mIvGoodsPic;
    @Bind(R.id.tv_goods_price)
    public TextView mTvGoodsPrice;
    @Bind(R.id.tv_add_cart)
    TextView mTvAddCart;
    @Bind(R.id.tv_price_saver)
    public TextView mTvPriceSaver;
    @Bind(R.id.ib_reduce)
    ImageButton mIbReduce;
    @Bind(R.id.tv_select_goods_number)
    EditText    mTvSelectGoodsNumber;
    @Bind(R.id.ib_add)
    ImageButton mIbAdd;
    //初始选中的颜色和尺码
    private int selectedColoer = 1;
    private int selectedSize   = 3;
    private List<CartNewBean.ProductEntity.ProductBeanEntity> mProductProperty;
    private int                                               mColoerSize;
    private int                                               mUesrId;
    private GoodsDetailUI                                     mContext;
    private boolean                                           flag;

    public RightSelectFragment(CartNewBean goodsDeanBean, Context sildContext, int pid) {
        mGoodsDeanBean = goodsDeanBean;
        mPid = pid;
        mSildContext = sildContext;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    /**
     * 加载数据
     */
    private void initData() {

        mContext = (GoodsDetailUI) getActivity();

        mProductProperty = mGoodsDeanBean.getProduct().getProductProperty();

        for (CartNewBean.ProductEntity.ProductBeanEntity property : mProductProperty) {


            setProperty(property);
            mTvSelectGoodsNumber.setSelection(mTvSelectGoodsNumber.getText().length());


            //设置光标的显示隐藏
            if (mTvSelectGoodsNumber.isFocusable()) {
                mTvSelectGoodsNumber.setCursorVisible(true);
            } else {
                mTvSelectGoodsNumber.setCursorVisible(false);
            }
            //增加商品数量
            mIbAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = mTvSelectGoodsNumber.getText().toString();
                    int value = Integer.parseInt(number);
                    if (value < mGoodsDeanBean.getProduct().getBuyLimit()) {
                        value++;
                    } else {
                        Toast.makeText(getContext(), "亲，仓库被你搬空了", Toast.LENGTH_SHORT).show();
                    }
                    mTvSelectGoodsNumber.setText(value + "");
                    mTvSelectGoodsNumber.setSelection(mTvSelectGoodsNumber.getText().length());
                }
            });
            //减少商品数量
            mIbReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = mTvSelectGoodsNumber.getText().toString();
                    int value = Integer.parseInt(number);

                    if (value > 1) {
                        value--;
                    } else {
                        value = 1;
                    }
                    mTvSelectGoodsNumber.setText(value + "");
                    mTvSelectGoodsNumber.setSelection(mTvSelectGoodsNumber.getText().length());
                }
            });

            //加入购物车
            mTvAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean b = addShopingCar();
                    if (b) {
                        Toast.makeText(getContext(), "加入成功", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //设置顶部商品的图片
            BitmapUtils bitmapUtils = new BitmapUtils(getContext());
            if (mGoodsDeanBean.getProduct().getPics() != null && mGoodsDeanBean.getProduct().getPics().size() > 0) {
                bitmapUtils.display(mIvGoodsPic, MyConstants.URL.BASEURL +
                        mGoodsDeanBean.getProduct().getPics().get(0));
            }
            //设置商品价格
            mTvGoodsPrice.setText("￥" + mGoodsDeanBean.getProduct().getLimitPrice() + "");
            //设置节省的钱
            mTvPriceSaver.setText("节省" + (mGoodsDeanBean.getProduct().getPrice() -
                    mGoodsDeanBean.getProduct().getLimitPrice()) + "元");
        }
    }

    /**
     * 添加到购物车
     */
    public boolean addShopingCar() {
        //获取取原价
        int marketPrice = mGoodsDeanBean.getProduct().getMarketPrice();
        //获取购买数
        String number = mTvSelectGoodsNumber.getText().toString();
        //获取商品的颜色
        String color = selectedColoer + "";
        //获取商品尺码
        String size = selectedSize + "";

        //是否添加成功的标记
        boolean tag = false;
        if (MyApplication.isLogin()) {
            mUesrId = MyApplication.getmUserId();
            setShopingInfo(mPid + "", number, color, size);
            tag = true;
            flag = true;

        } else {
          /*  Intent intent = new Intent(getContext(), 登录界面的.class);
            startActivity(intent);*/
        }
        return tag;

    }

    private void setShopingInfo(String pid, String number, String color, String size) {
        CartParamsUtils cartUtils = new CartParamsUtils();
        cartUtils.addCart(pid, number, color, size);


    }


    private void setProperty(final CartNewBean.ProductEntity.ProductBeanEntity property) {
        if ("颜色".equals(property.getK())) {
            mColoerSize = property.getId();
            selectedSize = mColoerSize + 1;
            final TextView tv = new TextView(getContext());
            tv.setGravity(Gravity.CENTER);
            int pading = UIUtils.dp2px(3);
            tv.setBackgroundResource(R.drawable.selecter_backgrad);
            tv.setEnabled(true);
            if (property.getId() == selectedColoer) {
                tv.setBackgroundResource(R.drawable.select_color);
            }
            tv.setText(property.getV());
            tv.setPadding(pading, pading, pading, pading);
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
            layoutParams.leftMargin =UIUtils.dp2px(10);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearTvColoerBackgrad();

                    selectedColoer = property.getId();
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundResource(R.drawable.select_color);
                }
            });

            mLlColor.addView(tv, layoutParams);
        } else if ("尺码".equals(property.getK())) {
            final TextView tv = new TextView(getContext());
            tv.setGravity(Gravity.CENTER);
            int pading = UIUtils.dp2px(3);
            tv.setBackgroundResource(R.drawable.selecter_backgrad);
            tv.setEnabled(true);
            if (property.getId() == selectedSize) {
                tv.setBackgroundResource(R.drawable.select_color);
            }
            tv.setText(property.getV());
            tv.setPadding(pading, pading, pading, pading);
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
            layoutParams.leftMargin = UIUtils.dp2px(10);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearTvSizeBackgrad();
                    selectedSize = property.getId();
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundResource(R.drawable.select_color);
                }
            });

            mLlSize.addView(tv, layoutParams);
        }
    }

    private void clearTvColoerBackgrad() {
        for (int i = 0; i < mProductProperty.size(); i++) {
            if (mProductProperty.get(i).getK().equals("颜色")) {
                TextView textView = (TextView) mLlColor.getChildAt(i);
                textView.setTextColor(Color.GRAY);
                mLlColor.getChildAt(i).setBackgroundResource(R.drawable.selecter_backgrad);

            }
        }
    }

    private void clearTvSizeBackgrad() {
        for (int i = 0; i < mProductProperty.size(); i++) {
            if (mProductProperty.get(i).getK().equals("尺码")) {
                TextView textView = (TextView) mLlSize.getChildAt(i - mColoerSize);
                textView.setTextColor(Color.GRAY);
                mLlSize.getChildAt(i - mColoerSize).setBackgroundResource(R.drawable.selecter_backgrad);

            }
        }
    }


    /**
     * 加载视图
     *
     * @return
     */
    private View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_select_right, null);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 设置在左边页面已选中显示的文字类容
     */
    public String getSelectedInfo() {
        return mProductProperty.get(selectedColoer - 1).getV() + "  " +
                mProductProperty.get(selectedSize - 1).getV();
    }
    // TODO: 2016/3/6 买了物品跳转购物车的功能待定
}

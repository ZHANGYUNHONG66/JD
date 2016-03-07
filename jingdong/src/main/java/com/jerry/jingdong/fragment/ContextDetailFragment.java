package com.jerry.jingdong.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jerry.jingdong.R;
import com.jerry.jingdong.activity.GoodsDetailUI;
import com.jerry.jingdong.application.MyApplication;
import com.jerry.jingdong.base.BaseDetailFragment;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.entity.CartNewBean;
import com.jerry.jingdong.entity.CollectBean;
import com.jerry.jingdong.factory.ThreadPoolProxyFactory;
import com.jerry.jingdong.manager.ThreadPoolProxy;
import com.jerry.jingdong.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 封装细节的内容
 */
public class ContextDetailFragment extends BaseDetailFragment {

    private static final String TAG = "ContextDetailContainer";
    private final Context     mContext;
    private final CartNewBean mCartNewBean;
    private final Context     mSildContext;
    private final int         mPid;
    //viewpager图片
    @Bind(R.id.vp_goods_pic)
    ViewPager      mVpGoodsPic;
    //返回键
    @Bind(R.id.tv_back)
    TextView       mTvBack;
    //分享键
    @Bind(R.id.ib_share)
    ImageButton    mIbShare;
    //包裹viewpager的rl
    @Bind(R.id.rl_view_pager)
    RelativeLayout mRlViewPager;
    //优惠价
    @Bind(R.id.tv_now_price)
    TextView       mTvNowPrice;
    //抢购的图片
    @Bind(R.id.iv_qiang)
    ImageView      mIvQiang;
    //原价
    @Bind(R.id.tv_cost_price)
    TextView       mTvCostPrice;
    //评分的星星
    @Bind(R.id.ll_grade)
    LinearLayout   mLlGrade;
    //限时抢购的时间
    @Bind(R.id.tv_surplus_time)
    TextView       mTvSurplusTime;
    //商品的名字
    @Bind(R.id.tv_wares_name)
    TextView       mTvWaresName;
    //选择的商品规格
    @Bind(R.id.tv_selected_goods)
    TextView       mTvSelectedGoods;
    //点击打开右侧的规格参数
    @Bind(R.id.ll_show_right_parameter)
    LinearLayout   mLlShowRightParameter;
    //送货范围
    @Bind(R.id.tv_inventory_area)
    TextView       mTvInventoryArea;
    //商品总数
    @Bind(R.id.tv_foods_total)
    TextView       mTvGoodsTotal;
    //好评率
    @Bind(R.id.tv_good_comment)
    TextView       mTvGoodComment;
    //商品的评论数
    @Bind(R.id.tv_comment_count)
    TextView       mTvCommentCount;
    //跳转评论界面
    @Bind(R.id.ll_show_comment)
    LinearLayout   mLlShowComment;
    //整个可拉动的区域
    @Bind(R.id.ll_comm_detail)
    LinearLayout   mLlCommDetail;
    //收藏
    @Bind(R.id.tv_collect)
    TextView       mTvCollect;
    @Bind(R.id.rl_to_description)
    RelativeLayout mRLToDescription;
    //加入购物车
    @Bind(R.id.tv_add_shop_car)
    TextView       mTvAddShopCar;
    //立即购买
    @Bind(R.id.tv_go_shoped)
    TextView       mTvGoShoped;
    //图片底部的圆点
    @Bind(R.id.ll_bottom_dot)
    LinearLayout   mLlBottomDot;
    private int                       mLeftTime;
    private Runnable                  mTask;
    private List<String>              mPics;
    private int                       mUserId;
    private Handler                   mHandler;
    private ThreadPoolProxy           mProxy;
    private CartNewBean.ProductEntity mProduct;

    public ContextDetailFragment(Context context, CartNewBean goodsDeanBean, Context sildContext, int pid) {
        mContext = context;
        mCartNewBean = goodsDeanBean;
        mSildContext = sildContext;
        mPid = pid;
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.container_context_detail, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void initData() {
        if (mCartNewBean != null) {
            mProduct = mCartNewBean.getProduct();
            //设置商品名
            mTvWaresName.setText(mProduct.getName());
            //抢购价
            mTvNowPrice.setText("￥" + mProduct.getLimitPrice());
            //会员价
            mTvCostPrice.setText("￥" + mProduct.getPrice());
            //评分
            int score = mProduct.getScore();
            for (int i = 0; i < score; i++) {
                ImageView iv = new ImageView(mContext);
                iv.setBackgroundResource(R.mipmap.level_1);
                mLlGrade.addView(iv);
            }
            if (score < 5) {
                for (int i = 0; i < 5 - score; i++) {
                    ImageView iv = new ImageView(mContext);
                    iv.setBackgroundResource(R.mipmap.level_3);
                    mLlGrade.addView(iv);
                }
            }
            //剩余抢购时间
            mLeftTime = mProduct.getLeftTime();
            //先显示一次剩余时间，防止闪烁
            showTiem();
            //动态减少时间
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    showTiem();

                }
            };
            noaddData(mLeftTime);

            mTvInventoryArea.setText(mProduct.getInventoryArea());
            mTvGoodsTotal.setText(mProduct.getBuyLimit() + "件");
            //评论数
            mTvCommentCount.setText(mProduct.getCommentCount() + "人评论");

            mPics = mProduct.getPics();
            mVpGoodsPic.setAdapter(new picsAdapter());

            //设置已选的商品的参数属性
            mTvSelectedGoods.setText(((GoodsDetailUI) mSildContext).
                    getRightSelectFragment().getSelectedInfo());
            ((GoodsDetailUI) mSildContext).getSlidingMenu().setOnClosedListener(new SlidingMenu.OnClosedListener() {
                @Override
                public void onClosed() {
                    mTvSelectedGoods.setText(((GoodsDetailUI) mSildContext).
                            getRightSelectFragment().getSelectedInfo());
                }
            });

            mLlBottomDot.removeAllViews();
            for (int i = 0; i < mPics.size(); i++) {
                ImageView iv = new ImageView(mContext);
                int width = UIUtils.dp2px(5);
                int heigth = UIUtils.dp2px(5);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, heigth);
                if (i != 0) {
                    params.leftMargin = UIUtils.dp2px(5);
                }
                if (i == 0) {
                    iv.setImageResource(R.mipmap.arl_dot_normal_bg);
                    mLlBottomDot.addView(iv, params);
                } else {
                    iv.setImageResource(R.mipmap.arl_dot_current_bg);
                    mLlBottomDot.addView(iv, params);

                }
            }
            //设置小圆点的滑动
            mVpGoodsPic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < mPics.size(); i++) {
                        ImageView childAt = (ImageView) mLlBottomDot.getChildAt(i);
                        childAt.setImageResource(R.mipmap.arl_dot_current_bg);
                    }
                    ImageView iv = (ImageView) mLlBottomDot.getChildAt(position);
                    iv.setImageResource(R.mipmap.arl_dot_normal_bg);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            //加入购物车
            mTvAddShopCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean addShopCar = ((GoodsDetailUI) mSildContext).getRightSelectFragment().addShopingCar();
                    if (addShopCar) {
                        Toast.makeText(getContext(), "加入成功", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            //立即购买
            mTvGoShoped.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2016/3/6 理解购买的功能待定
                }
            });

            //加入收藏
            mTvCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyApplication.isLogin()) {
                        mUserId = MyApplication.getmUserId();
                        addCollect();
                    } else {
                        // TODO: 2016/3/6 跳转用户中心功能待定
                    }
                }
            });

            //跳转到评论界面
            mLlShowComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GoodsDetailUI) mSildContext).getContentFragment().setViewState(2);
                }
            });

            //跳转到详情界面
            mRLToDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GoodsDetailUI) mSildContext).getContentFragment().setViewState(1);

                }
            });

            //返回键
            mTvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GoodsDetailUI) mSildContext).finish();
                    ((GoodsDetailUI) mSildContext).overridePendingTransition(R.anim.previous_enter, R.anim.previous_exit);
                }
            });

            //打开右侧菜单
            mLlShowRightParameter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GoodsDetailUI) mSildContext).getSlidingMenu().toggle();
                }
            });

        }

    }

    /**
     * 显示剩余时间
     */
    private void showTiem() {
        MyApplication.getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mLeftTime == 0) {
                    if (mIvQiang != null) {
                        mIvQiang.setImageResource(R.mipmap.qiang_gray);

                    }
                    if (mProduct != null) {
                        //会员价
                        mTvNowPrice.setText("￥" + mProduct.getPrice());
                        //原价
                        mTvCostPrice.setText("￥" + mProduct.getMarketPrice());
                        ((GoodsDetailUI) mSildContext).getRightSelectFragment().mTvGoodsPrice.setText(
                                "￥" + mProduct.getPrice());
                        ((GoodsDetailUI) mSildContext).getRightSelectFragment().mTvPriceSaver.setText(
                                "节省" + (mCartNewBean.getProduct().getMarketPrice() -
                                        mCartNewBean.getProduct().getPrice()) + "元");
                    }
                }

                if (mTvSurplusTime != null) {
                    if (mLeftTime >= 3600000) {
                        mTvSurplusTime.setText("剩余促销时间" + mLeftTime / 3600000 + "小时" + mLeftTime % 3600000 / 60000 + "分" +
                                mLeftTime % 3600000 % 60000 / 1000 + "秒");

                    } else if (mLeftTime >= 60000) {
                        mTvSurplusTime.setText("剩余促销时间" + mLeftTime / 60000 + "分" + mLeftTime % 60000 / 1000 + "秒");
                    } else {
                        mTvSurplusTime.setText("剩余促销时间" + mLeftTime / 1000 + "秒");
                    }
                }
            }
        });

    }

    /**
     * 发送添加收藏夹的请求
     */
    private void addCollect() {
        HttpUtils httpUtils = new HttpUtils();

        RequestParams requestParams = new RequestParams();
        requestParams.addHeader("userid", mUserId + "");
        //        String url=Constants.URLS.DETAIL+"/favorites?pId="+mPid;
        String url = "http://188.188.5.24:8080/market/product/favorites?pId=" + mPid;
        httpUtils.send(HttpRequest.HttpMethod.GET,
                url, requestParams, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (responseInfo.statusCode == 200 || responseInfo.statusCode == 0) {
                            String result = responseInfo.result;
                            Gson gson = new Gson();
                            CollectBean collectBean = gson.fromJson(result, CollectBean.class);
                            if ("error".equals(collectBean.getResponse())) {
                                if (collectBean.getError_code().equals("1533")) {

                                    // TODO: 2016/3/6 跳转用户中心待定
                                } else if (collectBean.getError_code().equals("1535") || collectBean.getError_code().equals("1536")) {
                                    Toast.makeText(getContext(), "宝贝已经收藏", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        e.printStackTrace();
                    }
                });

    }


    /**
     *
     * @param leftTime
     */
    private void noaddData(int leftTime) {

        if (mProxy == null) {
            mProxy = ThreadPoolProxyFactory.createNormalThreadPool();
        }
        if (mTask == null) {
            mTask = new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
                    if (mLeftTime > 0) {
                        mLeftTime -= 1000;
                        mHandler.sendMessage(Message.obtain());
                        noaddData(mLeftTime);
                    }
                }
            };

        }
        mProxy.execute(mTask);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class picsAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mPics.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BitmapUtils bitmapUtils = new BitmapUtils(mContext);
            ImageView iv = new ImageView(mContext);
            bitmapUtils.display(iv, MyConstants.URL.BASEURL + mPics.get(position));
            container.addView(iv);

            //// TODO: 2016/3/6 准备引用第三方框架展现图片

            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    //登陆成功后的操作
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mUserId = MyApplication.getmUserId();
        if (MyApplication.isLogin()) {
            addCollect();

        }
    }

}

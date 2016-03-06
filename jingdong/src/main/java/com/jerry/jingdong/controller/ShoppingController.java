package com.jerry.jingdong.controller;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.jingdong.R;
import com.jerry.jingdong.application.MyApplication;
import com.jerry.jingdong.base.BaseController;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.entity.CartInfoBean;
import com.jerry.jingdong.entity.CartNewBean;
import com.jerry.jingdong.holder.cart.CartHolder;
import com.jerry.jingdong.holder.cart.CartInterestHolder;
import com.jerry.jingdong.holder.product.ProductHolder;
import com.jerry.jingdong.protocol.CartProtocol;
import com.jerry.jingdong.protocol.ProductProtocol;
import com.jerry.jingdong.utils.CartParamsUtils;
import com.jerry.jingdong.utils.UIUtils;
import com.jerry.jingdong.views.ListScrollView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShoppingController extends BaseController implements View.OnTouchListener {

    private ProductProtocol mProductProtocol;
    private static final int CART_NORMAL   = 0;
    private static final int CART_INTEREST = 1;
    private static final int CART_LOADMORE = 2;
    private List<CartNewBean.ProductEntity> mProductEntities;//商品集合
    private List<CartInfoBean.CartEntity>   mCartEntities;
    private boolean                         mIsLogin;
    private CartParamsUtils                 mCartParamsUtils;
    private String                          mSku;
    private CartProtocol                    mCartProtocol;
    private CartInfoBean                    mCartInfoBean;
    private MyAdapter                       mAdapter;

    //登录View的控件
    @Bind(R.id.cart_login_btn)
    Button         mCartLoginBtn;
    @Bind(R.id.cart_login_ll)
    LinearLayout   mCartLoginLl;
    @Bind(R.id.cart_goods_show_lv)
    ListScrollView mCartGoodsShowLv;
    @Bind(R.id.cart_select_iv)
    ImageView      mCartSelectIv;
    @Bind(R.id.cart_select_tv)
    TextView       mCartSelectTv;
    @Bind(R.id.cart_total_tv)
    TextView       mCartTotalTv;
    @Bind(R.id.cart_all_tv)
    TextView       mCartAllTv;
    @Bind(R.id.cart_goto_accounts_btn)
    ImageButton    mCartGotoAccountsBtn;
    @Bind(R.id.cart_accounts_rl)
    RelativeLayout mCartAccountsRl;

    //没有商品
    @Bind(R.id.cart_nogoods_iv)
    ImageView      mCartNogoodsIv;
    @Bind(R.id.cart_nogoods_btn)
    Button         mCartNogoodsBtn;
    @Bind(R.id.cart__nogoods_ll)
    LinearLayout   mCartNogoodsLl;
    @Bind(R.id.cart_scrollview)
    ScrollView     mCartScrollview;
    @Bind(R.id.cart_delete_iv)
    ImageView      mCartDeleteIv;
    @Bind(R.id.cart_delete_all)
    TextView       mCartDeleteAll;
    @Bind(R.id.cart_delete_btn)
    Button         mCartDeleteBtn;
    @Bind(R.id.cart_favorite_btn)
    Button         mCartFavoriteBtn;
    @Bind(R.id.cart_delete_rl)
    RelativeLayout mCartDeleteRl;
    @Bind(R.id.cart_bottom_fl)
    FrameLayout    mCartBottomFl;
    @Bind(R.id.cart_root_fl)
    RelativeLayout mCartRootFl;
    private int mUserId;


    public ShoppingController(Context context) {
        super(context);
    }

    /**
     * 初始化导航栏
     */
    @Override
    public void initTitle() {
        mTvTitle.setText("杨哥的购物车");
    }

    /**
     * 在子线程中加载数据
     *
     * @return
     */
    @Override
    public LoadingPager.LoadResultState initData() {
        SystemClock.sleep(2000);

        mProductProtocol = new ProductProtocol();

        mProductEntities = new ArrayList<>();
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("pId", "1");
            CartNewBean cartInfoBean = mProductProtocol.loadData(HttpRequest.HttpMethod.GET, map, null);
            mProductEntities.add(cartInfoBean.product);
            Log.d("CartFragment", cartInfoBean.response);

        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadResultState.ERROR;
        }

        mIsLogin = MyApplication.isLogin();//拿到当前登录状态

        if (mIsLogin) { //已登录状态
            mCartParamsUtils = new CartParamsUtils();
            //mCartParamsUtils 1:3:1,2,3,4|2:2:2,3
            mSku = mCartParamsUtils.getString(MyApplication.getmUserId() + "");
            if (mSku.equals("")) {
                mCartEntities = null;
                return LoadingPager.LoadResultState.SUCCESS;
            }

            mCartProtocol = new CartProtocol();
            HashMap<String, String> map = new HashMap<>();
            map.put("sku", mSku);
            try {
                mCartInfoBean = mCartProtocol.loadData(HttpRequest.HttpMethod.POST, map);
                if (mCartInfoBean != null) {
                    mCartEntities = mCartInfoBean.cart;
                    Log.d("CartFragment", mCartInfoBean.response);
                }
                return LoadingPager.LoadResultState.SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                return LoadingPager.LoadResultState.ERROR;
            }
        } else {
            //没登录，显示本地数据
            return LoadingPager.LoadResultState.SUCCESS;
        }
    }

    /**
     * 初始化请求成功后的内容视图
     */
    @Override
    protected View initSuccessView() {
        View view = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.fragment_cart, null);
        ButterKnife.bind(this, view);

        //根据返回值确定是否显示登陆项,
        if (mIsLogin) {
            //已经登陆
            mCartLoginLl.setVisibility(View.GONE);
            //在根据集合mCartEntities是否有值，去判断是否应该显示结算和商品栏显示项目
            if (mCartEntities != null) {
                //有数据时，隐藏
                mCartNogoodsLl.setVisibility(View.GONE);
                //有数据时显示结算界面
                mCartAccountsRl.setVisibility(View.VISIBLE);
            } else {

                mCartNogoodsLl.setVisibility(View.VISIBLE);
                mCartAccountsRl.setVisibility(View.GONE);
                mCartDeleteRl.setVisibility(View.GONE);
            }
        } else { //未登陆时，购物车未空
            mCartLoginLl.setVisibility(View.VISIBLE);

            mCartNogoodsLl.setVisibility(View.VISIBLE);
            mCartAccountsRl.setVisibility(View.GONE);
            mCartDeleteRl.setVisibility(View.GONE);
        }
        mAdapter = new MyAdapter();
        mCartGoodsShowLv.setAdapter(mAdapter);
        setItemSelectedListener();
        mCartDeleteRl.setOnTouchListener(this);
        mCartAccountsRl.setOnTouchListener(this);

        //设置金额
        setAllMoney();

        return view;
    }

    @OnClick(value = {R.id.cart_delete_iv, R.id.cart_select_iv,
            R.id.cart_goto_accounts_btn, R.id.cart_nogoods_btn,
            R.id.cart_delete_btn, R.id.cart_favorite_btn,
            R.id.cart_login_btn})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.cart_select_iv:
            case R.id.cart_delete_iv:
                //点击了全选
                setAllSelected();
                break;
            case R.id.cart_goto_accounts_btn:
                //点击了结算
                setGoToAccpunts();
                break;
            case R.id.cart_nogoods_btn:
                //点击了随便逛逛
                setGoToHome();
                break;
            case R.id.cart_delete_btn:
                //点击了删除
                setDeleteData();
                break;
            case R.id.cart_favorite_btn:
                //点击了加入购物车
                setAddFavorite();
                break;
            case R.id.cart_login_btn:
                //点击了加入购物车
                Toast.makeText(UIUtils.getContext(), "点击了登陆", Toast.LENGTH_SHORT).show();
                Login();
                break;

        }
    }

    private void setGoToAccpunts() {
        Toast.makeText(UIUtils.getContext(), "结算", Toast.LENGTH_SHORT).show();
    }

    private void setGoToHome() {

    }

    private void setDeleteData() {

        ListIterator<CartInfoBean.CartEntity> cartEntityListIterator = mCartEntities.listIterator();
        int num = mCartEntities.size();
        while (cartEntityListIterator.hasNext()) {
            CartInfoBean.CartEntity cartEntity = cartEntityListIterator.next();
            if (cartEntity.isSeleced()) {
                String pid = cartEntity.product.id + "";
                String number = 1 + "";
                String color = "2";
                String size = "1";
                mCartParamsUtils.deleteCart(pid, number, color, size);
                cartEntityListIterator.remove();
            }
        }
        if (mCartEntities != null) {
            if (num == mCartEntities.size()) {
                Toast.makeText(UIUtils.getContext(), "亲，你还没选定哦", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //等于零，说明已经全部删除了
        if (mCartEntities.size() == 0) {
            setEdit();
            mCartNogoodsLl.setVisibility(View.VISIBLE);
            mCartAccountsRl.setVisibility(View.GONE);
            mCartDeleteRl.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();

    }

    private void setEdit() {

        if (!mIsLogin) {
            Toast.makeText(UIUtils.getContext(), "亲，你还没登陆哟", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mCartAccountsRl.getVisibility() == View.INVISIBLE) {
            for (int i = 0; i < mCartEntities.size(); i++) {
                mCartEntities.get(i).setSeleced(true);
            }
            //设置全选建
            mCartSelectIv.setSelected(true);
            mCartDeleteRl.setVisibility(View.INVISIBLE);
            mCartAccountsRl.setVisibility(View.VISIBLE);
            setAllMoney();
        } else if (mCartAccountsRl.getVisibility() == View.VISIBLE) {
            mCartSelectIv.setSelected(false);
            for (int i = 0; i < mCartEntities.size(); i++) {
                mCartEntities.get(i).setSeleced(false);
            }
            mCartAccountsRl.setVisibility(View.INVISIBLE);
            mCartDeleteRl.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();

    }

    //加入购物车
    private void setAddFavorite() {
        if (MyApplication.isLogin()) {
            mUserId = MyApplication.getmUserId();
            addCollect();
        }
       /* else {
            Intent intent = new Intent(getContext(), XXXActivity.class);
            startActivityForResult(intent, 0);
        }*/
    }

    private void addCollect() {
        HttpUtils httpUtils = new HttpUtils();
        for (int i = 0; i < mCartEntities.size(); i++) {
            if (!mCartEntities.get(i).isSeleced()) {
                continue;
            } else {
                RequestParams requestParams = new RequestParams();
                requestParams.addHeader("userid", mUserId + "");
                String url = MyConstants.URL.PRODUCT + "/favorites?pId=" + mCartEntities.get(i).product.id;
                httpUtils.send(HttpRequest.HttpMethod.GET, url, requestParams, new RequestCallBack<Object>() {
                    @Override
                    public void onSuccess(ResponseInfo<Object> responseInfo) {
                        if (responseInfo.statusCode == 200 || responseInfo.statusCode == 0) {
                            Toast.makeText(UIUtils.getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    //登录中心
    private void Login() {
        /*Intent intent = new Intent(UIUtils.getContext(), XXXActivity.class);
        startActivityForResult(intent, 1);*/
    }

    private void setAllSelected() {
        if (mCartEntities == null) {
            return;
        }
        ListAdapter adapter = mCartGoodsShowLv.getAdapter();
        if (mCartSelectIv.isSelected() || mCartDeleteIv.isSelected()) {
            mCartSelectIv.setSelected(false);
            mCartDeleteIv.setSelected(false);
        } else {
            mCartSelectIv.setSelected(true);
            mCartDeleteIv.setSelected(true);
        }
        for (int i = 0; i < mCartEntities.size(); i++) {
            CartInfoBean.CartEntity cartEntity = (CartInfoBean.CartEntity) adapter.getItem(i);
            cartEntity.setSeleced(mCartSelectIv.isSelected());
        }
        mAdapter.notifyDataSetChanged();
        //重新刷新结算
        setAllMoney();
    }

    private void setItemSelectedListener() {

    }

    private void setAllMoney() {
        if (mCartEntities == null) {
            return;
        }

        ListAdapter adapter = mCartGoodsShowLv.getAdapter();
        int sum = 0;
        for (int i = 0; i < mCartEntities.size(); i++) {
            CartInfoBean.CartEntity cartEntity = (CartInfoBean.CartEntity) adapter.getItem(i);
            if (!cartEntity.isSeleced()) {
                continue;
            }
            int prodNum = cartEntity.prodNum;
            int price = cartEntity.product.price;
            sum += prodNum * price;
        }
        mCartAllTv.setText("合计:￥" + sum + ".00");
        mCartTotalTv.setText("总计:￥" + sum + ".00");
        //设置全选符号
        if (!mCartSelectIv.isSelected()) {
            int count = 0;
            for (int i = 0; i < mCartEntities.size(); i++) {
                if (mCartEntities.get(i).isSeleced()) {
                    count++;
                }
            }

            if (count == mCartEntities.size()) {
                mCartSelectIv.setSelected(true);
            } else if (count == 0) {
                mCartSelectIv.setSelected(false);
            }
        }
    }


    /**
     * @param v
     * @param event
     * @return
     * @des 消费事件
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    private class MyAdapter extends BaseAdapter {

        private CartHolder         mCartHolder;
        private CartInterestHolder mCartInterestHolder;
        private ProductHolder      mProductHolder;

        @Override
        public int getCount() {
            if (mProductEntities != null && mCartEntities != null) {
                return mProductEntities.size() + mCartEntities.size() + 1;
            } else if (mCartEntities != null) {
                return mCartEntities.size() + 1;
            } else if (mProductEntities != null) {
                return mProductEntities.size() + 1;
            }
            return 1;
        }

        @Override
        public Object getItem(int position) {
            if (mCartEntities == null) {
                if (position == 0) {
                    return View.inflate(UIUtils.getContext(), R.layout.fragment_cart_item_division, null);
                } else {
                    return mProductEntities.get(position - mProductEntities.size() - 1);
                }
            } else {
                if (position < mCartEntities.size()) {
                    return mCartEntities.get(position);
                } else if (position == mCartEntities.size()) {
                    return View.inflate(UIUtils.getContext(), R.layout.fragment_cart_item_division, null);
                } else {
                    return mProductEntities.get(position - mProductEntities.size() - 1);
                }
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (getItemViewType(position) == CART_NORMAL) {
                mCartHolder = null;
                convertView = null;
                if (convertView == null) {
                    //显示正常视图
                    mCartHolder = new CartHolder(mCartGoodsShowLv);
                    mCartHolder.refreshView(mCartEntities.get(position));
                } else {
                    mCartHolder = (CartHolder) convertView.getTag();
                }
                //添加观察者
                mCartHolder.setOnDataChangeListener(new CartHolder.OnDataChangeListener() {
                    @Override
                    public void onChanged() {
                        setAllMoney();
                    }

                    @Override
                    public void onDelete() {
                        mCartEntities.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                convertView = mCartHolder.mRootView;
            } else if (getItemViewType(position) == CART_INTEREST) {
                mCartInterestHolder = null;
                convertView = null;
                if (convertView == null) {
                    //显示正常视图
                    mCartInterestHolder = new CartInterestHolder();
                } else {
                    mCartInterestHolder = (CartInterestHolder) convertView.getTag();
                }
                convertView = mCartInterestHolder.mRootView;
            } else if (getItemViewType(position) == CART_LOADMORE) {
                mProductHolder = null;
                convertView = null;
                if (convertView == null) {
                    //显示正常视图
                    mProductHolder = new ProductHolder();
                    mProductHolder.refreshView(mProductEntities.get(mCartEntities == null ? position - 1 : position - 1 - mCartEntities.size()));
                } else {
                    mProductHolder = (ProductHolder) convertView.getTag();
                }
                convertView = mProductHolder.mRootView;
            }

            return convertView;
        }

        //获取视图种类
        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount();
        }

        //每个视图的类型
        @Override
        public int getItemViewType(int position) {
            if (mCartEntities == null) {
                if (position == 0) {
                    return CART_INTEREST;
                } else {
                    return CART_LOADMORE;
                }
            } else {
                if (position < mCartEntities.size()) {
                    //显示购物车中的物品视图
                    return CART_NORMAL;

                } else if (position == mCartEntities.size()) {
                    //你可能还想要视图
                    return CART_INTEREST;
                } else {
                    //加载更多视图
                    return CART_LOADMORE;
                }
            }
        }
    }
}

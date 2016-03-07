package com.jerry.jingdong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.jerry.jingdong.R;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.entity.ProductListBean;
import com.jerry.jingdong.fragment.ProductListContentFragment;
import com.jerry.jingdong.fragment.ProductListFilterFragment;
import com.jerry.jingdong.protocol.BasePlusProtocol;
import com.jerry.jingdong.protocol.BaseProtocol;
import com.jerry.jingdong.utils.UIUtils;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用于商品列表展示的Activity
 */
public class ProductListActivity extends SlidingFragmentActivity implements RadioGroup.OnCheckedChangeListener, Parcelable {

    public static final String CATEGORY_ID      = "cId";
    public static final String CATEGORY_KEYWORD = "keyword";
    public static final String CATEGORY_NAME    = "cName";
    public static final String FROM             = "from";
    private        ProductListContentFragment              mContentFragment;
    public         LoadingPager.LoadResultState            mLoadedResult;
    public         List<ProductListBean.ProductListEntity> mdatas;
    public         SlidingMenu                             mSlidingMenu;
    public         boolean                                 isListVIewItemClickAble;
    public         ProductListFilterFragment               mFilterFragment;
    public         String                                  mKeyword;
    private static ProductListActivity                     Tag;
    public         boolean                                 hasEmptyData;

    public ProductListActivity() {
        super();
    }

    protected ProductListActivity(Parcel in) {
        isListVIewItemClickAble = in.readByte() != 0;
        mKeyword = in.readString();
        mCID = in.readInt();
        mFrom = in.readInt();
        mCName = in.readString();
        mPage = in.readInt();
        mPageNum = in.readInt();
        mOrderby = in.readString();
        mFilterColor = in.readString();
        mFilterPrice = in.readString();
        mFilterBond = in.readString();
        mCurRbId = in.readInt();
        mLastOrderRule = in.readString();
        isRequestData = in.readByte() != 0;
    }

    public static final Creator<ProductListActivity> CREATOR = new Creator<ProductListActivity>() {
        @Override
        public ProductListActivity createFromParcel(Parcel in) {
            return new ProductListActivity(in);
        }

        @Override
        public ProductListActivity[] newArray(int size) {
            return new ProductListActivity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isListVIewItemClickAble ? 1 : 0));
        dest.writeString(mKeyword);
        dest.writeInt(mCID);
        dest.writeInt(mFrom);
        dest.writeString(mCName);
        dest.writeInt(mPage);
        dest.writeInt(mPageNum);
        dest.writeString(mOrderby);
        dest.writeString(mFilterColor);
        dest.writeString(mFilterPrice);
        dest.writeString(mFilterBond);
        dest.writeInt(mCurRbId);
        dest.writeString(mLastOrderRule);
        dest.writeByte((byte) (isRequestData ? 1 : 0));
    }


    public interface From {
        public static final int CATEGORY  = 1;
        public static final int HOT       = 2;
        public static final int BOND      = 3;
        public static final int SEARCH    = 4;
        public static final int LIMIT_BUY = 5;
        public static final int NEW       = 6;
        public static final int RECOMMEND = 7;
        public static final int CUXIAO    = 8;
        public static final int OTHER     = 9;
    }

    //内容fragment的标记
    public static final String TAG_PRODUCTLIST_CONTENT = "tag_productlist_content";
    //筛选fragment的标记
    public static final String TAG_PRODUCTLIST_FILTER  = "tag_productlist_filter";

    //持有的内部共有变量
    public ProductListBean mProductListBean;

    //    /productlist?page=1&pageNum=10&cId=125
    public int    mCID     = 125;//当前的分类ID
    public int    mFrom    = From.CATEGORY;
    public String mCName   = "母婴用品";//当前的分类名称
    public int    mPage    = 1;//第几页
    public int    mPageNum = 10;//每页个数
    /**
     * orderby	排序	,目前只有价格有双向排序，其他都只有降序，其中默认为saleDown
     * saleDown(销量降序)，priceUp(价格升序)，priceDown(价格降序)，
     * commentDown(评价降序)，shelvesDown(上架降序)。
     */
    public String mOrderby;
    //filter	筛选	"t1-s1-p8"  (三个条件)
    public String mFilterColor;
    public String mFilterPrice;
    public String mFilterBond;

    int mCurRbId;
    private String mLastOrderRule;

    @Bind(R.id.productlist_title_tv_category)
    TextView mProductlistTitleTvCategory;

    @Bind(R.id.productlist_title_tv_filter)
    TextView mProductlistTitleTvFilter;

    @Bind(R.id.productlist_sort_rg)
    RadioGroup mProductlistSortRg;

    @Bind(R.id.fl_productlist_content)
    FrameLayout mContentContainer;

    @Bind(R.id.fl_productlist_filter)
    FrameLayout mFilterContainer;


    public static final int WITH_KEY    = 0;
    public static final int WITH_ORDER  = 1;
    public static final int WITH_FILTER = 10;

    private boolean isRequestData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

        initView();

        Tag = this;
    }

    //关闭自己
    public static void finishSelf() {
        if (Tag != null) {
            Tag.finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        isListVIewItemClickAble = true;
    }

    private void initData() {

        Intent intent = getIntent();
        mCName = intent.getStringExtra(CATEGORY_NAME);
        //        mKeyword = "奶粉";
        mKeyword = intent.getStringExtra(CATEGORY_KEYWORD);
        mCID = intent.getIntExtra(CATEGORY_ID, -1);
        //        mFrom = From.SEARCH;
        mFrom = intent.getIntExtra(FROM, From.CATEGORY);

        isListVIewItemClickAble = true;
        //请求参数需要 key
        new Thread(new Runnable() {
            @Override
            public void run() {

                mLoadedResult = requestData(WITH_KEY);

                UIUtils.postTaskSafely(new Runnable() {
                    @Override
                    public void run() {

                        initFragment();
                    }
                });
            }
        }).start();
    }


    private void initView() {

        setContentView(R.layout.productlist_content_container);
        setBehindContentView(R.layout.productlist_filter_container);
        ButterKnife.bind(this);

        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setBehindOffset(150);

        mSlidingMenu.setBehindScrollScale(1f);
        /*Animation animation = mSlidingMenu.getAnimation();*/
        mSlidingMenu.setShadowWidth(10);
        mSlidingMenu.setMode(SlidingMenu.RIGHT);
        mSlidingMenu.setFadeDegree(.5f);

        if (mFrom != From.SEARCH) {
            mProductlistTitleTvCategory.setText(mCName);
            mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        } else {
            mProductlistTitleTvFilter.setVisibility(View.GONE);
            mProductlistTitleTvCategory.setText(mKeyword + "-搜索结果");
            mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            mSlidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_NONE);
        }


        //初始化排序相关View
        mCurRbId = R.id.productlist_sort_tv_default;
        mProductlistSortRg.check(mCurRbId);

        mProductlistSortRg.setOnCheckedChangeListener(this);


    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.fl_productlist_content, new ProductListContentFragment(), TAG_PRODUCTLIST_CONTENT);
        if (mFrom != From.SEARCH) {
            transaction.add(R.id.fl_productlist_filter, new ProductListFilterFragment(), TAG_PRODUCTLIST_FILTER);
        }
        transaction.commit();

    }

    /**
     * 得到LeftMenuFragment的实例
     *
     * @return
     */

    public ProductListFilterFragment getFilterFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        ProductListFilterFragment productFilterFragment =
                (ProductListFilterFragment) supportFragmentManager.findFragmentByTag(TAG_PRODUCTLIST_FILTER);
        return productFilterFragment;
    }

    /**
     * 得到ContentFragment的实例
     *
     * @return
     */

    public ProductListContentFragment getContentFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        ProductListContentFragment contentFragment = (ProductListContentFragment) supportFragmentManager.findFragmentByTag(TAG_PRODUCTLIST_CONTENT);
        return contentFragment;
    }


    public String getFilterString() {
        //筛选参数的顺序是:颜色-品牌-价格
        String orderString = mFilterColor;


        if (!TextUtils.isEmpty(mFilterBond)) {
            //品牌筛选有内容
            if (!TextUtils.isEmpty(orderString)) {
                //筛选语句已经有内容了,需要用-连接
                orderString += "-";
            }
            orderString = mFilterBond;
        }

        if (!TextUtils.isEmpty(mFilterPrice)) {
            //价格筛选有内容
            if (!TextUtils.isEmpty(orderString)) {
                //筛选语句已经有内容了,需要用-连接
                orderString += "-";
            }
            orderString = mFilterPrice;
        }

        return orderString;
    }

    @OnClick(R.id.productlist_title_iv_back)
    public void back(View view) {
        finish();
        overridePendingTransition(R.anim.previous_enter, R.anim.previous_exit);
    }

    @OnClick(R.id.productlist_title_tv_filter)
    public void switchFilter(View view) {
        mSlidingMenu.toggle(true);
    }

    @OnClick(R.id.productlist_sort_tv_default)
    public void sortByDefault(RadioButton v) {
        if (mCurRbId == v.getId()) {
            return;
        }
        mOrderby = "saleDown";
        mCurRbId = v.getId();
        if (mdatas != null) {
            mdatas.clear();
        }

        requestDataAndRefreshUiInNewThread(ProductListActivity.WITH_KEY + ProductListActivity.WITH_ORDER);
    }


    @OnClick(R.id.productlist_sort_tv_sale)
    public void sortBySale(RadioButton v) {
        if (mCurRbId == v.getId()) {
            return;
        }
        mOrderby = "saleDown";
        mCurRbId = v.getId();
        if (mdatas != null) {
            mdatas.clear();
        }

        requestDataAndRefreshUiInNewThread(ProductListActivity.WITH_KEY + ProductListActivity.WITH_ORDER);

    }

    @OnClick(R.id.productlist_sort_tv_price)
    public void sortByPrice(RadioButton v) {
        if (mCurRbId != v.getId()) {
            mCurRbId = v.getId();
            mOrderby = TextUtils.isEmpty(mLastOrderRule) ? "priceDown" : mLastOrderRule;
            requestDataAndRefreshUiInNewThread(ProductListActivity.WITH_KEY + ProductListActivity.WITH_ORDER);
            return;
        }
        String text = (String) v.getText();
        String tag = (String) v.getTag();

        switchOrderRule("price");

        mLastOrderRule = mOrderby;
        v.setTag(text);
        v.setText(tag);
        if (mdatas != null) {
            mdatas.clear();
        }

        requestDataAndRefreshUiInNewThread(ProductListActivity.WITH_KEY + ProductListActivity.WITH_ORDER);
    }

    private void switchOrderRule(String rule) {
        if ((rule + "Down").equals(mOrderby)) {
            mOrderby = rule + "Up";
        } else {
            mOrderby = rule + "Down";
        }
    }

    @OnClick(R.id.productlist_sort_tv_comment)
    public void sortByComment(View v) {

        if (mCurRbId == v.getId()) {
            return;
        }
        mOrderby = "commentDown";
        mCurRbId = v.getId();
        if (mdatas != null) {
            mdatas.clear();
        }
        requestDataAndRefreshUiInNewThread(ProductListActivity.WITH_KEY + ProductListActivity.WITH_ORDER);
    }

    public void requestDataAndRefreshUiInNewThread(int mode) {
        if (mContentFragment == null) {
            mContentFragment = getContentFragment();
        }
        if (mContentFragment.mProductlistLv != null) {
            mContentFragment.mProductlistLv.smoothScrollToPosition(0);
            mContentFragment.requestDataAndRefreshUiInNewThread(mode);
        }
    }

    @OnClick(R.id.productlist_sort_tv_shelves)
    public void sortByShelves(View v) {
        if (mCurRbId == v.getId()) {
            return;
        }
        mOrderby = "shelvesDown";
        mCurRbId = v.getId();
        if (mdatas != null) {
            mdatas.clear();
        }
        requestDataAndRefreshUiInNewThread(ProductListActivity.WITH_KEY + ProductListActivity.WITH_ORDER);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
    }

    @NonNull
    public LoadingPager.LoadResultState requestData(int mode) {
        try {
            mProductListBean = requestDataReal();

            if (mProductListBean == null || mProductListBean.productList == null || mProductListBean.productList.size() == 0) {
                mdatas = null;
                return LoadingPager.LoadResultState.EMPTY;
            }

            mdatas = mProductListBean.productList;
            return LoadingPager.LoadResultState.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            mdatas = null;
            return LoadingPager.LoadResultState.ERROR;
        } finally {
            isRequestData = false;
        }
    }

    public ProductListBean requestDataReal() throws Exception {
        isRequestData = true;
        //productlist?page=1&pageNum=10&cId=125&filter=p1-t3-s1

        HashMap<String, String> params = new HashMap<>();
        //params.put("page", mPage + "&pageNum=" + 10 + "&cId=" + cid);
        params.put("page", mPage + "");
        params.put("pageNum", mPageNum + "");
        if (!TextUtils.isEmpty(mOrderby)) {
            params.put("orderby", mOrderby);
        }

        switch (mFrom) {
            case From.SEARCH:
                params.put(ProductListActivity.CATEGORY_KEYWORD, mKeyword);
                return new SearchProductListProtocol().loadData(HttpRequest.HttpMethod.GET, params);
            case From.BOND:
            case From.CATEGORY:
            case From.CUXIAO:
            case From.HOT:
            case From.LIMIT_BUY:
            case From.NEW:
            case From.OTHER:
            case From.RECOMMEND:
            default:
                params.put(ProductListActivity.CATEGORY_ID, mCID + "");

                if (!TextUtils.isEmpty(getFilterString())) {
                    //        if (mode % 100 >= WITH_FILTER && !TextUtils.isEmpty(getFilterString())) {
                    //需要带上筛选参数
                    params.put("filter", getFilterString());
                }

                return new ProductListProtocol().loadData(HttpRequest.HttpMethod.GET, params,null);
        }
    }

    class ProductListProtocol extends BasePlusProtocol<ProductListBean> {
        // http://188.188.5.24:8080/market/productlist?page=1&pageNum=10&cId=125&filter=p1-t3-s1

        @Override
        public String getInterfaceKey() {
            return "productlist?page=1&pageNum=10&cId=125&orderby=saleDown&filter=t1-s1-p8";
        }

        @Override
        public ProductListBean parseJsonString(String resultJsonString) {

            Gson gson = new Gson();
            return gson.fromJson(resultJsonString, ProductListBean.class);
        }
    }

    class SearchProductListProtocol extends BaseProtocol<ProductListBean> {
        // search?page=1&pageNum=10&orderby=saleDown&keyword=%E5%A5%B6%E7%B2%89&filter=t1-s1-p8
        @Override
        public String getInterfaceKey() {
            return "search";
        }

        @Override
        public ProductListBean parseJsonString(String resultJsonString) {
            Gson gson = new Gson();
            return gson.fromJson(resultJsonString, ProductListBean.class);
        }
    }
}

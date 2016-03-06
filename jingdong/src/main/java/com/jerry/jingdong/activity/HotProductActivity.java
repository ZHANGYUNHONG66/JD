package com.jerry.jingdong.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.application.MyApplication;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.base.SuperBaseAdapter;
import com.jerry.jingdong.entity.SaleProductBean;
import com.jerry.jingdong.holder.HomeProductHolder;
import com.jerry.jingdong.protocol.HotProductProtocol;
import com.jerry.jingdong.utils.UIUtils;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

//http://localhost:8080/market/hotproduct?page=0&pageNum=20&orderby=saleDown
public class HotProductActivity extends AppCompatActivity {

    private ListView                mListView;
    private SaleProductBean         mData;
    private LoadingPager            mLoadingPager;
    private HotProductProtocol      mHotProductProtocol;
    private HashMap<String, String> mParamsMap;
    private int mPageIndex = 0;//url的参数page 第几页  每次加载更多 ++1
    private        String             mInterfaceKey;
    private static HotProductActivity Tag; //标记

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_hot_product);

        mInterfaceKey = getIntent().getStringExtra("interfaceKey");

        initView();
        initData();
        Tag = this;
    }

    private void initView() {

        mLoadingPager = new LoadingPager(UIUtils.getContext()) {
            @Override
            public LoadResultState initData() {
                return HotProductActivity.this.loadData();

            }

            @Override
            public View initSuccessView() {
                return HotProductActivity.this.initSuccessView();
            }
        };
        setContentView(mLoadingPager);
    }

    private LoadingPager.LoadResultState loadData() {

        try {
            mHotProductProtocol = new HotProductProtocol(mInterfaceKey);
            MyApplication app = (MyApplication) UIUtils.getContext();

            mParamsMap = new HashMap<>();
            mParamsMap.put("page", mPageIndex + "");//第几页
            mParamsMap.put("pageNum", "10");//每页个数
            mParamsMap.put("orderby", "saleDown");//排序
            SaleProductBean saleProductBean = mHotProductProtocol.loadData(HttpRequest.HttpMethod.GET, mParamsMap);
            if (saleProductBean == null) {
                return LoadingPager.LoadResultState.EMPTY;
            }
            if (saleProductBean.productList == null || saleProductBean.productList.size() == 0) {
                return LoadingPager.LoadResultState.EMPTY;
            }
            mData = saleProductBean;

          /*  *//**模拟数据*//*
            SaleProductBean saleProductBean = hotProductProtocol.parseJsonString("");
            mData = saleProductBean;*/


        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadResultState.ERROR;
        }
        return LoadingPager.LoadResultState.SUCCESS;
    }

    private View initSuccessView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.activity_hot_product, null);
        mListView = (ListView) rootView.findViewById(R.id.home_hotproduct_lv);

        ImageView headIv = new ImageView(UIUtils.getContext());
        headIv.setImageResource(R.drawable.head);
        headIv.setScaleType(ImageView.ScaleType.FIT_XY);

        mListView.addHeaderView(headIv);

        mListView.setAdapter(new MyListAdapter(mListView, mData.productList));

        return rootView;
    }

    class MyListAdapter extends SuperBaseAdapter<SaleProductBean.SaleInfoBean> {


        public MyListAdapter(AbsListView absListView, List datas) {
            super(absListView, datas);
        }

        @Override
        public BaseHolder getSpacialHolder(int position) {
            return new HomeProductHolder();
        }

        @Override
        public boolean haseMoreData() {
            return true;
        }

        @Override
        public List<SaleProductBean.SaleInfoBean> loadMoreData() throws IOException {
            SaleProductBean saleProductBean = null;
            try {
                mParamsMap.put("page", ++mPageIndex + "");
                saleProductBean = mHotProductProtocol.loadData(HttpRequest.HttpMethod.GET, mParamsMap);
                //return saleProductBean.productList;
                //mDatas.addAll(saleProductBean.productList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return saleProductBean.productList;
        }
    }

    //触发加载数据
    private void initData() {
        mLoadingPager.triggerLoadData();
    }

    //关闭自己
    public static void finishSelf() {
        if (Tag != null) {
            Tag.finish();
        }
    }
}

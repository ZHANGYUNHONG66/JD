package com.jerry.jingdong.activity.home;

import android.app.Activity;
import android.os.Bundle;
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

import org.xutils.http.HttpMethod;

import java.util.HashMap;
import java.util.List;

//http://localhost:8080/market/hotproduct?page=0&pageNum=20&orderby=saleDown
public class HotProductActivity extends Activity {

    private ListView        mListView;
    private SaleProductBean mData;
    private LoadingPager mLoadingPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_hot_product);


        initView();
        initData();
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
            HotProductProtocol hotProductProtocol = new HotProductProtocol();
            MyApplication app = (MyApplication) UIUtils.getContext();

            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put("page", "0");//第几页
            paramsMap.put("pageNum", "10");//每页个数
            paramsMap.put("orderby","saleDown");//排序
            SaleProductBean saleProductBean = hotProductProtocol.loadData(HttpMethod.GET, paramsMap, null);
            if (saleProductBean == null){
                return LoadingPager.LoadResultState.EMPTY;
            }
            if (saleProductBean.productList == null || saleProductBean.productList.size() == 0 ){
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

        ImageView imageView = new ImageView(UIUtils.getContext());
        imageView.setImageResource(R.drawable.dvl_text);
        imageView.setScaleType(ImageView.ScaleType.FIT_START);
        //mListView.addHeaderView(imageView);
        mListView.setAdapter(new MyListAdapter(mListView, mData.productList));

        return rootView;
    }

    class MyListAdapter extends SuperBaseAdapter<SaleProductBean> {

        public MyListAdapter(AbsListView absListView, List datas) {
            super(absListView, datas);
        }

        @Override
        public BaseHolder getSpacialHolder(int position) {
            return new HomeProductHolder();
        }

      /*  @Override
        public boolean haseMoreData() {
            return true;
        }

        @Override
        public List<SaleProductBean> loadMoreData() throws IOException {
            return super.loadMoreData();
        }*/
    }

    //触发加载数据
    private void initData() {
        mLoadingPager.triggerLoadData();
    }


}

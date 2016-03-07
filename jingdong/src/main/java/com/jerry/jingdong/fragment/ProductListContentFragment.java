package com.jerry.jingdong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.jingdong.R;
import com.jerry.jingdong.activity.GoodsDetailUI;
import com.jerry.jingdong.activity.ProductListActivity;
import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.base.SuperBaseAdapter;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.entity.ProductListBean;
import com.jerry.jingdong.utils.UIUtils;
import com.jerry.jingdong.views.RatioLayout;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ProductListContentFragment extends BaseFragment<ProductListActivity> {

    @Bind(R.id.productlist_lv)
    public GridView mProductlistLv;

    final int mPadding = UIUtils.dp2px(5);

    private ProductlistAdapter mAdapter;
    private boolean            isRequestData;
    private View               mSuccessView;


    @Override
    public View initSuccessView() {

        if (mSuccessView == null) {
            mSuccessView = View.inflate(UIUtils.getContext(), R.layout.fragment_productlist_content, null);
            ButterKnife.bind(this, mSuccessView);

        }
        refreshSuccessView();

        return mSuccessView;
    }

    private void refreshSuccessView() {
        if (mAdapter == null) {
            mAdapter = new ProductlistAdapter(mProductlistLv, mContext.mdatas);
            mProductlistLv.setAdapter(mAdapter);
        } else {
            mAdapter.mDatas = mContext.mdatas;
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initTitle() {

    }

    @Override
    public LoadingPager.LoadResultState initData() {
        return mContext.mLoadedResult;
    }


    public void requestDataAndRefreshUiInNewThread(final int mode) {
        if (isRequestData) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                //同步请求服务器数据,请求参数需要 key + order
                final LoadingPager.LoadResultState loadResultState = mContext.requestData(mode);
                UIUtils.postTaskSafely(new Runnable() {
                    @Override
                    public void run() {

                        refreshSuccessView();

                        refreshLoadingPagerUi(loadResultState);
                    }
                });
            }
        }).start();
    }
    /*
    *
     * orderby	排序	,目前只有价格有双向排序，其他都只有降序，其中默认为saleDown
     * saleDown(销量降序)，priceUp(价格升序)，priceDown(价格降序)，
     * commentDown(评价降序)，shelvesDown(上架降序)。
     *
            public String mOrderby;
    * */


    class ProductlistAdapter extends SuperBaseAdapter<ProductListBean.ProductListEntity> {


        @Override
        public int getCount() {
            int count = super.getCount();
            if (count % 2 != 0) {

                mContext.hasEmptyData = false;
                return count;
            }
            if (mDatas == null) {
                mDatas = new ArrayList<ProductListBean.ProductListEntity>();
            }
            mDatas.add(new ProductListBean.ProductListEntity());
            mContext.hasEmptyData = true;
            return count + 1;
        }


        public ProductlistAdapter(AbsListView absListView, List<ProductListBean.ProductListEntity> dataSource) {
            super(absListView, dataSource);
        }

        @Override
        public BaseHolder getSpacialHolder(int position) {

            ProductlistHolder productlistHolder = new ProductlistHolder();

            ProductListBean.ProductListEntity entity = (ProductListBean.ProductListEntity) mDatas.get(position);
            productlistHolder.initView(position);
            productlistHolder.initListener(entity);

            return productlistHolder;
        }

        @Override
        public void dealWithDatasInGridView() {

            ProductListBean.ProductListEntity entity = (ProductListBean.ProductListEntity) mDatas.get(mDatas.size() - 1);
            if (entity.id == 0) {
                mDatas.remove(entity);
            }
        }

        @Override
        public List<ProductListBean.ProductListEntity> loadMoreData() {

            SystemClock.sleep(1000);
            ProductListBean productListBean = null;
            try {
                productListBean = mContext.requestDataReal();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return productListBean.productList;
        }

        @Override
        public void onNormalItenClick(AdapterView<?> parent, View view, int position, long id) {

            if (!mContext.isListVIewItemClickAble) {
                return;
            }
            mContext.isListVIewItemClickAble = false;
            ProductListBean.ProductListEntity data = mContext.mProductListBean.productList.get(position);
            String text = "商品是:" + data.name + "--id:" + data.id;

            Intent intent = new Intent(mContext, GoodsDetailUI.class);

            intent.putExtra(GoodsDetailUI.PID, data.id);
            intent.putExtra(GoodsDetailUI.FROM, GoodsDetailUI.From.CATEGORY);

            intent.putExtra(GoodsDetailUI.FROM, GoodsDetailUI.From.CATEGORY);
            Bundle bundle = new Bundle();
            bundle.putParcelable(GoodsDetailUI.From.FROM, mContext);

            bundle.putParcelable(GoodsDetailUI.FROM, mContext);
            intent.putExtras(bundle);

            startActivity(intent);
            mContext.overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
        }
    }

    class ProductlistHolder extends BaseHolder<ProductListBean.ProductListEntity> {


        @Bind(R.id.item_productlist_iv_icon)
        ImageView   mItemProductlistIvIcon;
        @Bind(R.id.item_productlist_rl_icon)
        RatioLayout mItemProductlistRlIcon;
        @Bind(R.id.item_productlist_tv_name)
        TextView    mItemProductlistTvName;
        @Bind(R.id.item_productlist_tv_desc)
        TextView    mItemProductlistTvDesc;
        @Bind(R.id.item_productlist_tv_price)
        TextView    mItemProductlistTvPrice;
        @Bind(R.id.item_productlist_tv_comment)
        TextView    mItemProductlistTvComment;


        @Bind(R.id.item_productlist_iv_joincar)
        ImageView mItemProductlistIvJoinCar;
        @Bind(R.id.item_productlist_iv_favorites)
        ImageView mItemProductlistIvFavorites;

        private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);

        @Override
        public View initRootView() {
            View view = View.inflate(UIUtils.getContext(), R.layout.item_productlist, null);
            ButterKnife.bind(this, view);

            initView();

            return view;
        }


        @Override
        public void initView() {
            //            mItemProductlistIvJoinCar.setBackground(new ColorDrawable(Color.TRANSPARENT));
            mItemProductlistIvJoinCar.setTag(false);
            mItemProductlistIvJoinCar.setPadding(mPadding, mPadding, mPadding, mPadding);
            mItemProductlistIvFavorites.setImageResource(R.mipmap.details_ico_like);
            mItemProductlistIvFavorites.setTag(false);
            mItemProductlistIvFavorites.setPadding(mPadding, mPadding, mPadding, mPadding);
            mItemProductlistTvName.setText("");

            mItemProductlistTvDesc.setText("");

            mItemProductlistTvPrice.setText("");

            mItemProductlistTvComment.setText("");

            mItemProductlistIvIcon.setImageResource(R.drawable.product_loading);
        }

        @Override
        public void initView(int position) {
            super.initView(position);
        }

        @Override
        public void refreshView(ProductListBean.ProductListEntity data) {

            if (data.id == 0) {
                initView();
                return;
            }
            String picUrl = MyConstants.URL.BASEURL + data.pic;
            BitmapUtils bitmapUtils = new BitmapUtils(UIUtils.getContext());
            bitmapUtils.display(mItemProductlistIvIcon, picUrl);

            mItemProductlistTvName.setText(data.name);

            mItemProductlistTvDesc.setText("荷兰原装进口-自家牧场-鲜奶-DHA智研360°");

            mItemProductlistTvPrice.setText("RMB:" + data.price);

            mItemProductlistTvComment.setText("评论 " + data.commentCount);
        }

        public void initListener(ProductListBean.ProductListEntity entity) {
            int pId = entity.id;

            mItemProductlistIvJoinCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView iv = (ImageView) v;
                    Boolean tag = (Boolean) v.getTag();
                    if (!tag) {

                        iv.setTag(true);
                        Toast.makeText(mContext, "添加进购物车,请继续购物", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(mContext, "已加入购物车,无需重复添加", Toast.LENGTH_SHORT).show();
                    }
                    mItemProductlistIvJoinCar.setPadding(mPadding, mPadding, mPadding, mPadding);
                }
            });
            mItemProductlistIvFavorites.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    ImageView iv = (ImageView) v;
                    Boolean tag = (Boolean) v.getTag();
                    if (!tag) {
                        iv.setTag(true);
                        iv.setImageResource(R.mipmap.details_ico_like_press);
                        Toast.makeText(mContext, "已收藏", Toast.LENGTH_SHORT).show();

                    } else {
                        iv.setTag(false);
                        iv.setImageResource(R.mipmap.details_ico_like);
                        Toast.makeText(mContext, "已取消收藏", Toast.LENGTH_SHORT).show();
                    }
                    mItemProductlistIvFavorites.setPadding(mPadding, mPadding, mPadding, mPadding);
                }
            });
        }
    }
}

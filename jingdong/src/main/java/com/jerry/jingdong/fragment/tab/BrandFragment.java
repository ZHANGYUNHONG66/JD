package com.jerry.jingdong.fragment.tab;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.activity.MainUI;
import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.entity.BrandListBean;
import com.jerry.jingdong.utils.UIUtils;

import butterknife.ButterKnife;

/**
 * 品牌
 */

public class BrandFragment extends BaseFragment {


    private GridView      mBrandGrid;
    private GridView mBrandGridSell;
    private BrandListBean mBrandListBean;

    // 图标
    private int[] mBrand = new int[]{
            R.drawable.brand_1,
            R.drawable.brand_2,
            R.drawable.brand_3,
            R.drawable.brand_4,
            R.drawable.brand_5,
            R.drawable.brand_6,
            R.drawable.brand_7,
            R.drawable.brand_more
    };

    private int[] mBrandSell = new int[]{
            R.drawable.brand_sell_1,
            R.drawable.brand_sell_1,
            R.drawable.brand_sell_1

    };



    @Override
    public void initTitle() {
        ((MainUI) getActivity()).mMainTitleview.setTvTitle("品牌");
    }

    /**
     * 在子线程中加载数据
     *
     * @return
     */
    @Override
    public LoadingPager.LoadResultState initData() {


        SystemClock.sleep(2000);
        //        try {
        //            BrandProtocal brandProtocal = new BrandProtocal();
        //            mBrandListBean = brandProtocal.loadData(HttpRequest.HttpMethod.GET, null);
        //

        // if (mBrandListBean != null) {
        return LoadingPager.LoadResultState.SUCCESS;
        //}

        //        } catch (Exception e) {
        //            e.printStackTrace();
        //            return LoadingPager.LoadResultState.ERROR;
//        }
//        return LoadingPager.LoadResultState.EMPTY;
    }

    /**
     * 初始化请求成功后的内容视图
     */
    @Override
    public View initSuccessView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.activity_brand_ui, null);

        mBrandGrid = (GridView) view.findViewById(R.id.brand_grid);
        mBrandGridSell = (GridView) view.findViewById(R.id.brand_grid_sell);

        mBrandGrid.setAdapter(new BrandGridAdapter());
        mBrandGridSell.setAdapter(new BrandGridSellAdapter());
        //
        //        //mBrandList = (ListView) view.findViewById(R.id.brand_list);
        //        mImageView = (ImageView) view.findViewById(R.id.myiamges);
        //
        //
        //        String pic = mBrandListBean.brand.get(0).value.get(0).pic;
        //        //LogUtils.sf(name);
        //        //拼接url
        //        StringBuffer sb = new StringBuffer();
        //        sb.append(MyConstants.URL.BASEURL);
        //        sb.append(pic);
        //        String picURL = sb.toString();
        //
        //        Picasso.with(UIUtils.getContext()).load(picURL).into(mImageView);
        //


        return view;
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

    class BrandGridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mBrand != null) {
                return mBrand.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mBrand != null) {
                return mBrand[position];
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = View.inflate(UIUtils.getContext(), R.layout.items_brand_info, null);
            ImageView brandIv = (ImageView) convertView.findViewById(R.id.brand_images);

            //url+position
            //String pic = mBrandInfos.get(position).value.get(subject_id).pic;

            brandIv.setImageResource(mBrand[position]);

            //图片读取
            // Picasso.with(UIUtils.getContext()).load(url).into(holder.brandIv);

            return convertView;
        }

    }


    class BrandGridSellAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mBrand != null) {
                return mBrand.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mBrand != null) {
                return mBrand[position];
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = View.inflate(UIUtils.getContext(), R.layout.items_brandsell_info, null);
            ImageView brandIvSell = (ImageView) convertView.findViewById(R.id.brand_sell);

            //url+position
            //String pic = mBrandInfos.get(position).value.get(subject_id).pic;

            brandIvSell.setImageResource(mBrandSell[position]);

            //图片读取
            // Picasso.with(UIUtils.getContext()).load(url).into(holder.brandIv);

            return convertView;
        }
    }
}

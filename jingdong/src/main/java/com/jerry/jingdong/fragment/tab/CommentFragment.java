package com.jerry.jingdong.fragment.tab;

import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.activity.MainUI;
import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.entity.BrandListBean;
import com.jerry.jingdong.protocol.BrandProtocal;
import com.jerry.jingdong.utils.UIUtils;
import com.lidroid.xutils.http.client.HttpRequest;
import com.squareup.picasso.Picasso;

/**
 * 品牌
 */

public class CommentFragment extends BaseFragment {

    private ListView      mBrandList;
    private BrandListBean mBrandListBean;

    private int subject_id = 0;
    private ImageView mImageView;


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
        try {
            BrandProtocal brandProtocal = new BrandProtocal();
            mBrandListBean = brandProtocal.loadData(HttpRequest.HttpMethod.GET, null);



            if(mBrandListBean!=null){
                return LoadingPager.LoadResultState.SUCCESS;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadResultState.ERROR;
        }

        return LoadingPager.LoadResultState.EMPTY;
    }

    /**
     * 初始化请求成功后的内容视图
     */
    @Override
    public View initSuccessView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.activity_brand_ui,null);

        //mBrandList = (ListView) view.findViewById(R.id.brand_list);
        mImageView = (ImageView) view.findViewById(R.id.myiamges);


        String pic = mBrandListBean.brand.get(0).value.get(0).pic;
        //LogUtils.sf(name);
        //拼接url
        StringBuffer sb = new StringBuffer();
        sb.append(MyConstants.URL.BASEURL);
        sb.append(pic);
        String picURL = sb.toString();

        Picasso.with(UIUtils.getContext()).load(picURL).into(mImageView);

//        BrandListAdapter brandListAdapter = new BrandListAdapter(data);
//        mBrandList.setAdapter(brandListAdapter);

        return view;
    }

//    class BrandListAdapter extends MyBaseAdapter<BrandListBean> {
//
//        public BrandListAdapter(List<BrandListBean> datas) {
//            super(datas);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            return null;
//        }
//    }

}

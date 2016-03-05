package com.jerry.jingdong.holder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.entity.SaleProductBean;
import com.jerry.jingdong.utils.UIUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 * @创建者     Administrator
 * @创建时间   2016/3/5 0005 14:03
 * @描述	      ${TODO}
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */
public class HomeProductHolder extends BaseHolder<SaleProductBean.SaleInfoBean> {
    //private ImageView mIv;

     @Bind(R.id.item_hotproduct_iv_product)
    ImageView mItem_hotproduct_iv_product;
    @Bind(R.id.item_hotproduct_tv_name)
    TextView  mItem_hotproduct_tv_name;
    @Bind(R.id.item_hotproduct_tv__marketprice)
    TextView  mItem_hotproduct_tv_marketprice;
    @Bind(R.id.item_hotproduct_tv_price)
    TextView  mItem_hotproduct_tv_price;

    @Override
    public View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_home_product, null);
        //mIv = (ImageView) rootView.findViewById(R.id.item__iv);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void refreshView(SaleProductBean.SaleInfoBean data) {
        mItem_hotproduct_tv_name.setText(data.name);
        mItem_hotproduct_tv_marketprice.setText("原价"+data.marketPrice);
        mItem_hotproduct_tv_price.setText("现价"+data.price);
        String picUrl = MyConstants.URL.BASEURL + data.pic.substring(1);
        Log.d("dvl", picUrl);
        Picasso.with(UIUtils.getContext()).load(picUrl).error(R.drawable.q20).into(mItem_hotproduct_iv_product);
    }
}

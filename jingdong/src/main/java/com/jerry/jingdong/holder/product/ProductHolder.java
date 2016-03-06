package com.jerry.jingdong.holder.product;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.activity.GoodsDetailUI;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.entity.CartNewBean;
import com.jerry.jingdong.utils.UIUtils;
import com.jerry.jingdong.views.RatioLayout;
import com.lidroid.xutils.BitmapUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class ProductHolder extends BaseHolder<CartNewBean.ProductEntity> {

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
    ImageView   mItemProductlistIvJoincar;
    @Bind(R.id.item_productlist_iv_favorites)
    ImageView   mItemProductlistIvFavorites;
    private CartNewBean.ProductEntity productEntity;

    @Override
    public View initRootView() {
        View view = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_productlist, null);
        ButterKnife.bind(this, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIUtils.getContext(), GoodsDetailUI.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pid", productEntity.id);
                UIUtils.getContext().startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void refreshView(CartNewBean.ProductEntity data) {
        productEntity = data;
        String picUrl = MyConstants.URL.BASEURL + data.pics.get(0);
        BitmapUtils bitmapUtils = new BitmapUtils(UIUtils.getContext());
        bitmapUtils.display(mItemProductlistIvIcon, picUrl);

        mItemProductlistTvName.setText(data.name);
        mItemProductlistTvPrice.setText("RMB:" + data.price);
        mItemProductlistTvComment.setText("评论 " + data.commentCount);
    }


}

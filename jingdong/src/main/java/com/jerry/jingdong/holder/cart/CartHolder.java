package com.jerry.jingdong.holder.cart;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.jingdong.R;
import com.jerry.jingdong.activity.GoodsDetailUI;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.entity.CartInfoBean;
import com.jerry.jingdong.utils.CartParamsUtils;
import com.jerry.jingdong.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CartHolder extends BaseHolder<CartInfoBean.CartEntity> implements View.OnClickListener {

    private ListView mListView;
    @Bind(R.id.item_normal_select_iv)
    ImageView   mItemNormalSelectIv;
    @Bind(R.id.item_normal_show_iv)
    ImageView   mItemNormalShowIv;
    @Bind(R.id.item_normal_content_tv)
    TextView    mItemNormalContentTv;
    @Bind(R.id.item_normal_color_tv)
    TextView    mItemNormalColorTv;
    @Bind(R.id.item_normal_size_tv)
    TextView    mItemNormalSizeTv;
    @Bind(R.id.item_normal_newprice_tv)
    TextView    mItemNormalNewpriceTv;
    @Bind(R.id.item_normal_oldprice_tv)
    TextView    mItemNormalOldpriceTv;
    @Bind(R.id.item_normal_showmore_ib)
    ImageButton mItemNormalShowmoreIb;
    @Bind(R.id.item_normal_lessen_ib)
    ImageButton mItemNormalLessenIb;
    @Bind(R.id.item_normal_num_et)
    EditText    mItemNormalNumEt;
    @Bind(R.id.item_normal_add_ib)
    ImageButton mItemNormalAddIb;
    @Bind(R.id.item_normal_nogoods_iv)
    ImageView   mItemNormalNogoodsIv;

    private CartInfoBean.CartEntity mCartEntity;
    private CartParamsUtils         mCartUtils;
    private int                     newNum;
    private String                  mNumber;
    private int                     mProdNum;
    private int                     mBuyLimit;

    public CartHolder(ListView cartGoodsShowLv) {
        mListView = cartGoodsShowLv;
    }

    @Override
    public View initRootView() {
        View view = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.cart_item_normal, null);
        ButterKnife.bind(this, view);
        //设置点击事件
        mItemNormalSelectIv.setOnClickListener(this);
        mItemNormalAddIb.setOnClickListener(this);
        mItemNormalLessenIb.setOnClickListener(this);
        mItemNormalShowmoreIb.setOnClickListener(this);
        mItemNormalNumEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final String num = mItemNormalNumEt.getText().toString();
                if ("".equals(num)) {
                    Toast.makeText(UIUtils.getContext(), "不能为空，请重新输入", Toast.LENGTH_SHORT).show();
                    mItemNormalNumEt.setText("1");
                } else if (Integer.parseInt(num) > 10) {
                    Toast.makeText(UIUtils.getContext(), "此商品单词购买上限为10件", Toast.LENGTH_SHORT).show();
                    mItemNormalNumEt.setText("10");
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (Integer.parseInt(num) - newNum >= 0) {
                            for (int i = 0; i < Integer.parseInt(num) - newNum; i++) {
                                //添加到本地数据
                                String pid = mCartEntity.product.id + "";
                                String number = 1 + "";
                                String color = "2";
                                String size = "1";
                                mCartUtils.addCart(pid, number, color, size);
                            }
                        } else {
                            for (int i = 0; i < Integer.parseInt(num) - newNum; i++) {
                                //修改sp数量
                                String pid = mCartEntity.product.id + "";
                                String number = 1 + "";
                                String color = "2";
                                String size = "1";
                                mCartUtils.deleteCart(pid, number, color, size);
                            }
                        }
                        UIUtils.postTaskSafely(new Runnable() {
                            @Override
                            public void run() {
                                if (mOnDataChangeListener != null) {
                                    mOnDataChangeListener.onChanged();
                                }
                            }
                        });
                    }

                });


            }
        });
        return view;
    }

    @Override
    protected void refreshView(CartInfoBean.CartEntity data) {

    }

    @Override
    public void onClick(View v) {

    }

    public void refreshHolderView(CartInfoBean.CartEntity cartEntity) {
        mCartEntity = cartEntity;
        //设置勾选状态
        if (cartEntity.isSeleced()) {
            mItemNormalSelectIv.setSelected(true);
        } else {
            mItemNormalSelectIv.setSelected(false);
        }
        newNum = cartEntity.prodNum;
        mProdNum = cartEntity.prodNum;
        mItemNormalNumEt.setText(mProdNum + "");
        CartInfoBean.CartEntity.ProductEntity product = cartEntity.product;
        mBuyLimit = product.buyLimit;

        int id = product.id;
        //商品姓名
        String name = product.name;
        //商品库存数量，0为缺货或下架
        mNumber = product.number;
        if ("0".equals(mNumber)) {
            mItemNormalNogoodsIv.setVisibility(View.VISIBLE);
            //下架了,字体全部设置为灰色
            mItemNormalColorTv.setTextColor(UIUtils.getColor(R.color.gray));
            mItemNormalSizeTv.setTextColor(UIUtils.getColor(R.color.gray));
            mItemNormalContentTv.setTextColor(UIUtils.getColor(R.color.gray));
            mItemNormalNewpriceTv.setTextColor(UIUtils.getColor(R.color.gray));
            mItemNormalOldpriceTv.setTextColor(UIUtils.getColor(R.color.gray));
            mItemNormalNumEt.setTextColor(UIUtils.getColor(R.color.gray));

            mItemNormalAddIb.setClickable(false);
            mItemNormalLessenIb.setClickable(false);
            mItemNormalNumEt.setClickable(false);
            mItemNormalSelectIv.setClickable(false);
            cartEntity.setSeleced(false);
        }

        String pic = product.pic;

        BitmapUtils bt = new BitmapUtils(UIUtils.getContext());
        pic = MyConstants.URL.BASEURL + pic;
        bt.display(mItemNormalShowIv, pic);
        //得到价格
        int price = product.price;

        mItemNormalNewpriceTv.setText("￥" + price + ".00");
        List<CartInfoBean.CartEntity.ProductEntity.ProductPropertyEntity> productProperty =
                product.productProperty;
        StringBuffer colorSb = new StringBuffer();
        StringBuffer sizeSb = new StringBuffer();
        if (productProperty != null && productProperty.size() > 0)

        {
            for (CartInfoBean.CartEntity.ProductEntity.ProductPropertyEntity productPropertyEntity : productProperty) {
                String k = productPropertyEntity.k;
                if ("颜色".equals(k)) {
                    //证明是颜色
                    colorSb.append(productPropertyEntity.v + ",");
                } else {
                    //证明是尺寸
                    sizeSb.append(productPropertyEntity.v + ",");
                }

            }
            //颜色
            mItemNormalColorTv.setText(UIUtils.getString(R.string.color) + colorSb.toString() + ";");
            //尺寸
            mItemNormalSizeTv.setText(UIUtils.getString(R.string.size) + sizeSb.toString());
        } else

        {
            mItemNormalColorTv.setText(UIUtils.getString(R.string.color) + "空");
            mItemNormalSizeTv.setText(UIUtils.getString(R.string.size) + "空");
        }

        mRootView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UIUtils.getContext(), GoodsDetailUI.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("pid", mCartEntity.product.id);
                        UIUtils.getContext().startActivity(intent);
                    }
                }
        );
    }

    //定义接口
    public interface OnDataChangeListener {
        void onChanged();

        void onDelete();

    }

    private OnDataChangeListener mOnDataChangeListener;

    //接口回调方法
    public void setOnDataChangeListener(OnDataChangeListener listener) {
        mOnDataChangeListener = listener;
    }
}

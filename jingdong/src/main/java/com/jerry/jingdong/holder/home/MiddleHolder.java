package com.jerry.jingdong.holder.home;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.activity.HotProductActivity;
import com.jerry.jingdong.activity.MainUI;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/6 3:11
 * @包名: com.jerry.jingdong.holder.home
 * @工程名: JingDong
 * @描述: 首页中间的部分
 * @描述: TODO：参数暂时不确定
 */
public class MiddleHolder<T> extends BaseHolder
        implements AdapterView.OnItemClickListener {

    private FragmentActivity mHomeFragment;
    // 描述
    private String[]         mTitles = new String[] { "热门单品", "新品上架", "限时抢购",
            "促销快报", "推荐品牌", "商品分类" };

    // 图标
    private int[]            mIcons  = new int[] {
            R.drawable.neirong_remendanpin_anniu_tiaozhuan_moren,
            R.drawable.neirong_xinpinshangjia_anniu_tiaozhuan_moren,
            R.drawable.neirong_xianshiqianggou_anniu_tiaozhuan_moren,
            R.drawable.neirong_cuxiaokuaibao_anniu_tiaozhuan_moren,
            R.drawable.neirong_tuijianpinpai_anniu_tiaozhuan_moren,
            R.drawable.neirong_shangpinfenlei_anniu_tiaozhuan_moren };

    @Bind(R.id.home_middle_gridview)
    GridView                 mHomeMiddleGridview;

    public MiddleHolder(FragmentActivity activity) {
        mHomeFragment = activity;
    }

    @Override
    public View initRootView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.gridview, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    protected void refreshView(Object data) {
        mHomeMiddleGridview.setAdapter(new GridViewAdapter());

        mHomeMiddleGridview.setOnItemClickListener(this);
    }

    /**
     * GridView条目点击事件监听
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        Intent intent = null;
        switch (position) {
        case 0:// 热门单品
               // intent = new Intent(UIUtils.getContext(),
               // HotProductActivity.class);
               // intent.putExtra("interfaceKey", "hotproduct");
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // UIUtils.getContext().startActivity(intent);
            ((MainUI) mHomeFragment).mMainTitleview.setTvTitle("热门单品");

            break;
        case 1:// 新品上架
            intent = new Intent(UIUtils.getContext(), HotProductActivity.class);
            intent.putExtra("interfaceKey", "newproduct");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UIUtils.getContext().startActivity(intent);
            break;
        case 2:// 限时抢购

            break;
        case 3:// 促销快报

            break;
        case 4:// 推荐品牌

            break;
        case 5:// 商品分类

            break;

        default:
            break;
        }
    }

    private class GridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(UIUtils.getContext(),
                    R.layout.item_home_gridview, null);

            ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            TextView tvDes = (TextView) view.findViewById(R.id.tv_des);

            ivIcon.setImageResource(mIcons[position]);
            tvDes.setText(mTitles[position]);

            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }
}

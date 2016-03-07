package com.jerry.jingdong.fragment.tab;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.activity.HotProductActivity;
import com.jerry.jingdong.application.MyApplication;
import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.base.LoadingPager;
import com.jerry.jingdong.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment
        implements ViewPager.OnPageChangeListener, View.OnTouchListener,
        AdapterView.OnItemClickListener {

    @Bind(R.id.home_upview_viewpager)
    ViewPager              mHomeUpviewViewpager;
    @Bind(R.id.home_upview_point_container)
    LinearLayout           mHomeUpviewPointContainer;
    @Bind(R.id.home_middle_gridview)
    GridView               mHomeMiddleGridview;

    private int[]          mPics   = new int[] { R.drawable.top_show_pic1,
            R.drawable.top_show_pic2, R.drawable.top_show_pic3,
            R.drawable.top_show_pic4, R.drawable.top_show_pic5,
            R.drawable.top_show_pic6, R.drawable.top_show_pic7 };

    private AutoScrollTask mAutoScrollTask;

    // 描述
    private String[]       mTitles = new String[] { "热门单品", "新品上架", "限时抢购",
            "促销快报", "推荐品牌", "商品分类" };

    // 图标
    private int[]          mIcons  = new int[] {
            R.drawable.neirong_remendanpin_anniu_tiaozhuan_moren,
            R.drawable.neirong_xinpinshangjia_anniu_tiaozhuan_moren,
            R.drawable.neirong_xianshiqianggou_anniu_tiaozhuan_moren,
            R.drawable.neirong_cuxiaokuaibao_anniu_tiaozhuan_moren,
            R.drawable.neirong_tuijianpinpai_anniu_tiaozhuan_moren,
            R.drawable.neirong_shangpinfenlei_anniu_tiaozhuan_moren };

    @Override
    public void initTitle() {

    }

    /**
     * 在子线程中加载数据
     *
     * @return
     */
    @Override
    public LoadingPager.LoadResultState initData() {
        SystemClock.sleep(2000);

        return LoadingPager.LoadResultState.SUCCESS;
    }

    /**
     * 初始化请求成功后的内容视图
     */
    @Override
    public View initSuccessView() {
        View homeView = View.inflate(UIUtils.getContext(),
                R.layout.home_content_view, null);
        ButterKnife.bind(this, homeView);

        init();

        return homeView;
    }

    /**
     * GridView条目点击事件监听
     */
    // TODO:跳转
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

    /**
     * 初始化
     */
    private void init() {
        // 给ViewPager轮播图设置Adapter
        mHomeUpviewViewpager.setAdapter(new PicturePagerAdapter());
        // 设置页面监听
        mHomeUpviewViewpager.setOnPageChangeListener(this);
        // 设置滑动监听完成自动轮播
        mHomeUpviewViewpager.setOnTouchListener(this);

        // 初始化点
        for (int i = 0; i < mPics.length; i++) {
            ImageView indicator = new ImageView(UIUtils.getContext());
            // 设置点的显示
            indicator.setImageResource(i == 0 ? R.drawable.slide_adv_selected
                    : R.drawable.slide_adv_normal);

            // 获得布局参数对象
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    UIUtils.dp2px(7), UIUtils.dp2px(7));
            if (i != 0)
                params.leftMargin = UIUtils.dp2px(3);

            // 添加到点的容器
            mHomeUpviewPointContainer.addView(indicator, params);
        }

        // 初始化ViewPager选中的页数
        int currIndex = Integer.MAX_VALUE / 2 % mPics.length;

        mHomeUpviewViewpager
                .setCurrentItem((Integer.MAX_VALUE / 2) - currIndex);

        if (mAutoScrollTask == null)
            mAutoScrollTask = new AutoScrollTask();

        mAutoScrollTask.start();

        mHomeMiddleGridview.setAdapter(new GridViewAdapter());

        mHomeMiddleGridview.setOnItemClickListener(this);
    }

    /**
     * 完成自动轮播
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            mAutoScrollTask.stop();
            break;
        case MotionEvent.ACTION_MOVE:
            break;
        case MotionEvent.ACTION_UP:
            mAutoScrollTask.start();
            break;
        }

        return false;
    }

    class AutoScrollTask implements Runnable {
        @Override
        public void run() {
            int currentItem = mHomeUpviewViewpager.getCurrentItem();
            currentItem++;
            mHomeUpviewViewpager.setCurrentItem(currentItem);

            this.start();
        }

        /**
         * 开始自动轮播
         */
        public void start() {
            MyApplication.getMainThreadHandler().postDelayed(this, 2000);
        }

        /**
         * 停止自动轮播
         */
        public void stop() {
            MyApplication.getMainThreadHandler().removeCallbacks(this);
        }
    }

    private class PicturePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            // 无限轮播
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mPics.length;

            ImageView iv = new ImageView(UIUtils.getContext());

            iv.setImageResource(mPics[position]);
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            container.addView(iv);

            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
            int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        position = position % mPics.length;

        for (int i = 0; i < mPics.length; i++) {
            ImageView indicator = (ImageView) mHomeUpviewPointContainer
                    .getChildAt(i);
            indicator.setImageResource(
                    i == position ? R.drawable.slide_adv_selected
                            : R.drawable.slide_adv_normal);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

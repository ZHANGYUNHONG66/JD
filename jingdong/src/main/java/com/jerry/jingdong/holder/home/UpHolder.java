package com.jerry.jingdong.holder.home;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jerry.jingdong.R;
import com.jerry.jingdong.application.MyApplication;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.utils.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/6 2:47
 * @包名: com.jerry.jingdong.holder.home
 * @工程名: JingDong
 * @描述: TODO
 */
public class UpHolder extends BaseHolder<List<String>>
        implements ViewPager.OnPageChangeListener, View.OnTouchListener {

    @Bind(R.id.home_upview_viewpager)
    ViewPager              mHomeUpviewViewpager;
    @Bind(R.id.home_upview_point_container)
    LinearLayout           mHomeUpviewPointContainer;

    private int[]          mPics = new int[] { R.drawable.top_show_pic1,
            R.drawable.top_show_pic2, R.drawable.top_show_pic3,
            R.drawable.top_show_pic4, R.drawable.top_show_pic5,
            R.drawable.top_show_pic6, R.drawable.top_show_pic7 };

    private AutoScrollTask mAutoScrollTask;

    @Override
    public View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.viewpager,
                null);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    protected void refreshView(List<String> data) {
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
}

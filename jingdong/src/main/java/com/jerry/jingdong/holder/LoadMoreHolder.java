package com.jerry.jingdong.holder;

import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.application.MyApplication;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 12:33
 * @包名: com.jerry.jingdong
 * @工程名: JingDong
 * @描述: TODO
 */
public class LoadMoreHolder extends BaseHolder<Integer> {
    public static final int LOADDATA_LOADING = 0;         // 正在加载更多的状态
    public static final int LOADDATA_RETRY   = 1;         // 加载更多失败，点击重试的状态
    public static final int LOADDATA_NONE    = 2;         // 没有加载更多的状态

    private boolean         pointShow        = true;      // 是否变化加载更多中的文字的点
    private int             pointCount       = 1;         // 加载更多文字变化的点的个数

    @Bind(R.id.item_loadmore_container_loading)
    LinearLayout            mItemLoadmoreContainerLoading;
    @Bind(R.id.item_loadmore_container_retry)
    LinearLayout            mItemLoadmoreContainerRetry;
    @Bind(R.id.item_loadmore_iv_loading)
    ImageView               mItemLoadmoreIvLoading;
    @Bind(R.id.item_loadmore_tv_loading)
    TextView                mItemLoadmoreTvLoading;

    @Override
    protected void refreshView(Integer data) {
        // 先隐藏两个控件
        mItemLoadmoreContainerLoading.setVisibility(View.GONE);
        mItemLoadmoreContainerRetry.setVisibility(View.GONE);

        System.out.println(data);

        // 根据状态显示控件
        switch (data) {
        case LOADDATA_LOADING:
            mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
            break;
        case LOADDATA_RETRY:
            // 加载数据完成，不管成功与否都会更新这个的状态，停止下面文字点的循环
            pointShow = false;
            mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
            break;
        case LOADDATA_NONE:
            // 加载数据完成，不管成功与否都会更新这个的状态，停止下面文字点的循环
            pointShow = false;
            break;
        }
    }

    @Override
    public View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(),
                R.layout.item_loadmore_view, null);
        ButterKnife.bind(this, rootView);

        initLoadingMoreAnimation();

        return rootView;
    }

    /**
     * 初始化加载更多动画，给图片和文字添加动画效果
     */
    private void initLoadingMoreAnimation() {
        RotateAnimation ra = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,
                .5f);

        mItemLoadmoreIvLoading.startAnimation(ra);

        new Thread() {
            public void run() {
                while (pointShow) {
                    String points = "";

                    for (int i = 0; i < (pointCount % 6) + 1; i++) {
                        points += ".";
                    }
                    pointCount++;

                    SystemClock.sleep(500);

                    final String mess = "加载更多数据" + points;

                    MyApplication.getMainThreadHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            mItemLoadmoreTvLoading.setText(mess);
                        }
                    });
                }
            };
        }.start();
    }
}

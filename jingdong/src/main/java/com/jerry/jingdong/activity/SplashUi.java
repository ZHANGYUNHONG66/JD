package com.jerry.jingdong.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.jerry.jingdong.R;

public class SplashUi extends Activity {

    private ImageView mIvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_ui);

        initView();

        initAnimation();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mIvWelcome = (ImageView) findViewById(R.id.splash_iv_welcome);
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        AlphaAnimation aa = new AlphaAnimation(.2f, 1);
        aa.setDuration(3000);
        aa.setFillAfter(true);

        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                /* ############ 跳转主页 ############ */
                startActivity(MainUI.class);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mIvWelcome.startAnimation(aa);

    }

    /**
     * 跳转其他界面方法
     */
    private void startActivity(Class type) {
        startActivity(new Intent(SplashUi.this, type));
        finish();
    }
}

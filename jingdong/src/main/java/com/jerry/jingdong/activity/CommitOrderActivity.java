package com.jerry.jingdong.activity;

import android.app.Activity;
import android.os.Bundle;

import com.jerry.jingdong.R;

/**
 * 项目名:    JingDong
 * 包名:      com.jerry.jingdong.activity
 * 文件名:    CommitOrderActivity
 * 创建者:    任洛仟
 * 创建时间:  2016/03/05 下午 6:15
 * 描述:      订单提交成功
 */
public class CommitOrderActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_commit_order);
    }
}
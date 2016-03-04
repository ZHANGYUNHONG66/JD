package com.jerry.jingdong.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jerry.jingdong.R;
import com.jerry.jingdong.fragment.ContentFragment;

public class MainUI extends FragmentActivity {

    private static final String   TAG_FRAGMENT_CONTENT = "fragment_content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 告诉它内容的区域
        setContentView(R.layout.fragment_main_content);

        initFragment();
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction transaction = fragmentManager
                .beginTransaction();

        transaction.add(R.id.fl_main_cantent, new ContentFragment(),
                TAG_FRAGMENT_CONTENT);

        transaction.commit();
    }

}

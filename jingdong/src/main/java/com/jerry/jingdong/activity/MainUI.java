package com.jerry.jingdong.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jerry.jingdong.R;
import com.jerry.jingdong.fragment.ContentFragment;
import com.jerry.jingdong.fragment.TitleFragment;

public class MainUI extends FragmentActivity {

    private static final String TAG_FRAGMENT_CONTENT = "fragment_content";
    private static final String TAG_FRAGMENT_TITLE   = "fragment_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        // 告诉它内容的区域
        setContentView(R.layout.fragment_main_content);

        initFragment();
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.fl_main_title, new TitleFragment(),
                TAG_FRAGMENT_TITLE);

        transaction.add(R.id.fl_main_content, new ContentFragment(),
                TAG_FRAGMENT_CONTENT);

        transaction.commit();
    }

    /**
     * 获得内容Fragment的实例，通过这个实例可以获得内容区域的容器
     */
    public ContentFragment getContentFragment() {
        // 通过添加视图时的tag获得右侧内容区域的实例
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        ContentFragment contentFragment = (ContentFragment) fragmentManager
                .findFragmentByTag(TAG_FRAGMENT_CONTENT);

        return contentFragment;
    }

    /**
     * 获得标题Fragment的实例，通过这个实例可以获得自定义标题，通过标题可以设置相关标题内容
     */
    public TitleFragment getTitleFragment() {
        // 通过添加视图时的tag获得右侧内容区域的实例
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        TitleFragment titleFragment = (TitleFragment) fragmentManager
                .findFragmentByTag(TAG_FRAGMENT_TITLE);

        return titleFragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }
}

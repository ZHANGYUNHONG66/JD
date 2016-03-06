package com.jerry.jingdong.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.jerry.jingdong.R;

/**
 * Created by Administrator on 2016/3/6.
 */
public class LogingView extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.login_view);
    }
}

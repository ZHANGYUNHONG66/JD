package com.jerry.jingdong.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jerry.jingdong.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/6.
 */
public class AccountCentre extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_centre_view);
        ButterKnife.bind(this);

        final Button mQuit = (Button) findViewById(R.id.account_contre_btn_quit);
        mQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuit.setFocusable(true);
                mQuit.setFocusableInTouchMode(true);
                Toast.makeText(AccountCentre.this, "dianji", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

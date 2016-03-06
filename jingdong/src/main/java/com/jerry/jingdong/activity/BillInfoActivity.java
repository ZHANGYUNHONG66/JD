package com.jerry.jingdong.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jerry.jingdong.R;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.entity.BillInfoBean;

import java.io.IOException;

import butterknife.Bind;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 项目名:    JingDong
 * 包名:      com.jerry.jingdong.activity
 * 文件名:    BillInfoActivity
 * 创建者:    任洛仟
 * 创建时间:  2016/03/05 下午 6:31
 * 描述:      填写发票信息
 */
public class BillInfoActivity extends Activity {
    @Bind(R.id.activity_billinfo_lv)
    ListView mActivityBillinfoLv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = MyConstants.URL.BASEURL + "/invoice";
                    FormBody body = new FormBody.Builder().build();
                    Request request = new Request.Builder().post(body).header("userid", "154636").url(url).build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String resJson = response.body().string();
                        parese(resJson);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                mActivityBillinfoLv.setAdapter();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void parese(String resJson) {
        Gson gson = new Gson();
        BillInfoBean billInfoBean = gson.fromJson(resJson, BillInfoBean.class);
    }

    private void initView() {
        setContentView(R.layout.activity_billinfo);
    }
}
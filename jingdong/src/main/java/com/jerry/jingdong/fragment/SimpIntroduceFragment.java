package com.jerry.jingdong.fragment;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jerry.jingdong.R;
import com.jerry.jingdong.activity.GoodsDetailUI;
import com.jerry.jingdong.base.BaseDetailFragment;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.entity.ProductdescBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SimpIntroduceFragment extends BaseDetailFragment {

    private final Context mContext;
    private final Context mSildContext;
    private final int     mPid;
    @Bind(R.id.tv_simp_blck)
    TextView mTvSimpBlck;
    @Bind(R.id.tv_description)
    TextView mTvDescription;

    public SimpIntroduceFragment(Context context, Context sildContext, int pid) {
        mContext = context;
        mSildContext = sildContext;
        mPid = pid;

    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_simp_introduce, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        //加载数据
        HttpUtils httpUtils = new HttpUtils();
        String url = MyConstants.URL.DETAIL + "/description?pId=" + mPid;
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (responseInfo.statusCode == 200 || responseInfo.statusCode == 0) {
                    String result = responseInfo.result;
                    disposeData(result);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });

        mTvSimpBlck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GoodsDetailUI) mSildContext).getContentFragment().setViewState(0);
            }
        });
    }

    private void disposeData(String result) {
        Gson gson = new Gson();
        ProductdescBean productdescBean = gson.fromJson(result, ProductdescBean.class);
        String productdesc = productdescBean.getProductdesc();
        mTvDescription.setText(productdesc);

    }
}

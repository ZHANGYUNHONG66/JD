package com.jerry.jingdong.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jerry.jingdong.R;
import com.jerry.jingdong.activity.GoodsDetailUI;
import com.jerry.jingdong.base.BaseDetailFragment;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.entity.CommentBean;
import com.jerry.jingdong.utils.SPUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class CommentFragment extends BaseDetailFragment {

    private static final String TAG = "commentFragment";
    private final Context mContext;
    private final Context mSildContext;
    private       int     mPid;
    @Bind(R.id.tv_comment_back)
    TextView mTvCommentBlck;
    @Bind(R.id.lv_comment)
    ListView mLvComment;
    int page    = 1;
    int pageNum = 10;
    private List<CommentBean.CommentEntity> mComment;
    private String                          mResult;

    public CommentFragment(Context context, int pid, Context sildContext) {
        mContext = context;
        mPid = pid;
        mSildContext = sildContext;

    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_comment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        //加载数据
        HttpUtils httpUtils = new HttpUtils();
        //数据不全写死在这里
        mPid = 1;
        final String url = MyConstants.URL.DETAIL + "/comment?pId=" + mPid + "&page=" + page + "&pageNum=" + pageNum;
        final SPUtils spUtils = new SPUtils(getContext());
        String result = spUtils.getString(url, "");
        if (!TextUtils.isEmpty(result)) {
            mResult = result;
        }
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (responseInfo.statusCode == 200 || responseInfo.statusCode == 0) {
                    mResult = responseInfo.result;
                    spUtils.putString(url,mResult);
                    disposeData(mResult);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });
        /**
         * 设置返回按钮
         */
        mTvCommentBlck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GoodsDetailUI)mSildContext).getContentFragment().setViewState(0);
            }
        });
    }

    private void disposeData(String result) {
        Gson gson = new Gson();
        CommentBean commentBean = gson.fromJson(result, CommentBean.class);
        if (commentBean!=null) {
            mComment = commentBean.getComment();
        }
        mLvComment.setAdapter(new CommentAdapter());
    }

    private class CommentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mComment.size();
        }

        @Override
        public Object getItem(int position) {
            return mComment.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                ViewHondler hondler;
            if (convertView==null){
                convertView = View.inflate(mContext, R.layout.item_comment, null);
               hondler= new ViewHondler();
                hondler.Name= (TextView) convertView.findViewById(R.id.tv_name);
                hondler.Time= (TextView) convertView.findViewById(R.id.tv_comment_time);
                hondler.Title= (TextView) convertView.findViewById(R.id.tv_title);
                hondler.Context= (TextView) convertView.findViewById(R.id.tv_comment_context);
                convertView.setTag(hondler);
            }else {
               hondler= (ViewHondler) convertView.getTag();
            }
            if (mComment!=null&&mComment.size()>0) {
                hondler.Name.setText(mComment.get(position).getUsername());
                hondler.Title.setText(mComment.get(position).getTitle());
                hondler.Time.setText(mComment.get(position).getTime()+"");
                hondler.Context.setText(mComment.get(position).getContent());
            }

            return convertView;
        }
    }
    public static class ViewHondler{
        TextView Name;
        TextView Time;
        TextView Title;
        TextView Context;
    }

}

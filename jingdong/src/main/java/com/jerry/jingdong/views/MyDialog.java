package com.jerry.jingdong.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.jerry.jingdong.R;

import java.util.List;

/**
 * 项目名:    JingDong
 * 包名:      com.jerry.jingdong.views
 * 文件名:    MyDialog
 * 创建者:    任洛仟
 * 创建时间:  2016/03/06 下午 2:17
 * 描述:      自定义dialog,供显示选择支付方式，送货时间
 */
public class MyDialog extends Dialog {
    private List mDatas;
    private Context mContext;
    private View mRootView;
    private ListView mListView;

    public MyDialog(Context context,List mData) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = View.inflate(mContext, R.layout.pay_dialog_view,null);
        mListView = (ListView) mRootView.findViewById(R.id.dialog_view_lv);
        mListView.setAdapter(new MyAdapter());

        initView();
    }

    private void initView() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
            }
        });
    }

    public void setData(List data){
        mDatas = data;
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }


}

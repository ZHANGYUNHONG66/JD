package com.jerry.jingdong.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.utils.SPUtils;
import com.jerry.jingdong.utils.UIUtils;

import java.util.List;

/**
 * 项目名:    JingDong
 * 包名:      com.jerry.jingdong.views
 * 文件名:    MyDialog
 * 创建者:    任洛仟
 * 创建时间:  2016/03/06 下午 6:15
 * 描述:      TODO
 */
public abstract class MyDialog<T> extends Dialog {
    private Context mContext;
    private View mRootView;
    private ListView mLv;
    public List<T>  mDatas;

    public MyDialog(Context context,List<T> data) {
        this(context, R.style.locationstyles);
        mContext = context;
        mDatas = data;

    }

    public MyDialog(Context context,int theme) {
        super(context, theme);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = View.inflate(mContext, R.layout.pay_dialog_view, null);
        setContentView(mRootView);
        mLv = (ListView) mRootView.findViewById(R.id.dialog_view_lv);

        mLv.setAdapter(new MyAdapter());
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(mDatas != null){
                return mDatas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext,R.layout.item_balanceaccount_center_pay_way,null);
            TextView tv = (TextView) convertView.findViewById(R.id.item_balanceaccount_tv_pay_way);
            final ImageButton iv = (ImageButton) convertView.findViewById(R.id.item_balanceaccount_iv_isSelect);
            final SPUtils spUtils = new SPUtils(UIUtils.getContext());
            int payType = spUtils.getInt("payType", 0);
            int delverType = spUtils.getInt("delverType", 0);
            iv.setImageResource(R.drawable.sina_un);
            if(payType == position && type ==1){
                iv.setImageResource(R.drawable.sina_on);
            }

            if(delverType == position && type == 2){
                iv.setImageResource(R.drawable.sina_on);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnChangeListener.onChange(position, type);
                    iv.setImageResource(R.drawable.sina_on);
                    if(type == 1){
                        spUtils.putInt("payType",position);
                    }
                   if(type == 2){
                       spUtils.putInt("delverType",position);
                   }
                    notifyDataSetChanged();
                    dismiss();
                }
            });
            tv.setText(initData(position));
            return convertView;
        }
    }

    public abstract String initData(int position);
    OnMyChangeListener mOnChangeListener;
    private int type;

    public void setOnChangeListener(OnMyChangeListener onChangeListener,int type){
        mOnChangeListener = onChangeListener;
        this.type = type;
    }
    public interface OnMyChangeListener{
        void onChange(int position,int type);
    }
}

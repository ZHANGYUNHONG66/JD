package com.jerry.jingdong.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jerry.jingdong.R;
import com.jerry.jingdong.utils.UIUtils;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/6 18:57
 * @包名: com.jerry.jingdong.views
 * @工程名: JingDong
 * @描述: 标题的自定义View
 */
public class TitleView extends RelativeLayout implements View.OnClickListener {

    // 搜索输入框
    public EditText       mDaohangSearchEdit;
    // 搜索按钮
    public ImageButton    mDaohangSearchBtn;
    // 普通title左边按钮
    public Button         mBtnLeft;
    // 普通title右边按钮
    public Button         mBtnRight;
    //// 普通title
    public TextView       mTvTitle;
    // 搜索容器
    public LinearLayout   mLlDaohangSearchcontainer;
    // 普通标题容器
    public RelativeLayout mRlDaohangTitlecontainer;

    /**
     * 是否是普通title导航栏,可控制哪个导航栏显示和隐藏
     */
    public void isTitleDaohang(boolean isTitleDaohang) {
        if (isTitleDaohang) {
            mLlDaohangSearchcontainer.setVisibility(View.GONE);
            mRlDaohangTitlecontainer.setVisibility(View.VISIBLE);
        } else {
            mLlDaohangSearchcontainer.setVisibility(View.VISIBLE);
            mRlDaohangTitlecontainer.setVisibility(View.GONE);

        }

    }

    /**
     * 链式调用
     */
    public TitleView setBtnLeftText(String mess) {
        mBtnLeft.setText(mess);
        return this;
    }

    public TitleView setBtnRightText(String mess) {
        mBtnRight.setText(mess);
        return this;
    }

    /**
     * 返回输入框实例
     */
    public EditText getEditText() {
        return mDaohangSearchEdit;
    }

    public TitleView setTvTitle(String mess) {
        mTvTitle.setText(mess);
        return this;

    }

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();

        initListener();
    }

    private void initView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.title_view,
                this);

        mDaohangSearchEdit = (EditText) rootView
                .findViewById(R.id.daohang_search_edit);
        mDaohangSearchBtn = (ImageButton) rootView
                .findViewById(R.id.daohang_search_btn);

        mBtnLeft = (Button) rootView.findViewById(R.id.daohang_btn_left);
        mBtnRight = (Button) rootView.findViewById(R.id.daohang_btn_right);
        mTvTitle = (TextView) rootView.findViewById(R.id.daohang_tv_title);

        // 搜索容器
        mLlDaohangSearchcontainer = (LinearLayout) rootView
                .findViewById(R.id.ll_daohang_searchcontainer);
        // 普通title容器
        mRlDaohangTitlecontainer = (RelativeLayout) rootView
                .findViewById(R.id.rl_daohang_titlecontainer);
    }

    private void initListener() {
        setOnClickListener(this);
    }

    private onTitleButtonClickListener mListener;

    /**
     * 设置接口回调
     */
    public void setOnTitleButtonClickListener(
            onTitleButtonClickListener listener) {
        if (listener != null) {
            this.mListener = listener;
        }
    }

    /**
     * 当任何一个按钮点击时都会回调给监听者
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.daohang_btn_left:
            // 左边按钮
            mListener.onLeftBtnClick();
            break;

        case R.id.daohang_btn_right:
            // 右边按钮
            mListener.onRightBtnClick();
            break;

        case R.id.daohang_search_btn:
            // 搜索按钮
            mListener.onSearchBtnClick();
            break;

        default:
            break;
        }
    }

    /**
     * 回调按钮点击事件的接口
     */
    public interface onTitleButtonClickListener {
        void onLeftBtnClick();

        void onRightBtnClick();

        void onSearchBtnClick();
    }

}

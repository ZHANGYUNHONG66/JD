package com.jerry.jingdong.holder.home;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.utils.UIUtils;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/6 3:11
 * @包名: com.jerry.jingdong.holder.home
 * @工程名: JingDong
 * @描述: 首页中间的部分
 * @描述: TODO：参数暂时不确定
 */
public class MiddleHolder<T> extends BaseHolder {
    @Override
    public View initRootView() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(getClass().getSimpleName());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(30);
        return tv;
    }

    @Override
    protected void refreshView(Object data) {

    }
}

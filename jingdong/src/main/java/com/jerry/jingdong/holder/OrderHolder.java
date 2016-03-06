package com.jerry.jingdong.holder;

import android.view.View;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.utils.UIUtils;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/6 17:16
 * @包名: com.jerry.jingdong.holder
 * @工程名: JingDong
 * @描述: TODO
 */
public class OrderHolder<T> extends BaseHolder {
    @Override
    public View initRootView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.bb, null);
        return view;
    }

    @Override
    protected void refreshView(Object data) {

    }
}

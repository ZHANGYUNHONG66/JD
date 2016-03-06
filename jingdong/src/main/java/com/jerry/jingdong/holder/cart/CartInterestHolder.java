package com.jerry.jingdong.holder.cart;

import android.view.MotionEvent;
import android.view.View;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.utils.UIUtils;


public class CartInterestHolder extends BaseHolder<Object> {

    @Override
    public View initRootView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_cart_item_division, null);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return view;
    }

    @Override
    protected void refreshView(Object data) {

    }
}

package com.jerry.jingdong.holder.mine;

import android.view.View;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.utils.UIUtils;

import butterknife.ButterKnife;

/**
 * @创建者: yangyin
 * @包名: com.jerry.jingdong.base
 * @工程名: JingDong
 * @描述: 账户中心页面
 */
public class AccountCentreHolder extends BaseHolder<Class> {


    @Override
    public View initRootView() {
        View mView = View.inflate(UIUtils.getContext(), R.layout.account_centre_view, null);
        ButterKnife.bind(this, mView);


        return mView;
    }

    @Override
    protected void refreshView(Class data) {
    }
}

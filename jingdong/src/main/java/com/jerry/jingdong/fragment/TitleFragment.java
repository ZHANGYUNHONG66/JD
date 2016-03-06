package com.jerry.jingdong.fragment;

import android.view.View;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseFragment;
import com.jerry.jingdong.utils.UIUtils;
import com.jerry.jingdong.views.TitleView;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/6 20:43
 * @包名: com.jerry.jingdong.fragment
 * @工程名: JingDong
 * @描述: TODO
 */
public class TitleFragment extends BaseFragment {

    public TitleView mTitleView;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_title,
                null);
        mTitleView = (TitleView) view.findViewById(R.id.fragment_title_daohang);

        return view;
    }

}

package com.jerry.jingdong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.jingdong.R;
import com.jerry.jingdong.utils.UIUtils;
import com.jerry.jingdong.views.TitleView;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/6 20:43
 * @包名: com.jerry.jingdong.fragment
 * @工程名: JingDong
 * @描述: 通过该类可以修改标题内容和展示信息
 */
public class TitleFragment extends Fragment {

    public TitleView mTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_title,
                null);

        mTitle = (TitleView) view.findViewById(R.id.fragment_title_daohang);

        return view;
    }
}

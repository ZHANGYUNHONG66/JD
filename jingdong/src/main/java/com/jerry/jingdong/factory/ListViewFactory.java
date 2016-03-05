package com.jerry.jingdong.factory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import com.jerry.jingdong.utils.UIUtils;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 14:33
 * @包名: com.jerry.jingdong.factory
 * @工程名: JingDong
 * @描述: ListView的工厂类
 */
public class ListViewFactory {
    /**
     * 创建进行过常规设置的ListView
     */
    public static ListView createListView() {
        ListView listView = new ListView(UIUtils.getContext());
        listView.setDividerHeight(0);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        listView.setCacheColorHint(Color.TRANSPARENT);
        return listView;
    }
}

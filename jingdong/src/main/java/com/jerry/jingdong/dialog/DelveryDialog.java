package com.jerry.jingdong.dialog;

import android.content.Context;

import com.jerry.jingdong.entity.BalanceAccountCenterInfo;
import com.jerry.jingdong.views.MyDialog;

import java.util.List;

/**
 * 项目名:    JingDong
 * 包名:      com.jerry.jingdong.dialog
 * 文件名:    DelveryDialog
 * 创建者:    任洛仟
 * 创建时间:  2016/03/06 下午 7:53
 * 描述:      TODO
 */
public class DelveryDialog extends MyDialog<BalanceAccountCenterInfo.DelveryList> {


    public DelveryDialog(Context context, List<BalanceAccountCenterInfo.DelveryList> data) {
        super(context, data);
    }

    @Override
    public String initData(int position) {
        return mDatas.get(position).des;
    }
}

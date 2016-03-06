package com.jerry.jingdong.dialog;

import android.content.Context;

import com.jerry.jingdong.entity.BalanceAccountCenterInfo;
import com.jerry.jingdong.views.MyDialog;

import java.util.List;

/**
 * 项目名:    JingDong
 * 包名:      com.jerry.jingdong.dialog
 * 文件名:    PayWayDialog
 * 创建者:    任洛仟
 * 创建时间:  2016/03/06 下午 6:46
 * 描述:      TODO
 */
public class PayWayDialog extends MyDialog<BalanceAccountCenterInfo.PaymentList>{


    public PayWayDialog(Context context, List<BalanceAccountCenterInfo.PaymentList> data) {
        super(context, data);
    }

    @Override
    public String initData(int position) {
        return mDatas.get(position).des;
    }

}


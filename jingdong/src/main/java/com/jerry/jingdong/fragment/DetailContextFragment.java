package com.jerry.jingdong.fragment;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseDetailFragment;
import com.jerry.jingdong.entity.CartNewBean;
import com.jerry.jingdong.utils.UIUtils;

public class DetailContextFragment extends BaseDetailFragment {
    private static final String TAG = "DetailContextFragment";

    private static final int STATE_DETAIL_CONTEXT = 0;
    private static final int STATE_SIMP_INTRODUCE = 1;
    private static final int STATE_COMMENT        = 2;
    private final Context     mSildContext;
    private final int         mPid;
    public        CartNewBean mCartNewBean;
    private int DetailCurrentState = 0;
    private ContextDetailFragment mContextDetailFragment;
    private SimpIntroduceFragment mSimpIntroduceFragment;
    private FragmentManager       mFragmentManager;
    private CommentFragment       mCommentFragment;

    public DetailContextFragment(CartNewBean cartNewBean, Context sildContext, int pid) {
        mCartNewBean = cartNewBean;
        mSildContext = sildContext;
        mPid = pid;
    }


    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.main_context, null);
        initFragment();
        return view;
    }

    private void initFragment() {
        mFragmentManager = getFragmentManager();
        mContextDetailFragment = new ContextDetailFragment(mContext, mCartNewBean, mSildContext, mPid);
        mSimpIntroduceFragment = new SimpIntroduceFragment(mContext, mSildContext, mPid);
        mCommentFragment = new CommentFragment(mContext, mPid, mSildContext);
    }

    @Override
    public void initData() {
        switch (DetailCurrentState) {
            case STATE_DETAIL_CONTEXT:
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.replace(R.id.context_details_fragment, mContextDetailFragment);
                transaction.commit();
                break;
            case STATE_SIMP_INTRODUCE:
                FragmentTransaction tr = mFragmentManager.beginTransaction();
                tr.replace(R.id.context_details_fragment, mSimpIntroduceFragment);
                tr.commit();
                break;
            case STATE_COMMENT:
                FragmentTransaction ctr = mFragmentManager.beginTransaction();
                ctr.replace(R.id.context_details_fragment, mCommentFragment);
                ctr.commit();
                break;
        }
    }

    public void setViewState(int currentState) {
        DetailCurrentState = currentState;
        initData();
    }
}

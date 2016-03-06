package com.jerry.jingdong.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.jerry.jingdong.R;


/**
 * @创建者: Jerry
 * @创建时间: 2016/3/6 12:37
 * @包名: com.jerry.jingdong.views
 * @工程名: JingDong
 * @描述: TODO
 */
public class RatioLayout extends FrameLayout {
    private float           mPicRatio       = 1.0f;
    public static final int RELATIVE_WIDTH  = 0;             // 已知宽度,动态的计算高度
    public static final int RELATIVE_HEIGHT = 1;             // 已知高度,动态的计算宽度
    public int              mCurState       = RELATIVE_WIDTH;

    public void setPicRatio(float picRatio) {
        mPicRatio = picRatio;
    }

    public void setCurState(int curState) {
        mCurState = curState;
    }

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 取出自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RatioLayout);

        mCurState = typedArray.getInt(R.styleable.RatioLayout_rlRelative,RELATIVE_WIDTH);
        mPicRatio = typedArray.getFloat(R.styleable.RatioLayout_rlPicRatio,1.0f);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // 什么时候自己测绘
        // 可以确定宽度是已知的时候
        /*
         * UNSPECIFIED 不确定的 wrap_content AT_MOST 至多 EXACTLY 精确的 match_parent
         * 100dp
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && mCurState == RELATIVE_WIDTH) {// 明确宽度已知->宽度可以得到
            // 得到自身的宽度
            int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
            // 根据公式 RatioLayout自身宽度/RatioLayout自身的高度=图片的宽高比 计算自身的应有的高度
            int selfHeight = (int) (selfWidth / mPicRatio + .5f);

            // 自己测量孩子
            // 得到孩子应有的宽度
            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            // 得到孩子应有的高度
            int childHeight = selfHeight - getPaddingTop() - getPaddingBottom();

            // 测绘孩子
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth,
                    MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec
                    .makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            // 保存测绘的结果
            setMeasuredDimension(selfWidth, selfHeight);
        } else if (heightMode == MeasureSpec.EXACTLY
                && mCurState == RELATIVE_HEIGHT) {// 确保了可以得到对应的高度

            // 得到自身的高度
            int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
            // 根据公式 RatioLayout自身宽度/RatioLayout自身的高度=图片的宽高比 计算自身的应有的宽度
            int selfWidth = (int) (selfHeight * mPicRatio + .5f);

            // 自己测量孩子
            // 得到孩子应有的宽度
            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            // 得到孩子应有的高度
            int childHeight = selfHeight - getPaddingTop() - getPaddingBottom();

            // 测绘孩子
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth,
                    MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec
                    .makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            // 保存测绘的结果
            setMeasuredDimension(selfWidth, selfHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}

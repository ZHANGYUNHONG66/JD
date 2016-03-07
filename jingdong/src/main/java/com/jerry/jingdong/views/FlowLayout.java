package com.jerry.jingdong.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式布局
 */
public class FlowLayout extends ViewGroup {
    private List<Line> mLines = new ArrayList<Line>(); // 用来记录描述有多少行View
    private Line mCurrrenLine;                // 用来记录当前已经添加到了哪一行
    private int mHorizontalSpace = 10;
    private int mVerticalSpace   = 10;

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context) {
        this(context,null);
    }

    public void setSpace(int horizontalSpace, int verticalSpace) {
        this.mHorizontalSpace = horizontalSpace;
        this.mVerticalSpace = verticalSpace;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mLines.clear();
        mCurrrenLine = null;

        int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxLineWidth = layoutWidth - getPaddingLeft() - getPaddingRight();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);

            if (view.getVisibility() == View.GONE) {
                continue;
            }
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            if (mCurrrenLine == null) {
                mCurrrenLine = new Line(maxLineWidth, mHorizontalSpace);
                mLines.add(mCurrrenLine);
                mCurrrenLine.addView(view);
            } else {
                boolean canAdd = mCurrrenLine.canAdd(view);
                if (canAdd) {
                    mCurrrenLine.addView(view);
                } else {
                    mCurrrenLine = new Line(maxLineWidth, mHorizontalSpace);
                    mLines.add(mCurrrenLine);
                    mCurrrenLine.addView(view);
                }
            }
        }

        int measuredWidth = layoutWidth;

        float allHeight = 0;
        for (int i = 0; i < mLines.size(); i++) {
            float mHeigth = mLines.get(i).mHeigth;

            allHeight += mHeigth;
            if (i != 0) {
                allHeight += mVerticalSpace;
            }
        }

        int measuredHeight = (int) (allHeight + getPaddingTop() + getPaddingBottom() + 0.5f);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int paddingLeft = getPaddingLeft();
        int offsetTop = getPaddingTop();
        for (int i = 0; i < mLines.size(); i++) {
            Line line = mLines.get(i);
            line.layout(paddingLeft, offsetTop);
            offsetTop += line.mHeigth + mVerticalSpace;
        }
    }

    class Line {

        private List<View> mViews = new ArrayList<View>();    // 用来记录每一行有几个View
        private float mMaxWidth;                            // 行最大的宽度
        private float mUsedWidth;                        // 已经使用了多少宽度
        private float mHeigth;                            // 行的高度
        private float mMarginLeft;
        private float mMarginRight;
        private float mMarginTop;
        private float mMarginBottom;
        private float mHorizontalSpace;                    // View和view之间的水平间距

        public Line(int maxWidth, int horizontalSpace) {
            this.mMaxWidth = maxWidth;
            this.mHorizontalSpace = horizontalSpace;
        }

        /**
         * 添加view，记录属性的变化
         * @param view
         */
        public void addView(View view) {
            int size = mViews.size();
            int viewWidth = view.getMeasuredWidth();
            int viewHeight = view.getMeasuredHeight();
            if (size == 0) {
                if (viewWidth > mMaxWidth) {
                    mUsedWidth = mMaxWidth;
                } else {
                    mUsedWidth = viewWidth;
                }
                mHeigth = viewHeight;
            } else {
                mUsedWidth += viewWidth + mHorizontalSpace;
                mHeigth = mHeigth < viewHeight ? viewHeight : mHeigth;
            }

            mViews.add(view);
        }

        /**
         * 用来判断是否可以将View添加到line中
         *
         * @param view
         * @return
         */
        public boolean canAdd(View view) {
            int size = mViews.size();
            if (size == 0) {
                return true;
            }

            int viewWidth = view.getMeasuredWidth();
            float planWidth = mUsedWidth + mHorizontalSpace + viewWidth;
            if (planWidth > mMaxWidth) {
                return false;
            }

            return true;
        }

        /**
         * 给孩子布局
         *
         * @param offsetLeft
         * @param offsetTop
         */
        public void layout(int offsetLeft, int offsetTop) {
            int currentLeft = offsetLeft;

            int size = mViews.size();
            float extra = 0;
            float widthAvg = 0;
            if (mMaxWidth > mUsedWidth) {
                extra = mMaxWidth - mUsedWidth;
                widthAvg = extra / size;
            }

            for (int i = 0; i < size; i++) {
                View view = mViews.get(i);
                int viewWidth = view.getMeasuredWidth();
                int viewHeight = view.getMeasuredHeight();
                if (widthAvg != 0) {
                    int newWidth = (int) (viewWidth + widthAvg + 0.5f);
                    int widthMeasureSpec = MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY);
                    int heightMeasureSpec = MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY);
                    view.measure(widthMeasureSpec, heightMeasureSpec);

                    viewWidth = view.getMeasuredWidth();
                    viewHeight = view.getMeasuredHeight();
                }

                int left = currentLeft;
                int top = (int) (offsetTop + (mHeigth - viewHeight) / 2 +
                        0.5f);
                int right = left + viewWidth;
                int bottom = top + viewHeight;
                view.layout(left, top, right, bottom);

                currentLeft += viewWidth + mHorizontalSpace;
            }
        }
    }
}

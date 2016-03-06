package com.jerry.jingdong.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.Toast;

import com.jerry.jingdong.utils.UIUtils;


public class ListScrollView extends ListView {
    public ListScrollView(Context context) {
        super(context);
    }

    public ListScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandspec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandspec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(UIUtils.getContext(), "dwon来了", Toast.LENGTH_SHORT).show();
            break;
            case MotionEvent.ACTION_MOVE:
                Toast.makeText(UIUtils.getContext(), "move来了", Toast.LENGTH_SHORT).show();
            break;
            case MotionEvent.ACTION_UP:
                Toast.makeText(UIUtils.getContext(), "up来了", Toast.LENGTH_SHORT).show();
            break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

}

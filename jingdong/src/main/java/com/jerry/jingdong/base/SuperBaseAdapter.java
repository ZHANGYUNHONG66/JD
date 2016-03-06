package com.jerry.jingdong.base;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jerry.jingdong.application.MyApplication;
import com.jerry.jingdong.factory.ThreadPoolProxyFactory;
import com.jerry.jingdong.holder.LoadMoreHolder;

import java.io.IOException;
import java.util.List;

/**
 * @创建者: Jerry
 * @创建时间: 2016/3/5 12:31
 * @包名: com.jerry.jingdong.base
 * @工程名: JingDong
 * @描述: TODO
 */
public abstract class SuperBaseAdapter<ITEMBEANTYPE> extends MyBaseAdapter
        implements AdapterView.OnItemClickListener {
    public static final int  VIEWTYPE_LOADMORE = 0; // 加载更多的条目类型
    public static final int  VIEWTYPE_NORMAL   = 1; // 普通的条目类型
    public static final int  VIEWTYPE_TITLE    = 2; // 普通的条目类型

    private static final int PAGERSIZE         = 20;
    private LoadMoreTask     mLoadMoreTask;
    private AbsListView      mAbsListView;
    private int              mState;
    private LoadMoreHolder   mLoadMoreHolder;

    public SuperBaseAdapter(AbsListView absListView, List datas) {
        super(datas);
        this.mAbsListView = absListView;

        mAbsListView.setOnItemClickListener(this);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;

        if (convertView == null) {
            // Baseholder完成了设置tag，而设置布局和从布局中find到孩子是交给了BaseHolder的子类具体实现
            if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
                // 如果是加载更多类型，获得加载更多的holder
                holder = getLoadHolder();
            } else {
                // 如果是普通类型，获取普通类型的holder
                holder = getSpacialHolder(position);
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }

        // 通过holder中的这个方法进行数据的设置和数据与视图的绑定，数据从MyBaseAdapter中继承而来
        // 而MyBaseAdapter中的数据从Fragment中设置adapter的构造方法中来
        if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
            if (haseMoreData()) {
                // 初始化加载更多holder的状态
                holder.setDataAndRefreshView(LoadMoreHolder.LOADDATA_LOADING);

                triggerLoadMoreData();
            } else {
                holder.setDataAndRefreshView(LoadMoreHolder.LOADDATA_NONE);
            }
        } else {
            holder.setDataAndRefreshView(mDatas.get(position));
        }

        return holder.mRootView;
    }

    /*------------------ 添加加载更多逻辑 -------------------*/
    /**
     * 是否要加载更多数据，让子类选择性实现，默认没有加载更多数据
     *
     * @return
     */
    public boolean haseMoreData() {
        return false;
    }

    /**
     * @des 触发加载更多数据
     */
    public void triggerLoadMoreData() {
        // 优化：加载更多状态中再上滑不重复加载
        if (mLoadMoreTask == null) {
            // 更新LoadMoreHolderUI
            mLoadMoreHolder
                    .setDataAndRefreshView(LoadMoreHolder.LOADDATA_LOADING);

            // 异步请求网络
            mLoadMoreTask = new LoadMoreTask();
            new ThreadPoolProxyFactory().createNormalThreadPool()
                    .execute(mLoadMoreTask);
        }
    }

    private class LoadMoreTask implements Runnable {
        @Override
        public void run() {
            List<ITEMBEANTYPE> list = null;
            try {
                // 请求数据，获得状态，刷新UI
                // 定义一个状态记录加载更多条目的状态
                mState = LoadMoreHolder.LOADDATA_LOADING;
                list = loadMoreData();

                if (list == null) {
                    // 说明没有更多数据
                    mState = LoadMoreHolder.LOADDATA_NONE;
                } else if (list.size() == PAGERSIZE) {
                    // 说明请求到了20条数据，并且还可能有更多数据
                    // 将状态变为正在加载更多，使下次用户滑动到底部显示
                    mState = LoadMoreHolder.LOADDATA_LOADING;
                } else {
                    // 说明小于20条，说明下次再加载没有更多数据了
                    mState = LoadMoreHolder.LOADDATA_NONE;
                }
            } catch (IOException e) {
                e.printStackTrace();
                // 出现异常时更改状态为加载失败
                mState = LoadMoreHolder.LOADDATA_RETRY;
            }

            // 将更新UI的操作通过Handler提交到主线程
            final List<ITEMBEANTYPE> finalList = list;
            MyApplication.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    // 更新UI-->ListView
                    if (finalList != null) {
                        mDatas.addAll(finalList);
                        notifyDataSetChanged();
                    }

                    Log.d("1111", "加载更多数据完成更新UI" );
                    // 更新UI-->LoadMoreHolder
                    mLoadMoreHolder.setDataAndRefreshView(mState);
                    mLoadMoreHolder.stopTextPointThread();
                }
            });

            // 将任务置空
            mLoadMoreTask = null;
        }
    }

    /**
     * @des 在子线程中加载更多数据，不知道子类要加载的具体数据，交给子类实现
     * @des 不是每个子类都有加载更多数据，让子类选择性实现
     * @throws IOException:抛出异常让调用处根据抛出的异常进行相应的处理
     * @return
     */
    public List<ITEMBEANTYPE> loadMoreData() throws IOException {
        return null;
    }

    /**
     * @des 获得加载更多holder的方法，因为不知道子类要返回什么样的holder，所以让子类去实现
     * @des 不是每个子类都有加载更多，所以让子类选择性实现
     *
     * @return
     */
    public BaseHolder getLoadHolder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }

    /**
     * 获得视图类型的总数
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    /**
     * 获得当前条目的视图类型，在getView方法中根据类型显示不同的视图
     *
     * @param position
     *            当前条目
     * @return 0 — getViewTypeCount()-1之间
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            // 如果是最后一个
            return VIEWTYPE_LOADMORE;
        } else {
            return getNormalType(position);
        }
    }

    /**
     * 返回普通的条目类型，子类需要就重写
     *
     * @param position
     * @return
     */
    public int getNormalType(int position) {
        return VIEWTYPE_NORMAL;
    }

    /**
     * 由于添加了一个加载更多条目，所以在父类的总数上+1个
     *
     * @return
     */
    @Override
    public int getCount() {
        return super.getCount() + 1;
    }
    /*------------------ 添加结束 -------------------*/

    /**
     * 用于子类创建具体的Holder给getView方法
     *
     * @return BaseHolder
     * @param position
     */
    public abstract BaseHolder getSpacialHolder(int position);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        if (parent instanceof ListView) {
            position = position - ((ListView) parent).getHeaderViewsCount();
        }

        // 当当前条目是加载更多类型,并且LoadMoreHolder当前状态为加载失败，点击重试时才响应条目点击事件
        if (getItemViewType(position) == VIEWTYPE_LOADMORE
                && mState == LoadMoreHolder.LOADDATA_RETRY) {
            // 请求数据
            triggerLoadMoreData();
        } else {
            onNormalItenClick(parent, view, position, id);
        }
    }

    /**
     * 普通条目的点击事件，传递给子类实现,子类选择性实现
     */
    public void onNormalItenClick(AdapterView<?> parent, View view,
            int position, long id) {

    }
}

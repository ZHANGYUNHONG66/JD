package com.jerry.jingdong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.jerry.jingdong.R;
import com.jerry.jingdong.conf.MyConstants;
import com.jerry.jingdong.entity.CartNewBean;
import com.jerry.jingdong.factory.ThreadPoolProxyFactory;
import com.jerry.jingdong.fragment.DetailContextFragment;
import com.jerry.jingdong.fragment.RightSelectFragment;
import com.jerry.jingdong.manager.ThreadPoolProxy;
import com.jerry.jingdong.utils.SPUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 商品详细
 */
public class GoodsDetailUI extends SlidingFragmentActivity {

    public static final String TAG_MAIN_CONTENT = "tag_main_content";
    public static final String TAG_RIGHT_SELECT = "tag_right_select";
    public int mPid;
    public final static String PID  = "pid";

    /*public final static String FROM = "from";*/

    public  Parcelable  mParcelable;
    private String      mResultJosn;
    private CartNewBean mCartNewBean;
    private Context     mSildContext;

    public interface From {

        String FROM = "key";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_ui);
        initView();
        initGetPid();
        initDatas(mPid);
    }

    private void initGetPid() {
        Intent intent = getIntent();
        mPid = intent.getIntExtra(PID, 1);
        Bundle bundle = intent.getExtras();
        mParcelable = bundle.getParcelable(From.FROM);
    }

    private void initDatas(final int pid) {

        ThreadPoolProxy threadPoolProxy = ThreadPoolProxyFactory.createNormalThreadPool();
        threadPoolProxy.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpUtils httpUtils = new HttpUtils();
                    String url = MyConstants.URL.DETAIL + "?pId=" + pid;
                    SPUtils spUtils = new SPUtils(getApplicationContext());
                    String result = spUtils.getString(url, "");
                    if (!TextUtils.isEmpty(result)) {
                        mResultJosn = result;
                    }
                    ResponseStream stream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url);
                    mResultJosn = stream.readString();
                    spUtils.putString(url, mResultJosn);
                    disposeData(mResultJosn);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "网络请求失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void disposeData(String resultJosn) {
        Gson gson = new Gson();
        mCartNewBean = gson.fromJson(resultJosn, CartNewBean.class);
    }


    private void initView() {
        mSildContext = this;
        // 告诉它内容区域(above)
        setContentView(R.layout.fragment_main_context);

        // 告诉它菜单区域(behind)-->
        setBehindContentView(R.layout.fragment_select_parameter_right);
        initSlidingMenu();
        initFragment();
    }

    private void initFragment() {
        if (mCartNewBean == null) {
            ThreadPoolProxyFactory.createNormalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1);
                    initFragment();
                }
            });
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.add(R.id.fl_main_context, new DetailContextFragment(mCartNewBean, mSildContext, mPid), TAG_MAIN_CONTENT);

            transaction.add(R.id.fl_right_select, new RightSelectFragment(mCartNewBean, mSildContext, mPid), TAG_RIGHT_SELECT);
            transaction.commit();
        }
    }

    private void initSlidingMenu() {

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setBehindOffset(100);
        slidingMenu.setMode(SlidingMenu.RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.setBehindScrollScale(0.5f);
        slidingMenu.setShadowWidth(10);
        slidingMenu.setShadowDrawable(R.drawable.right_shadow);
    }


    /**
     * 得到RightMenuFragment的实例
     *
     * @return
     */
    public RightSelectFragment getRightSelectFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        RightSelectFragment rightSelectFragment =
                (RightSelectFragment) supportFragmentManager.findFragmentByTag(TAG_RIGHT_SELECT);
        return rightSelectFragment;
    }

    /**
     * 得到ContentFragment的实例
     *
     * @return
     */
    public DetailContextFragment getContentFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        DetailContextFragment DetailContextFragment = (DetailContextFragment) supportFragmentManager.findFragmentByTag(TAG_MAIN_CONTENT);
        return DetailContextFragment;
    }
}

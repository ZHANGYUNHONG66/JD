package com.jerry.jingdong.holder.item;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.jingdong.R;
import com.jerry.jingdong.base.BaseHolder;
import com.jerry.jingdong.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/5.
 */
public class LoginHolder extends BaseHolder<Class> {
    @Bind(R.id.login_activity_et_username)
    EditText mLoginActivityEtUsername;
    @Bind(R.id.login_activity_et_password)
    EditText mLoginActivityEtPwd;
    @Bind(R.id.login_activity_btn_login)
    Button mLoginActivityTvLogin;
    @Bind(R.id.login_activity_tv_forgetpassword)
    TextView mForgetpassword;

    private View mView;
    private SharedPreferences mSp;
    private boolean mIsLogin;

    @Override
    public View initRootView() {
        mView = View.inflate(UIUtils.getContext(), R.layout.activity_login, null);
        ButterKnife.bind(this, mView);

        mLoginActivityTvLogin.setClickable(true);

        //创建SharedPreferences对象
        mSp = UIUtils.getContext().getSharedPreferences("data", 0);
        //获取当前是否已登录的标记
        mIsLogin = mSp.getBoolean("isLogin", false);

        //回显登录状态
        loginStatus(mIsLogin);

        Toast.makeText(UIUtils.getContext(), "isLogin=" + mIsLogin, Toast.LENGTH_SHORT).show();

        return mView;
    }

    @OnClick(R.id.login_activity_et_username)
    public void userName(View v) {
        Toast.makeText(UIUtils.getContext(), "点击", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.login_activity_btn_login)
    public void login(View v) {
        mIsLogin = mSp.getBoolean("isLogin", false);
        if (!mIsLogin) {
            //如果不是登录状态,就显示登录的界面
            String username = mLoginActivityEtUsername.getText().toString();
            String password = mLoginActivityEtPwd.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(UIUtils.getContext(), "帐号或密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            //点击后,将标记打成true
            saveLoginFlag(true);
            loginStatus(true);
            /**
             * 将数据存储到数据库的逻辑代码
             */

        } else {
            //如果是登录状态,点击后就将标记打成false
            saveLoginFlag(false);
            loginStatus(false);
        }
    }

    /**
     * 保存当前是否已登录的标记
     *
     * @param isLogin
     */
    public void saveLoginFlag(boolean isLogin) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putBoolean("isLogin", isLogin);
        editor.commit();
    }

    /**
     * 显示登录状态
     *
     * @param isLogin
     */
    private void loginStatus(boolean isLogin) {
        Toast.makeText(UIUtils.getContext(), "isLogin=" + mIsLogin, Toast.LENGTH_SHORT).show();
        if (!isLogin) {
            //不是登录状态,就将输入框控件类型为登录,并设置控件为可点击
            mLoginActivityTvLogin.setText("登录");
            mLoginActivityEtUsername.setEnabled(true);
            mLoginActivityEtPwd.setEnabled(true);
        } else {
            //是登录状态,就将输入框控件类型为注销,并设置控件不可点击
            mLoginActivityTvLogin.setText("退出登录");
            mLoginActivityEtUsername.setEnabled(false);
            mLoginActivityEtPwd.setEnabled(false);
        }
    }

    @OnClick(R.id.login_activity_tv_forgetpassword)
    public void forget(View v) {
        Toast.makeText(UIUtils.getContext(), "忘记密码被点击了", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void refreshView(Class data) {
    }
}

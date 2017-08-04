package com.betterda.betterdapay.activity;

import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.UserInfo;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.ShapeLoadingDialog;

import java.util.Set;



import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 登录
 * Created by Administrator on 2016/7/29.
 */
public class LoginActivity extends BaseActivity {
    private static final String Tag = "LoginActivity";
    @BindView(R.id.topbar_login)
    NormalTopBar topbarLogin;
    @BindView(R.id.et_login_number)
    TextInputEditText etLoginNumber;
    @BindView(R.id.et_login_pwd)
    TextInputEditText etLoginPwd;
    @BindView(R.id.tv_login_pwd)
    TextView tvLoginPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.relative_login_register)
    RelativeLayout relativeLoginRegister;
    @BindView(R.id.linear_login_pwd)
    LinearLayout linearLoginPwd;
    @BindView(R.id.iv_login_jizhu)
    ImageView iv_login_jizhu;
    private String account, pwd;

    ShapeLoadingDialog dialog;


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_login);
    }


    @Override
    public void init() {
        btnLogin.setSelected(true);
        setTopBar();
        judgePwd();
        dialog = UtilMethod.createDialog(getmActivity(), "正在登录...");

    }



    /**
     * 判断用户是否有记住密码
     */
    private void judgePwd() {

        boolean remember = CacheUtils.getBoolean(getmActivity(), Constants.Cache.REMEMBER, false);
        if (remember) {
            String accout = CacheUtils.getString(getmActivity(), Constants.Cache.ACCOUNT, "");
            etLoginNumber.setText(accout);
            etLoginPwd.setText(CacheUtils.getString(getmActivity(), accout + Constants.Cache.PWD, ""));
            iv_login_jizhu.setSelected(remember);
            //  isLogin();
        }
    }



    private void setTopBar() {
        topbarLogin.setBackVisibility(false);
        topbarLogin.setTitle("登录");
    }

    /**
     * 判断是否可以登录
     */
    private void isLogin() {
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd)) {
            btnLogin.setSelected(true);
        } else {
            btnLogin.setSelected(false);
        }
    }

    @OnClick({R.id.linear_login_pwd, R.id.tv_login_pwd, R.id.btn_login, R.id.relative_login_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_login_pwd://记住密码
                iv_login_jizhu.setSelected(!iv_login_jizhu.isSelected());
                CacheUtils.putBoolean(getmActivity(), Constants.Cache.REMEMBER, iv_login_jizhu.isSelected());
                //获取一下当前输入的帐号保存
                CacheUtils.putString(getmActivity(), Constants.Cache.ACCOUNT, etLoginNumber.getText().toString().toString());
                break;
            case R.id.tv_login_pwd://忘记密码
                UtilMethod.startIntent(getmActivity(), ForgetPwdActivity.class);
                break;
            case R.id.btn_login://登录
             /*   CacheUtils.putBoolean(getmActivity(), "15160700380" + Constants.Cache.AUTH, true);
                CacheUtils.putString(getmActivity(), "15160700380" + Constants.Cache.RANK, "经理");
                CacheUtils.putString(getmActivity(), Constants.Cache.ACCOUNT, "15160700380");
                setAlias();
                finish();*/
               // UtilMethod.showDialog(getmActivity(), dialog);
                Login();
                break;
            case R.id.relative_login_register://注册
                UtilMethod.startIntent(getmActivity(), RegisterActivity.class);
                break;
        }
    }

    private void Login() {
        account = etLoginNumber.getText().toString().trim();
        pwd = etLoginPwd.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            showToast("请输入帐号");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            showToast("请输入密码");
            return;
        }
        NetworkUtils.isNetWork(getmActivity(), btnLogin, () -> getData());


    }

    private void getData() {
        //显示进度对话框
        UtilMethod.showDialog(getmActivity(), dialog);
        mRxManager.add(
                NetWork.getNetService()
                        .getLogin(account, pwd,Constants.APPCODE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<UserInfo>() {
                            @Override
                            protected void onSuccess(UserInfo userInfo, String resultMsg) {
                                parseAndSave(userInfo);

                            }

                            @Override
                            public void onFail(String resultMsg) {

                                UtilMethod.dissmissDialog(getmActivity(), dialog);
                                showToast(resultMsg);
                            }

                            @Override
                            public void onExit() {
                                UtilMethod.dissmissDialog(getmActivity(), dialog);
                            }


                        })
        );
    }

    /**
     * 解析和缓存数据
     *
     * @param userInfo
     */
    private void parseAndSave(UserInfo userInfo) {

        if (userInfo != null) {
            String account = userInfo.getAccount();
            String rate = userInfo.getRank();
            String rankName = userInfo.getRankName();
            CacheUtils.putString(getmActivity(), Constants.Cache.ACCOUNT, account);
            CacheUtils.putString(getmActivity(), account + Constants.Cache.PWD, pwd);
            CacheUtils.putString(getmActivity(), account + Constants.Cache.AUTH, userInfo.getAuth());
            CacheUtils.putString(getmActivity(), account + Constants.Cache.RANK, rate);
            CacheUtils.putString(getmActivity(), account + Constants.Cache.RANKNAME, rankName);
            setAlias();

        }
    }


    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    private void setAlias() {
        boolean alias = CacheUtils.getBoolean(getmActivity(), UtilMethod.getAccout(getmActivity()) + Constants.Cache.ALIAS, false);
        // 调用 Handler 来异步设置别名
        if (!alias) {
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, UtilMethod.getAccout(getmActivity())));
        }
        startToHome();
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    CacheUtils.putBoolean(getmActivity(), UtilMethod.getAccout(getmActivity()) + Constants.Cache.ALIAS, true);

                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";

                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);

                    break;
                default:

                    break;
            }

        }
    };



    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    break;
            }
        }
    };


    public void startToHome() {
        UtilMethod.dissmissDialog(getmActivity(), dialog);
        UtilMethod.startIntent(getmActivity(), HomeActivity.class);
        finish();
    }

}

package com.betterda.betterdapay.activity;

import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.application.MyApplication;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.callback.MyTextWatcher;
import com.betterda.betterdapay.component.DaggerLoginActivityComponent;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.UserInfo;
import com.betterda.betterdapay.modules.LoginActivityModules;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.RxBus;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.ShapeLoadingDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
    @Inject
    ShapeLoadingDialog dialog;
    private Observable<Object> observable;//rxus
    private Subscription subscribe; //网络请求


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_login);
    }



    @Override
    public void init() {
        inject();
        setTopBar();
        judgePwd();
        register();
    }

    /**
     * 注入dagger
     */
    private void inject() {
        DaggerLoginActivityComponent.builder()
                .appComponent(MyApplication.getInstance().getAppComponent())
                .loginActivityModules(new LoginActivityModules(this))
                .build()
                .inject(this);
    }

    /**
     * 注册rxbus 用来关闭页面
     */
    private void register() {
        observable = RxBus.get().register(LoginActivity.class.getSimpleName());
        subscription = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        finish();
                    }
                });
    }

    /**
     *  判断用户是否有记住密码
     */
    private void judgePwd() {

        boolean remember = CacheUtils.getBoolean(getmActivity(), Constants.Cache.REMEMBER, false);
        if (remember) {
            String accout = CacheUtils.getString(getmActivity(), Constants.Cache.ACCOUNT, "");
            etLoginNumber.setText(accout);
            Log.i(Tag, CacheUtils.getString(getmActivity(), accout + Constants.Cache.PWD, "") + "密码");
            etLoginPwd.setText(CacheUtils.getString(getmActivity(), accout + Constants.Cache.PWD, ""));
            iv_login_jizhu.setSelected(remember);
            isLogin();
        }
    }


    @Override
    public void initListener() {
        super.initListener();
        etLoginNumber.addTextChangedListener(new MyTextWatcher(etLoginNumber) {
            @Override
            public void afterTextChanged(Editable s) {
                account = s.toString();
                isLogin();
            }
        });
        etLoginPwd.addTextChangedListener(new MyTextWatcher(etLoginPwd) {
            @Override
            public void afterTextChanged(Editable s) {
                pwd = s.toString();
                isLogin();
            }
        });
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
                // UtilMethod.startIntent(getmActivity(), HomeActivity.class);
                Login();
                break;
            case R.id.relative_login_register://注册
                UtilMethod.startIntent(getmActivity(), RegisterActivity.class);
                break;
        }
    }

    private void Login() {
        if (btnLogin.isSelected()) {//是否是选中状态
           NetworkUtils.isNetWork(getmActivity(), etLoginNumber, new NetworkUtils.SetDataInterface() {
               @Override
               public void getDataApi() {
                   getData();
               }
           });




        }

    }

    private void getData() {
        //显示进度对话框
        UtilMethod.showDialog(getmActivity(), dialog);
        subscribe = NetWork.getNetService(subscribe)
                .getLogin(account, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<UserInfo>() {
                    @Override
                    protected void onSuccess(UserInfo userInfo, String resultMsg) {
                        System.out.println("成功");
                        parseAndSave(userInfo);
                        showToast(resultMsg);
                    }

                    @Override
                    public void onFail(String resultMsg) {
                        System.out.println("失败");
                        UtilMethod.dissmissDialog(getmActivity(), dialog);
                        showToast(resultMsg);
                    }

                    @Override
                    public void onExit() {
                        System.out.println("exit");
                        UtilMethod.dissmissDialog(getmActivity(), dialog);
                    }


                });
    }

    /**
     * 解析和缓存数据
     *
     * @param userInfo
     */
    private void parseAndSave(UserInfo userInfo) {
        if (userInfo != null) {
            String account = userInfo.getAccount();
            String rate = userInfo.getRate();
            String role = userInfo.getRole();
            String cardNo = userInfo.getCardNo();
            String token = userInfo.getToken();
            String trueName = userInfo.getTrueName();
            boolean auth = userInfo.isAuth();
            CacheUtils.putString(getmActivity(), Constants.Cache.ACCOUNT, account);
            CacheUtils.putString(getmActivity(), account + Constants.Cache.TOKEN, token);
            CacheUtils.putString(getmActivity(), account + Constants.Cache.PWD, pwd);
            CacheUtils.getBoolean(getmActivity(), account + Constants.Cache.AUTH, auth);
            UtilMethod.startIntent(getmActivity(), HomeActivity.class);
            UtilMethod.dissmissDialog(getmActivity(), dialog);

        }
    }




    @Override
    protected void onStop() {
        super.onStop();
        UtilMethod.unSubscribe(subscribe);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(LoginActivity.class.getSimpleName(), observable);
    }
}

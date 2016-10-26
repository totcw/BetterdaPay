package com.betterda.betterdapay.activity;

import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.callback.MyTextWatcher;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.ShapeLoadingDialog;
import com.betterda.mylibrary.view.CountDown;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码
 * Created by Administrator on 2016/8/1.
 */
public class ForgetPwdActivity extends BaseActivity {

    @BindView(R.id.topbar_forgetpwd)
    NormalTopBar topbarForgetpwd;
    @BindView(R.id.et_forgetpwd_number)
    TextInputEditText etForgetpwdNumber;
    @BindView(R.id.et_forgetpwd_yzm)
    TextInputEditText et_forgetpwd_yzm;
    @BindView(R.id.countdown_register)
    CountDown countdownRegister;
    @BindView(R.id.et_forgetpwd_pwd)
    TextInputEditText etForgetpwdPwd;
    @BindView(R.id.et_forgetpwd_pwd2)
    TextInputEditText etForgetpwdPwd2;
    @BindView(R.id.btn_forgetpwd)
    Button btnForgetpwd;
    private String number, pwd, pwd2, yzm;
    private ShapeLoadingDialog dialog;
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_forgetpwd);
    }

    @Override
    public void init() {
        super.init();
        topbarForgetpwd.setTitle("忘记密码");
        setTextListener();
    }

    private void setTextListener() {
        etForgetpwdNumber.addTextChangedListener(new MyTextWatcher(etForgetpwdNumber) {
            @Override
            public void afterTextChanged(Editable s) {
                number = s.toString();
                judge();
            }
        });
        et_forgetpwd_yzm.addTextChangedListener(new MyTextWatcher(et_forgetpwd_yzm) {
            @Override
            public void afterTextChanged(Editable s) {
                yzm = s.toString();
                judge();
            }
        });
        etForgetpwdPwd.addTextChangedListener(new MyTextWatcher(etForgetpwdPwd) {
            @Override
            public void afterTextChanged(Editable s) {
                pwd = s.toString();
                judge();
            }
        });
        etForgetpwdPwd2.addTextChangedListener(new MyTextWatcher(etForgetpwdPwd2) {
            @Override
            public void afterTextChanged(Editable s) {
                pwd2 = s.toString();
                judge();
            }
        });
    }

    private void judge() {
        if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(pwd2) && !TextUtils.isEmpty(yzm)) {
            btnForgetpwd.setSelected(true);
        } else {
            btnForgetpwd.setSelected(false);
        }
    }

    @OnClick({R.id.countdown_register, R.id.btn_forgetpwd, R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.countdown_register:
                break;
            case R.id.btn_forgetpwd:
                register();
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }

    private void register() {
        if (pwd != number) {
            if (!pwd.equals(pwd2)) {
                showToast("密码不一致");
                return;
            }
        }
        if (dialog == null) {
            dialog = UtilMethod.createDialog(getmActivity(), "");
        }
        getData();
    }

    private void getData() {
        NetworkUtils.isNetWork(getmActivity(), et_forgetpwd_yzm, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                UtilMethod.showDialog(getmActivity(),dialog);
                subscription = NetWork.getNetService(subscription)
                        .getPwdUpdate(UtilMethod.getAccout(getmActivity()),pwd)
                        .compose(NetWork.handleResult(new BaseCallModel<String>()))
                        .subscribe(new MyObserver<String>() {
                            @Override
                            protected void onSuccess(String data, String resultMsg) {
                                showToast(resultMsg);
                                UtilMethod.dissmissDialog(getmActivity(),dialog);
                                finish();
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                showToast(resultMsg);
                                UtilMethod.dissmissDialog(getmActivity(),dialog);
                            }

                            @Override
                            public void onExit() {
                                ExitToLogin();
                                UtilMethod.dissmissDialog(getmActivity(), dialog);
                            }
                        });
            }
        });
    }
}

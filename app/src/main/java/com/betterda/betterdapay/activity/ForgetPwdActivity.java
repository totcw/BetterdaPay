package com.betterda.betterdapay.activity;

import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.callback.MyTextWatcher;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.Constants;
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
public class ForgetPwdActivity extends BaseActivity implements CountDown.onSelectListener {

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
    private String verfication, verficationNumber;//服务器返回的验证码和验证时的手机号

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_forgetpwd);
    }

    @Override
    public void init() {
        super.init();
        topbarForgetpwd.setTitle("忘记密码");
        countdownRegister.setListener(this);
        countdownRegister.setBackground(R.drawable.blue_selector);
        setTextListener();
    }

    private void setTextListener() {
        etForgetpwdNumber.addTextChangedListener(new MyTextWatcher(etForgetpwdNumber) {
            @Override
            public void afterTextChanged(Editable s) {
                number = s.toString();
                judge();
                isGetVerification();
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
                countDown();
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

        if (TextUtils.isEmpty(number)) {
            showToast("手机号不能为空");
            return;
        }
      /*  if (TextUtils.isEmpty(yzm)) {
            showToast("验证码不能为空");
            return;
        }*/
        if (TextUtils.isEmpty(pwd)) {
            showToast("密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd2)) {
            showToast("确认密码不能为空");
            return;
        }

        if (number != null) {
            boolean ismobile = number.matches(Constants.NUMBER_REGULAR);
            if (!ismobile) {
                showToast("请填写正确的手机号码");
                return;
            } else {
             /*   if (!number.equals(verficationNumber)) {
                    showToast("验证码错误");
                    return;
                } else {
                    if (!yzm.equals(verfication)) {
                        showToast("验证码错误");
                        return;
                    }
                }*/
            }
        }

        if (pwd != null) {
            if (!pwd.equals(pwd2)) {
                showToast("密码不一致");
                return;
            }
            boolean isPwd = pwd.matches("^[a-zA-Z0-9]{6,20}+$");
            if (!isPwd) {
                showToast("请输入正确的密码位数");
                return;
            }
        }
        if (dialog == null) {
            dialog = UtilMethod.createDialog(getmActivity(), "正在提交...");
        }
        getData();
    }

    private void getData() {
        NetworkUtils.isNetWork(getmActivity(), btnForgetpwd, () -> {
            UtilMethod.showDialog(getmActivity(), dialog);
            mRxManager.add(
                    NetWork.getNetService()
                            .getPwdUpdate(number, pwd,Constants.APPCODE)
                            .compose(NetWork.handleResult(new BaseCallModel<String>()))
                            .subscribe(new MyObserver<String>() {
                                @Override
                                protected void onSuccess(String data, String resultMsg) {
                                    showToast(resultMsg);
                                    UtilMethod.dissmissDialog(getmActivity(), dialog);
                                    finish();
                                }

                                @Override
                                public void onFail(String resultMsg) {

                                    showToast(resultMsg);
                                    UtilMethod.dissmissDialog(getmActivity(), dialog);
                                }

                                @Override
                                public void onExit(String resultMsg) {
                                    UtilMethod.dissmissDialog(getmActivity(), dialog);
                                    ExitToLogin(resultMsg);
                                }
                            })
            );
        });
    }

    /**
     * 短信验证
     */
    private void countDown() {
        //处理验证码
        if (countdownRegister.isSelected()) {
            //用正则判断是否是手机号码
            if (!TextUtils.isEmpty(number)) {
                boolean ismobile = number.matches(Constants.NUMBER_REGULAR);
                if (ismobile) {
                    verficationNumber = number;
                    getVerification();
                    //显示倒计时
                    countdownRegister.showCountDown("秒后重新获取", "60秒后重新获取");
                } else {
                    UtilMethod.Toast(this, "请输入正确的手机号码");
                }
            }

        }
    }


    @Override
    public void setSelect(CountDown countDown) {
        //倒计时完了要判断手机号是否为空
        if (TextUtils.isEmpty(number)) {
            countDown.setSelected(false);
        } else {
            countDown.setSelected(true);
        }
    }

    /**
     * 是否可以获取验证码
     */
    private void isGetVerification() {
        //如果是不可以点击的状态,就一直设置为不选中
        if (countdownRegister.isClickable()) {
            if (!TextUtils.isEmpty(number)) {
                countdownRegister.setSelected(true);
            } else {
                countdownRegister.setSelected(false);
            }
        } else {
            countdownRegister.setSelected(false);
        }


    }

    /**
     * 获取验证码
     */
    private void getVerification() {
        NetworkUtils.isNetWork(this, null, () -> mRxManager.add(
                NetWork.getNetService().getSendMsg(number)
                        .compose(NetWork.handleResult(new BaseCallModel<>()))
                        .subscribe(new MyObserver<String>() {
                            @Override
                            protected void onSuccess(String data, String resultMsg) {
                                verfication = data;
                                if (BuildConfig.LOG_DEBUG) {

                                    System.out.println("yzm:" + data);
                                }
                            }

                            @Override
                            public void onFail(String resultMsg) {

                            }

                            @Override
                            public void onExit(String resultMsg) {
                                ExitToLogin(resultMsg);
                            }
                        })
        ));
    }
}

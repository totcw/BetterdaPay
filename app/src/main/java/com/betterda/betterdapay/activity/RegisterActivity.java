package com.betterda.betterdapay.activity;

import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 注册
 * Created by Administrator on 2016/7/29.
 */
public class RegisterActivity extends BaseActivity implements CountDown.onSelectListener {
    private static final String Tag = "RegisterActivity";
    @BindView(R.id.topbar_register)
    NormalTopBar topbarRegister;
    @BindView(R.id.et_register_number)
    TextInputEditText etRegisterNumber;
    @BindView(R.id.et_register_yzm)
    TextInputEditText etRegisterYzm;
    @BindView(R.id.countdown_register)
    CountDown countdownRegister;
    @BindView(R.id.et_register_pwd)
    TextInputEditText etRegisterPwd;
    @BindView(R.id.et_register_pwd2)
    TextInputEditText etRegisterPwd2;
    @BindView(R.id.et_register_phone)
    TextInputEditText etRegisterPhone;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.linear_register_xieyi)
    LinearLayout linearRegisterXieyi;
    @BindView(R.id.iv_register_choose)
    ImageView ivRegisterChoose;
    private String number, pwd, pwd2, yzm, phone;
    private ShapeLoadingDialog dialog;
    private String verfication,verficationNumber;//服务器返回的验证码和验证时的手机号

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_register);
    }

    @Override
    public void init() {
        super.init();
        topbarRegister.setTitle("注册");
        countdownRegister.setListener(this);
        countdownRegister.setBackground(R.drawable.blue_selector);
        dialog = UtilMethod.createDialog(getmActivity(), "正在注册...");
        setEditListener();

    }

    private void setEditListener() {
        etRegisterNumber.addTextChangedListener(new MyTextWatcher(etRegisterNumber) {
            @Override
            public void afterTextChanged(Editable s) {
                number = s.toString();
                judge();
                isGetVerification();
            }
        });
        etRegisterPwd.addTextChangedListener(new MyTextWatcher(etRegisterPwd) {
            @Override
            public void afterTextChanged(Editable s) {
                pwd = s.toString();
                judge();
            }
        });
        etRegisterPwd2.addTextChangedListener(new MyTextWatcher(etRegisterPwd2) {
            @Override
            public void afterTextChanged(Editable s) {
                pwd2 = s.toString();
                judge();
            }
        });
        etRegisterYzm.addTextChangedListener(new MyTextWatcher(etRegisterYzm) {
            @Override
            public void afterTextChanged(Editable s) {
                yzm = s.toString();
                judge();
            }
        });
        etRegisterPhone.addTextChangedListener(new MyTextWatcher(etRegisterPhone) {
            @Override
            public void afterTextChanged(Editable s) {
                phone = s.toString();
                judge();
            }
        });
    }

    private void judge() {
        if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(pwd2)
                && !TextUtils.isEmpty(yzm)  && ivRegisterChoose.isSelected()) {
            btnRegister.setSelected(true);
        } else {
            btnRegister.setSelected(false);
        }
    }

    @OnClick({R.id.countdown_register, R.id.btn_register, R.id.linear_register_xieyi, R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.countdown_register:
                countDown();
                break;
            case R.id.linear_register_xieyi:
                ivRegisterChoose.setSelected(!ivRegisterChoose.isSelected());
                judge();
                break;
            case R.id.btn_register:
                register();
                break;
            case R.id.bar_back:
                back();
                break;
        }
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
            } else {
                showToast("请输入手机号码");
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
     * 注册
     */
    private void register() {

        if (TextUtils.isEmpty(number)) {
            showToast("手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(yzm)) {
            showToast("验证码不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            showToast("密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd2)) {
            showToast("确认密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showToast("邀请码不能为空");
            return;
        }

        if (!ivRegisterChoose.isSelected()) {
            showToast("请勾选用户服务协议");
            return;
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

        if (number != null) {
            boolean ismobile = number.matches(Constants.NUMBER_REGULAR);
            if (!ismobile) {
                showToast("请填写正确的手机号码");
                return;
            } else {
                if (!number.equals(verficationNumber)) {
                    showToast("验证码错误");
                    return;
                } else {
                    if (!yzm.equals(verfication)) {
                        showToast("验证码错误");
                        return;
                    }
                }
            }



        }

        NetworkUtils.isNetWork(getmActivity(), etRegisterNumber, () -> getdata(number, pwd, phone));

    }

    /**
     * 获取验证码
     */
    private void getVerification() {
        NetworkUtils.isNetWork(this, btnRegister, () -> mRxManager.add(
                NetWork.getNetService().getSendMsg(number,getString(R.string.appCode))
                        .compose(NetWork.handleResult(new BaseCallModel<String>()))
                        .subscribe(new MyObserver<String>() {
                            @Override
                            protected void onSuccess(String data, String resultMsg) {
                                verfication = data;
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("验证码:"+data);
                                }
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                showToast(resultMsg);
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("验证码fail:"+resultMsg);
                                }
                            }

                            @Override
                            public void onExit(String resultMsg) {
                                showToast(resultMsg);
                            }
                        })
        ));
    }


    private void getdata(final String number, final String password, final String phone) {
        UtilMethod.showDialog(getmActivity(), dialog);
        mRxManager.add(
                NetWork.getNetService()
                        .getRegister(number, password, phone,getString(R.string.appCode))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
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
    }


}

package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyTextWatcher;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.ShapeLoadingDialog;
import com.betterda.mylibrary.view.CountDown;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 注册
 * Created by Administrator on 2016/7/29.
 */
public class RegisterActivity extends BaseActivity {
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

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_register);
    }

    @Override
    public void init() {
        super.init();
        topbarRegister.setTitle("注册");
        dialog = UtilMethod.createDialog(getmActivity(), "正在注册...");
        setEditListener();

    }

    private void setEditListener() {
        etRegisterNumber.addTextChangedListener(new MyTextWatcher(etRegisterNumber) {
            @Override
            public void afterTextChanged(Editable s) {
                number = s.toString();
                judge();
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
                && !TextUtils.isEmpty(yzm) && !TextUtils.isEmpty(phone) && ivRegisterChoose.isSelected()) {
            btnRegister.setSelected(true);
        } else {
            btnRegister.setSelected(false);
        }
    }

    @OnClick({R.id.countdown_register, R.id.btn_register, R.id.linear_register_xieyi, R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.countdown_register:
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
     * 注册
     */
    private void register() {

        if (pwd != null) {
            if (!pwd.equals(pwd2)) {
                showToast("密码不一致");
                return;
            }
        }

        NetworkUtils.isNetWork(getmActivity(), etRegisterNumber, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                getdata(number, pwd, phone);
            }
        });

    }

    private void getdata(String number, String password, String phone) {
        UtilMethod.showDialog(getmActivity(), dialog);
        subscription = NetWork.getNetService(subscription)
                .getRegister(number, password, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseCallModel<String>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(Tag, "completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.toString());
                        UtilMethod.dissmissDialog(getmActivity(), dialog);
                    }

                    @Override
                    public void onNext(BaseCallModel<String> stringBaseCallModel) {
                        UtilMethod.dissmissDialog(getmActivity(), dialog);
                        if (stringBaseCallModel != null) {
                            showToast(stringBaseCallModel.getResultMsg());
                        }
                        finish();
                    }
                });
    }


}

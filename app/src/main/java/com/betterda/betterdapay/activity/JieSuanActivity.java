package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.callback.MyTextWatcher;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.WithDraw;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.ShapeLoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 结算
 * Created by Administrator on 2016/8/5.
 */
public class JieSuanActivity extends BaseActivity {
    @BindView(R.id.topbar_tixian)
    NormalTopBar topbarJiesuan;
    @BindView(R.id.et_jiesuan_number)
    TextInputEditText etJiesuanNumber;
    @BindView(R.id.tv_jiesuan_money)
    TextView tvJiesuanMoney;
    @BindView(R.id.tv_jiesuan_all)
    TextView tvJiesuanAll;
    @BindView(R.id.btn_jiesuan_comfirm)
    Button btnJiesuanComfirm;

    private float money ;//可结算的余额
    private float sum;//输入的结算金额
    private ShapeLoadingDialog mDialog;
    private AlertDialog mAlertDialog;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_tixian);
    }


    @Override
    public void init() {
        super.init();
        topbarJiesuan.setTitle(Constants.WITHDRAW);
        getIntentData();
        setTextChange();
        tvJiesuanMoney.setText("余额￥" + money);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            String payUp = intent.getStringExtra("money");
            if (TextUtils.isEmpty(payUp)) {
                payUp = "0";
            }
            try {
                money = Float.valueOf(payUp);
            } catch (Exception e) {

            }
        }
    }

    private void setTextChange() {
        etJiesuanNumber.addTextChangedListener(new MyTextWatcher(etJiesuanNumber) {
            @Override
            public void afterTextChanged(Editable s) {
                judge(s.toString());
            }
        });
    }

    /**
     * 判断输入的值是否正确
     *
     * @param s
     */
    private void judge(String s) {

        if (TextUtils.isEmpty(s)) {
            sum = 0;
            btnJiesuanComfirm.setSelected(false);
            return;
        }
        if (s.matches(Constants.str)) {
            try {
                float money2 = Float.valueOf(s);
                if (money2 <= money) {
                    btnJiesuanComfirm.setSelected(true);
                    sum = money2;
                } else {
                    sum = 0;
                    etJiesuanNumber.setError("余额不足");
                    btnJiesuanComfirm.setSelected(false);
                }

            } catch (Exception e) {
                sum = 0;
                etJiesuanNumber.setError("输入有误");
                btnJiesuanComfirm.setSelected(false);
            }
        } else {
            sum = 0;
            etJiesuanNumber.setError("输入有误");
            btnJiesuanComfirm.setSelected(false);
        }


    }

    @OnClick({R.id.tv_jiesuan_all, R.id.btn_jiesuan_comfirm, R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_jiesuan_all:
                takeAll();
                break;
            case R.id.btn_jiesuan_comfirm:
                comfirm();
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }

    private void takeAll() {
        if (money == 0) {
            showToast("余额不足");
        } else {
            sum = money;
            submit();
        }
    }

    private void comfirm() {
        if (btnJiesuanComfirm.isSelected()) {
            submit();

        } else {
            showToast("请输入提现金额");
        }

    }

    private void submit() {
        //判断余额是否足够
        if (sum == 0) {
            showToast("请输入"+Constants.WITHDRAW+"余额");
            return;
        }
        //手续费要1元
        if (sum <= 1) {
            showToast(Constants.WITHDRAW+"金额必须大于1元");
            return;
        }

        if (sum <= money) {
            NetworkUtils.isNetWork(getmActivity(), null, () -> {
                if (mDialog == null) {
                    mDialog = UtilMethod.createDialog(getmActivity(), "正在提交...");
                }
                UtilMethod.showDialog(getmActivity(), mDialog);
                mRxManager.add(
                        NetWork.getNetService()
                                .getJiesuan(UtilMethod.getAccout(getmActivity()), sum + "",getString(R.string.appCode))
                                .compose(NetWork.handleResult(new BaseCallModel<>()))
                                .subscribe(new MyObserver<WithDraw>() {
                                    @Override
                                    protected void onSuccess(WithDraw data, String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {

                                            System.out.println("结算:" + data);
                                        }
                                        UtilMethod.dissmissDialog(getmActivity(), mDialog);
                                        createWithDrawDialog(resultMsg);
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("结算fail:" + resultMsg);
                                        }
                                        UtilMethod.dissmissDialog(getmActivity(), mDialog);
                                        showToast(resultMsg);
                                    }

                                    @Override
                                    public void onExit(String resultMsg) {
                                        UtilMethod.dissmissDialog(getmActivity(), mDialog);
                                        ExitToLogin(resultMsg);
                                    }
                                })
                );
            });
        } else {
            showToast("余额不足");
        }
    }

    /**
     * 创建提现的提示对话框
     */
    public void createWithDrawDialog(String content) {
        if (mAlertDialog == null) {
            View view = LayoutInflater.from(getmActivity()).inflate(R.layout.dialog_withdraw, null);
            TextView mTvContent = (TextView) view.findViewById(R.id.tv_dialog_call_content);
            mTvContent.setText(content);
            Button mBtnComfirm = (Button) view.findViewById(R.id.btn_dialog_call_comfrim);
            mBtnComfirm.setOnClickListener(v -> {
                UtilMethod.dissmissDialog(getmActivity(), mAlertDialog);
                getmActivity().finish();
            });
            AlertDialog.Builder builder = new AlertDialog.Builder(getmActivity());
            mAlertDialog = builder.setView(view).create();

        }
        UtilMethod.showDialog(getmActivity(), mAlertDialog);

    }
}

package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.callback.MyTextWatcher;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 结算
 * Created by Administrator on 2016/8/5.
 */
public class JieSuanActivity extends BaseActivity {
    @BindView(R.id.topbar_jiesuan)
    NormalTopBar topbarJiesuan;
    @BindView(R.id.et_jiesuan_number)
    TextInputEditText etJiesuanNumber;
    @BindView(R.id.tv_jiesuan_money)
    TextView tvJiesuanMoney;
    @BindView(R.id.tv_jiesuan_all)
    TextView tvJiesuanAll;
    @BindView(R.id.btn_jiesuan_comfirm)
    Button btnJiesuanComfirm;

    private float money =10.01f;//可结算的余额
    private float sum;//输入的结算金额

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_jiesuan);
    }


    @Override
    public void init() {
        super.init();
        topbarJiesuan.setTitle("结算");
        getIntentData();
        setTextChange();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            money= intent.getFloatExtra("money", 0);
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
     * @param s
     */
    private void judge(String s) {

        if (TextUtils.isEmpty(s)) {

            return;
        }
        if (s.matches(Constants.str)) {
            try {
                float money2 = Float.valueOf(s);
                if (money2 <= money) {
                    btnJiesuanComfirm.setSelected(true);
                    sum = money2;
                } else {
                    etJiesuanNumber.setError("输入的金额超过可计算的余额");
                    btnJiesuanComfirm.setSelected(false);
                }

            } catch (Exception e) {

            }
        } else {
            etJiesuanNumber.setError("输入有误");
            btnJiesuanComfirm.setSelected(false);
        }


    }

    @OnClick({R.id.tv_jiesuan_all, R.id.btn_jiesuan_comfirm,R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_jiesuan_all:
                break;
            case R.id.btn_jiesuan_comfirm:
                comfirm();
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }

    private void comfirm() {
        if (btnJiesuanComfirm.isSelected()) {
            //判断余额是否足够
            if (money <= sum) {
                NetworkUtils.isNetWork(getmActivity(), topbarJiesuan, new NetworkUtils.SetDataInterface() {
                    @Override
                    public void getDataApi() {
                        subscription = NetWork.getNetService(subscription)
                                .getJiesuan(UtilMethod.getAccout(getmActivity()),UtilMethod.getToken(getmActivity()),sum+"")
                                .compose(NetWork.handleResult(new BaseCallModel<String>()))
                                .subscribe(new MyObserver<String>() {
                                    @Override
                                    protected void onSuccess(String data, String resultMsg) {
                                            showToast(resultMsg);
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        showToast(resultMsg);
                                    }

                                    @Override
                                    public void onExit() {
                                        ExitToLogin();
                                    }
                                });
                    }
                });
            } else {
                showToast("余额不足");
            }
        }

    }


}

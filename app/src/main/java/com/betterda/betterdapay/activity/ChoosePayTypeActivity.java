package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.ShapeLoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * 选择支付通道
 * Created by Administrator on 2016/8/4.
 */
public class ChoosePayTypeActivity extends BaseActivity {

    @BindView(R.id.topbar_chose)
    NormalTopBar topbarChose;
    @BindView(R.id.tv_item_balance_money)
    TextView tvItemBalanceMoney;
    @BindView(R.id.tv_item_balance_type)
    TextView tvItemBalanceType;
    @BindView(R.id.relative_choose_zhifubao)
    RelativeLayout relativeChooseZhifubao;
    @BindView(R.id.relative_choose_weixin)
    RelativeLayout relativeChooseWeixin;
    @BindView(R.id.relative_choose_yinlian)
    RelativeLayout relativeChooseYinlian;

    private String money;//支付金额
    private String account;
    private String token;
    private String type = "0";
    private String orderType = "0";
    private String channel;
    private String TAG = ChoosePayTypeActivity.class.getSimpleName();

    private ShapeLoadingDialog dialog;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_choosepaytype);

    }

    @Override
    public void init() {
        super.init();
        getInitData();
        topbarChose.setTitle("选择支付通道");
        tvItemBalanceMoney.setText(money + "元");
        dialog = UtilMethod.createDialog(getmActivity(), "正在提交...");
    }

    /**
     * 获取数据
     */
    private void getInitData() {
        Intent intent = getIntent();
        if (intent != null) {
            money = intent.getStringExtra("money");
        }
        getToken();

    }

    /**
     * 获取token
     */
    private void getToken() {
        account = CacheUtils.getString(getmActivity(), Constants.Cache.ACCOUNT, "");
        token = CacheUtils.getString(getmActivity(), account + Constants.Cache.TOKEN, "");
    }

    @OnClick({R.id.relative_choose_zhifubao, R.id.relative_choose_weixin, R.id.relative_choose_yinlian,R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_choose_zhifubao:
                channel = "支付宝支付";
                getData(0);
                break;
            case R.id.relative_choose_weixin:
                channel = "微信支付";
                getData(1);
                break;
            case R.id.relative_choose_yinlian:
                channel = "银联支付";
                getData(2);
                break;
            case R.id.bar_back:
                 back();
                break;
        }
    }

    public void getData(final int payType) {
        UtilMethod.showDialog(getmActivity(), dialog);
        NetworkUtils.isNetWork(getmActivity(), topbarChose, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                subscription = NetWork.getNetService(subscription)
                        .getOrder(account, token, type, money, orderType, channel)
                        .compose(NetWork.handleResult(new BaseCallModel<String>()))
                        .subscribe(new MyObserver<String>() {
                            @Override
                            protected void onSuccess(String data, String resultMsg) {

                                if (BuildConfig.LOG_DEBUG) {
                                    Log.i(TAG, data + "," + resultMsg);
                                }
                                if (0 == payType) {
                                    zhifubaoPay();
                                } else if (1 == payType) {
                                    weixinPay();
                                } else {
                                    yinlianPay();
                                }

                            }

                            @Override
                            public void onFail(String resultMsg) {
                                showToast(resultMsg);
                                UtilMethod.dissmissDialog(getmActivity(), dialog);
                            }

                            @Override
                            public void onExit() {
                                UtilMethod.dissmissDialog(getmActivity(), dialog);
                                ExitToLogin();
                            }
                        });
            }
        });


    }

    /**
     * 微信支付
     */
    private void weixinPay() {
        toActivity();
    }

    private void zhifubaoPay() {
        toActivity();
    }
    private void yinlianPay() {
        toActivity();
    }

    private void toActivity() {
        UtilMethod.dissmissDialog(getmActivity(), dialog);
        UtilMethod.startIntent(getmActivity(), CollectionActivity.class, "money", money);
        finish();
    }
}

package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.Wallet;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分润钱包
 * Created by Administrator on 2017/3/30.
 */

public class FWalletActivity extends BaseActivity {
    @BindView(R.id.topbar_fwallet)
    NormalTopBar mTopbarFwallet;
    @BindView(R.id.tv_fwallet_money)
    TextView mTvFwalletMoney;
    @BindView(R.id.tv_fwallet_yesterday)
    TextView mTvFwalletYesterday;
    @BindView(R.id.tv_fwallet_addup)
    TextView mTvFwalletAddup;
    @BindView(R.id.tv_ywallet_money_yesterday)
    TextView tvYwalletMoneyYesterday;
    @BindView(R.id.tv_ywallet_money_acount)
    TextView tvYwalletMoneyAcount;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_fwallet);
    }

    @Override
    public void init() {
        super.init();
        mTopbarFwallet.setTitle("钱包");
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }


    @OnClick({R.id.linear_fwallet_withdraw, R.id.linear_fwallet_details, R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_fwallet_withdraw:
                UtilMethod.startIntent(getmActivity(), TiXianDetailActivity.class);
                break;
            case R.id.linear_fwallet_details:
                UtilMethod.startIntent(getmActivity(), WalletDetailActivity.class);
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }

    private void getData() {
        NetworkUtils.isNetWork(this, null, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                mRxManager.add(
                        NetWork.getNetService()
                                .getWallet("18206051563")
                                .compose(NetWork.handleResult(new BaseCallModel<Wallet>()))
                                .subscribe(new MyObserver<Wallet>() {
                                    @Override
                                    protected void onSuccess(Wallet data, String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("钱包:" + data);
                                        }
                                        if (data != null) {

                                            parserData(data);
                                        }
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("钱包fail:" + resultMsg);
                                        }
                                    }

                                    @Override
                                    public void onExit() {

                                    }
                                })
                );
            }
        });
    }

    private void parserData(Wallet data) {
        if (mTvFwalletMoney != null) {
            mTvFwalletMoney.setText(data.getBalance());
        }
        if (mTvFwalletYesterday != null) {
            mTvFwalletYesterday.setText(data.getTodayIncome());
        }
        if (mTvFwalletAddup != null) {
            mTvFwalletAddup.setText(data.getTotalIncome());
        }
        if (tvYwalletMoneyYesterday != null) {
            tvYwalletMoneyYesterday.setText(data.getTodayAmount());
        }
        if (tvYwalletMoneyAcount != null) {
            tvYwalletMoneyAcount.setText(data.getTotalAmount());
        }
    }
}

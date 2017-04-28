package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 余额钱包
 * Created by Administrator on 2017/3/30.
 */

public class YWalletActivity extends BaseActivity {
    @BindView(R.id.topbar_fwallet)
    NormalTopBar topbarFwallet;
    @BindView(R.id.tv_ywallet_money)
    TextView tvYwalletMoney;
    @BindView(R.id.tv_ywallet_money_yesterday)
    TextView tvYwalletMoneyYesterday;
    @BindView(R.id.tv_ywallet_money_acount)
    TextView tvYwalletMoneyAcount;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_ywallet);
    }

    @Override
    public void init() {
        super.init();
        topbarFwallet.setTitle("余额钱包");
    }

    @OnClick({R.id.linear_ywallet_tixian, R.id.linear_ywallet_mingxi,R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_ywallet_tixian:
                UtilMethod.startIntent(getmActivity(),TiXianActivity.class);
                break;
            case R.id.linear_ywallet_mingxi:
                UtilMethod.startIntent(getmActivity(),WalletDetailActivity.class);
                break;
            case R.id.bar_back:
                 back();
                break;
        }
    }
}

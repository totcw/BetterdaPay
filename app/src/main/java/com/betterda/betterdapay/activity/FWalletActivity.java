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

    @OnClick({R.id.linear_fwallet_withdraw, R.id.linear_fwallet_details, R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_fwallet_withdraw:
                if (UtilMethod.showNotice(getmActivity())) {
                    UtilMethod.startIntent(getmActivity(), TiXianActivity.class);
                }
                break;
            case R.id.linear_fwallet_details:
                UtilMethod.startIntent(getmActivity(), WalletDetailActivity.class);
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }
}

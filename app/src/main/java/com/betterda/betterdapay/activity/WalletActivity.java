package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.view.View;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的钱包
 * Created by Administrator on 2017/3/30.
 */

public class WalletActivity extends BaseActivity {
    @BindView(R.id.topbar_my_wallet)
    NormalTopBar topbarMyWallet;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_wallet);
    }



    @OnClick({R.id.linear_wallet, R.id.linear_fwallet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_wallet://余额钱包
                UtilMethod.startIntent(getmActivity(),YWalletActivity.class);
                break;
            case R.id.linear_fwallet://分润钱包
                UtilMethod.startIntent(getmActivity(),FWalletActivity.class);
                break;
        }
    }
}

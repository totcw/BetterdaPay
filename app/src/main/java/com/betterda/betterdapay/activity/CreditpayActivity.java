package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 信用卡还贷
 * Created by Administrator on 2017/4/10.
 */

public class CreditpayActivity extends BaseActivity {
    @BindView(R.id.topbar_creditrepay)
    NormalTopBar mTopbarCreditrepay;
    @BindView(R.id.linear_creditrepay)
    LinearLayout mLinearCreditrepay;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_creditrepay);
    }



    @OnClick({R.id.bar_back, R.id.linear_creditrepay})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bar_back:
                back();
                break;
            case R.id.linear_creditrepay:
                UtilMethod.startIntent(getmActivity(),AddCreditrepayActivity.class);
                break;
        }
    }
}

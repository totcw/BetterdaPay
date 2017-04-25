package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.view.GradientTextView;
import com.betterda.betterdapay.view.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 便捷贷款
 * Created by Administrator on 2017/3/29.
 */

public class BianJieDaiKuanActivity extends BaseActivity {

    @BindView(R.id.topbar_loan)
    NormalTopBar mTopbarLoan;
    @BindView(R.id.gdtv_loadn_limit)
    GradientTextView mGdtvLoadnLimit;
    @BindView(R.id.tv_loadn_money)
    TextView mTvLoadnMoney;
    @BindView(R.id.tv_loadn_deanline)
    TextView mTvLoadnDeanline;
    @BindView(R.id.tv_loadn_accountamount)
    TextView mTvLoadnAccountamount;
    @BindView(R.id.tv_loadn_repayamount)
    TextView mTvLoadnRepayamount;
    @BindView(R.id.tv_loan_repayamount)
    TextView mTvLoanRepayamount;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_bianjiedaikuan);
    }

    @Override
    public void init() {
        super.init();
        mTopbarLoan.setTitle("便捷贷款");
    }

    @OnClick({R.id.btn_loan_loan, R.id.gdtv_loan_repayment,R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_loan_loan:
                break;
            case R.id.gdtv_loan_repayment:
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }
}

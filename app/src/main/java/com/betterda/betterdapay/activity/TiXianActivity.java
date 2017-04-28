package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.view.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现
 * Created by Administrator on 2017/3/30.
 */

public class TiXianActivity extends BaseActivity {
    @BindView(R.id.topbar_tixian)
    NormalTopBar mTopbarTixian;
    @BindView(R.id.et_tixian)
    EditText mEtTixian;
    @BindView(R.id.tv_tixian_balance)
    TextView mTvTixianBalance;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_tixian);
    }

    @Override
    public void init() {
        super.init();
        mTopbarTixian.setTitle("提现");
    }

    @OnClick({R.id.tv_tixian_all, R.id.btn_tixian_commit,R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tixian_all:
                break;
            case R.id.btn_tixian_commit:
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }
}

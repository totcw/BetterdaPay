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
 * 个人信息
 * Created by Administrator on 2017/3/30.
 */

public class InformationActivity extends BaseActivity {

    @BindView(R.id.topbar_information)
    NormalTopBar topbarInformation;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_information);
    }

    @Override
    public void init() {
        super.init();
        topbarInformation.setTitle("个人信息");
    }

    @OnClick({R.id.linear_realname, R.id.linear_photo, R.id.linear_reset,R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_realname:
                UtilMethod.startIntent(getmActivity(),RealNameAuthActivity.class);
                break;
            case R.id.linear_photo:
                break;
            case R.id.linear_reset:
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }
}

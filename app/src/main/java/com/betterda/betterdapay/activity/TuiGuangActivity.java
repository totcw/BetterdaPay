package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 推广,升级界面
 * Created by Administrator on 2016/8/8.
 */
public class TuiGuangActivity extends BaseActivity {

    @BindView(R.id.topbar_tuiguang)
    NormalTopBar topbarTuiguang;
    @BindView(R.id.relative_tuiguang_my)
    RelativeLayout relativeTuiguangMy;
    @BindView(R.id.tv_tuiguang_rate)
    TextView mTvTuiguangRate;
    private String payUp;
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_tuiguang);
    }

    @Override
    public void init() {
        super.init();
        topbarTuiguang.setTitle("升级");
        Intent intent = getIntent();
        if (intent != null) {
            String rate = intent.getStringExtra("rate");
            mTvTuiguangRate.setText("升级到"+rate);
            payUp = intent.getStringExtra("payUp");
        }

    }

    @OnClick({R.id.relative_tuiguang_my,R.id.tv_tuiguang_rate, R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tuiguang_rate:
                // TODO 生成付款号
                break;
            case R.id.relative_tuiguang_my:
                UtilMethod.startIntent(getmActivity(), TuiguangActivity2.class);
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }


}

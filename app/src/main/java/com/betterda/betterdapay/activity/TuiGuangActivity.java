package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

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

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_tuiguang);
    }

    @Override
    public void init() {
        super.init();
        topbarTuiguang.setTitle("升级");
    }

    @OnClick({R.id.relative_tuiguang_my,R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_tuiguang_my:
                UtilMethod.startIntent(getmActivity(),TuiguangActivity2.class);
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }
}

package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/17.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.topbar_setting)
    NormalTopBar topbarSetting;
    @BindView(R.id.relative_my_yijian)
    RelativeLayout relativeMyYijian;
    @BindView(R.id.relative_setting_update)
    RelativeLayout relativeSettingUpdate;
    @BindView(R.id.btn_setting_exit)
    Button btnSettingExit;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void init() {
        topbarSetting.setTitle("设置");
    }

    @Override
    public void initListener() {
        topbarSetting.setOnBackListener(this);
    }

    @OnClick({R.id.relative_my_yijian, R.id.relative_setting_update, R.id.btn_setting_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_my_yijian:
                UtilMethod.startIntent(getmActivity(),FeedbackActivity.class);
                break;
            case R.id.relative_setting_update:
                break;
            case R.id.btn_setting_exit:
                UtilMethod.startIntent(getmActivity(),LoginActivity.class);
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }
}

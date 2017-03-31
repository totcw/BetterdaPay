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
 * 升级界面
 * Created by Administrator on 2017/3/31.
 */

public class UpActivity extends BaseActivity {

    @BindView(R.id.topbar_up)
    NormalTopBar topbarUp;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_up);
    }



    @OnClick({R.id.tv_up_dianzhang, R.id.tv_up_jingli, R.id.tv_up_zongjingli, R.id.tv_up_boss})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_up_dianzhang:
                UtilMethod.startIntent(getmActivity(),TuiGuangActivity.class);
                break;
            case R.id.tv_up_jingli:
                break;
            case R.id.tv_up_zongjingli:
                break;
            case R.id.tv_up_boss:
                break;
        }
    }
}

package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
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
    @BindView(R.id.tv_information_auth)
    TextView mTvAuth;
    private String mIsAuth;

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

    @Override
    protected void onStart() {
        super.onStart();
        mIsAuth = CacheUtils.getString(this, UtilMethod.getAccout(this) + Constants.Cache.AUTH, "0");
        if ("0".equals(mIsAuth)) {
            mTvAuth.setText("未认证");
        } else if ("1".equals(mIsAuth)) {
            mTvAuth.setText("已认证");
        }else if ("2".equals(mIsAuth)) {
            mTvAuth.setText("审核中");
        }
    }


    @OnClick({R.id.linear_realname, R.id.linear_reset, R.id.linear_bankcard, R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_realname:
                if ("0".equals(mIsAuth)) {
                    UtilMethod.startIntent(getmActivity(), RealNameAuthActivity.class);
                } else if ("1".equals(mIsAuth)) {
                    showToast("已经实名认证");
                }else if ("2".equals(mIsAuth)) {
                    showToast("正在审核中");
                }
                break;

            case R.id.linear_bankcard:
                UtilMethod.startIntent(getmActivity(), MyYinHangKa.class);
                break;
            case R.id.linear_reset:
                UtilMethod.startIntent(getmActivity(), ForgetPwdActivity.class);
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }
}

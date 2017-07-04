package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.util.ImageTools;
import com.betterda.betterdapay.view.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/7/4
 * 功能说明： 二维码收款
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class QrCodeActicity extends BaseActivity {

    @BindView(R.id.iv_qrcode)
    ImageView mIvQrcode;
    @BindView(R.id.topbar_qrcode)
    NormalTopBar mNormalTopBar;
    @BindView(R.id.iv_qrcode_icon)
    ImageView mIvQrcodeIcon;
    @BindView(R.id.tv_qrcode_type)
    TextView mTvQrcodeType;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_qrcode);
    }

    @Override
    public void init() {
        super.init();
        mNormalTopBar.setTitle("二维码收款");
        getIntentData();

        mIvQrcode.setImageBitmap(ImageTools.generateQRCode("", this));
    }

    private void getIntentData() {
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        mTvQrcodeType.setText(type+"收款");
        if ("微信".equals(type)) {
            mIvQrcodeIcon.setImageResource(R.mipmap.wxshoukuan);
        } else {
            mIvQrcodeIcon.setImageResource(R.mipmap.zfbshoukuan);
        }
    }


    @OnClick(R.id.bar_back)
    public void onViewClicked() {
        back();
    }


}

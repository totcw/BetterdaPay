package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.util.ImageTools;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 扫码支付界面
 * Created by Administrator on 2016/8/5.
 */
public class CollectionActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.topbar_collection)
    NormalTopBar topbarCollection;
    @BindView(R.id.tv_collection_money)
    TextView tvCollectionMoney;
    @BindView(R.id.iv_ewm)
    ImageView ivEwm;

    private String money;
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_collection);
    }

    @Override
    public void initListener() {
        super.initListener();
        topbarCollection.setOnBackListener(this);
    }

    @Override
    public void init() {
        super.init();
        setTopbar();
        getIntentData();
        setEweiMa();
        tvCollectionMoney.setText("￥" + money + "元");
    }

    private void setTopbar() {
        topbarCollection.setTitle("收款");
    }

    private void setEweiMa() {
        Bitmap bitmap = ImageTools.generateQRCode("1", getmActivity());
        ivEwm.setImageBitmap(bitmap);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            money = intent.getStringExtra("money");
        }
    }


    @Override
    public void onClick(View v) {
        back();
    }
}

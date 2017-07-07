package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.ImageTools;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;

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
    @BindView(R.id.loadpager_qrcode)
    LoadingPager mLoadpagerQrcode;
    private String payType;//类型  1为支付宝 2为微信
    private String body;//商品内容
    private int money;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_qrcode);
    }

    @Override
    public void init() {
        super.init();
        mNormalTopBar.setTitle("扫码收款");
        getIntentData();

        getData(money);
        mLoadpagerQrcode.setonErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(money);
            }
        });
    }

    private void getData(int payUp) {
        mLoadpagerQrcode.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), mLoadpagerQrcode, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                mRxManager.add(
                        NetWork.getNetService().getOrderForScan("15805939207", "1", body, payType)
                                .compose(NetWork.handleResult(new BaseCallModel<String>()))
                                .subscribe(new MyObserver<String>() {
                                    @Override
                                    protected void onSuccess(String data, String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("扫码:"+data);
                                        }
                                        mIvQrcode.setImageBitmap(ImageTools.generateQRCode(data, getmActivity()));
                                        mLoadpagerQrcode.hide();
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println(resultMsg);
                                        }
                                        mLoadpagerQrcode.setErrorVisable();
                                    }

                                    @Override
                                    public void onExit() {

                                    }
                                })
                );
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");

        money = intent.getIntExtra("money", 0);

        mTvQrcodeType.setText(type + "收款");
        if ("微信".equals(type)) {
            payType = "2";
            body = type + "收款";
            mIvQrcodeIcon.setImageResource(R.mipmap.wxshoukuan);
        } else {
            payType = "1";
            body = type + "收款";
            mIvQrcodeIcon.setImageResource(R.mipmap.zfbshoukuan);
        }
    }


    @OnClick(R.id.bar_back)
    public void onViewClicked() {
        back();
    }


}

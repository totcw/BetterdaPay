package com.betterda.betterdapay.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.EWeiMa;
import com.betterda.betterdapay.util.ImageTools;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的二维码
 * Created by Administrator on 2016/8/8.
 */
public class MyEweiMaActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.topbar_myeweima)
    NormalTopBar topbarMyeweima;
    @BindView(R.id.iv_myerweima)
    ImageView ivMyerweima;
    @BindView(R.id.loadpager_myeweima)
    LoadingPager loadingPager;


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_myeweima);
    }

    @Override
    public void initListener() {
        super.initListener();
        topbarMyeweima.setOnBackListener(this);
    }

    @Override
    public void init() {
        super.init();

        setTopBar();
        getData();
    }

    private void getData() {
        loadingPager.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), loadingPager, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                 NetWork.getNetService()
                        .getCode(UtilMethod.getAccout(getmActivity()), UtilMethod.getToken(getmActivity()))
                        .compose(NetWork.handleResult(new BaseCallModel<EWeiMa>()))
                        .subscribe(new MyObserver<EWeiMa>() {
                            @Override
                            protected void onSuccess(EWeiMa data, String resultMsg) {
                                if (data != null) {
                                    parser(data);
                                }
                                if (loadingPager != null) {
                                    loadingPager.hide();
                                }
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                if (loadingPager != null) {
                                    loadingPager.setErrorVisable();
                                }
                            }

                            @Override
                            public void onExit() {
                                if (loadingPager != null) {
                                    loadingPager.hide();
                                }
                            }
                        });
            }
        });

    }

    private void parser(EWeiMa data) {
        String account = data.getAccount();
        String url = data.getUrl();
        log(url);
        Bitmap bitmap = ImageTools.generateQRCode(url, getmActivity());
        if (ivMyerweima != null) {
            ivMyerweima.setImageBitmap(bitmap);
        }
    }

    private void setTopBar() {
        topbarMyeweima.setTitle("我的二维码");
        topbarMyeweima.setShareVisibility(true);
        topbarMyeweima.setOnShareListener(this);
    }

    /**
     * 分享
     */
    private void share() {
        View view = View.inflate(getmActivity(), R.layout.pp_share, null);
        RelativeLayout relative_wxfriend = (RelativeLayout) view.findViewById(R.id.relative_share_wxfriend);
        RelativeLayout relative_pyquan = (RelativeLayout) view.findViewById(R.id.relative_share_pyquan);
        RelativeLayout relative_qq = (RelativeLayout) view.findViewById(R.id.relative_qqfriend);
        RelativeLayout relative_qqzone = (RelativeLayout) view.findViewById(R.id.relative_share_qqzone);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_share_cancel);

        relative_wxfriend.setOnClickListener(this);
        relative_pyquan.setOnClickListener(this);
        relative_qq.setOnClickListener(this);
        relative_qqzone.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        setUpPopupWindow(view, null);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_relative_share:
                share();
                break;
            case R.id.relative_share_wxfriend:
                showToast("微信好友");
                closePopupWindow();
                break;
            case R.id.relative_share_pyquan:
                break;
            case R.id.relative_qqfriend:
                break;
            case R.id.relative_share_qqzone:
                break;
            case R.id.tv_share_cancel:
                closePopupWindow();
                break;
            case R.id.bar_back:
                back();
                break;

        }
    }
}

package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.MyShangHu;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的商户
 * Created by Administrator on 2016/8/17.
 */
public class ShangHuActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.topbar_shanghu)
    NormalTopBar topbarShanghu;
    @BindView(R.id.tv_shanghu_my)
    TextView tvShanghuMy;
    @BindView(R.id.relative_shanghu_my)
    RelativeLayout relativeShanghuMy;
    @BindView(R.id.tv_shanghu_one)
    TextView tvShanghuOne;
    @BindView(R.id.tv_shanghu_two)
    TextView tvShanghuTwo;
    @BindView(R.id.loadpager_shanghu)
    LoadingPager loadingPager;

    @Override
    public void initView() {
        setContentView(R.layout.activity_shanghu);
    }

    @Override
    public void init() {
        topbarShanghu.setTitle("我的商户");
        getData();
    }

    private void getData() {
        loadingPager.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), loadingPager, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                subscription = NetWork.getNetService(subscription)
                        .getSubnum(UtilMethod.getAccout(getmActivity()), UtilMethod.getToken(getmActivity()))
                        .compose(NetWork.handleResult(new BaseCallModel<MyShangHu>()))
                        .subscribe(new MyObserver<MyShangHu>() {
                            @Override
                            protected void onSuccess(MyShangHu data, String resultMsg) {
                                if (data != null) {
                                    parser(data);
                                }
                                loadingPager.hide();
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                loadingPager.setErrorVisable();
                            }

                            @Override
                            public void onExit() {
                                ExitToLogin();
                            }
                        });
            }
        });
    }

    private void parser(MyShangHu data) {
        tvShanghuMy.setText("人数:"+data.getSubNumOne()+"人");
        tvShanghuOne.setText("人数:"+data.getSubNumTwo()+"人");
        tvShanghuTwo.setText("人数:"+data.getSubNumTree()+"人");
    }

    @Override
    public void initListener() {
        topbarShanghu.setOnBackListener(this);
    }



    @OnClick(R.id.relative_shanghu_my)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_shanghu_my:
                UtilMethod.startIntent(getmActivity(),MyTuiGuangAcitivty.class);
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }
}

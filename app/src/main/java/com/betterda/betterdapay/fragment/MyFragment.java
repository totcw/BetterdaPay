package com.betterda.betterdapay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.AddBankCardActivity;
import com.betterda.betterdapay.activity.AlreadAuthAcitity;
import com.betterda.betterdapay.activity.MyEweiMaActivity;
import com.betterda.betterdapay.activity.MyRatingActivity;
import com.betterda.betterdapay.activity.MyYinHangKa;
import com.betterda.betterdapay.activity.RealNameAuthActivity;
import com.betterda.betterdapay.activity.SettingActivity;
import com.betterda.betterdapay.activity.ShangHuActivity;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.data.RateData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.Information;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 * Created by Administrator on 2016/7/28.
 */
public class MyFragment extends BaseFragment {

    @BindView(R.id.topbar_my)
    NormalTopBar topbarMy;
    @BindView(R.id.sv_touxiang)
    ImageView svTouxiang;
    @BindView(R.id.tv_my_number)
    TextView tvMyNumber;
    @BindView(R.id.tv_my_rate)
    TextView tvMyRate;
    @BindView(R.id.relative_my_rating)
    RelativeLayout relativeMyRating;
    @BindView(R.id.relative_my_yinhangka)
    RelativeLayout relativeMyYinhangka;
    @BindView(R.id.relative_my_erweima)
    RelativeLayout relativeMyErweima;
    @BindView(R.id.relative_my_information)
    RelativeLayout relativeMyInformation;
    @BindView(R.id.relative_my_shanghu)
    RelativeLayout relativeMyShanghu;


    private String rate="员工";

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        return view;
    }


    @Override
    public void initData() {
        super.initData();
        topbarMy.setTitle("我的");
        topbarMy.setBackVisibility(false);
        topbarMy.setBusVisibility(true);


    }


    @Override
    public void onStart() {
        super.onStart();
        if (tvMyNumber != null) {
            tvMyNumber.setText(CacheUtils.getString(getmActivity(), Constants.Cache.ACCOUNT, ""));
        }
        getData();
    }

    private void getData() {
        NetworkUtils.isNetWork(getmActivity(), topbarMy, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
               subscription = NetWork.getNetService(subscription)
                       .getInformation(UtilMethod.getAccout(getmActivity()),UtilMethod.getToken(getmActivity()))
                       .compose(NetWork.handleResult(new BaseCallModel<Information>()))
                       .subscribe(new MyObserver<Information>() {
                           @Override
                           protected void onSuccess(Information data, String resultMsg) {
                               if (data != null) {
                                   rate = data.getRate();
                                   if (tvMyRate != null) {
                                       tvMyRate.setText("当前等级:" + data.getRate());

                                   }
                                   if (svTouxiang != null) {
                                       svTouxiang.setImageResource(RateData.getRate(data.getRate()));
                                   }
                               }
                           }

                           @Override
                           public void onFail(String resultMsg) {

                           }

                           @Override
                           public void onExit() {
                                ExitToLogin();
                           }
                       });
            }
        });
    }

    @OnClick({R.id.relative_my_rating, R.id.relative_my_yinhangka, R.id.relative_my_erweima, R.id.relative_my_information, R.id.relative_my_shanghu , R.id.bar_relative_bus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_my_rating://我的汇率
                UtilMethod.startIntent(getmActivity(), MyRatingActivity.class,"rate",rate);
                break;
            case R.id.relative_my_yinhangka://我的银行卡
                UtilMethod.startIntent(getmActivity(), MyYinHangKa.class);
                break;
            case R.id.relative_my_erweima://我的二维码
                UtilMethod.startIntent(getmActivity(), MyEweiMaActivity.class);
                break;
            case R.id.relative_my_information://认证
                auth();
                break;
            case R.id.relative_my_shanghu://我的商户
                UtilMethod.startIntent(getmActivity(), ShangHuActivity.class);
                break;
            case R.id.bar_relative_bus://设置
                UtilMethod.startIntent(getmActivity(), SettingActivity.class);
                break;
        }
    }

    private void auth() {
        boolean auth = CacheUtils.getBoolean(getmActivity(), UtilMethod.getAccout(getmActivity())+Constants.Cache.AUTH, false);
        if (auth) {
             UtilMethod.startIntent(getmActivity(), AlreadAuthAcitity.class);
        } else {
            UtilMethod.startIntent(getmActivity(), RealNameAuthActivity.class);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (RateData.rateMap != null) {
            RateData.rateMap.clear();
            RateData.rateMap = null;
        }
    }
}

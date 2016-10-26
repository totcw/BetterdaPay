package com.betterda.betterdapay.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.data.RateData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.Information;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;

import butterknife.BindView;

/**
 * 已经认证界面
 * Created by Administrator on 2016/9/2.
 */
public class AlreadAuthAcitity extends BaseActivity {
    @BindView(R.id.topbar_alreadauth)
    NormalTopBar topbarAlreadauth;
    @BindView(R.id.iv_alreadauth)
    ImageView ivAlreadauth;
    @BindView(R.id.tv_alreadauth_name)
    TextView tvAlreadauthName;
    @BindView(R.id.tv_alreadauth_identity)
    TextView tvAlreadauthIdentity;
    @BindView(R.id.loadpager_alreaauth)
    LoadingPager loadpagerAlreaauth;

    @Override
    public void initView() {
        setContentView(R.layout.activity_alreadauth);

    }

    @Override
    public void init() {
        super.init();
        getData();
    }

    private void getData() {
        loadpagerAlreaauth.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), loadpagerAlreaauth, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                subscription = NetWork.getNetService(subscription)
                        .getInformation(UtilMethod.getAccout(getmActivity()), UtilMethod.getToken(getmActivity()))
                        .compose(NetWork.handleResult(new BaseCallModel<Information>()))
                        .subscribe(new MyObserver<Information>() {
                            @Override
                            protected void onSuccess(Information data, String resultMsg) {
                                if (data != null) {
                                    if (tvAlreadauthIdentity != null) {
                                        tvAlreadauthIdentity.setText("身份证:"+data.getIdentityCar());
                                    }
                                    if (tvAlreadauthName != null) {
                                        tvAlreadauthName.setText(data.getRealName());
                                    }

                                    if (ivAlreadauth != null) {
                                        ivAlreadauth.setImageResource(RateData.getRate(data.getRate()));
                                    }

                                }
                                loadpagerAlreaauth.hide();
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                loadpagerAlreaauth.setErrorVisable();
                            }

                            @Override
                            public void onExit() {
                                ExitToLogin();
                            }
                        });
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (RateData.rateMap != null) {
            RateData.rateMap.clear();
            RateData.rateMap = null;
        }
    }
}

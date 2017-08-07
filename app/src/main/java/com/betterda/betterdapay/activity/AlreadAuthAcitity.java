package com.betterda.betterdapay.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
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
 * 已经认证界面,资料获取
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
               mRxManager.add(
                       NetWork.getNetService()
                               .getInformation(UtilMethod.getAccout(getmActivity()))
                               .compose(NetWork.handleResult(new BaseCallModel<Information>()))
                               .subscribe(new MyObserver<Information>() {
                                   @Override
                                   protected void onSuccess(Information data, String resultMsg) {
                                       if (BuildConfig.LOG_DEBUG) {
                                           System.out.println("个人信息:" + data);
                                       }

                                       if (data != null) {
                                           if (tvAlreadauthIdentity != null) {
                                               tvAlreadauthIdentity.setText("身份证:" + data.getIdentityCar());
                                           }
                                           if (tvAlreadauthName != null) {
                                               tvAlreadauthName.setText(data.getRealname());
                                           }

                                   /* if (ivAlreadauth != null) {
                                        ivAlreadauth.setImageResource(RateData.getRank(data.getRank()));
                                    }*/

                                       }
                                       loadpagerAlreaauth.hide();
                                   }

                                   @Override
                                   public void onFail(String resultMsg) {
                                       if (BuildConfig.LOG_DEBUG) {
                                           System.out.println("个人信息fail:" + resultMsg);
                                       }
                                       if (loadpagerAlreaauth != null) {

                                           loadpagerAlreaauth.setErrorVisable();
                                       }
                                   }

                                   @Override
                                   public void onExit(String resultMsg) {
                                       ExitToLogin(resultMsg);
                                   }
                               })
               );
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

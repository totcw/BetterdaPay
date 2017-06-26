package com.betterda.betterdapay.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.AlreadAuthAcitity;
import com.betterda.betterdapay.activity.FWalletActivity;
import com.betterda.betterdapay.activity.InformationActivity;
import com.betterda.betterdapay.activity.MemberActivity;
import com.betterda.betterdapay.activity.MyRatingActivity;
import com.betterda.betterdapay.activity.RealNameAuthActivity;
import com.betterda.betterdapay.activity.SettingActivity;
import com.betterda.betterdapay.activity.TransactionRecordActivity;
import com.betterda.betterdapay.data.RateData;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;

import butterknife.BindView;
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
        String account = CacheUtils.getString(getmActivity(), Constants.Cache.ACCOUNT, "");
        rate = CacheUtils.getString(getmActivity(), account + Constants.Cache.RANK, "员工");
        if (tvMyNumber != null) {
            tvMyNumber.setText(account);
        }
        if (svTouxiang != null) {
            svTouxiang.setImageResource(RateData.getRate(rate));
        }

        if (tvMyRate != null) {
            tvMyRate.setText("当前等级:" + rate);

        }

    }



    @OnClick({R.id.relative_my_rating, R.id.relative_my_yinhangka, R.id.relative_my_erweima, R.id.relative_my_information, R.id.relative_my_shanghu , R.id.bar_relative_bus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_my_rating://我的钱包
                UtilMethod.startIntent(getmActivity(), FWalletActivity.class);
                break;
            case R.id.relative_my_yinhangka://个人信息
                UtilMethod.startIntent(getmActivity(), InformationActivity.class);
                break;
            case R.id.relative_my_erweima://交易记录
                UtilMethod.startIntent(getmActivity(), TransactionRecordActivity.class);
                break;
            case R.id.relative_my_information://会员管理
                UtilMethod.startIntent(getmActivity(), MemberActivity.class);
                break;
            case R.id.relative_my_shanghu://我的扣率
                UtilMethod.startIntent(getmActivity(), MyRatingActivity.class,"rate",rate);
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

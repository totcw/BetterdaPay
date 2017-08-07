package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.data.RateData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.UpdateCondition;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 升级条件界面
 * Created by Administrator on 2017/3/31.
 */

public class UpActivity extends BaseActivity {

    @BindView(R.id.topbar_up)
    NormalTopBar topbarUp;
    @BindView(R.id.iv_activity_up_logo)
    ImageView mIvActivityUpLogo;
    @BindView(R.id.tv_activity_up_rate)
    TextView mTvActivityUpRate;
    @BindView(R.id.tv_activity_up_condition)
    TextView mTvActivityUpCondition;
    @BindView(R.id.tv_activity_up_staff_rating)
    TextView mTvActivityUpStaffRating;
    @BindView(R.id.tv_activity_up_staff_condition)
    TextView mTvActivityUpStaffCondition;
    @BindView(R.id.tv_activity_up_staff_award)
    TextView mTvActivityUpStaffAward;
    @BindView(R.id.linear_activity_up_staff)
    LinearLayout mLinearActivityUpStaff;
    @BindView(R.id.tv_activity_up_manager_rating)
    TextView mTvActivityUpManagerRating;
    @BindView(R.id.tv_activity_up_manager_condition)
    TextView mTvActivityUpManagerCondition;
    @BindView(R.id.tv_activity_up_manager_award)
    TextView mTvActivityUpManagerAward;
    @BindView(R.id.linear_activity_up_manager)
    LinearLayout mLinearActivityUpManager;
    @BindView(R.id.tv_activity_up_topmanger_rating)
    TextView mTvActivityUpTopmangerRating;
    @BindView(R.id.tv_activity_up_topmanager_condition)
    TextView mTvActivityUpTopmanagerCondition;
    @BindView(R.id.tv_activity_up_topmanager_award)
    TextView mTvActivityUpTopmanagerAward;
    @BindView(R.id.linear_activity_up_topmanager)
    LinearLayout mLinearActivityUpTopmanager;
    @BindView(R.id.tv_activity_up_boss_rating)
    TextView mTvActivityUpBossRating;
    @BindView(R.id.tv_activity_up_boss_condition)
    TextView mTvActivityUpBossCondition;
    @BindView(R.id.tv_activity_up_boss_award)
    TextView mTvActivityUpBossAward;
    @BindView(R.id.linear_activity_up_boss)
    LinearLayout mLinearActivityUpBoss;
    @BindView(R.id.loadpager_up)
    LoadingPager mLoadpagerUp;

    private String payUpForShopower, payUpForMnager, payUpForTopManager, payUpForBoss;//升级到各个条件的金额
    private String payUpForShopowerId, payUpForMnagerId, payUpForTopManagerId, payUpForBossId;//各个等级对应的id

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_up);
    }

    @Override
    public void init() {
        super.init();
        topbarUp.setTitle("升级");

        mLoadpagerUp.setonErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    @OnClick({R.id.tv_up_dianzhang, R.id.tv_up_jingli, R.id.tv_up_zongjingli, R.id.tv_up_boss, R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_up_dianzhang:
                startToTuiguang(RateData.UP_DIANZHANG, payUpForShopower,payUpForShopowerId);
                break;
            case R.id.tv_up_jingli:
                startToTuiguang(RateData.UP_JINGLI, payUpForMnager,payUpForMnagerId);
                break;
            case R.id.tv_up_zongjingli:
                startToTuiguang(RateData.UP_ZONGJINGLI, payUpForTopManager,payUpForTopManagerId);
                break;
            case R.id.tv_up_boss:
                startToTuiguang(RateData.UP_BOSS, payUpForBoss,payUpForBossId);
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }

    private void startToTuiguang(String upDianzhang, String payUp,String rateId) {
        System.out.println("rankid:"+rateId);
        Intent intent = new Intent(getmActivity(), TuiGuangActivity.class);
        intent.putExtra("rateId",rateId);
        intent.putExtra("rate", upDianzhang);
        intent.putExtra("payUp", payUp);
        startActivity(intent);
    }

    private void getData() {
        mLoadpagerUp.setLoadVisable();
        NetworkUtils.isNetWork(this, mLoadpagerUp, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {

                mRxManager.add(
                        NetWork.getNetService()
                                .getUpdateCondition(UtilMethod.getAccout(getmActivity()))
                                .compose(NetWork.handleResult(new BaseCallModel<List<UpdateCondition>>()))
                                .subscribe(new MyObserver<List<UpdateCondition>>() {
                                    @Override
                                    protected void onSuccess(List<UpdateCondition> data, String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("data"+data);
                                        }
                                        if (data != null) {
                                            parserData(data);
                                        }
                                        if (mLoadpagerUp != null) {
                                            mLoadpagerUp.hide();
                                        }
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        if (mLoadpagerUp != null) {
                                            mLoadpagerUp.setErrorVisable();
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

    private void parserData(List<UpdateCondition> data) {
        int size = data.size();
        hide();
        for (int i = 0; i < size; i++) {
            UpdateCondition updateCondition = data.get(i);
            if (updateCondition != null) {
                String rate = updateCondition.getRankName();
                if (!TextUtils.isEmpty(rate)) {
                    //设置当前等级的信息
                    String currentRate = CacheUtils.getString(getmActivity(), UtilMethod.getAccout(getmActivity()) + Constants.Cache.RANK, "1");
                    if (rate.equals(currentRate)) {
                        if (mIvActivityUpLogo != null) {
                            mIvActivityUpLogo.setImageResource(RateData.getRate(updateCondition.getRankName()));
                        }
                        if (mTvActivityUpRate != null) {
                            mTvActivityUpRate.setText("当前等级: "+updateCondition.getRankName());
                        }

                    } else {
                        //因为是按等级顺序返回的,所以只显示当前等级后面的数据
                            switch (rate) {
                                case RateData.UP_DIANZHANG:
                                    payUpForShopower = updateCondition.getPayUp();
                                    payUpForShopowerId = updateCondition.getRankId();
                                    setConditionInformation(updateCondition, mLinearActivityUpStaff, mTvActivityUpStaffRating
                                            , mTvActivityUpStaffAward, mTvActivityUpStaffCondition,RateData.UP_DIANZHANG);
                                    break;
                                case RateData.UP_JINGLI:

                                    payUpForMnager = updateCondition.getPayUp();
                                    payUpForMnagerId = updateCondition.getRankId();
                                    setConditionInformation(updateCondition, mLinearActivityUpManager, mTvActivityUpManagerRating
                                            , mTvActivityUpManagerAward, mTvActivityUpManagerCondition,RateData.UP_JINGLI);
                                    break;
                                case RateData.UP_ZONGJINGLI:
                                    payUpForTopManager = updateCondition.getPayUp();
                                    payUpForTopManagerId = updateCondition.getRankId();
                                    setConditionInformation(updateCondition, mLinearActivityUpTopmanager, mTvActivityUpTopmangerRating
                                            , mTvActivityUpTopmanagerAward, mTvActivityUpTopmanagerCondition,RateData.UP_ZONGJINGLI);
                                    break;
                                case RateData.UP_BOSS:
                                    payUpForBoss = updateCondition.getPayUp();
                                    payUpForBossId = updateCondition.getRankId();
                                    setConditionInformation(updateCondition, mLinearActivityUpBoss, mTvActivityUpBossRating
                                            , mTvActivityUpBossAward, mTvActivityUpBossCondition,RateData.UP_BOSS);
                                    break;
                            }


                    }
                }

            }

        }
    }

    private void hide() {
        mLinearActivityUpStaff.setVisibility(View.GONE);
        mLinearActivityUpManager.setVisibility(View.GONE);
        mLinearActivityUpTopmanager.setVisibility(View.GONE);
        mLinearActivityUpBoss.setVisibility(View.GONE);
    }

    /**
     * 设置各个等级的信息
     *
     * @param updateCondition
     */
    private void setConditionInformation(UpdateCondition updateCondition, LinearLayout linearLayout,
                                         TextView rating, TextView award, TextView condition,String rank) {
        String rank2 = CacheUtils.getString(getmActivity(), UtilMethod.getAccout(getmActivity()) + Constants.Cache.RANK, "1");
        int rate1 = RateData.getRateForRank(rank2);
        int rate2 = RateData.getRateForRank(rank);

        if (rate2 > rate1) {
            if (linearLayout != null) {
                linearLayout.setVisibility(View.VISIBLE);
            }

            if (award != null) {
                award.setText(updateCondition.getAward());
            }
            if (condition != null) {
                condition.setText(updateCondition.getRemarks());
            }
        } else {
            if (linearLayout != null) {
                linearLayout.setVisibility(View.GONE);
            }
        }



    }


}

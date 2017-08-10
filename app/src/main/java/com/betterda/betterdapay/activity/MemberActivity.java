package com.betterda.betterdapay.activity;

import android.view.View;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.MemberCounts;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员管理
 * Created by Administrator on 2017/3/30.
 */

public class MemberActivity extends BaseActivity {

    @BindView(R.id.topbar_member)
    NormalTopBar topbarMember;
    @BindView(R.id.gttv_member_count)
    TextView gttvMemberCount;
    @BindView(R.id.tv_member_my)
    TextView tvMemberMy;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_member_two)
    TextView tvMemberTwo;
    @BindView(R.id.tv_member_three)
    TextView tvMemberThree;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_member);
    }

    @Override
    public void init() {
        super.init();
        topbarMember.setTitle("会员管理");

    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    @OnClick({R.id.linear_member, R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_member:
                UtilMethod.startIntent(getmActivity(),MyTuiGuangAcitivty.class);
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }

    private void getData() {
        NetworkUtils.isNetWork(this, null, () -> mRxManager.add(
                NetWork.getNetService().getMemberCounts(UtilMethod.getAccout(MemberActivity.this), Constants.APPCODE)
                        .compose(NetWork.handleResult(new BaseCallModel<>()))
                        .subscribe(new MyObserver<MemberCounts>() {
                            @Override
                            protected void onSuccess(MemberCounts data, String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println(data);
                                }
                                if (data != null) {
                                    if (gttvMemberCount != null) {
                                        gttvMemberCount.setText(data.getCount());
                                    }
                                    if (tvMemberMy != null) {
                                        tvMemberMy.setText(data.getMySpreadCount());
                                    }
                                    if (tvMemberTwo != null) {
                                        tvMemberTwo.setText(data.getTwoSpreadCount());
                                    }
                                    if (tvMemberThree != null) {
                                        tvMemberThree.setText(data.getThredSpreadCount());
                                    }
                                }
                            }

                            @Override
                            public void onFail(String resultMsg) {

                            }

                            @Override
                            public void onExit(String resultMsg) {
                                ExitToLogin(resultMsg);
                            }
                        })
        ));
    }
}

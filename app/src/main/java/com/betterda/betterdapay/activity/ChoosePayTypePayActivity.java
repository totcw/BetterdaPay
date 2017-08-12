package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.javabean.RatingCalculateEntity;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.ShapeLoadingDialog;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择支付通道(付款)
 * Created by Administrator on 2016/8/4.
 */
public class ChoosePayTypePayActivity extends BaseActivity {


    @BindView(R.id.topbar_chose)
    NormalTopBar topbarChose;
    @BindView(R.id.rv_choosepaytype)
    RecyclerView mRecyclerView;
    @BindView(R.id.loadpager_choosepaytype)
    LoadingPager mLoadingPager;

    private CommonAdapter<RatingCalculateEntity> mAdapter;
    private List<RatingCalculateEntity> mList;
    private String payUp;//支付金额
    private String rankName;//升级到指定等级
    private String rank;//升级到的等级id
    private int mMoney;//单位为分



    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_choosepaytypepay);

    }

    @Override
    public void init() {
        super.init();
        getInitData();
        topbarChose.setTitle("选择支付通道");
        initRecycleView();

    }

    private void initRecycleView() {
        mList = new ArrayList<>();
        mList.add(new RatingCalculateEntity("银联手机控件支付"));
        //  mList.add(new RatingCalculateEntity("银联无跳转支付"));

        mAdapter = new CommonAdapter<RatingCalculateEntity>(this, R.layout.rv_item_choosepaytypepay, mList) {
            @Override
            public void convert(final ViewHolder holder, RatingCalculateEntity ratingCalculateEntity) {
                if (holder != null && ratingCalculateEntity != null) {


                    holder.setImageResource(R.id.iv_my_information, R.mipmap.yinlian);
                    holder.setText(R.id.tv_item_choosepaytype_type, ratingCalculateEntity.getPayWay());

                    holder.setOnClickListener(R.id.relative_choose_zhifubao, v -> {
                        if (holder.getAdapterPosition() == 0) {
                            Intent intent = new Intent(getmActivity(), MyYinHangKa.class);
                            intent.putExtra("rank", rank);
                            intent.putExtra("rankName", rankName);
                            intent.putExtra("isPay", true);
                            intent.putExtra("money", mMoney);
                            intent.putExtra("isClick", true);
                            startActivity(intent);
                            finish();
                        } else {
                            //TODO 无跳转
                        }
                    });
                }
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * 获取数据
     */
    private void getInitData() {
        Intent intent = getIntent();
        if (intent != null) {
            payUp = intent.getStringExtra("money");
            rankName = intent.getStringExtra("rankName");
            rank = intent.getStringExtra("rank");
        }
        if (TextUtils.isEmpty(payUp)) {
            payUp = "0";
        }
        try {
            mMoney = (int) (Float.valueOf(payUp) * 100);
        } catch (Exception e) {

        }
    }


    @OnClick({R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                back();
                break;
        }
    }




}

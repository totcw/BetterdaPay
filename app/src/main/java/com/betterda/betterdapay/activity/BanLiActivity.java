package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.view.NormalTopBar;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 办理信用卡
 * Created by Administrator on 2017/3/29.
 */

public class BanLiActivity extends BaseActivity {
    @BindView(R.id.topbar_banli)
    NormalTopBar mTopbarBanli;
    @BindView(R.id.rv_banli)
    RecyclerView mRvBanli;

    private List<String> list;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_banli);

    }

    @Override
    public void init() {
        super.init();
        initRv();
    }

    private void initRv() {
        list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        mRvBanli.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mRvBanli.addItemDecoration(new DividerItemDecoration(getmActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRvBanli.setAdapter(new CommonAdapter<String>(getmActivity(),R.layout.item_rv_tyepcard,list) {
            @Override
            public void convert(ViewHolder holder, String o) {

            }


        });
    }

    @OnClick({R.id.linear_banli_xingye, R.id.linear_banli_xbk, R.id.linear_banli_adls, R.id.linear_banli_lbs, R.id.linear_banli_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_banli_xingye:
                break;
            case R.id.linear_banli_xbk:
                break;
            case R.id.linear_banli_adls:
                break;
            case R.id.linear_banli_lbs:
                break;
            case R.id.linear_banli_more:
                break;
        }
    }
}
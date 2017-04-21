package com.betterda.betterdapay.livingpay;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.BaseActivity;
import com.betterda.betterdapay.javabean.LivingRecord;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/21
 * @功能说明： 生活缴费记录
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class LivingRecordActivity extends BaseActivity {

    @BindView(R.id.topbar_livingrecord)
    NormalTopBar mTopbarLivingrecord;
    @BindView(R.id.rv_layout)
    RecyclerView mRvLayout;
    @BindView(R.id.loadpager_layout)
    LoadingPager mLoadpagerLayout;

    private List<LivingRecord> mLivingRecordList;
    private CommonAdapter<LivingRecord> mAdapter;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_livingrecord);
    }

    @Override
    public void init() {
        super.init();
        mTopbarLivingrecord.setTitle("缴费记录");
        initRv();
    }

    private void initRv() {
        mLivingRecordList = new ArrayList<>();
        mLivingRecordList.add(null);
        mLivingRecordList.add(null);
        mLivingRecordList.add(null);
        mRvLayout.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mAdapter= new CommonAdapter<LivingRecord>(getmActivity(), R.layout.item_rv_livingrecord, mLivingRecordList) {

            @Override
            public void convert(ViewHolder holder, LivingRecord livingRecord) {
                if (livingRecord != null) {
                    holder.setText(R.id.tv_item_livingrecord_name, livingRecord.getName());
                    holder.setText(R.id.tv_item_livingrecord_number, livingRecord.getNumber());
                    holder.setText(R.id.tv_item_livingrecord_money, livingRecord.getMoney());
                    holder.setText(R.id.tv_item_livingrecord_type, livingRecord.getType());
                    holder.setText(R.id.tv_item_livingrecord_time, livingRecord.getTime());
                    holder.setText(R.id.tv_item_livingrecord_status, livingRecord.getStatus());
                    int id = R.mipmap.pay_shui;
                    switch (livingRecord.getType()) {
                        case "水费":
                            id = R.mipmap.pay_shui;
                            break;
                        case "电费":
                            id = R.mipmap.pay_dian;
                            break;
                        case "燃气费":
                            id = R.mipmap.pay_ranqi;
                            break;
                    }
                    holder.setImageResource(R.id.iv_item_livingrecord, id);
                }
            }
        };
        mRvLayout.setAdapter(mAdapter);
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

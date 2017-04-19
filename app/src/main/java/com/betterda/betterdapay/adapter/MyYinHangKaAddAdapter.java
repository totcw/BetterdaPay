package com.betterda.betterdapay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.AddBankCardActivity;
import com.betterda.betterdapay.util.UtilMethod;

/**
 * 我的银行卡 添加的item 适配器
 * Created by Administrator on 2017/4/13.
 */

public class MyYinHangKaAddAdapter extends DelegateAdapter.Adapter<MyYinHangKaAddAdapter.MainViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private int mCount = 0;

    public MyYinHangKaAddAdapter(Context mContext, LayoutHelper mLayoutHelper, int mCount) {
        this.mLayoutHelper = mLayoutHelper;
        this.mCount = mCount;
        this.mContext = mContext;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_yinhangkaadd, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        if (holder != null) {
            holder.mLinearAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UtilMethod.startIntent(mContext, AddBankCardActivity.class);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mCount;
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearAdd;

        public MainViewHolder(View itemView) {
            super(itemView);
            mLinearAdd = (LinearLayout) itemView.findViewById(R.id.linear_item_yinghangkaadd);
        }
    }
}

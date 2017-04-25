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
import com.betterda.betterdapay.util.UtilMethod;

/**
 * 办理信用卡特色主题的适配器
 * Created by Administrator on 2017/4/13.
 */

public class BanliCreditThemeAdapter extends DelegateAdapter.Adapter<BanliCreditThemeAdapter.MainViewHolder> implements View.OnClickListener {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private int mCount = 0;

    public BanliCreditThemeAdapter(Context mContext, LayoutHelper mLayoutHelper, int mCount) {
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
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_banlicredit_theme, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        if (holder != null) {
            holder.mLinearBanliXingye.setOnClickListener(this);
            holder.mLinearBanliXbk.setOnClickListener(this);
            holder.mLinearBanliAdls.setOnClickListener(this);
            holder.mLinearBanliLbs.setOnClickListener(this);
            holder.mLinearBanliMore.setOnClickListener(this);
        }

    }

    @Override
    public int getItemCount() {
        return mCount;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_banli_xingye:
                UtilMethod.Toast(mContext,"兴业");
                break;
            case R.id.linear_banli_xbk:
                UtilMethod.Toast(mContext,"星巴克");
                break;
            case R.id.linear_banli_adls:
                UtilMethod.Toast(mContext,"安德鲁森");
                break;
            case R.id.linear_banli_lbs:
                UtilMethod.Toast(mContext,"罗宾森");
                break;
            case R.id.linear_banli_more:
                UtilMethod.Toast(mContext,"更多");
                break;
        }
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLinearBanliXingye;
        private LinearLayout mLinearBanliXbk;
        private LinearLayout mLinearBanliAdls;
        private LinearLayout mLinearBanliLbs;
        private LinearLayout mLinearBanliMore;

        public MainViewHolder(View itemView) {
            super(itemView);
            mLinearBanliXingye = (LinearLayout) itemView.findViewById(R.id.linear_banli_xingye);
            mLinearBanliXbk = (LinearLayout) itemView.findViewById(R.id.linear_banli_xbk);
            mLinearBanliAdls = (LinearLayout) itemView.findViewById(R.id.linear_banli_adls);
            mLinearBanliLbs = (LinearLayout) itemView.findViewById(R.id.linear_banli_lbs);
            mLinearBanliMore = (LinearLayout) itemView.findViewById(R.id.linear_banli_more);
        }
    }
}

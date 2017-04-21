package com.betterda.betterdapay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.betterda.betterdapay.R;

/**
 * 生活缴费普通itme的适配器
 * Created by Administrator on 2017/4/13.
 */

public class LivingPayItemAdapter extends DelegateAdapter.Adapter<LivingPayItemAdapter.MainViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private int mCount = 0;
    private String type;

    public LivingPayItemAdapter(Context mContext, LayoutHelper mLayoutHelper, int mCount,String type) {
        this.mLayoutHelper = mLayoutHelper;
        this.mCount = mCount;
        this.mContext = mContext;
        this.type = type;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_livingpaynormal, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        if ("水费".equals(type)) {
            holder.mTextView.setText("水费");
            holder.mImageView.setImageResource(R.mipmap.small_shui);
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else if ("电费".equals(type)) {
            holder.mTextView.setText(type);
            holder.mImageView.setImageResource(R.mipmap.small_dian);
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else if ("燃气费".equals(type)) {
            holder.mTextView.setText(type);
            holder.mImageView.setImageResource(R.mipmap.small_ranqi);
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mCount;
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        ImageView mImageView;
        TextView mTextView;

        public MainViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.linear_item_livingpaynormal);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_item_livingpaynormal);
            mTextView = (TextView) itemView.findViewById(R.id.tv_item_livingpaynormal);
        }
    }
}

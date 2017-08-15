package com.betterda.betterdapay.view;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.betterda.betterdapay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/8/4
 * 功能说明： 快报滚动
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class ScrollWidget extends LinearLayout {
    //数据源
    private List<String> mData;
    //存放创建的TextSwitcher
    private List<TextSwitcher> mSwitcherList;
    //存放每个TextSwitcher自动滚动的值
    private List<Integer> mIntegerList;

    //可见的数目
    private int visableItem = 1;

    private Handler mHandler = new Handler();
    private Runnable mRunnable;


    public ScrollWidget(Context context) {
        this(context, null);
    }

    public ScrollWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
        init(context);
    }

    private void init(Context context) {
        mSwitcherList = new ArrayList<>();
        mIntegerList = new ArrayList<>();
        mData = new ArrayList<>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);

        for (int i = 0; i < visableItem; i++) {

            TextSwitcher textSwitcher = new TextSwitcher(context);
            textSwitcher.setOutAnimation(context, R.anim.push_up_out);
            textSwitcher.setInAnimation(context, R.anim.push_up_in);
            textSwitcher.setLayoutParams(params);
            textSwitcher.setFactory(() -> {
                FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(-1, -1);
                final TextView tv = new TextView(context);
                tv.setLayoutParams(param);
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                return tv;
            });

            mSwitcherList.add(textSwitcher);
            mIntegerList.add(i);
            addView(textSwitcher);
        }
    }


    public List<String> getData() {
        return mData;
    }

    /**
     * 设置数据源,并设置初始值
     * @param data
     */
    public void setData(List<String> data) {
        mData = data;
        if (mSwitcherList != null && data != null && data.size() > 0) {
            for (int i = 0; i < mSwitcherList.size(); i++) {
                mSwitcherList.get(i).setText(mData.get(i % data.size()));
            }
        }

    }

    /**
     * 更新数据源
     * @param data
     */
    public void upDate(List<String> data){
        if (mData != null&&data!=null) {
            mData.clear();
            mData.addAll(data);
        }
    }


    /**
     * 开启滚动
     */
    public void startRunning() {
        if (mRunnable == null) {
            mRunnable = () -> {
                for (int i = 0; i < visableItem; i++) {
                    if (mIntegerList != null && mData != null &&mData.size()>0&& mSwitcherList != null) {
                        Integer integer = mIntegerList.get(i);
                        mIntegerList.set(i, ++integer);
                        TextSwitcher textSwitcher = mSwitcherList.get(i);
                        if (textSwitcher != null) {
                            textSwitcher.setText(mData.get(mIntegerList.get(i) % mData.size()));
                        }
                    }
                }
                if (mHandler != null) {
                    mHandler.postDelayed(mRunnable, 1000);
                }
            };
        }

        if (mHandler != null) {
            mHandler.postDelayed(mRunnable, 1000);
        }
    }

    /**
     * 停止滚动
     */
    public void stopRunning() {
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

}

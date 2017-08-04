package com.betterda.betterdapay.view;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
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
 * 功能说明：
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

    private List<Integer> mIntegerList;

    //可见的数目
    private int visableItem =3;

    private Handler mHandler = new Handler();
    private Runnable mRunnable;


    public ScrollWidget(Context context) {
        this(context, null);
    }

    public ScrollWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        init(context);
    }

    private void init(Context context) {
        mSwitcherList = new ArrayList<>();
        mIntegerList = new ArrayList<>();
        for (int i = 0; i < visableItem; i++) {
            TextSwitcher textSwitcher = new TextSwitcher(context);
            textSwitcher.setOutAnimation(context, R.anim.push_up_out);
            textSwitcher.setInAnimation(context, R.anim.push_up_in                                                                                                                                                                                                                                                                                                                                                           );
            textSwitcher.setFactory(() -> {
                final TextView tv = new TextView(context);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv.setPadding(10, 10, 10, 10);
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

    public void setData(List<String> data) {
        mData = data;
        if (mSwitcherList != null&&data!=null&&data.size()>0) {
            for (int i = 0; i <mSwitcherList.size(); i++) {
                mSwitcherList.get(i).setText(mData.get(i%data.size()));
            }
        }

    }


    public void startRunning() {
        mRunnable = () -> {
            for (int i = 0; i < visableItem; i++) {
                if (mIntegerList != null&&mData!=null&&mSwitcherList!=null) {
                    Integer integer = mIntegerList.get(i);
                    mIntegerList.set(i, ++integer);
                    mSwitcherList.get(i).setText(mData.get(mIntegerList.get(i)%mData.size()));
                }
            }
            if (mHandler != null) {
                mHandler.postDelayed(mRunnable, 1000);
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }

}

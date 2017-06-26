package com.betterda.betterdapay.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.activity.MessageActivity;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.RxManager;
import com.betterda.betterdapay.util.UtilMethod;

import cn.jpush.android.api.JPushInterface;

/**
 * 极光推送的 接收消息的广播
 * Created by Administrator on 2016/12/1.
 */

public class JpushReceiver extends BroadcastReceiver {
    private String TAG = JpushReceiver.class.getSimpleName();
    protected RxManager mRxManager = new RxManager();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (BuildConfig.LOG_DEBUG) {

            System.out.println("接受到");
        }
        if (intent != null) {
            if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
                mRxManager.post(JpushReceiver.class.getSimpleName(), true);
                String account = CacheUtils.getString(context, Constants.Cache.ACCOUNT, "");
                CacheUtils.putBoolean(context, account + Constants.Cache.MESSAGE, true);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.d(TAG, "用户点击打开了通知");
                // 在这里可以自己写代码去定义用户点击后的行为
                if (context != null) {
                    Intent i = new Intent(context, MessageActivity.class);  //自定义打开的界面
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }

            }
        }


    }
}


package com.betterda.betterdapay.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.betterda.betterdapay.util.UtilMethod;

/**
 * 极光推送的 接收消息的广播
 * Created by Administrator on 2016/12/1.
 */

public class JpushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("接受到");
        UtilMethod.Toast(context,"接受到");

    }
}

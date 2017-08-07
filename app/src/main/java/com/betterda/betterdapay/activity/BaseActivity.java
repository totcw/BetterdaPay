package com.betterda.betterdapay.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;


import com.betterda.betterdapay.R;
import com.betterda.betterdapay.util.PermissionUtil;
import com.betterda.betterdapay.util.RxManager;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.mylibrary.Utils.Toast;
import com.example.lyf.yflibrary.Permission;
import com.example.lyf.yflibrary.PermissionResult;

import java.util.List;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * 基类
 * Created by Administrator on 2016/7/27.
 */
public class BaseActivity extends FragmentActivity {
    private String[] REQUEST_PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION};
    private PopupWindow popupWindow;
    protected RxManager mRxManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager();
        initView();
        ButterKnife.bind(this);
        initListener();
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        //统一检查权限
        if (Build.VERSION.SDK_INT > 22) {
            Permission.checkPermisson(this, REQUEST_PERMISSIONS, new PermissionResult() {
                @Override
                public void success() {

                }

                @Override
                public void fail() {
                    finish();
                }
            });
        }

    }

    /**
     * 处理业务逻辑
     */
    public void init() {

    }

    /**
     * 设置监听
     */
    public void initListener() {

    }

    /**
     * 初始化view
     */
    public void initView() {

    }

    /**
     * 显示土司
     *
     * @param message
     */
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }




    /**
     * 创建对话框
     */

    public void showDialog(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(content)
                .setPositiveButton("OK", (dialog, which) -> comfirmDialog())
                .setNegativeButton("Cancel", (dialog, which) -> exitDialog()).show();

    }

    /**
     * 对话框取消回调的方法
     */
    public void exitDialog() {

    }

    /**
     * 对话框确定回调的方法
     */
    public void comfirmDialog() {

    }


    /**
     * 关闭activity的方法
     */
    public void back() {
        finish();
    }

    /**
     * 初始化并显示PopupWindow
     *
     * @param view     要显示的界面
     * @param showView 显示在哪个控件下面,为null 默认显示在屏幕下边
     */
    public void setUpPopupWindow(View view, View showView) {

        // 如果activity不在运行 就返回
        if (this.isFinishing()) {
            return;
        }

        popupWindow = new PopupWindow(view, -1, -2);


        // 设置点到外面可以取消,下面这2句要一起
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.update();
        //设置为true 会拦截事件,pop外部的控件无法获取到事件

        popupWindow.setFocusable(true);

        //设置可以触摸
        popupWindow.setTouchable(true);

        UtilMethod.backgroundAlpha(0.7f, this);

        if (popupWindow != null) {
            if (!popupWindow.isShowing()) {
                if (null == showView) {
                    popupWindow.setAnimationStyle(R.style.popwin_anim_style);
                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                } else {
                    popupWindow.showAsDropDown(showView);
                }

            }
        }
        popupWindow.setOnDismissListener(() -> {

            dismiss();
            popupWindow = null;
        });


    }

    /**
     * popupwindow消失回调方法
     */
    public void dismiss() {
        UtilMethod.backgroundAlpha(1.0f, getmActivity());
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }


    /**
     * 关闭popupwindow
     */
    public void closePopupWindow() {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            popupWindow = null;
        }
    }

    public Activity getmActivity() {
        return this;
    }

    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄漏
        // ((MyApplication)getApplication()).removeAcitivty(this);
        closePopupWindow();
        mRxManager.clear();
    }



    /**
     * 强制跳转到登录界面
     */
    public void ExitToLogin(String resultMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getmActivity());

        builder.setTitle("温馨提示")
                .setMessage(resultMsg)
                .setNegativeButton("确定", (dialog, which) -> {
                    dialog.dismiss();
                    Intent intent = new Intent();
                    intent.setClass(this, LoginActivity.class);
                    //添加清除任务栈中所有activity的log,如果要启动的activity不在任务栈中了,还需要添加FLAG_ACTIVITY_NEW_TASK,才会关闭任务栈中的其他activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();

    }

    public void log(String msg) {
        Log.i("BaseActivity", msg);
    }



    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}

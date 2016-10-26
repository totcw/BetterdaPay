package com.betterda.betterdapay.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.betterda.betterdapay.util.UtilMethod;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * 基类
 * Created by Administrator on 2016/7/27.
 */
public class BaseActivity extends FragmentActivity {

    private PopupWindow popupWindow;
    protected Subscription subscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   ((MyApplication)getApplication()).addActivity(this);
        //base
        initView();
        ButterKnife.bind(this);
        initListener();
        init();
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
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示提示信息
     *
     * @param view    任何一个view 即可
     * @param message
     */
    public void showSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    public void showSnackBar(View view, String message, String title) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(title, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doSnack();
                    }
                }).show();
    }

    /**
     * 做snackbar对应的事情
     */
    public void doSnack() {

    }


    /**
     * 创建对话框
     */

    public void showDialog(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(content)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        comfirmDialog();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitDialog();
                    }
                }).show();

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
                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                } else {
                    popupWindow.showAsDropDown(showView);
                }

            }
        }
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {

                dismiss();
                popupWindow = null;
            }
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

        //取消订阅
        unsubscribe();
    }

    public void unsubscribe() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
            subscription = null;
        }
    }

    /**
     * 强制跳转到登录界面
     */
    public void ExitToLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getmActivity());

        builder.setTitle("温馨提示")
                .setMessage("您的帐号已在别处登录,请重新登录")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        UtilMethod.startIntent(getmActivity(),LoginActivity.class);
                    }
                })
                .setCancelable(false)
                .show();

    }

    public void log(String msg) {
        Log.i("BaseActivity", msg);
    }
}

package com.betterda.betterdapay.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.LoginActivity;
import com.betterda.betterdapay.dialog.CallDialog;
import com.betterda.betterdapay.util.UtilMethod;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by Administrator on 2016/7/27.
 */
public abstract class BaseFragment extends Fragment {

    private Activity mActivity;
    private PopupWindow popupWindow;

    private String TAG = BaseFragment.class.getSimpleName();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity; //在这里获取到acitiviy,防止内存不够,activity被销毁,调用getactivity方法时返回null
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = initView(inflater);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initListenr();
        initData();
    }

    /**
     * 设置监听
     */
    public void initListenr() {

    }


    /**
     * 子类必须实现此方法, 返回一个View对象, 作为当前Fragment的布局来展示.
     *
     * @return
     */
    public abstract View initView(LayoutInflater inflater);

    /**
     * 如果子类需要初始化自己的数据, 把此方法给覆盖.
     */
    public void initData() {

    }

    /**
     * 显示土司
     *
     * @param message
     */
    public void showToast(String message) {
        Toast.makeText(getmActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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


    /**
     * 创建对话框
     */

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getmActivity());
        builder.setTitle(getDialogTitle())
                .setMessage(getDialogMessage())
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
                }).setCancelable(false).show();

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
     * 返回对话框的内容
     *
     * @return
     */
    public String getDialogMessage() {
        return "";
    }

    /**
     * 返回对话框的标题
     *
     * @return
     */
    public String getDialogTitle() {
        return "";
    }


    /**
     * 初始化并显示PopupWindow
     *
     * @param view     要显示的界面
     * @param showView 显示在哪个控件下面
     */
    public void setUpPopupWindow(View view, View showView) {

        // 如果activity不在运行 就返回
        if (getActivity().isFinishing()) {
            return;
        }


        if (null == showView) {
            popupWindow = new PopupWindow(view, -1, -2);
        } else {
            popupWindow = new PopupWindow(view, -1, -1);
        }

        // 设置点到外面可以取消,下面这2句要一起
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.update();
        //设置为true 会拦截事件,pop外部的控件无法获取到事件
        if (null == showView) {
            popupWindow.setFocusable(true);
        } else {

            popupWindow.setFocusable(false);
        }
        if (null == showView) {
            UtilMethod.backgroundAlpha(0.7f, getmActivity());
        }

        //设置可以触摸
        popupWindow.setTouchable(true);

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

    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }


    /**
     * 关闭popupwindow
     */
    public void closePopupWindow() {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    public Activity getmActivity() {
        return mActivity;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closePopupWindow();

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
        Log.i("BaseFragment",msg);
    }
}

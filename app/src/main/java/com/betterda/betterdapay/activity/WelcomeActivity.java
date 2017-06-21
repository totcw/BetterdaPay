package com.betterda.betterdapay.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.dialog.DeleteDialog;
import com.betterda.betterdapay.dialog.PermissionDialog;
import com.betterda.betterdapay.http.DownloadAPI;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.interfac.DownloadProgressListener;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.FileUtils;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.PermissionUtil;
import com.betterda.betterdapay.util.RxManager;
import com.betterda.betterdapay.util.UtilMethod;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/6/14
 * 功能说明： 欢迎界面
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class WelcomeActivity extends FragmentActivity {
    private String[] REQUEST_PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION};
    private HashMap<String, String> map;//管理权限的map
    private static final int REQUEST_PERMISSION_CODE_TAKE_PIC = 9; //权限的请求码
    private static final int REQUEST_PERMISSION_SEETING = 8; //去设置界面的请求码
    private PermissionDialog permissionDialog;//权限请求对话框
    protected RxManager mRxManager;
    private ProgressBar mMProgressBar;//下载的进度对话框
    private AlertDialog mAlertDialog;//下载对话框
    private TextView mMTvProgress;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg != null) {
                int progress = msg.arg1;
                if (mMProgressBar != null) {
                    //更新进度条
                    mMTvProgress.setText(progress + "%");
                    mMProgressBar.setProgress(progress);
                }

            }

            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager();
        startTopermission();

    }

    /**
     * 获取权限
     */
    private void startTopermission() {
        if (Build.VERSION.SDK_INT < 23) {
            //6.0一下直接去主页
            starToHome();

        } else {
            //6.0以上请求权限
            checkPermiss();
        }
    }

    //跳转到首页
    public void starToHome() {
        //TODO 访问网络获取更新信息
        getData();

    }

    /**
     * 显示更新对话框
     *
     * @param url
     */
    public void showUpdateDialog(String url) {
        View view = LayoutInflater.from(WelcomeActivity.this).inflate(R.layout.dialog_update, null);
        TextView mTvCancel = (TextView) view.findViewById(R.id.tv_update_cancel);
        TextView mTvComfirm = (TextView) view.findViewById(R.id.tv_update_comfirm);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.setView(view).setCancelable(false)
                .show();
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilMethod.dissmissDialog(WelcomeActivity.this, alertDialog);
                startToLogin();

            }
        });

        mTvComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilMethod.dissmissDialog(WelcomeActivity.this, alertDialog);
                checkApk();

            }
        });
    }

    /**
     * 检查本地是有apk
     */
    public void checkApk() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(WelcomeActivity.this, "sd卡不可用", 0).show();
            return;
        }
        final File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/" + "update.apk");

        if (null != externalFilesDir && externalFilesDir.exists()) {
            PackageInfo apkInfo = UtilMethod.getApkInfo(WelcomeActivity.this, externalFilesDir.getAbsolutePath());
            //如果apk没问题就不会为null
            if (null != apkInfo) {
                //检测是否是有一个apk的版本高于当前app,那么就直接安装
                if (UtilMethod.compare(apkInfo, WelcomeActivity.this)) {
                    UtilMethod.startInstall(WelcomeActivity.this, Uri.fromFile(externalFilesDir));
                    finish();
                    return;
                }
            }
        }

        createDownDialog();
        download(externalFilesDir);


    }

    public void download(final File externalFilesDir) {
        DownloadAPI.DownloadProgressInterceptor interceptor = new DownloadAPI.DownloadProgressInterceptor(new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                int progress = (int) ((bytesRead * 100) / contentLength);
                Message message = Message.obtain();
                message.arg1 = progress;
                mHandler.sendMessage(message);
                if (progress == 100) {//进度到100 就启动安装
                    if (null != externalFilesDir && externalFilesDir.exists()) {
                        //检测是否是通过一个应用,且下载的apk的版本高于当前app
                        //检测是否是有一个apk的版本高于当前app,那么就直接安装
                        PackageInfo apkInfo = UtilMethod.getApkInfo(WelcomeActivity.this, externalFilesDir.getAbsolutePath());
                        if (null != apkInfo) {

                            if (UtilMethod.compare(apkInfo, WelcomeActivity.this)) {
                                UtilMethod.startInstall(WelcomeActivity.this, Uri.fromFile(externalFilesDir));
                                finish();
                             }
                        }
                    }

                }
            }
        });
        //如果没有就重新下载
        mRxManager.add(DownloadAPI.downloadService("http://139.199.178.209:8080/", interceptor).download("app-debug.apk")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        UtilMethod.dissmissDialog(WelcomeActivity.this, mAlertDialog);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //如果下载出错,就删除当前apk
                        if (null != externalFilesDir && externalFilesDir.exists()) {
                            externalFilesDir.delete();
                        }
                        UtilMethod.dissmissDialog(WelcomeActivity.this, mAlertDialog);

                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                        try {
                            FileUtils.writeFile(responseBody.byteStream(), externalFilesDir);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e.getMessage(), e);
                        }


                    }
                }));
    }

    /**
     * 创建下载对话框
     */
    private void createDownDialog() {
        if (mAlertDialog == null) {
            View downView = LayoutInflater.from(WelcomeActivity.this).inflate(R.layout.dialog_updatedown, null);
            Button mBtnCancel = (Button) downView.findViewById(R.id.btn_updatedown);
            mMProgressBar = (ProgressBar) downView.findViewById(R.id.pb_updatedown);
            mMTvProgress = (TextView) downView.findViewById(R.id.tv_updatedown_progress);
            AlertDialog.Builder downDialog = new AlertDialog.Builder(WelcomeActivity.this);
            mAlertDialog = downDialog.setView(downView).setCancelable(false).create();


            mBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //如果用户点击取消下载,就关闭对话框和停止任务下载,同时删除文件,统一在rxjava的fail删除
                    UtilMethod.dissmissDialog(WelcomeActivity.this, mAlertDialog);
                    if (mRxManager != null) {
                        mRxManager.clear();
                    }
                }
            });
        }
        mMProgressBar.setProgress(0);
        UtilMethod.showDialog(WelcomeActivity.this, mAlertDialog);
    }


    public void startToLogin() {
        isToGuide();
        finish();
    }

    /**
     * 获取版本信息
     */
    private void getData() {

        int appVersionCode = UtilMethod.getAppVersionCode(WelcomeActivity.this);

        //wifi状态下才提示更新
        boolean netAvailable = NetworkUtils.isWifi(WelcomeActivity.this);
        if (netAvailable) {
            mRxManager.add(
                    NetWork.getNetService().getUpdate(appVersionCode + "")
                            .compose(NetWork.handleResult(new BaseCallModel<String>()))
                            .subscribe(new MyObserver<String>() {
                                @Override
                                protected void onSuccess(String data, String resultMsg) {
                                    if (BuildConfig.LOG_DEBUG) {
                                        System.out.println("版本更新:"+data);
                                    }
                                    showUpdateDialog(data);
                                }

                                @Override
                                public void onFail(String resultMsg) {
                                    // startToLogin();
                                    if (BuildConfig.LOG_DEBUG) {
                                        System.out.println("版本更新fail:"+resultMsg);
                                    }
                                     showUpdateDialog(resultMsg);
                                }

                                @Override
                                public void onExit() {

                                }
                            })
            );
        } else {
            startToLogin();
        }
    }

    /**
     * 是否去引导界面
     */
    public void isToGuide() {
        boolean isGuide = CacheUtils.getBoolean(WelcomeActivity.this, Constants.Cache.GUIDE, false);
        if (isGuide) {
            UtilMethod.startIntent(this, LoginActivity.class);
        } else {
            UtilMethod.startIntent(this, GuideActivity.class);
        }
    }

    /**
     * 请求权限
     */
    private void checkPermiss() {
        PermissionUtil.checkPermission(this, REQUEST_PERMISSIONS, new PermissionUtil.permissionInterface() {
            @Override
            public void success() {
                //请求成功
                starToHome();
            }

            @Override
            public void fail(final List<String> permissions) {

                if (map == null) {
                    map = new HashMap<>();
                    map.put("android.permission.CAMERA", "拍照");
                    map.put("android.permission.WRITE_EXTERNAL_STORAGE", "存储空间");
                    map.put("android.permission.READ_PHONE_STATE", "电话状态");
                    map.put("android.permission.ACCESS_COARSE_LOCATION", "位置信息");

                }

                requestPermission(permissions.toArray(new String[permissions.size()]));


            }
        });
    }


    /**
     * 请求权限
     *
     * @param permissions
     */
    private void requestPermission(final String[] permissions) {

        if (permissionDialog != null) {
            permissionDialog.dismiss();
        }

        //请求权限
        permissionDialog = new PermissionDialog(WelcomeActivity.this, new PermissionDialog.onConfirmListener() {
            @Override
            public void comfirm() {
                //请求权限
                PermissionUtil.requestContactsPermissions(WelcomeActivity.this, permissions, REQUEST_PERMISSION_CODE_TAKE_PIC);
            }

            @Override
            public void cancel() {
                WelcomeActivity.this.finish();
            }
        });

        StringBuilder sb = new StringBuilder();
        for (String permission : permissions) {
            if (map != null) {
                String s = map.get(permission);
                if (!TextUtils.isEmpty(s)) {
                    sb.append(s + " ");
                }
            }
        }

        permissionDialog.setTvcontent(sb.toString());
        permissionDialog.show();

    }

    /**
     * 请求权限2
     *
     * @param permissions
     */
    private void requestPermission2(final String[] permissions) {
        DeleteDialog deleteDialog = new DeleteDialog(WelcomeActivity.this, new DeleteDialog.onConfirmListener() {
            @Override
            public void comfirm() {
                //去掉已经请求过的权限
                List<String> deniedPermissions = PermissionUtil.findDeniedPermissions(WelcomeActivity.this, permissions);
                //请求权限
                PermissionUtil.requestContactsPermissions(WelcomeActivity.this, deniedPermissions.toArray(new String[deniedPermissions.size()]), REQUEST_PERMISSION_CODE_TAKE_PIC);
            }

            @Override
            public void cancel() {
                WelcomeActivity.this.finish();
            }
        });

        StringBuilder sb = new StringBuilder();
        for (String permission : permissions) {
            if (map != null) {
                String s = map.get(permission);
                if (!TextUtils.isEmpty(s)) {
                    sb.append(s + " ");
                }
            }
        }
        deleteDialog.setTvcontent("请允许" + sb + "权限请求");
        deleteDialog.show();
    }

    private void startToSetting() {
        DeleteDialog deleteDialog = new DeleteDialog(WelcomeActivity.this, new DeleteDialog.onConfirmListener() {
            @Override
            public void comfirm() {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_PERMISSION_SEETING);
            }

            @Override
            public void cancel() {
                WelcomeActivity.this.finish();
            }
        });


        deleteDialog.setTvcontent("去设置界面开启权限?");
        deleteDialog.show();
    }


    /**
     * 检测权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION_CODE_TAKE_PIC) {
            if (PermissionUtil.verifyPermissions(grantResults)) {//有权限
                starToHome();

            } else {
                //没有权限
                if (!PermissionUtil.shouldShowPermissions(this, permissions)) {//这个返回false 表示勾选了不再提示

                    startToSetting();

                } else {
                    //表示没有权限 ,但是没勾选不再提示
                    for (String s : permissions) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(WelcomeActivity.this,
                                s)) {
                            //去掉已经允许的
                            if (map != null) {
                                map.remove(s);
                            }
                        }
                    }
                    requestPermission2(permissions);
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //如果是从设置界面返回,就继续判断权限
        if (requestCode == REQUEST_PERMISSION_SEETING) {
            checkPermiss();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (map != null) {
            map.clear();
            map = null;
        }
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }

        if (mMProgressBar != null) {
            mMProgressBar = null;
        }

        if (mMTvProgress != null) {
            mMTvProgress = null;
        }
        if (mRxManager != null) {
            mRxManager.clear();
            mRxManager = null;
        }


    }
}

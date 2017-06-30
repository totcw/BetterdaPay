package com.betterda.betterdapay.activity;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.betterda.betterdapay.http.DownloadAPI;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.interfac.DownloadProgressListener;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.FileUtils;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.RxManager;
import com.betterda.betterdapay.util.UtilMethod;
import com.example.lyf.yflibrary.Permission;
import com.example.lyf.yflibrary.PermissionResult;

import java.io.File;
import java.io.IOException;

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
            Permission.checkPermisson(this, REQUEST_PERMISSIONS, new PermissionResult() {
                @Override
                public void success() {
                    starToHome();
                }

                @Override
                public void fail() {
                    finish();
                }
            });
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
    public void showUpdateDialog(final String url) {
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
                checkApk(url);

            }
        });
    }

    /**
     * 检查本地是有apk
     */
    public void checkApk(String url) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(WelcomeActivity.this, "sd卡不可用", 0).show();
            return;
        }
        final File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/" + "update.apk");
       // File externalFilesDir = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOWNLOADS+"/" + "update.apk");
        if (null != externalFilesDir && externalFilesDir.exists()) {
            PackageInfo apkInfo = UtilMethod.getApkInfo(WelcomeActivity.this, externalFilesDir.getAbsolutePath());
            //如果apk没问题就不会为null
            if (null != apkInfo) {
                //检测是否是有一个apk的版本高于当前app,那么就直接安装
                if (UtilMethod.compare(apkInfo, WelcomeActivity.this)) {
                    UtilMethod.startInstall(WelcomeActivity.this, externalFilesDir);
                    finish();
                    return;
                }
            }
        }

        createDownDialog();
        download(externalFilesDir,url);


    }

    public void download(final File externalFilesDir,String url) {

        int indexOf = url.lastIndexOf("/");
        if (indexOf > 0 && indexOf+1 < url.length()) {
            String baseUrl = url.substring(0, indexOf);
            String name = url.substring(indexOf + 1);
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
                                UtilMethod.startInstall(WelcomeActivity.this, externalFilesDir);
                                finish();
                            } else {
                                finish();
                            }
                        } else {
                            finish();
                        }
                    } else {
                        finish();
                    }

                }
            }
        });
        //如果没有就重新下载
        mRxManager.add(DownloadAPI.downloadService(baseUrl, interceptor).download(name)
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
                        mRxManager.cancel();
                    }
                    startToLogin();
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
                                    if (!TextUtils.isEmpty(data) && data.startsWith("http://")) {
                                        showUpdateDialog(data);
                                    } else {
                                        startToLogin();
                                    }

                                }

                                @Override
                                public void onFail(String resultMsg) {
                                     startToLogin();
                                    if (BuildConfig.LOG_DEBUG) {
                                        System.out.println("版本更新fail:"+resultMsg);
                                    }

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

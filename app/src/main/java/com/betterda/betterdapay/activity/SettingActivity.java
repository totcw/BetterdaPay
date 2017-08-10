package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.DownloadAPI;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.interfac.DownloadProgressListener;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.FileUtils;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.ShapeLoadingDialog;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/17.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.topbar_setting)
    NormalTopBar topbarSetting;
    @BindView(R.id.relative_my_yijian)
    RelativeLayout relativeMyYijian;
    @BindView(R.id.relative_setting_update)
    RelativeLayout relativeSettingUpdate;
    @BindView(R.id.btn_setting_exit)
    Button btnSettingExit;
    private ProgressBar mMProgressBar;//下载的进度对话框
    private AlertDialog mAlertDialog;//下载对话框
    private TextView mMTvProgress;
    private ShapeLoadingDialog mDialog;
    private boolean isDown;
    private final static int MSG_SUCCESS = 1;
    private final static int MSG_FAIL = 2;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg != null) {
                if (msg.what == MSG_SUCCESS) {
                    int progress = msg.arg1;
                    if (mMProgressBar != null) {
                        //更新进度条
                        mMTvProgress.setText(progress + "%");
                        mMProgressBar.setProgress(progress);
                    }
                } else if (msg.what == MSG_FAIL) {
                    if (mRxManager != null) {
                        mRxManager.cancel();
                        UtilMethod.dissmissDialog(getmActivity(), mAlertDialog);
                        UtilMethod.Toast(getmActivity(), "获取apk下载地址失败,请去应用商店更新");
                    } else {
                        finish();
                    }
                }

            }

            return false;
        }
    });

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void init() {
        topbarSetting.setTitle("设置");
    }

    @Override
    public void initListener() {
        topbarSetting.setOnBackListener(this);
    }

    @OnClick({R.id.relative_my_yijian, R.id.relative_setting_update, R.id.btn_setting_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_my_yijian:
                UtilMethod.startIntent(getmActivity(), FeedbackActivity.class);
                break;
            case R.id.relative_setting_update:
                getData();
                break;
            case R.id.btn_setting_exit:

                Intent intent = new Intent();
                intent.setClass(this, LoginActivity.class);
                //添加清除任务栈中所有activity的log,如果要启动的activity不在任务栈中了,还需要添加FLAG_ACTIVITY_NEW_TASK,才会关闭任务栈中的其他activity
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }


    /**
     * 获取版本信息
     */
    private void getData() {


        int appVersionCode = UtilMethod.getAppVersionCode(SettingActivity.this);

        //wifi状态下才提示更新
        boolean netAvailable = NetworkUtils.isWifi(SettingActivity.this);
        if (netAvailable) {
            if (mDialog == null) {
                mDialog = UtilMethod.createDialog(this, "正在加载...");
            }
            UtilMethod.showDialog(this, mDialog);
            mRxManager.add(
                    NetWork.getNetService().getUpdate(appVersionCode + "", getString(R.string.appCode))
                            .compose(NetWork.handleResult(new BaseCallModel<>()))
                            .subscribe(new MyObserver<String>() {
                                @Override
                                protected void onSuccess(String data, String resultMsg) {
                                    if (BuildConfig.LOG_DEBUG) {
                                        System.out.println("版本更新:" + data);
                                    }
                                    UtilMethod.dissmissDialog(SettingActivity.this, mDialog);
                                    if (!TextUtils.isEmpty(data) && data.startsWith("http://")) {
                                        showUpdateDialog(data,resultMsg);
                                    } else {
                                        showToast(resultMsg);
                                    }
                                }

                                @Override
                                public void onFail(String resultMsg) {
                                    UtilMethod.dissmissDialog(SettingActivity.this, mDialog);
                                    if (BuildConfig.LOG_DEBUG) {
                                        System.out.println("版本更新fail:" + resultMsg);
                                    }
                                    showToast(resultMsg);
                                }

                                @Override
                                public void onExit(String resultMsg) {
                                    UtilMethod.dissmissDialog(getmActivity(), mDialog);
                                    ExitToLogin(resultMsg);
                                }
                            })
            );
        } else {
            showToast("请开启wifi");
        }
    }

    /**
     * 显示更新对话框
     *
     * @param url
     */
    public void showUpdateDialog(final String url,String msg) {
        View view = LayoutInflater.from(SettingActivity.this).inflate(R.layout.dialog_update, null);
        TextView mTvCancel = (TextView) view.findViewById(R.id.tv_update_cancel);
        TextView mTvComfirm = (TextView) view.findViewById(R.id.tv_update_comfirm);
        TextView mTvUpdate = (TextView) view.findViewById(R.id.tv_dialog_update);
        mTvUpdate.setText(msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.setView(view).setCancelable(false)
                .show();
        mTvCancel.setOnClickListener(v -> UtilMethod.dissmissDialog(SettingActivity.this, alertDialog));

        mTvComfirm.setOnClickListener(v -> {
            UtilMethod.dissmissDialog(SettingActivity.this, alertDialog);
            checkApk(url);

        });
    }

    /**
     * 检查本地是有apk
     */
    public void checkApk(String url) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(SettingActivity.this, "sd卡不可用", 0).show();
            return;
        }
        final File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/" + "update.apk");

        if (null != externalFilesDir && externalFilesDir.exists()) {
            PackageInfo apkInfo = UtilMethod.getApkInfo(SettingActivity.this, externalFilesDir.getAbsolutePath());
            //如果apk没问题就不会为null
            if (null != apkInfo) {
                //检测是否是有一个apk的版本高于当前app,那么就直接安装
                if (UtilMethod.compare(apkInfo, SettingActivity.this)) {
                    UtilMethod.startInstall(SettingActivity.this, externalFilesDir);

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
            isDown = true;
            String baseUrl = url.substring(0, indexOf+1);
            String name = url.substring(indexOf + 1);
            DownloadAPI.DownloadProgressInterceptor interceptor = new DownloadAPI.DownloadProgressInterceptor(new DownloadProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    if (!isDown) {
                        return;
                    }

                    //拿不到文件的大小就直接关闭界面
                    Message message = Message.obtain();

                    if (contentLength == -1 || contentLength == 0) {
                        isDown = false;
                        message.what = MSG_FAIL;
                        mHandler.sendMessage(message);
                        return;
                    }
                    int progress = (int) ((bytesRead * 100) / contentLength);
                    message.what = MSG_SUCCESS;
                    message.arg1 = progress;
                    mHandler.sendMessage(message);
                    if (progress == 100) {//进度到100 就启动安装
                        if (null != externalFilesDir && externalFilesDir.exists()) {
                            //检测是否是通过一个应用,且下载的apk的版本高于当前app
                            //检测是否是有一个apk的版本高于当前app,那么就直接安装
                            PackageInfo apkInfo = UtilMethod.getApkInfo(SettingActivity.this, externalFilesDir.getAbsolutePath());
                            if (null != apkInfo) {

                                if (UtilMethod.compare(apkInfo, SettingActivity.this)) {
                                    UtilMethod.startInstall(SettingActivity.this, externalFilesDir);

                                }
                            }
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
                            UtilMethod.dissmissDialog(SettingActivity.this, mAlertDialog);
                        }

                        @Override
                        public void onError(Throwable e) {
                            //如果下载出错,就删除当前apk
                            if (null != externalFilesDir && externalFilesDir.exists()) {
                                externalFilesDir.delete();
                            }
                            UtilMethod.dissmissDialog(SettingActivity.this, mAlertDialog);

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
            View downView = LayoutInflater.from(SettingActivity.this).inflate(R.layout.dialog_updatedown, null);
            Button mBtnCancel = (Button) downView.findViewById(R.id.btn_updatedown);
            mMProgressBar = (ProgressBar) downView.findViewById(R.id.pb_updatedown);
            mMTvProgress = (TextView) downView.findViewById(R.id.tv_updatedown_progress);
            AlertDialog.Builder downDialog = new AlertDialog.Builder(SettingActivity.this);
            mAlertDialog = downDialog.setView(downView).setCancelable(false).create();


            mBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //如果用户点击取消下载,就关闭对话框和停止任务下载,同时删除文件,统一在rxjava的fail删除
                    UtilMethod.dissmissDialog(SettingActivity.this, mAlertDialog);
                    if (mRxManager != null) {
                        mRxManager.cancel();
                    }
                }
            });
        }
        mMProgressBar.setProgress(0);
        UtilMethod.showDialog(SettingActivity.this, mAlertDialog);
    }

}

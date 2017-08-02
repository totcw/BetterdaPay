package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.ShapeLoadingDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我要推广
 * Created by Administrator on 2017/3/31.
 */

public class TuiguangActivity2 extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.btn_tuiguang2_share)
    Button mBtnTuiguang2Share;
    @BindView(R.id.loadpager_tuiguang)
    LoadingPager mLoadingPager;
    private String url = "http://www.baidu.com";
    private ShapeLoadingDialog mDialog;
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_tuiguang2);
    }



    @OnClick(R.id.btn_tuiguang2_share)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tuiguang2_share:
                getData();
                break;
            case R.id.relative_share_wxfriend:
                shareToWx(SHARE_MEDIA.WEIXIN);
                closePopupWindow();
                break;
            case R.id.relative_share_pyquan:
                shareToWx(SHARE_MEDIA.WEIXIN_CIRCLE);
                closePopupWindow();
                break;
            case R.id.tv_share_cancel:
                closePopupWindow();
                break;
        }
    }

    private void getData() {
        if (mDialog == null) {
            mDialog = UtilMethod.createDialog(getmActivity(), "正在加载...");
        }

        NetworkUtils.isNetWork(getmActivity(), null, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                UtilMethod.showDialog(getmActivity(),mDialog);
                mRxManager.add(
                        NetWork.getNetService()
                                .getCode(UtilMethod.getAccout(getmActivity()))
                                .compose(NetWork.handleResult(new BaseCallModel<String>()))
                                .subscribe(new MyObserver<String>() {
                                    @Override
                                    protected void onSuccess(String data, String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("分享:"+data);
                                        }
                                        url = data;
                                        UtilMethod.dissmissDialog(getmActivity(),mDialog);
                                        share();
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("分享fail:"+resultMsg);
                                        }
                                        UtilMethod.dissmissDialog(getmActivity(),mDialog);
                                    }

                                    @Override
                                    public void onExit() {
                                        UtilMethod.dissmissDialog(getmActivity(),mDialog);
                                    }
                                })
                );
            }
        });
    }

    /**
     * 分享
     */
    private void share() {
        View view = View.inflate(getmActivity(), R.layout.pp_share, null);
        RelativeLayout relative_wxfriend = (RelativeLayout) view.findViewById(R.id.relative_share_wxfriend);
        RelativeLayout relative_pyquan = (RelativeLayout) view.findViewById(R.id.relative_share_pyquan);
        RelativeLayout relative_qq = (RelativeLayout) view.findViewById(R.id.relative_qqfriend);
        RelativeLayout relative_qqzone = (RelativeLayout) view.findViewById(R.id.relative_share_qqzone);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_share_cancel);

        relative_wxfriend.setOnClickListener(this);
        relative_pyquan.setOnClickListener(this);
        relative_qq.setOnClickListener(this);
        relative_qqzone.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        setUpPopupWindow(view, null);
    }


    public void shareToWx(SHARE_MEDIA platform) {

        UMShareAPI mShareAPI = UMShareAPI.get(getmActivity());
        boolean install = mShareAPI.isInstall(getmActivity(), SHARE_MEDIA.WEIXIN);
        if (install) {
            UMImage image = new UMImage(getmActivity(), R.mipmap.ic_launcher);//资源文件
            UMWeb web = new UMWeb(url);
            web.setTitle("来逗阵");//标题
            web.setThumb(image);  //缩略图
            web.setDescription("注册有礼");//描述
            new ShareAction(getmActivity()).setPlatform(platform)
                    .withMedia(web)
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            showToast(share_media.toString());
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {
                            showToast(share_media.toString());
                        }
                    })
                    .share();
        } else {
            showToast("请先安装微信!");
        }


    }
    @Override
    public void dismiss() {
        super.dismiss();
        UtilMethod.backgroundAlpha(1.0f,getmActivity());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //showToast("分享回调");
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}

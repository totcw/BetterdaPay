package com.betterda.betterdapay.activity;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.mylibrary.LoadingPager;
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
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_tuiguang2);
    }



    @OnClick(R.id.btn_tuiguang2_share)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tuiguang2_share:
                share();
                break;
            case R.id.relative_share_wxfriend:
                shareToWx(SHARE_MEDIA.WEIXIN);
                closePopupWindow();
                break;
            case R.id.relative_share_pyquan:
                shareToWx(SHARE_MEDIA.WEIXIN_CIRCLE);
                closePopupWindow();
                break;
        }
    }


    private void getData() {
        mLoadingPager.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), mLoadingPager, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                mRxManager.add(
                        NetWork.getNetService()
                                .getCode(UtilMethod.getAccout(getmActivity()))
                                .compose(NetWork.handleResult(new BaseCallModel<String>()))
                                .subscribe(new MyObserver<String>() {
                                    @Override
                                    protected void onSuccess(String data, String resultMsg) {

                                        if (mLoadingPager != null) {
                                            mLoadingPager.hide();
                                        }
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        if (mLoadingPager != null) {
                                            mLoadingPager.setErrorVisable();
                                        }
                                    }

                                    @Override
                                    public void onExit() {
                                        if (mLoadingPager != null) {
                                            mLoadingPager.hide();
                                        }
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
            web.setTitle("诚享钱包");//标题
            web.setThumb(image);  //缩略图
            web.setDescription("注册有礼");//描述
            new ShareAction(getmActivity()).setPlatform(platform)
                    .withMedia(web)
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                            System.out.println(share_media);
                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            System.out.println(share_media);
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            System.out.println(share_media);
                            throwable.printStackTrace();
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {
                            System.out.println(share_media);
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

}

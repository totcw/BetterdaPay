package com.betterda.betterdapay.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.ShareInfo;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.ImageTools;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 分享
 * Created by Administrator on 2017/3/29.
 */

public class ShareFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.btn_fragment_share)
    Button btnFragmentShare;
    @BindView(R.id.topbar_share)
    NormalTopBar mNormalTopBar;
    @BindView(R.id.loadpager_fragmeng_share)
    LoadingPager mLoadingPager;
    @BindView(R.id.iv_fragmeng_share_qrmember)
    ImageView mIvFragmengShareQrmember;//用户二维码
    @BindView(R.id.tv_fragmeng_share_qrmember)
    TextView mTvFragmengShareQrmember; //用户邀请码
    @BindView(R.id.iv_fragmeng_share_qr)
    ImageView mIvFragmengShareQr;//代理商二维码
    @BindView(R.id.linear_fragment_share)
    LinearLayout mLinearShare; //代理商的内容
    @BindView(R.id.tv_fragmeng_share_qr)
    TextView mTvFragmengShareQr; //代理商邀请码
    private View mView;


    public final static String AGENTS_CODE = "10"; //代理商
    public final static String USER_CODE = "20";
    public final static String INVITE_CODE = "邀请码:";
    public final static int  QR_SIZE = 72;
    private ShareInfo mShareInfoMember; //用户的信息
    private ShareInfo mShareInfo; //代理商的信息
    private String url="http://www.baidu.com";

    @Override
    public View initView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.fragment_share, null);
        return mView;

    }


    @Override
    public void initData() {
        super.initData();
        mNormalTopBar.setTitle("分享");
        mNormalTopBar.setBackVisibility(false);
        mNormalTopBar.setBackgroundColor(ContextCompat.getColor(getmActivity(), R.color.bg_blue));
        mLoadingPager.setonErrorClickListener(v -> {
            getData();
        });
        getData();


    }

    private void getData() {

        NetworkUtils.isNetWork(getmActivity(), null, () -> {

            mRxManager.add(
                    NetWork.getNetService()
                            .getCode(UtilMethod.getAccout(getmActivity()), Constants.APPCODE)
                            .compose(NetWork.handleResult(new BaseCallModel<>()))
                            .subscribe(new MyObserver<List<ShareInfo>>() {
                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mLoadingPager.setLoadVisable();
                                }

                                @Override
                                protected void onSuccess(List<ShareInfo> data, String resultMsg) {
                                    if (BuildConfig.LOG_DEBUG) {
                                        System.out.println("分享:"+data);
                                    }
                                    if (data != null) {
                                        int size = data.size();
                                        for (int i=0;i<size;i++) {
                                            ShareInfo shareInfo = data.get(i);
                                            if (shareInfo != null) {
                                                if (AGENTS_CODE.equals(shareInfo.getUserType())) {
                                                    mShareInfo = shareInfo;
                                                } else if (USER_CODE.equals(shareInfo.getUserType())) {
                                                    mShareInfoMember = shareInfo;
                                                }
                                            }
                                        }
                                    }
                                    if (mShareInfoMember != null) {
                                        mTvFragmengShareQrmember.setText(INVITE_CODE+mShareInfoMember.getInviteCode());
                                         Bitmap bitmap = ImageTools.generateQRCode(mShareInfoMember.getUserInviteUrl(), getmActivity(),QR_SIZE);
                                          mIvFragmengShareQrmember.setImageBitmap(bitmap);
                                    }

                                    if (mShareInfo != null) {
                                        mTvFragmengShareQr.setText(INVITE_CODE+mShareInfo.getInviteCode());
                                        Bitmap bitmap = ImageTools.generateQRCode(mShareInfo.getUserInviteUrl(), getmActivity(),QR_SIZE);
                                        mIvFragmengShareQr.setImageBitmap(bitmap);
                                    } else {
                                        //隐藏代理商
                                        mLinearShare.setVisibility(View.GONE);
                                    }

                                    mLoadingPager.hide();

                                }

                                @Override
                                public void onFail(String resultMsg) {
                                    if (BuildConfig.LOG_DEBUG) {
                                        System.out.println("分享fail:"+resultMsg);
                                    }
                                    showToast(resultMsg);
                                     mLoadingPager.setErrorVisable();

                                }

                                @Override
                                public void onExit(String resultMsg) {
                                    ExitToLogin(resultMsg);
                                }
                            })
            );
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


    @OnClick({R.id.btn_fragment_share,R.id.btn_fragment_sharemember})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fragment_share://分享代理商
                if (mShareInfo != null) {
                    url = mShareInfo.getUserInviteUrl();
                }
                share();
                break;
            case R.id.btn_fragment_sharemember://分享会员
                if (mShareInfoMember != null) {
                    url = mShareInfoMember.getUserInviteUrl();
                }
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
            case R.id.relative_qqfriend:
                break;
            case R.id.relative_share_qqzone:
                break;
            case R.id.tv_share_cancel:
                closePopupWindow();
                break;

        }
    }


    public void shareToWx(SHARE_MEDIA platform) {

        UMShareAPI mShareAPI = UMShareAPI.get(getmActivity());
        boolean install = mShareAPI.isInstall(getmActivity(), SHARE_MEDIA.WEIXIN);
        if (install) {
            UMImage image = new UMImage(getmActivity(), R.mipmap.ic_launcher);//资源文件
            UMWeb web = new UMWeb(url);
            web.setTitle(getString(R.string.share_title));//标题
            web.setThumb(image);  //缩略图
            web.setDescription(getString(R.string.share_content));//描述

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
        UtilMethod.backgroundAlpha(1.0f, getmActivity());
    }





}

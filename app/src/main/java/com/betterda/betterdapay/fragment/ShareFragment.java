package com.betterda.betterdapay.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.Utils.StatusBarCompat;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/29.
 */

public class ShareFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.btn_fragment_share)
    Button btnFragmentShare;
    @BindView(R.id.topbar_share)
    NormalTopBar mNormalTopBar;
    private View mView;

    @Override
    public View initView(LayoutInflater inflater) {
         mView =  inflater.inflate(R.layout.fragment_share, null);
        return mView;

    }



    @Override
    public void initData() {
        super.initData();
        mNormalTopBar.setTitle("分享");
        mNormalTopBar.setBackVisibility(false);
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


    @OnClick(R.id.btn_fragment_share)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fragment_share:
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
            new ShareAction(getmActivity()).setPlatform(platform)
                    .withText("hello")
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

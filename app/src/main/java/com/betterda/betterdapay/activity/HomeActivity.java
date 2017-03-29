package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.fragment.BalanceFragment;
import com.betterda.betterdapay.fragment.MyFragment;
import com.betterda.betterdapay.fragment.ShouKuanFragment;
import com.betterda.betterdapay.fragment.UpFragment;
import com.betterda.betterdapay.fragment.WalletFragment;
import com.betterda.betterdapay.util.RxBus;
import com.betterda.mylibrary.view.IndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/28.
 */
public class HomeActivity extends BaseActivity {
    @BindView(R.id.idv_shoukuan)
    IndicatorView idvShoukuan;
    @BindView(R.id.idv_wallet)
    IndicatorView idvWallet;
    @BindView(R.id.idv_banlance)
    IndicatorView idvBanlance;
    @BindView(R.id.idv_my)
    IndicatorView idvMy;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.iv_home_oval)
    ImageView mIvOval;
    @BindView(R.id.tv_home_oval)
    TextView mTvOval;

    private FragmentManager fm;
    private Fragment shoukuanFragment, walletFragment, upFragment, balanceFragment, myFragment;


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_home);
        //主页
        //随便
    }


    @Override
    public void init() {
        super.init();
        /**
         * 设置底部布局的属性
         */
        idvShoukuan.setIvBackground(R.mipmap.shoukuan_normal, R.mipmap.shoukuan_pressed);
        idvWallet.setIvBackground(R.mipmap.wallet_normal, R.mipmap.wallet_pressed);
        idvBanlance.setIvBackground(R.mipmap.banlance_normal, R.mipmap.balance_pressed);
        idvMy.setIvBackground(R.mipmap.my_normal, R.mipmap.my_pressed);
        idvShoukuan.setLineBackground(0xff909090, 0xff00aaee);
        idvWallet.setLineBackground(0xff909090, 0xff00aaee);
        idvBanlance.setLineBackground(0xff909090, 0xff00aaee);
        idvMy.setLineBackground(0xff909090, 0xff00aaee);
        idvShoukuan.setTitle("钱包");
        idvWallet.setTitle("等级");
        idvBanlance.setTitle("分享");
        idvMy.setTitle("我的");

        //默认选中收款
        idvShoukuan.setTabSelected(true);
        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (!HomeActivity.this.isFinishing()) {
            if (null == walletFragment) {
                walletFragment = new WalletFragment();
            }
            fragmentTransaction.replace(R.id.frame, walletFragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    private void replace(Fragment fragment) {
        if (!HomeActivity.this.isFinishing()) {

            if (fm != null) {
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).commitAllowingStateLoss();
            }
        }
    }


    public void change(IndicatorView idv, ImageView imageView) {
        /**
         * 设置收款为选中,其它为默认
         */
        if (null != idvShoukuan && null != idvWallet && null != mIvOval
                 && idvBanlance != null && null != idvMy) {

            idvShoukuan.setTabSelected(false);
            idvWallet.setTabSelected(false);
            mIvOval.setSelected(false);
            idvBanlance.setTabSelected(false);
            idvMy.setTabSelected(false);
            mTvOval.setTextColor(0xff909090);
            if (imageView != null) {
                imageView.setSelected(true);
                mTvOval.setTextColor(0xff00aaee);
            } else if ( null != idv){
                idv.setTabSelected(true);
            }


        }

    }

    @OnClick({R.id.idv_shoukuan, R.id.idv_wallet, R.id.relative_home_oval, R.id.idv_banlance, R.id.idv_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idv_shoukuan://钱包
                if (null == walletFragment) {
                    walletFragment = new WalletFragment();
                }
                replace(walletFragment);
                change(idvShoukuan,null);
                break;
            case R.id.idv_wallet: //等级
                if (null == upFragment) {
                    upFragment = new UpFragment();
                }
                replace(upFragment);
                change(idvWallet,null);

                break;
            case R.id.relative_home_oval://收款
                //只要activity没被销毁,fragment就不会真正的为空
                if (null == shoukuanFragment) {
                    shoukuanFragment = new ShouKuanFragment();
                }
                replace(shoukuanFragment);
                change(null,mIvOval);


                break;
            case R.id.idv_banlance://分享
                if (null == balanceFragment) {
                    balanceFragment = new BalanceFragment();
                }
                replace(balanceFragment);
                change(idvBanlance,null);
                break;
            case R.id.idv_my://我的
                if (null == myFragment) {
                    myFragment = new MyFragment();

                }
                replace(myFragment);
                change(idvMy,null);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        RxBus.get().post("LoginActivity", "");
        super.onBackPressed();
    }
}

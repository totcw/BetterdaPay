package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

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
    @BindView(R.id.idv_up)
    IndicatorView idvUp;
    @BindView(R.id.idv_banlance)
    IndicatorView idvBanlance;
    @BindView(R.id.idv_my)
    IndicatorView idvMy;
    @BindView(R.id.frame)
    FrameLayout frame;
    private FragmentManager fm;
    private Fragment shoukuanFragment,walletFragment,upFragment,balanceFragment,myFragment;


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_home);
        //主页

    }


    @Override
    public void init() {
        super.init();
        /**
         * 设置底部布局的属性
         */
        idvShoukuan.setIvBackground(R.mipmap.shoukuan_normal, R.mipmap.shoukuan_pressed);
        idvWallet.setIvBackground(R.mipmap.wallet_normal, R.mipmap.wallet_pressed);
        idvUp.setIvBackground(R.mipmap.up_normal, R.mipmap.up_pressed);
        idvBanlance.setIvBackground(R.mipmap.banlance_normal, R.mipmap.balance_pressed);
        idvMy.setIvBackground(R.mipmap.my_normal, R.mipmap.my_pressed);
        idvShoukuan.setLineBackground(0xff909090, 0xff16d9cf);
        idvWallet.setLineBackground(0xff909090, 0xff16d9cf);
        idvUp.setLineBackground(0xff909090, 0xff16d9cf);
        idvBanlance.setLineBackground(0xff909090, 0xff16d9cf);
        idvMy.setLineBackground(0xff909090, 0xff16d9cf);
        idvShoukuan.setTitle("收款");
        idvWallet.setTitle("钱包");
        idvUp.setTitle("升级");
        idvBanlance.setTitle("账单");
        idvMy.setTitle("我的");

        //默认选中收款
        idvShoukuan.setTabSelected(true);
        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (!HomeActivity.this.isFinishing()) {
            if (null == shoukuanFragment) {
                shoukuanFragment = new ShouKuanFragment();
            }
            fragmentTransaction.replace(R.id.frame, shoukuanFragment);
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


    public void change(IndicatorView idv) {
        /**
         * 设置收款为选中,其它为默认
         */
        if (null != idvShoukuan && null != idvWallet && null != idvUp
                && null != idv && idvBanlance != null && null != idvMy) {

            idvShoukuan.setTabSelected(false);
            idvWallet.setTabSelected(false);
            idvUp.setTabSelected(false);
            idvBanlance.setTabSelected(false);
            idvMy.setTabSelected(false);
            idv.setTabSelected(true);

        }

    }

    @OnClick({R.id.idv_shoukuan, R.id.idv_wallet, R.id.idv_up, R.id.idv_banlance, R.id.idv_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idv_shoukuan:
                //只要activity没被销毁,fragment就不会真正的为空
                if (null == shoukuanFragment) {
                    shoukuanFragment = new ShouKuanFragment();
                }
                replace(shoukuanFragment);
                change(idvShoukuan);
                break;
            case R.id.idv_wallet:
                if (null == walletFragment) {
                    walletFragment = new WalletFragment();
                }
                replace(walletFragment);
                change(idvWallet);
                break;
            case R.id.idv_up:
                if (null == upFragment) {
                    upFragment = new UpFragment();
                }
                replace(upFragment);
                change(idvUp);
                break;
            case R.id.idv_banlance:
                if (null == balanceFragment) {
                    balanceFragment = new BalanceFragment();
                }
                replace(balanceFragment);
                change(idvBanlance);
                break;
            case R.id.idv_my:
                if (null == myFragment) {
                    myFragment = new MyFragment();

                }
                replace(myFragment);
                change(idvMy);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        RxBus.get().post("LoginActivity","");
        super.onBackPressed();
    }
}

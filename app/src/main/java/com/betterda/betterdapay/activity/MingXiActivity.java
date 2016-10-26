package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.fragment.BalanceFragment2;
import com.betterda.betterdapay.fragment.FenRunFragment;
import com.betterda.betterdapay.util.UtilMethod;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 明细
 * Created by Administrator on 2016/8/9.
 */
public class MingXiActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_mingxi_back)
    ImageView ivMingxiBack;
    @BindView(R.id.tv_mingxi_title)
    TextView tvMingxiTitle;
    @BindView(R.id.linear_mingxi_title)
    LinearLayout linearMingxiTitle;
    @BindView(R.id.iv_mingxi_search)
    ImageView ivMingxiSearch;
    @BindView(R.id.relative_mingxi_top)
    RelativeLayout top;

    private Fragment balanceFragment;
    private FenRunFragment fenrunFragment;
    private FragmentManager fm;
    private String TAG = MingXiActivity.class.getSimpleName();
    private String item="1";//当前是哪个fragment可见

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_mingxi);
    }

    @Override
    public void init() {
        super.init();

        balanceFragment = new BalanceFragment2();
        fenrunFragment = new FenRunFragment();
        fm = getSupportFragmentManager();
        change(balanceFragment);
    }


    @OnClick({R.id.iv_mingxi_back, R.id.linear_mingxi_title, R.id.iv_mingxi_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_mingxi_back:
                back();
                break;
            case R.id.linear_mingxi_title:
                choose();
                break;
            case R.id.iv_mingxi_search:
                search();
                break;
            case R.id.tv_fapiao_shi:
                change(balanceFragment);
                tvMingxiTitle.setText("收款");
                closePopupWindow();
                break;
            case R.id.tv_fapiao_fou:
                change(fenrunFragment);
                tvMingxiTitle.setText("分润");
                closePopupWindow();
                break;
        }
    }

    /**
     * 搜索
     */
    private void search() {
        if (balanceFragment != null) {
            if (balanceFragment.isVisible()) {
                item = "1";
            }
        }

        if (fenrunFragment != null) {
            if (fenrunFragment.isVisible()) {
                if (fenrunFragment.vpFenrun != null) {
                    if (fenrunFragment.vpFenrun.getCurrentItem() == 0) {
                        item = "2";
                    } else {
                        item = "3";
                    }
                }
            }
        }
        UtilMethod.startIntent(getmActivity(),SearchActivity.class,"item",item);
    }


    private void change(Fragment fragment) {
        if (!this.isFinishing()) {
            if (fm != null) {
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fram_mingxi_content, fragment).commitAllowingStateLoss();
            }
        }
    }

    /**
     * 选择类型
     */
    private void choose() {
        View view = View.inflate(getmActivity(), R.layout.pp_choose_type, null);
        TextView tv_shoukuan = (TextView) view.findViewById(R.id.tv_fapiao_shi);
        TextView tv_fenrun = (TextView) view.findViewById(R.id.tv_fapiao_fou);
        tv_shoukuan.setOnClickListener(this);
        tv_fenrun.setOnClickListener(this);
        setUpPopupWindow(view, top);
    }
}

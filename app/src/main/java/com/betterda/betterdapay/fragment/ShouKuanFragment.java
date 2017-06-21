package com.betterda.betterdapay.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.ChooseCityActivity;
import com.betterda.betterdapay.activity.ChoosePayTypeActivity;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.mylibrary.Utils.StatusBarCompat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 收款
 * Created by Administrator on 2016/7/28.
 */
public class ShouKuanFragment extends BaseFragment {
    private static final String TAG = "shoukuanFragment";
    @BindView(R.id.btn_shoukuan)
    Button btnShoukuan;

    ImageView ivShouyeSearch;
    @BindView(R.id.tv_shoukuan_money)
    TextView tvShoukuanMoney;
    @BindView(R.id.iv_shoukuan_one)
    TextView ivShoukuanOne;
    @BindView(R.id.iv_shoukuan_four)
    TextView ivShoukuanFour;
    @BindView(R.id.iv_shoukuan_seven)
    TextView ivShoukuanSeven;
    @BindView(R.id.iv_shoukuan_zero)
    TextView ivShoukuanZero;
    @BindView(R.id.iv_shoukuan_two)
    TextView ivShoukuanTwo;
    @BindView(R.id.iv_shoukuan_five)
    TextView ivShoukuanFive;
    @BindView(R.id.iv_shoukuan_eight)
    TextView ivShoukuanEight;
    @BindView(R.id.iv_shoukuan_zero2)
    TextView ivShoukuanZero2;
    @BindView(R.id.iv_shoukuan_three)
    TextView ivShoukuanThree;
    @BindView(R.id.iv_shoukuan_six)
    TextView ivShoukuanSix;
    @BindView(R.id.iv_shoukuan_nine)
    TextView ivShoukuanNine;
    @BindView(R.id.iv_shoukuan_point)
    TextView ivShoukuanPoint;
    @BindView(R.id.iv_shoukuan_del)
    TextView ivShoukuanDel;
    @BindView(R.id.relative_shoukuan_qr)
    RelativeLayout relative_shoukuan_qr;

    private StringBuilder sb;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_shoukuan, null);
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void onStart() {
        super.onStart();
        sb = new StringBuilder();
        sb.append("0");
        if (tvShoukuanMoney != null) {
            tvShoukuanMoney.setText("0");
        }
    }


    @OnClick({R.id.iv_shoukuan_one, R.id.iv_shoukuan_four, R.id.iv_shoukuan_seven, R.id.iv_shoukuan_zero, R.id.iv_shoukuan_two, R.id.iv_shoukuan_five, R.id.iv_shoukuan_eight, R.id.iv_shoukuan_zero2, R.id.iv_shoukuan_three, R.id.iv_shoukuan_six, R.id.iv_shoukuan_nine, R.id.iv_shoukuan_point, R.id.iv_shoukuan_del, R.id.relative_shoukuan_qr, R.id.btn_shoukuan})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_shoukuan_one:
                write("1");
                break;
            case R.id.iv_shoukuan_four:
                write("4");
                break;
            case R.id.iv_shoukuan_seven:
                write("7");
                break;
            case R.id.iv_shoukuan_zero:
                write("0");
                break;
            case R.id.iv_shoukuan_two:
                write("2");
                break;
            case R.id.iv_shoukuan_five:
                write("5");
                break;
            case R.id.iv_shoukuan_eight:
                write("8");
                break;
            case R.id.iv_shoukuan_zero2:
                write("00");
                break;
            case R.id.iv_shoukuan_three:
                write("3");
                break;
            case R.id.iv_shoukuan_six:
                write("6");
                break;
            case R.id.iv_shoukuan_nine:
                write("9");
                break;
            case R.id.iv_shoukuan_point:
                write(".");
                break;
            case R.id.iv_shoukuan_del://清零
                clear();
                break;
            case R.id.relative_shoukuan_qr: //删除一位
                deleteOne();
                break;
            case R.id.btn_shoukuan://收款
                if (UtilMethod.showNotice(getmActivity())) {
                    pay();
                }
                break;
        }
    }


    private void pay() {
        if (!sb.toString().matches(Constants.str)) {//输入的没问题
            showToast("输入格式有误");
            return;
        }

        try {
            float sum = Float.valueOf(sb.toString());
            if (sum == 0) {
                showToast("收款不能为0");
            } else if (sum < 50000) {
                UtilMethod.startIntent(getmActivity(), ChoosePayTypeActivity.class, "money", sb.toString());
            } else {
                showToast("每次最多收款50000");
            }
        } catch (Exception e) {
            showToast("输入格式有误");
        }

    }

    /**
     * 清理
     */
    private void clear() {
        tvShoukuanMoney.setText("0");
        sb = new StringBuilder();
        sb.append("0");

    }

    /**
     * 删除一位
     */
    private void deleteOne() {

        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        //默认显示0
        if (sb.length() == 0) {
            sb.append("0");
        }

        tvShoukuanMoney.setText(sb.toString());
    }

    private void write(String number) {

        //为0时,替换,其他情况就直接添加
        if ("0".equals(sb.toString())||"00".equals(sb.toString())) {
            if (".".equals(number)) {
                //如果为0的时候输入.就什么都不做,因为收款是要大于1块
                return;
            }
            sb.replace(0, sb.length(), number);
        } else {
            sb.append(number);
        }
        tvShoukuanMoney.setText(sb.toString());
    }
}

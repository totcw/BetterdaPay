package com.betterda.betterdapay.livingpay;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.BaseActivity;
import com.betterda.betterdapay.view.NormalTopBar;


/**
 * 缴费输入充值界面
 * Created by Administrator on 2016/9/18.
 */
public class LivingPayActivity extends BaseActivity implements View.OnClickListener {
    private String type;
    private NormalTopBar topBar;
    private ImageView ivPayshui;
    private TextView tvPayshuiType;
    private Button btnPaishuiCommit;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_payshui);
        topBar = (NormalTopBar) findViewById(R.id.topbar_payshui);
        ivPayshui = (ImageView) findViewById(R.id.iv_payshui);
        tvPayshuiType = (TextView) findViewById(R.id.tv_payshui_type);
        btnPaishuiCommit = (Button) findViewById(R.id.btn_paishui_commit);
    }

    @Override
    public void initListener() {
        super.initListener();
        topBar.setOnBackListener(this);
        btnPaishuiCommit.setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        getIntentData();
        topBar.setTitle("生活缴费");
        setType();
    }

    /**
     * 设置充值类型
     */
    private void setType() {
        tvPayshuiType.setText(type);
        int id = R.mipmap.pay_shui;
        switch (type) {
            case "水费":
                id = R.mipmap.pay_shui;
                break;
            case "电费":
                id = R.mipmap.pay_dian;
                break;
            case "燃气费":
                id = R.mipmap.pay_ranqi;
                break;
        }
        ivPayshui.setImageResource(id);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
          type =  intent.getStringExtra("type");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                back();
                break;

            case R.id.btn_paishui_commit:
                break;
        }
    }
}

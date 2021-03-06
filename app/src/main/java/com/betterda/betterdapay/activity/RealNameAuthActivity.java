package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyTextWatcher;
import com.betterda.betterdapay.data.BankData;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 实名认证
 * Created by Administrator on 2016/8/17.
 */
public class RealNameAuthActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.topbar_realnameauth)
    NormalTopBar topbarRealnameauth;
    @BindView(R.id.et_realnameauth_name)
    EditText etRealnameauthName;
    @BindView(R.id.et_realnameauth_identity)
    EditText etRealnameauthIdentity;
    @BindView(R.id.tv_realnameauth_bankname)
    TextView tvRealnameauthBankname;
    @BindView(R.id.linear_realnameauth_bankname)
    LinearLayout linearRealnameauthBankname;
    @BindView(R.id.et_realnameauth_cardNo)
    EditText etRealnameauthCardNo;
    @BindView(R.id.et_realnameauth_number)
    EditText etRealnameauthNumber;
    @BindView(R.id.btn_realnameauth_next)
    Button btnRealnameauthNext;

    private String realName;//认证姓名
    private String identityCard;//身份证号
    private String cardNum;//银行卡号
    private String bank;//所属银行
    private String number;//预留手机号码
    private String cardType = "储蓄卡";//银行卡类型


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_realnameauth);
    }

    @Override
    public void init() {
        super.init();
        topbarRealnameauth.setTitle("实名认证");
        setEditListener();
    }

    private void setEditListener() {
        etRealnameauthName.addTextChangedListener(new MyTextWatcher(etRealnameauthName) {
            @Override
            public void afterTextChanged(Editable s) {
                realName = s.toString();
                commit();
            }
        });
        etRealnameauthIdentity.addTextChangedListener(new MyTextWatcher(etRealnameauthIdentity) {
            @Override
            public void afterTextChanged(Editable s) {
                identityCard = s.toString();
                commit();
            }
        });
        etRealnameauthCardNo.addTextChangedListener(new MyTextWatcher(etRealnameauthCardNo) {
            @Override
            public void afterTextChanged(Editable s) {
                cardNum = s.toString();
                commit();
            }
        });
        etRealnameauthNumber.addTextChangedListener(new MyTextWatcher(etRealnameauthNumber) {
            @Override
            public void afterTextChanged(Editable s) {
                number = s.toString();
                commit();
            }
        });
    }

    /**
     * 验证是否都输入了数据
     */
    private void commit() {
        if (!TextUtils.isEmpty(realName) && !TextUtils.isEmpty(identityCard)
                && !TextUtils.isEmpty(cardNum) && !TextUtils.isEmpty(bank)
                && !TextUtils.isEmpty(number)) {
            btnRealnameauthNext.setSelected(true);
        } else {
            btnRealnameauthNext.setSelected(false);
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        topbarRealnameauth.setOnBackListener(this);
    }

    @OnClick({R.id.linear_realnameauth_bankname, R.id.btn_realnameauth_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_realnameauth_bankname:
                chooseBank();
                break;
            case R.id.btn_realnameauth_next:
                next();
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }

    /**
     * 下一步
     */
    private void next() {
     if (btnRealnameauthNext.isSelected()) {
            Intent intent = new Intent(getmActivity(), AddBankCardActivity2.class);
            intent.putExtra("realName", realName);
            intent.putExtra("identityCard", identityCard);
            intent.putExtra("cardNum", cardNum);
            intent.putExtra("bank", bank);
            intent.putExtra("number", number);
            intent.putExtra("cardType", cardType);
            startActivity(intent);
       }
    }

    /**
     * 选择银行卡
     */
    private void chooseBank() {
        final View view = View.inflate(getmActivity(), R.layout.pp_chose_bank, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_ppchosebank);
        recyclerView.setLayoutManager(new LinearLayoutManager(getmActivity()));
        recyclerView.setAdapter(new CommonAdapter<String>(getmActivity(), R.layout.item_recycleview_chose_bank, BankData.getBankList()) {

            @Override
            public void convert(ViewHolder viewHolder, final String s) {
                if (!TextUtils.isEmpty(s)) {
                    viewHolder.setText(R.id.tv_chose_bank, s);
                    viewHolder.setImageResource(R.id.iv_chose_bank, BankData.getBank(s));
                }

                viewHolder.setOnClickListener(R.id.linear_chose_bank, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bank = s;
                        tvRealnameauthBankname.setText(s);
                        closePopupWindow();
                        commit();
                    }
                });
            }
        });
        setUpPopupWindow(view, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != BankData.bankMap) {
            BankData.bankMap.clear();
            BankData.bankMap = null;
        }
        if (null != BankData.bankList) {
            BankData.bankList.clear();
            BankData.bankList = null;
        }

    }
}

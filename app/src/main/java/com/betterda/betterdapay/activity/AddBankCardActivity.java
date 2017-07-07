package com.betterda.betterdapay.activity;

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
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.callback.MyTextWatcher;
import com.betterda.betterdapay.data.BankData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.BankCardUtil;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.IDCardUtil;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.ShapeLoadingDialog;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加银行卡
 * Created by Administrator on 2016/8/17.
 */
public class AddBankCardActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.topbar_addbankcard)
    NormalTopBar topbarAddbankcard;
    @BindView(R.id.et_addbank_name)
    EditText etAddbankName;
    @BindView(R.id.et_addbank_identity)
    EditText etAddbankIdentity;
    @BindView(R.id.tv_addbank_bankname)
    TextView tvAddbankBankname;
    @BindView(R.id.linear_addbank_bankname)
    LinearLayout linearAddbankBankname;
    @BindView(R.id.et_addbank_cardNo)
    EditText etAddbankCardNo;
    @BindView(R.id.et_addbank_number)
    EditText etAddbankNumber;
    @BindView(R.id.btn_addbank_next)
    Button btnAddbankNext;
    private ShapeLoadingDialog dialog;
    private String truename;   // 持卡人姓名
    private String identitycard;//身份证
    private String bank;    // 所属银行
    private String cardnum;   // 卡号
    private String number;   // 预留手机号
    private String cardType = "信用卡";  //银行卡类型

    @Override
    public void initView() {
        setContentView(R.layout.activity_addbankcard);
    }

    @Override
    public void init() {
        setTopBar();
        dialog = UtilMethod.createDialog(getmActivity(), "正在加载...");
        setTextChange();


    }

    /**
     * 设置edit的监听
     */
    private void setTextChange() {
        etAddbankName.addTextChangedListener(new MyTextWatcher(etAddbankName) {
            @Override
            public void afterTextChanged(Editable s) {
                truename = s.toString();
                judge();
            }
        });
        etAddbankCardNo.addTextChangedListener(new MyTextWatcher(etAddbankCardNo) {
            @Override
            public void afterTextChanged(Editable s) {
                cardnum = s.toString();
                judge();
            }
        });
        etAddbankNumber.addTextChangedListener(new MyTextWatcher(etAddbankNumber) {
            @Override
            public void afterTextChanged(Editable s) {
                number = s.toString();
                judge();
            }
        });
        etAddbankIdentity.addTextChangedListener(new MyTextWatcher(etAddbankIdentity) {
            @Override
            public void afterTextChanged(Editable s) {
                identitycard = s.toString();
                judge();
            }
        });
    }

    private void setTopBar() {
        topbarAddbankcard.setTitle("添加银行卡");
    }

    @Override
    public void initListener() {
        super.initListener();
        topbarAddbankcard.setOnBackListener(this);
    }

    @OnClick({R.id.linear_addbank_bankname, R.id.btn_addbank_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_addbank_bankname:
                chooseBank();
                break;
            case R.id.btn_addbank_next:
                commit();
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }

    /**
     * 提交数据
     */
    private void commit() {

        if (btnAddbankNext.isSelected()) {
            boolean ismobile = number.matches(Constants.NUMBER_REGULAR);
            if (!ismobile) {
                showToast("请输入正确的手机号码");
                return;
            }
            //验证银行卡号
            if (!BankCardUtil.checkBankCard(cardnum)) {
                showToast("请输入正确的银行卡号");
                return;
            }
            //验证身份证
            String idCardValidate = IDCardUtil.IDCardValidate(identitycard);
            if ("YES".equals(idCardValidate)) {
                NetworkUtils.isNetWork(getmActivity(), topbarAddbankcard, new NetworkUtils.SetDataInterface() {
                    @Override
                    public void getDataApi() {
                        UtilMethod.showDialog(getmActivity(), dialog);
                        mRxManager.add(
                                NetWork.getNetService()
                                        .getBandAdd(UtilMethod.getAccout(getmActivity()),UtilMethod.getToken(getmActivity()),truename,identitycard,bank,cardnum,number,cardType)
                                        .compose(NetWork.handleResult(new BaseCallModel<String>()))
                                        .subscribe(new MyObserver<String>() {
                                            @Override
                                            protected void onSuccess(String data, String resultMsg) {
                                                showToast(resultMsg);
                                                UtilMethod.dissmissDialog(getmActivity(),dialog);
                                                finish();
                                            }

                                            @Override
                                            public void onFail(String resultMsg) {
                                                showToast(resultMsg);
                                                UtilMethod.dissmissDialog(getmActivity(),dialog);
                                            }

                                            @Override
                                            public void onExit() {

                                            }
                                        })
                        );
                    }
                });
            } else {
                showToast(idCardValidate);
            }
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
                        tvAddbankBankname.setText(s);
                        bank = s;
                        judge();
                        closePopupWindow();
                    }
                });
            }
        });
        setUpPopupWindow(view, null);
    }

    /**
     * 判断数据是不是为空
     */
    public void judge() {
        if (!TextUtils.isEmpty(truename) && !TextUtils.isEmpty(identitycard) &&
                !TextUtils.isEmpty(number) && !TextUtils.isEmpty(cardnum)
                && !TextUtils.isEmpty(bank) ) {
            btnAddbankNext.setSelected(true);
        } else {
            btnAddbankNext.setSelected(false);
        }
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

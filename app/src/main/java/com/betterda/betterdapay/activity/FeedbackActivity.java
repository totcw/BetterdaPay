package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.callback.MyTextWatcher;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.TuiGuang;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.ShapeLoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 意见反馈
 * Created by Administrator on 2016/9/6.
 */
public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.topbar_feedback)
    NormalTopBar topbarFeedback;
    @BindView(R.id.et_feedback)
    EditText etFeedback;
    @BindView(R.id.btn_feedback_commit)
    Button btnFeedbackCommit;
    private String content;
    private ShapeLoadingDialog dialog;
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_feedback);
    }

    @Override
    public void init() {
        super.init();
        topbarFeedback.setTitle("意见反馈");
        etFeedback.addTextChangedListener(new MyTextWatcher(etFeedback) {
            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString();
                if (!TextUtils.isEmpty(content)) {
                    btnFeedbackCommit.setSelected(true);
                } else {
                    btnFeedbackCommit.setSelected(false);
                }
            }
        });
    }

    @OnClick({R.id.btn_feedback_commit,R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_feedback_commit:
                commit();
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }

    private void commit() {

            if (TextUtils.isEmpty(content)) {
                showToast("反馈内容不能为空");
                return;
            }
            NetworkUtils.isNetWork(getmActivity(), null, new NetworkUtils.SetDataInterface() {
                @Override
                public void getDataApi() {
                    if (dialog == null) {
                        dialog = UtilMethod.createDialog(getmActivity(), "正在提交...");
                    }
                    UtilMethod.showDialog(getmActivity(),dialog);
                    mRxManager.add(
                            NetWork.getNetService()
                                    .getFeedBack(UtilMethod.getAccout(getmActivity()),content,Constants.APPCODE)
                                    .compose(NetWork.handleResult(new BaseCallModel<>()))
                                    .subscribe(new MyObserver<String>() {
                                        @Override
                                        protected void onSuccess(String data, String resultMsg) {
                                            UtilMethod.dissmissDialog(getmActivity(), dialog);
                                            showToast(resultMsg);
                                            finish();
                                        }

                                        @Override
                                        public void onFail(String resultMsg) {
                                            if (BuildConfig.LOG_DEBUG) {
                                                System.out.println("意见反馈:"+resultMsg);
                                            }
                                            UtilMethod.dissmissDialog(getmActivity(), dialog);
                                            showToast(resultMsg);
                                        }
                                        @Override
                                        public void onExit(String resultMsg) {
                                            UtilMethod.dissmissDialog(getmActivity(), dialog);
                                            ExitToLogin(resultMsg);
                                        }
                                    })
                    );
                }
            });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //因为这里面引用了context,会内存泄漏
        dialog = null;
    }
}

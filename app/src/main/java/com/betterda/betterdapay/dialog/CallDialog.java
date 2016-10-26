package com.betterda.betterdapay.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.betterda.betterdapay.R;


/**
 * Created by Administrator on 2016/5/19.
 */
public class CallDialog {
    private Context mContext;
    private Dialog mDialog;
    private View mDialogContentView;
    private onConfirmListener listener;
    private Button btn_quxiao;
    private Button btn_comfirm;
    private TextView tv_content;
    private String content, comfirm;

    public CallDialog(Context context, onConfirmListener listener, String content, String comfirm) {
        this.mContext = context;
        this.listener = listener;
        this.content = content;
        this.comfirm = comfirm;
        init();
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.custom_dialog2);

        mDialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_call, null);
        btn_quxiao = (Button) mDialogContentView.findViewById(R.id.btn_dialog_call_quxiao);
        btn_comfirm = (Button) mDialogContentView.findViewById(R.id.btn_dialog_call_comfrim);
        tv_content = (TextView) mDialogContentView.findViewById(R.id.tv_dialog_call_content);
        tv_content.setText(content);
        if (!TextUtils.isEmpty(comfirm)) {
            btn_comfirm.setText(comfirm);
        }
        btn_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.cancel();
                }
                dismiss();
            }
        });

        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.comfirm();
                }
                dismiss();
            }
        });

        mDialog.setCancelable(false);//不能点外面取消,也不 能点back取消
        mDialog.setContentView(mDialogContentView);
    }

    public void show() {
        mDialog.show();

    }

    public void setTitle(String content) {
        if (tv_content != null) {
            tv_content.setText(content);
        }
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public interface onConfirmListener {
        void comfirm();

        void cancel();


    }
}

package com.betterda.betterdapay.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.betterda.BtPay;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.JsActivity;
import com.betterda.betterdapay.activity.LoginActivity;
import com.betterda.betterdapay.activity.TransactionRecordActivity;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.data.BankData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BankCard;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.CreateOrderEntity;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.callback.BtPayCallBack;
import com.betterda.callback.BtResult;
import com.betterda.javabean.BtPayResult;
import com.betterda.javabean.PayCloudReqModel;
import com.betterda.mylibrary.ShapeLoadingDialog;

import java.util.List;

/**
 * 银行卡item的适配器
 * Created by Administrator on 2017/4/13.
 */

public class MyYinHangKaItemAdapter<T extends BankCard> extends DelegateAdapter.Adapter<MyYinHangKaItemAdapter.MainViewHolder> {


    private Activity mContext;
    private LayoutHelper mLayoutHelper;
    private List<BankCard> data;
    private boolean isClick,isPay;//是否可以点击,是否是支付
    private int money;
    private String rankName,rank;
    private String channelId;
    private String typeCode;
    private ShapeLoadingDialog dialog;
    private String paymentType = TransactionRecordActivity.PAYTMENT_GET ;//交易类型

    public MyYinHangKaItemAdapter(Activity mContext, LayoutHelper mLayoutHelper, List<BankCard> data , boolean isClick,boolean isPay, int money,String rankName,String rank,String channelId,String typeCode) {
        this.mLayoutHelper = mLayoutHelper;
        this.data = data;
        this.mContext = mContext;
        this.isClick = isClick;
        this.money = money;
        this.isPay = isPay;
        this.rankName = rankName;
        this.rank = rank;
        this.channelId = channelId;
        this.typeCode = typeCode;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycleview_yinhangka, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        if (holder != null && position < data.size()) {
            final BankCard bankCard = data.get(position);
            holder.mTvName.setText(bankCard.getBankName());
            holder.mTvNumber.setText( UtilMethod.transforBankNumber(bankCard.getBankCard()));
            try {
                holder.mIvIcon.setImageResource(BankData.getBank(bankCard.getBankName()));
            } catch (Exception e) {

            }
            int size = position % 3;
            if (size == 0) {
                holder.mLinearAdd.setBackgroundResource(R.drawable.shape_corner_bankblue);
            } else if (size == 1) {
                holder.mLinearAdd.setBackgroundResource(R.drawable.shape_corner_bankgreed);
            } else if (size == 2) {
                holder.mLinearAdd.setBackgroundResource(R.drawable.shape_corner_bankred);
            }

            if (isClick) {
                holder.mLinearAdd.setOnClickListener(v -> {
                    if (isPay) {
                        //付款
                        paymentType = TransactionRecordActivity.PAYTMENT_PAY;
                        getDataForUnionMobilePay(bankCard.getBankCard());
                    } else {
                        //收款
                        if (Constants.UNION_CONTROL_T1.equals(typeCode) || Constants.UNION_CONTROL_D0.equals(typeCode)) {
                            paymentType = TransactionRecordActivity.PAYTMENT_GET;
                            rank = null;
                            getDataForUnionMobilePay(bankCard.getBankCard());
                        } else {
                            Intent intent = new Intent(mContext, JsActivity.class);
                            intent.putExtra("money", money);
                            intent.putExtra("paybankcard", bankCard.getBankCard());
                            intent.putExtra("channelId", channelId);
                            intent.putExtra("typeCode", typeCode);
                            mContext.startActivity(intent);
                            mContext.finish();
                        }



                    }

                });
            }

            holder.mTvDelete.setOnClickListener(v -> showDeleteDialog(bankCard.getId()));

        }
    }


    /**
     * 获取银联手机控件支付的tn
     */
    public void getDataForUnionMobilePay(final String cardNum) {
        NetworkUtils.isNetWork(mContext, null, () -> {
            if (dialog == null) {
                dialog = UtilMethod.createDialog(mContext, "正在加载...");
            }
            UtilMethod.showDialog(mContext, dialog);


            NetWork.getNetService()
                    .getOrder(UtilMethod.getAccout(mContext),  money+"",cardNum,channelId,rank, paymentType, mContext.getString(R.string.appCode))
                    .compose(NetWork.handleResult(new BaseCallModel<>()))
                    .subscribe(new MyObserver<String>() {
                        @Override
                        protected void onSuccess(String data1, String resultMsg) {
                            if (BuildConfig.LOG_DEBUG) {
                                System.out.println("手机支付控件:" + data1);
                            }
                            if (data1 != null) {
                                unionMobilePay(data1);
                            } else {
                                UtilMethod.dissmissDialog(mContext, dialog);
                                UtilMethod.Toast(mContext,"获取tn失败");
                            }
                        }

                        @Override
                        public void onFail(String resultMsg) {
                            UtilMethod.Toast(mContext,"获取tn失败");
                            UtilMethod.dissmissDialog(mContext, dialog);
                        }

                        @Override
                        public void onExit(String resultMsg) {
                            //直接回到登录界面
                            startToLogin(resultMsg);
                        }
                    });
        });
    }

    /**
     * 银联手机控件支付
     */
    public void unionMobilePay(String tn) {

        BtPay.getInstance(mContext).requestPayAndTn(tn,result -> {
            UtilMethod.dissmissDialog(mContext, dialog);
            BtPayResult payResult = (BtPayResult) result;
            if (payResult != null) {
                if (BtPayResult.RESULT_SUCCESS.equals(payResult.getResult())) {
                    CacheUtils.putString(mContext, UtilMethod.getAccout(mContext) + Constants.Cache.RANK, rank);
                    mContext.finish();
                } else if (BtPayResult.RESULT_CANCEL.equals(payResult.getResult())) {
                    UtilMethod.Toast(mContext,"取消支付");
                } else  {
                    UtilMethod.Toast(mContext,"支付失败");
                }
            }
            BtPay.clear();
        });

    }



    /**
     * 显示删除确认对话框
     * @param id
     */
    private void showDeleteDialog(final String id) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_notice, null);
        TextView mTvCancel = (TextView) view.findViewById(R.id.tv_update_cancel);
        TextView mTvContent = (TextView) view.findViewById(R.id.tv_notice_content);
        TextView mTvComfirm = (TextView) view.findViewById(R.id.tv_update_comfirm);
        mTvCancel.setText("取消");
        mTvComfirm.setText("确定");
        mTvContent.setText("确定要删除该银行卡吗?");
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final AlertDialog alertDialog = builder.setCancelable(false).setView(view).create();
        UtilMethod.showDialog( mContext, alertDialog);

        mTvCancel.setOnClickListener(v -> UtilMethod.dissmissDialog( mContext, alertDialog));

        mTvComfirm.setOnClickListener(v -> {
            UtilMethod.dissmissDialog( mContext, alertDialog);
            deleteBankCard(id);
        });



    }

    private void deleteBankCard(final String id) {
        NetworkUtils.isNetWork(mContext, null, () -> {
            if (dialog == null) {
                dialog = UtilMethod.createDialog(mContext, "正在加载...");
            }
            //开启进度显示
            UtilMethod.showDialog( mContext,dialog);
            if (data != null) {
                NetWork.getNetService()
                        .getBandDelete(id,mContext.getString(R.string.appCode))
                        .compose(NetWork.handleResult(new BaseCallModel<>()))
                        .subscribe(new MyObserver<List<BankCard>>() {
                            @Override
                            protected void onSuccess(List<BankCard> list, String resultMsg) {

                                for (BankCard bankCard : data) {
                                    if (bankCard.getId().equals(id)) {
                                        data.remove(bankCard);
                                        break;
                                    }
                                }
                                notifyDataSetChanged();
                                //取消进度显示
                                UtilMethod.dissmissDialog( mContext, dialog);
                                UtilMethod.Toast(mContext,resultMsg);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                UtilMethod.dissmissDialog( mContext, dialog);
                                UtilMethod.Toast(mContext,resultMsg);

                            }

                            @Override
                            public void onExit(String resultMsg) {
                                startToLogin(resultMsg);
                            }
                        });

            }

        });
    }

    public void startToLogin(String resultMsg) {
        UtilMethod.Toast(mContext,resultMsg);
        UtilMethod.dissmissDialog(mContext, dialog);
        Intent intent = new Intent();
        intent.setClass(mContext, LoginActivity.class);
        //添加清除任务栈中所有activity的log,如果要启动的activity不在任务栈中了,还需要添加FLAG_ACTIVITY_NEW_TASK,才会关闭任务栈中的其他activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
        mContext.finish();
    }

    @Override
    public int getItemCount() {
        if (null != data) {
            return data.size();
        } else {
            return 0;
        }
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearAdd;
        ImageView mIvIcon;
        TextView mTvName;
        TextView mTvDelete;
        TextView mTvNumber;
        public MainViewHolder(View itemView) {
            super(itemView);
            mLinearAdd = (LinearLayout) itemView.findViewById(R.id.linear_yinhangka);
            mIvIcon = (ImageView) itemView.findViewById(R.id.iv_yinhangka_icon);
            mTvName = (TextView) itemView.findViewById(R.id.tv_yinhangka_name);
            mTvDelete = (TextView) itemView.findViewById(R.id.tv_yinhangka_delete);
            mTvNumber = (TextView) itemView.findViewById(R.id.tv_yinhangka_number);

        }
    }
}

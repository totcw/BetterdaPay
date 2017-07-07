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
    public static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKqQ8mG2VN2rRi5pF4drOi9pB2kdIAiO6YR7LQGDWQkP2DkAI19apajGxDt3q1m2kmWdytX5dmI8AhxEgK+Ak+qoaf7qNv/6NRQUesnJ8kB7sACzEG79CNwxeZy0jLP2E0RC69r/vyyqcD5PwkIuaMNc5KIJhapl0pPmsMZ+F85QIDAQAB";
    public static final String APP_ID = "47cb95e8badd4521b3bd17da1516d5db";
    public static final String SERVICE_URL = "http://www.yuanxiangrui.cn/paycloud-openapi/api/unionpay/app/ctrl/getform/%s/%s";


    private Activity mContext;
    private LayoutHelper mLayoutHelper;
    private List<BankCard> data;
    private boolean isClick,isPay;
    private int money;
    private String rankId,rank;
    private ShapeLoadingDialog dialog;
    public MyYinHangKaItemAdapter(Activity mContext, LayoutHelper mLayoutHelper, List<BankCard> data , boolean isClick,boolean isPay, int money,String rankId,String rank) {
        this.mLayoutHelper = mLayoutHelper;
        this.data = data;
        this.mContext = mContext;
        this.isClick = isClick;
        this.money = money;
        this.isPay = isPay;
        this.rankId = rankId;
        this.rank = rank;
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
            holder.mTvName.setText(bankCard.getBank());
            holder.mTvNumber.setText("信用卡("+ UtilMethod.transforBankNumber(bankCard.getCardNum())+")");
            holder.mIvIcon.setImageResource(BankData.getBank(bankCard.getBank()));
            if (isClick) {
                holder.mLinearAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isPay) {
                            getDataForUnionMobilePay(bankCard.getCardNum());
                        } else {
                            Intent intent = new Intent(mContext, JsActivity.class);
                            intent.putExtra("money", money);
                            intent.putExtra("bankCard", bankCard.getCardNum());
                            mContext.startActivity(intent);
                            mContext.finish();
                        }

                    }
                });
            }

            holder.mLinearAdd.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showDeleteDialog(bankCard.getId());
                    return true;
                }
            });
        }
    }


    /**
     * 获取银联手机控件支付的订单号
     */
    public void getDataForUnionMobilePay(final String cardNum) {
        NetworkUtils.isNetWork(mContext, null, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                if (dialog == null) {
                    dialog = UtilMethod.createDialog(mContext, "正在加载...");
                }
                UtilMethod.showDialog(mContext, dialog);
                NetWork.getNetService()
                        .getOrder(UtilMethod.getAccout(mContext),  money+"", rankId, "升级付款")
                        .compose(NetWork.handleResult(new BaseCallModel<CreateOrderEntity>()))
                        .subscribe(new MyObserver<CreateOrderEntity>() {
                            @Override
                            protected void onSuccess(CreateOrderEntity data, String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("手机支付控件:" + data);
                                }
                                if (data != null) {
                                    unionMobilePay(data,cardNum);
                                } else {
                                    UtilMethod.dissmissDialog(mContext, dialog);
                                    UtilMethod.Toast(mContext,"获取支付订单失败");
                                }
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                UtilMethod.Toast(mContext,"获取支付订单失败");
                                UtilMethod.dissmissDialog(mContext, dialog);
                            }

                            @Override
                            public void onExit() {
                                UtilMethod.dissmissDialog(mContext, dialog);
                            }
                        });
            }
        });
    }

    /**
     * 银联手机控件支付
     */
    public void unionMobilePay(CreateOrderEntity data,String cardNum) {

        PayCloudReqModel payCloudReqModel = new PayCloudReqModel();
        payCloudReqModel.setAppid(APP_ID);
        payCloudReqModel.setPublicKey(PUB_KEY);
        payCloudReqModel.setBackUrl(data.getNotifyUrl());
        payCloudReqModel.setOrderId(data.getOrderId());
        payCloudReqModel.setTxnTime(data.getTxnTime());
        payCloudReqModel.setAccNo(cardNum);
        payCloudReqModel.setTxnAmt(money+"");
        payCloudReqModel.setServiceUrl(SERVICE_URL);
        BtPay.getInstance(mContext).requestPay(payCloudReqModel, new BtPayCallBack() {
            @Override
            public void done(BtResult result) {
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
            }
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

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilMethod.dissmissDialog( mContext, alertDialog);
            }
        });

        mTvComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilMethod.dissmissDialog( mContext, alertDialog);
                deleteBankCard(id);
            }
        });



    }

    private void deleteBankCard(final String id) {
        NetworkUtils.isNetWork(mContext, null, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                if (dialog == null) {
                    dialog = UtilMethod.createDialog(mContext, "正在加载...");
                }
                //开启进度显示
                UtilMethod.showDialog( mContext,dialog);
                if (data != null) {
                    NetWork.getNetService()
                            .getBandDelete(UtilMethod.getAccout(mContext),id)
                            .compose(NetWork.handleResult(new BaseCallModel<List<BankCard>>()))
                            .subscribe(new MyObserver<List<BankCard>>() {
                                @Override
                                protected void onSuccess(List<BankCard> list, String resultMsg) {

                                    data.clear();
                                    data.addAll(list);
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
                                public void onExit() {

                                }
                            });

                }

            }
        });
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
        TextView mTvNumber;
        public MainViewHolder(View itemView) {
            super(itemView);
            mLinearAdd = (LinearLayout) itemView.findViewById(R.id.linear_yinhangka);
            mIvIcon = (ImageView) itemView.findViewById(R.id.iv_yinhangka_icon);
            mTvName = (TextView) itemView.findViewById(R.id.tv_yinhangka_name);
            mTvNumber = (TextView) itemView.findViewById(R.id.tv_yinhangka_number);

        }
    }
}

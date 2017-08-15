/*
package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.data.RateData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.Income;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.RecyclerViewStateUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.EndlessRecyclerOnScrollListener;
import com.betterda.betterdapay.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.wheel.widget.ChangeBirthDialog;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

*/
/**
 * 搜索接口
 * Created by Administrator on 2016/8/16.
 *//*

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.topbar_search)
    NormalTopBar topbarSearch;
    @BindView(R.id.tv_search_starttime)
    TextView tvSearchStarttime;
    @BindView(R.id.linear_search_starttime)
    LinearLayout linearSearchStarttime;
    @BindView(R.id.tv_search_endtime)
    TextView tvSearchEndtime;
    @BindView(R.id.linear_search_endtime)
    LinearLayout linearSearchEndtime;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.loadpager_search)
    LoadingPager loadingPager;

    private String startTime, endTime;
    private String item;//区分收款,返还和推广分润
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<Order> orderList, orderListDetail;
    private List<Income> fenRunList, fenRunListDetail;

    private final String SHOU_KUAN = "1"; //1表示收款
    private final String FANHUI_FENRUN = "2"; //2表示返还分润
    private final String TUIGUANG_FENRUN = "3"; //3表示推广分润
    private int page = 1;
    private String account;
    private String token;
    private String orderType;//订单类型  0 收款 1 分润 2 提现
    private String profitType; //分润类型 0返还 1推广

    @Override
    public void initView() {
        setContentView(R.layout.activity_search);
    }

    @Override
    public void init() {
        setTopBar();
        account = UtilMethod.getAccout(getmActivity());
        token = UtilMethod.getToken(getmActivity());
        getIntentData();
        setRecycleview();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            item = intent.getStringExtra("item");
        }
    }

    private void setTopBar() {
        topbarSearch.setTitle("搜索");
        topbarSearch.setActionText("确定");
    }

    private void setRecycleview() {
        if (SHOU_KUAN.equals(item)) {//如果是收款,就初始化收款
            orderList = new ArrayList<>();
            orderListDetail = new ArrayList<>();
            adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Order>(getmActivity(), R.layout.item_recycleview_balance, orderList) {

                @Override
                public void convert(ViewHolder viewHolder, Order order) {
                    if (order != null) {
                        viewHolder.setText(R.id.tv_item_balance_orderNum, order.getOrderId());
                        viewHolder.setText(R.id.tv_item_balance_time, order.getOrderTime());
                        viewHolder.setText(R.id.tv_item_balance_type, order.getChannel());
                        viewHolder.setText(R.id.tv_item_balance_money, order.getDrawCash() + "元");
                        viewHolder.setText(R.id.tv_item_balance_money2, order.getBalance() + "元");
                        setPaymentType(viewHolder, order);
                    }
                }
            });
        } else {//否则初始化为分润的
            fenRunList = new ArrayList<>();
            fenRunListDetail = new ArrayList<>();
            adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Income>(getmActivity(), R.layout.item_recycleview_fenrun2, fenRunList) {

                @Override
                public void convert(ViewHolder viewHolder, Income fenRun) {
                    if (fenRun != null) {
                        viewHolder.setText(R.id.tv_item_fenru2_account, fenRun.getSourceAccount());
                        viewHolder.setText(R.id.tv_item_fenru2_rate, fenRun.getRankName());
                        viewHolder.setText(R.id.tv_item_fenru2_name, fenRun.getName());
                        viewHolder.setText(R.id.tv_item_fenru2_money, fenRun.getDrawCash());
                        viewHolder.setText(R.id.tv_item_fenru2_time, fenRun.getIncomeTimeStr());
                        viewHolder.setImageResource(R.id.iv_item_fenrun2, RateData.getRankName(fenRun.getRankName()));

                        switch (fenRun.getPaymentType()) {
                            case Constants.FEN_RUN_HUI:
                                viewHolder.setText(R.id.tv_item_fenru2_type, Constants.FEN_RUN_HUI2);
                                break;
                            case Constants.FEN_RUN_TUI:
                                viewHolder.setText(R.id.tv_item_fenru2_type, Constants.FEN_RUN_TUI2);
                                break;
                        }
                    }
                }
            });
        }
        rvSearch.setAdapter(adapter);
        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        rvSearch.addOnScrollListener(new EndlessRecyclerOnScrollListener(getmActivity()) {
            @Override
            public void onLoadNextPage(View view) {
                RecyclerViewStateUtils.next(getmActivity(), rvSearch, new RecyclerViewStateUtils.nextListener() {
                    @Override
                    public void load() {
                        if (SHOU_KUAN.equals(item)) {
                            //getData();
                        } else {
                            getData2();
                        }
                    }
                });
            }

            @Override
            public void show(boolean isShow) {
                if (SHOU_KUAN.equals(item)) {

                    RecyclerViewStateUtils.show(isShow, orderListDetail, rvSearch, getmActivity());
                } else {
                    RecyclerViewStateUtils.show(isShow, fenRunListDetail, rvSearch, getmActivity());
                }
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        topbarSearch.setActionTextVisibility(true);
        topbarSearch.setOnActionListener(this);
        topbarSearch.setOnBackListener(this);
    }

    @OnClick({R.id.linear_search_starttime, R.id.linear_search_endtime})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_search_starttime:
                getStartTime(true);
                break;
            case R.id.linear_search_endtime:
                getStartTime(false);
                break;
            case R.id.bar_back:
                back();
                break;
            case R.id.bar_action:
                comfirm();
                break;
        }
    }

    */
/**
     * 确定搜索
     *//*

    private void comfirm() {
        switch (item) {
            case SHOU_KUAN:
                orderType = "0";
                profitType = null;
                page = 1;
                //getData();
                break;
            case FANHUI_FENRUN:
                orderType = "1";
                profitType = "0";
                page = 1;
                getData2();
                break;
            case TUIGUANG_FENRUN:
                orderType = "1";
                profitType = "1";
                page = 1;
                getData2();
                break;
        }
    }


    */
/**
     * 分润的请求
     *//*

    private void getData2() {
        loadingPager.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), loadingPager, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                 NetWork.getNetService()
                        .getSearch2(account, token, startTime, endTime, orderType, profitType, page + "", Constants.PAGE_SIZE + "")
                        .compose(NetWork.handleResult(new BaseCallModel<List<Income>>()))
                        .subscribe(new MyObserver<List<Income>>() {
                            @Override
                            protected void onSuccess(List<Income> data, String resultMsg) {
                                if (data != null) {
                                    parserFenrun(data);
                                }
                                UtilMethod.judgeData(fenRunList, loadingPager);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                loadingPager.setErrorVisable();
                            }

                            @Override
                            public void onExit() {
                                ExitToLogin();
                            }
                        });
            }
        });

    }

    private void parserFenrun(List<Income> data) {
        fenRunListDetail = data;

        if (page == 1) {
            fenRunList.clear();
        }
        for (Income order : fenRunListDetail) {
            fenRunList.add(order);
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    */
/**
     * 收款的请求
     *//*

  */
/*  private void getData() {
        loadingPager.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), loadingPager, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                 NetWork.getNetService()
                        .getSearch(account, token, startTime, endTime, orderType, profitType, page + "", Constants.PAGE_SIZE + "")
                        .compose(NetWork.handleResult(new BaseCallModel<OrderALL>()))
                        .subscribe(new MyObserver<OrderALL>() {
                            @Override
                            protected void onSuccess(OrderALL data, String resultMsg) {
                                if (data != null) {
                                    parserShouKuan(data);
                                }
                                UtilMethod.judgeData(orderList, loadingPager);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    log(resultMsg);
                                }
                                loadingPager.setErrorVisable();
                            }

                            @Override
                            public void onExit() {
                                ExitToLogin();
                            }
                        });
            }
        });

    }

    *//*
*/
/**
     * 解析收款
     *
     * @param data
     *//*
*/
/*
    private void parserShouKuan(OrderALL data) {
        data.getHeapCollection();
        orderListDetail = data.getListOrder();

        if (page == 1) {
            orderList.clear();
        }
        if (orderListDetail != null) {

            for (Order order : orderListDetail) {
                orderList.add(order);
            }
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }*//*


    */
/**
     * 获取时间
     *
     * @param isStart
     *//*

    private void getStartTime(final boolean isStart) {
        ChangeBirthDialog changeBirthDialog = new ChangeBirthDialog(getmActivity());
        changeBirthDialog.setDate(UtilMethod.getYear(), UtilMethod.getMonth(), UtilMethod.getDay());
        changeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {
            @Override
            public void onClick(String s, String s1, String s2) {
                if (isStart) {
                    tvSearchStarttime.setText(s + "-" + s1 + "-" + s2);
                    startTime = s + "-" + s1 + "-" + s2;
                } else {
                    tvSearchEndtime.setText(s + "-" + s1 + "-" + s2);
                    endTime = s + "-" + s1 + "-" + s2;
                }
            }
        });
        changeBirthDialog.show();
    }


    */
/**
     * 设置订单类型
     *
     * @param viewHolder
     * @param order
     *//*

    private void setPaymentType(ViewHolder viewHolder, Order order) {
        switch (order.getPaymentType()) {
            case Constants.SHOU_KUAN:
                viewHolder.setText(R.id.tv_item_balance_ordetype, "收款");
                break;
            case Constants.FEN_RUN:
                viewHolder.setText(R.id.tv_item_balance_ordetype, "分润");
                break;
            case Constants.TI_XIAN:
                viewHolder.setText(R.id.tv_item_balance_ordetype, "提现");
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orderList != null) {
            orderList.clear();
            orderList = null;
        }
        if (orderListDetail != null) {
            orderListDetail.clear();
            orderListDetail = null;
        }
        if (fenRunList != null) {
            fenRunList.clear();
            fenRunList = null;
        }
        if (fenRunListDetail != null) {
            fenRunListDetail.clear();
            fenRunListDetail = null;
        }
    }
}
*/

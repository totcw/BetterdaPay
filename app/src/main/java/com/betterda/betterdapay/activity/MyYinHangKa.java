package com.betterda.betterdapay.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.adapter.MyYinHangKaAddAdapter;
import com.betterda.betterdapay.adapter.MyYinHangKaItemAdapter;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.data.BankData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BankCard;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.Order;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.RecyclerViewStateUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.EndlessRecyclerOnScrollListener;
import com.betterda.betterdapay.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.betterdapay.view.LoadingFooter;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.ShapeLoadingDialog;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的银行卡
 * Created by Administrator on 2016/8/16.
 */
public class MyYinHangKa extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.topbar_myyinhangka)
    NormalTopBar topbarMyyinhangka;
    @BindView(R.id.rv_layout)
    RecyclerView rvLayout;
    @BindView(R.id.loadpager_layout)
    LoadingPager loadpagerLayout;

    private MyYinHangKaItemAdapter<BankCard> mItemAdapter;
    private List<BankCard> list, bankCardList;
    private ShapeLoadingDialog dialog;
    private int page = 1;
    private int position;
    @Override
    public void initView() {
        setContentView(R.layout.activity_myyinhangka);
    }

    @Override
    public void init() {
        setTopBar();
        setRecycleview();

    }

    @Override
    protected void onStart() {
        super.onStart();
       // getData();
    }

    /**
     * 获取银行卡信息
     */
    private void getData() {
        loadpagerLayout.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), loadpagerLayout, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                subscription = NetWork.getNetService(subscription)
                        .getBandGet(UtilMethod.getAccout(getmActivity()), UtilMethod.getToken(getmActivity()))
                        .compose(NetWork.handleResult(new BaseCallModel<List<BankCard>>()))
                        .subscribe(new MyObserver<List<BankCard>>() {
                            @Override
                            protected void onSuccess(List<BankCard> data, String resultMsg) {
                                if (data != null) {
                                    parser(data);
                                }
                                UtilMethod.judgeData(list,loadpagerLayout);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                loadpagerLayout.setErrorVisable();
                            }

                            @Override
                            public void onExit() {
                                ExitToLogin();
                            }
                        });
            }
        });
    }

    /**
     * 解析服务器返回的数据
     * @param data
     */
    private void parser(List<BankCard> data) {
        bankCardList = data;
        if (page == 1) {
            list.clear();
        }
        if (bankCardList != null) {

            for (BankCard order : bankCardList) {
                if (null != list) {
                    list.add(order);

                }
            }
        }
      /*  if (adapter != null) {
            adapter.notifyDataSetChanged();
        }*/
    }

    private void setRecycleview() {
        list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        bankCardList = new ArrayList<>();
        rvLayout.addOnScrollListener(new EndlessRecyclerOnScrollListener(getmActivity()) {
            @Override
            public void onLoadNextPage(View view) {
                //滑到了最后,如果是正常状态才开始加载
                if (LoadingFooter.State.Normal == RecyclerViewStateUtils.getFooterViewState(rvLayout)) {

                    RecyclerViewStateUtils.setFooterViewState(getmActivity(), rvLayout, LoadingFooter.State.Loading, null);

                    getData();
                }
            }

            @Override
            public void show(boolean isShow) {
                RecyclerViewStateUtils.show(isShow, bankCardList, rvLayout, getmActivity());
            }
        });


        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getmActivity());

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）：
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        rvLayout.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        rvLayout.setLayoutManager(virtualLayoutManager);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        rvLayout.setAdapter(delegateAdapter);

        mItemAdapter = new MyYinHangKaItemAdapter<>(getmActivity(),new LinearLayoutHelper(),list);
        delegateAdapter.addAdapter(mItemAdapter);

        delegateAdapter.addAdapter(new MyYinHangKaAddAdapter(getmActivity(),new LinearLayoutHelper(),1));

    }

    private void setTopBar() {
        topbarMyyinhangka.setTitle("我的银行卡");
        topbarMyyinhangka.setActionText("添加");
        topbarMyyinhangka.setActionTextVisibility(true);
    }

    @Override
    public void initListener() {
        super.initListener();
        topbarMyyinhangka.setOnActionListener(this);
        topbarMyyinhangka.setOnBackListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                back();
                break;
            case R.id.bar_action:
                UtilMethod.startIntent(getmActivity(), AddBankCardActivity.class);
                break;
        }
    }

    /**
     * 删除银行卡的确定按钮
     */
    @Override
    public void comfirmDialog() {
        super.comfirmDialog();
        NetworkUtils.isNetWork(getmActivity(), rvLayout, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                if (dialog == null) {
                    dialog = UtilMethod.createDialog(getmActivity(), "正在删除...");
                }
                //开启进度显示
                UtilMethod.showDialog(getmActivity(),dialog);
                if (list != null&&position<list.size()) {
                    //获取要删除对象
                    BankCard bankCard = list.get(position);
                    if (bankCard != null) {
                        subscription =  NetWork.getNetService(subscription)
                                .getBandDelete(UtilMethod.getAccout(getmActivity()),UtilMethod.getToken(getmActivity()),bankCard.getId())
                                .compose(NetWork.handleResult(new BaseCallModel<String>()))
                                .subscribe(new MyObserver<String>() {
                                    @Override
                                    protected void onSuccess(String data, String resultMsg) {
                                        //将对象从容器中删除
                                        if (list != null) {
                                            list.remove(position);
                                        }
                                        // TODO 更新适配器

                                        UtilMethod.judgeData(list,loadpagerLayout);
                                        //取消进度显示
                                        UtilMethod.dissmissDialog(getmActivity(), dialog);
                                        showToast(resultMsg);
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        UtilMethod.dissmissDialog(getmActivity(), dialog);
                                        showToast(resultMsg);
                                        if (BuildConfig.LOG_DEBUG) {
                                            log(resultMsg);
                                        }
                                    }

                                    @Override
                                    public void onExit() {
                                        UtilMethod.dissmissDialog(getmActivity(),dialog);
                                        ExitToLogin();
                                    }
                                });


                    }

                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BankData.bankMap != null) {
            BankData.bankMap.clear();
            BankData.bankMap = null;
        }
    }
}

package com.betterda.betterdapay.fragment;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.data.RateData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.Rating;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.RxBus;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.mylibrary.LoadingPager;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 员工等的基类
 * Created by Administrator on 2016/8/24.
 */
public abstract class BaseUpFragment extends BaseFragment {
    private static final String TAG = BaseUpFragment.class.getSimpleName();
    protected List<Rating.RateDetail> list;
    protected Observable<Object> observable;
    protected HeaderAndFooterRecyclerViewAdapter adapter;
    protected Subscription subscribe;
    protected List<Rating.RateDetail> rateDetail; //每次返回的数据
    protected boolean isCurrent; //当前fragment是否显示
    protected boolean isVisible; //是否用户可见该fragment,只有通过setcurrentitem才会为true,所以配合isCurrent一起使用
    protected int item; //表示viewpager 当前的item
    protected String rate; //当前等级


    @Override
    public void initData() {
        super.initData();

    }

    /**
     * 注册一个rxbus
     */
    public void regiseterRxBus(String name, final LoadingPager loadingPager) {
        observable = RxBus.get().register(name);
        subscribe = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        isCurrent = true;
                        show(item);
                        getData(loadingPager);
                    }
                });
    }


    /**
     * 更改当前等级和下一等级箭头
     */
    public void show(int item) {

        if (((UpFragment) getParentFragment()) != null) {
            if (((UpFragment) getParentFragment()).vpUp.getCurrentItem() == item) {

                if (item == 0) { //第一个页面隐藏左边的箭头
                    if (((UpFragment) getParentFragment()).ivUpBack != null) {
                        ((UpFragment) getParentFragment()).ivUpBack.setVisibility(View.GONE);
                    }
                    if (((UpFragment) getParentFragment()).tvUpUp != null) {
                        ((UpFragment) getParentFragment()).tvUpUp.setVisibility(View.GONE);
                    }
                } else {

                    if (((UpFragment) getParentFragment()).ivUpBack != null) {
                        ((UpFragment) getParentFragment()).ivUpBack.setVisibility(View.VISIBLE);
                    }
                    if (((UpFragment) getParentFragment()).tvUpUp != null) {
                        String rank = CacheUtils.getString(getmActivity(), UtilMethod.getAccout(getmActivity()) + Constants.Cache.RANK, "1");
                        int rate2 = RateData.getRateForRank(rank);

                        if (rate2 < item) {
                            ((UpFragment) getParentFragment()).tvUpUp.setVisibility(View.VISIBLE);
                        } else {
                            ((UpFragment) getParentFragment()).tvUpUp.setVisibility(View.GONE);
                        }
                    }
                }

                if (item == 4) {//最后一个页面时隐藏下一等级
                    if (((UpFragment) getParentFragment()).linearUpUp != null) {
                        ((UpFragment) getParentFragment()).linearUpUp.setVisibility(View.GONE);
                    }

                } else {
                    if (((UpFragment) getParentFragment()).linearUpUp != null) {
                        ((UpFragment) getParentFragment()).linearUpUp.setVisibility(View.VISIBLE);
                    }


                }


            }
        }
    }

    /**
     * 修改等级
     */
    public void edit(String condition, String rate, String nextRate, int item) {

        if (((UpFragment) getParentFragment()) != null && ((UpFragment) getParentFragment()).vpUp != null) {
            if (((UpFragment) getParentFragment()).vpUp.getCurrentItem() == item) {

                if (((UpFragment) getParentFragment()).tvUpRate != null) {
                    ((UpFragment) getParentFragment()).tvUpRate.setText(rate);
                }

                if (((UpFragment) getParentFragment()).tvUpNextrate != null) {
                    ((UpFragment) getParentFragment()).tvUpNextrate.setText(nextRate);
                }
                if (((UpFragment) getParentFragment()).tvUpCondition != null) {
                    ((UpFragment) getParentFragment()).tvUpCondition.setText(condition);
                }
            }
        }
    }

    /**
     * 设置recycleview
     */
    public void setRecycleview(final RecyclerView rvYuangong, final LoadingPager loadingPager) {
        list = new ArrayList<>();
        rateDetail = new ArrayList<>();
        rvYuangong.setLayoutManager(new LinearLayoutManager(getmActivity()));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Rating.RateDetail>(getmActivity(), R.layout.item_recycleview_up, list) {


            @Override
            public void convert(ViewHolder viewHolder, Rating.RateDetail rating) {
                if (rating != null) {
                    viewHolder.setText(R.id.tv_item_up_name, rating.getType());
                    viewHolder.setText(R.id.tv_item_up_rating, rating.getT1TradeRate());
                    viewHolder.setText(R.id.tv_item_up_rating2, rating.getT0TradeRate());
                    viewHolder.setText(R.id.tv_item_up_jiesuan, rating.getT1DrawFee());
                    viewHolder.setText(R.id.tv_item_up_jiesuan2, rating.getT0DrawFee());
                    viewHolder.setText(R.id.tv_item_up_edu, rating.getT1TradeQuota() + "," + rating.getT1DayQuota());
                    viewHolder.setText(R.id.tv_item_up_edu2, rating.getT0TradeQuota() + "," + rating.getT0DayQuota());
                    if (Constants.ZHIFUBAO.equals(rating.getType())) {
                        viewHolder.setImageResource(R.id.iv_item_up, R.mipmap.zhifubao);
                    } else if (Constants.WEIXIN.equals(rating.getType())) {
                        viewHolder.setImageResource(R.id.iv_item_up, R.mipmap.weixin);
                    } else {
                        viewHolder.setImageResource(R.id.iv_item_up, R.mipmap.yinlian);
                    }
                }


            }
        });
        rvYuangong.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int padding = UtilMethod.dip2px(getmActivity(), 8);
                int padding2 = UtilMethod.dip2px(getmActivity(), 4);
                outRect.set(padding,padding2,padding,padding2);
            }
        });
        rvYuangong.setAdapter(adapter);
    }

    /**
     * 从网络获取数据
     */
    public void getData(final LoadingPager loadingPager) {
        if (BuildConfig.LOG_DEBUG) {
            Log.i(TAG, "getdata");
        }

        loadingPager.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), loadingPager, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                mRxManager.add(
                        NetWork.getNetService().getRating()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new MyObserver<List<Rating>>() {
                                    @Override
                                    protected void onSuccess(List<Rating> data, String resultMsg) {

                                        if (data != null) {
                                            parser(data);

                                        }
                                        UtilMethod.isDataEmpty(loadingPager, list);
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG)
                                            Log.i(TAG, resultMsg);
                                        loadingPager.setErrorVisable();
                                    }

                                    @Override
                                    public void onExit(String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            Log.i(TAG, "exit");
                                        }
                                        loadingPager.hide();

                                        if (isCurrent || isVisible) {
                                            isCurrent = false;
                                            ExitToLogin(resultMsg);
                                        }

                                    }
                                })
                );
            }
        });


    }

    /**
     * 解析数据
     *
     * @param data
     */
    public void parser(List<Rating> data) {
        if (item >= 0 && item < data.size()) {
            Rating currentRating = data.get(item);
            rateDetail = currentRating.getRates();
            if (rateDetail != null) {
                if (list != null) {
                    list.clear();
                    for (Rating.RateDetail rating : rateDetail) {
                        list.add(rating);
                    }
                }

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            String condition = currentRating.getRemarks();
            String nextRate = currentRating.getNextRankName();
            String rate = currentRating.getRankName();
            if (TextUtils.isEmpty(condition)) {
                condition = "员工可以升级为店长,经理,总经理,老板,升级之后费率更低!";
            }

            edit(condition, rate, nextRate, item);
        }


    }
}

package com.betterda.betterdapay.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.javabean.Order;
import com.betterda.betterdapay.javabean.OrderALL;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.RecyclerViewStateUtils;
import com.betterda.betterdapay.view.EndlessRecyclerOnScrollListener;
import com.betterda.betterdapay.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.betterdapay.view.LoadingFooter;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 账单的基类
 * Created by Administrator on 2016/8/24.
 */
public abstract class BaseBalanceFragment extends BaseFragment {

    private HeaderAndFooterRecyclerViewAdapter adapter;
    public List<Order> list;
    public List<Order> listOrder;
    public String account;
    public String token;
    public int page = 1;

    @Override
    public void initData() {
        super.initData();
        getToken();
    }

    public void setRecycleview(final RecyclerView rvBalance) {
        list = new ArrayList<>();
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Order>(getmActivity(), R.layout.item_recycleview_balance, list) {

            @Override
            public void convert(ViewHolder viewHolder, Order order) {
                if (order != null) {
                    viewHolder.setText(R.id.tv_item_balance_orderNum, order.getOrderNum());
                    viewHolder.setText(R.id.tv_item_balance_time, order.getOrderTime());
                    viewHolder.setText(R.id.tv_item_balance_type, order.getChannel());
                    viewHolder.setText(R.id.tv_item_balance_money, order.getMoney() + "元");
                    viewHolder.setText(R.id.tv_item_balance_money2, order.getBalance() + "元");
                    setOrderType(viewHolder, order);
                }

            }
        });
        rvBalance.setLayoutManager(new LinearLayoutManager(getmActivity()));
        rvBalance.setAdapter(adapter);
        rvBalance.addOnScrollListener(new EndlessRecyclerOnScrollListener(getmActivity()) {
            @Override
            public void onLoadNextPage(View view) {
                //滑到了最后,如果是正常状态才开始加载
                if (LoadingFooter.State.Normal == RecyclerViewStateUtils.getFooterViewState(rvBalance)) {

                    RecyclerViewStateUtils.setFooterViewState(getmActivity(), rvBalance, LoadingFooter.State.Loading, null);

                    getData();
                }
            }

            @Override
            public void show(boolean isShow) {
                RecyclerViewStateUtils.show(isShow, listOrder, rvBalance, getmActivity());
            }
        });
    }

    public void getData() {

    }


    /**
     * 设置订单类型
     * @param viewHolder
     * @param order
     */
    private void setOrderType(ViewHolder viewHolder, Order order) {
        switch (order.getOrderType()) {
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


    /**
     * 解析数据
     */
    public void parser(OrderALL data, String resultMsg) {
        listOrder = data.getListOrder();
        if (page == 1) {
            list.clear();
        }
        if (listOrder != null) {

            for (Order order : listOrder) {
                if (null != list) {
                    list.add(order);
                }
            }
        }
        adapter.notifyDataSetChanged();

    }


    /**
     * 获取token
     */
    public void getToken() {
        account = CacheUtils.getString(getmActivity(), Constants.Cache.ACCOUNT, "");
        token = CacheUtils.getString(getmActivity(), account + Constants.Cache.TOKEN, "");
    }
}

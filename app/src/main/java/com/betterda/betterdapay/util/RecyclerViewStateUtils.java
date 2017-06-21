package com.betterda.betterdapay.util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.betterdapay.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.betterdapay.view.LoadingFooter;

import java.util.List;


/**
 * Created by cundong on 2015/11/9.
 *
 * 分页展示数据时，RecyclerView的FooterView State 操作工具类
 *
 * RecyclerView一共有几种State：Normal/Loading/Error/TheEnd
 */
public class RecyclerViewStateUtils {

    /**
     * 设置headerAndFooterAdapter的FooterView State
     *
     * @param instance      context
     * @param recyclerView  recyclerView
     * @param state         FooterView State
     * @param errorListener FooterView处于Error状态时的点击事件
     */
    public static void setFooterViewState(Activity instance, RecyclerView recyclerView, LoadingFooter.State state, View.OnClickListener errorListener) {

        setViewState(instance, recyclerView, state, errorListener,true);
    }
    public static void setFooterViewState(Activity instance, RecyclerView recyclerView, LoadingFooter.State state, View.OnClickListener errorListener,boolean isShow) {

        setViewState(instance, recyclerView, state, errorListener,isShow);
    }

    private static void setViewState(Activity instance, RecyclerView recyclerView, LoadingFooter.State state, View.OnClickListener errorListener,boolean isShow) {
        if(instance==null || instance.isFinishing()||recyclerView==null) {
            return;
        }

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();


        if (outerAdapter == null ) {
            return;
        }

        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;

        LoadingFooter footerView;

        //已经有footerView了
        if (headerAndFooterAdapter.getFooterViewsCount() > 0) {
            footerView = (LoadingFooter) headerAndFooterAdapter.getFooterView();

            footerView.setState(state,isShow);

            if (state == LoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }

        } else {
            //加载底部布局但是不显示
            footerView = new LoadingFooter(instance);
            footerView.setState(state,false);

            if (state == LoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }

            headerAndFooterAdapter.addFooterView(footerView);

        }
    }

    /**
     * 获取当前RecyclerView.FooterView的状态
     *
     * @param recyclerView
     */
    public static LoadingFooter.State getFooterViewState(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            if (((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
                LoadingFooter footerView = (LoadingFooter) ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterView();
                return footerView.getState();
            }
        }

        return null;
    }





  /*
     *设置加载状态
     * @param list
     * @param rv_query
     * @param activity
     */
    public static   void setLoad(List list,RecyclerView rv_query,Activity activity) {


                if (list != null&&list.size() >= Constants.PAGE_SIZE) {

                    //加载完成就设置为正常
                    RecyclerViewStateUtils.setFooterViewState(activity,
                            rv_query, LoadingFooter.State.Normal, null);

                } else {

                    //返回的数据小于一页那就加载到底了
                    RecyclerViewStateUtils.setFooterViewState(activity,
                            rv_query, LoadingFooter.State.TheEnd, null);

                }



    }


    /**
     * 改变状态但是 不显示
     * @param recyclerView
     * @param state
     * @param errorListener
     */
    public static void change(Activity activity,  RecyclerView recyclerView,LoadingFooter.State state,View.OnClickListener errorListener) {

        if(recyclerView==null) {
            return;
        }

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();


        if (outerAdapter == null ) {
            return;
        }
        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;

        if (headerAndFooterAdapter.getFooterViewsCount() > 0) {
            LoadingFooter footerView = (LoadingFooter) headerAndFooterAdapter.getFooterView();
            footerView.setState(state, false);

            if (state == LoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }

        } else {

            RecyclerViewStateUtils.setFooterViewState(activity,
                    recyclerView, LoadingFooter.State.Normal, null);
        }
    }

    /**
     * 判断是否显示 footer
     * @param isShow  是否显示
     * @param list  服务返回的list
     * @param recyclerView
     * @param activity
     */
    public static   void show(boolean isShow,List list,RecyclerView recyclerView,Activity activity) {
        if (isShow) {
            RecyclerViewStateUtils.setLoad(list, recyclerView, activity);
        } else {
            RecyclerViewStateUtils.change(activity,recyclerView, LoadingFooter.State.TheEnd, null);
        }
    }

    /**
     * 加载
     */
    public static void next(Activity activity,RecyclerView recyclerView,nextListener listener) {
        //滑到了最后,如果是正常状态才开始加载
        if (LoadingFooter.State.Normal == RecyclerViewStateUtils.getFooterViewState(recyclerView)) {
            //设置为加载状态
            RecyclerViewStateUtils.setFooterViewState(activity, recyclerView, LoadingFooter.State.Loading, null);

            if (listener != null) {
                listener.load();
            }
        }
    }

    public interface nextListener{
        /**
         * 加载更多
         */
        void load();
    }

}

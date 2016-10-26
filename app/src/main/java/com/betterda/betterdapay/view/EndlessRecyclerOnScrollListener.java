package com.betterda.betterdapay.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.betterda.betterdapay.interfac.OnListLoadNextPageListener;
import com.betterda.betterdapay.util.RecyclerViewStateUtils;
import com.betterda.mylibrary.view.CountDown;

import javax.xml.parsers.FactoryConfigurationError;


/**
 * Created by cundong on 2015/10/9.
 * <p/>
 * 继承自RecyclerView.OnScrollListener，可以监听到是否滑动到页面最低部
 */
public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener implements OnListLoadNextPageListener {

    private Activity mContext;

    /**
     * 当前RecyclerView类型
     */
    protected LayoutManagerType layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;
    private int count;
    private int visableCount;


    public EndlessRecyclerOnScrollListener(Activity mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LayoutManagerType.LinearLayout;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LayoutManagerType.GridLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LayoutManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LinearLayout:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
               /* int firstCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
                int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();

                int vieable = lastCompletelyVisibleItemPosition - firstCompletelyVisibleItemPosition;
                count = ((LinearLayoutManager) layoutManager).getItemCount();
                System.out.println("vis:" + vieable + ",count:" + count);
                //判断当获取的数据超过一屏幕时候才显示已经到底了,

                if (vieable + 1 < count) {//当可见的数目小于总的数目时,表示超过一屏幕的个数,就显示
                    LoadingFooter.State footerViewState = RecyclerViewStateUtils.getFooterViewState(recyclerView);
                    if (footerViewState == null) {
                        footerViewState = LoadingFooter.State.TheEnd;

                    }
                    RecyclerViewStateUtils.changeviable(recyclerView, footerViewState, null);
                    System.out.println("--");
                } else {
                    System.out.println("++");
                    //当个数小于一个屏幕时,就不显示
                    RecyclerViewStateUtils.change(recyclerView, LoadingFooter.State.TheEnd, null);
                }*/



                break;
            case GridLayout:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }


    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();


        if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1)) {

            onLoadNextPage(recyclerView);
        }
        //判断当获取的数据超过一屏幕时候才显示已经到底了,
        int firstCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        int vieable = lastCompletelyVisibleItemPosition - firstCompletelyVisibleItemPosition;
        count = ((LinearLayoutManager) layoutManager).getItemCount();
        if (vieable + 1 < count) {//当可见的数目小于总的数目时,表示超过一屏幕的个数,就显示
            show(true);
        } else {
            //当个数小于一个屏幕时,就不显示
            show(false);
        }

    }

    /**
     * 取数组中最大值
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }


    @Override
    public void onLoadNextPage(View view) {

    }

    @Override
    public void show(boolean isShow) {

    }


    public static enum LayoutManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }


}

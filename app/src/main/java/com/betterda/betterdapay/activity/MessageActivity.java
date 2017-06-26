package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.Messages;
import com.betterda.betterdapay.receiver.JpushReceiver;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.RecyclerViewStateUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.EndlessRecyclerOnScrollListener;
import com.betterda.betterdapay.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 消息中心
 * Created by Administrator on 2017/4/10.
 */

public class MessageActivity extends BaseActivity {

    @BindView(R.id.topbar_meassage)
    NormalTopBar mTopbarMeassage;
    @BindView(R.id.rv_layout)
    RecyclerView mRvLayout;
    @BindView(R.id.loadpager_layout)
    LoadingPager mLoadpagerLayout;

    private HeaderAndFooterRecyclerViewAdapter mAdapter;
    private List<Messages> list,mMessagesList;
    private int page = 1;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_message);
    }

    @Override
    public void init() {
        super.init();
        mTopbarMeassage.setTitle("消息");
        list = new ArrayList<>();
        mRvLayout.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mAdapter =new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Messages>(getmActivity(), R.layout.item_rv_meassagecontent, list) {
            @Override
            public void convert(ViewHolder holder, Messages messages) {
                if (messages != null && holder != null) {
                    holder.setText(R.id.tv_item_meassagecontent_time, messages.getTime());
                    holder.setText(R.id.tv_item_meassagecontent_title, messages.getTitle());
                    holder.setText(R.id.tv_item_meassagecontent_content, messages.getContent());
                }
            }

        });

        mRvLayout.addOnScrollListener(new EndlessRecyclerOnScrollListener(getmActivity()) {
            @Override
            public void onLoadNextPage(View view) {
                RecyclerViewStateUtils.next(getmActivity(), mRvLayout, new RecyclerViewStateUtils.nextListener() {
                    @Override
                    public void load() {
                        page++;
                        getData();
                    }
                });

            }

            @Override
            public void show(boolean isShow) {
                //这里是要传当前服务器返回的list
                RecyclerViewStateUtils.show(isShow, mMessagesList, mRvLayout, getmActivity());
            }
        });

        mRvLayout.setAdapter(mAdapter);
        getData();
        mLoadpagerLayout.setonErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        //将新消息的标志设置为false
        mRxManager.post(JpushReceiver.class.getSimpleName(),false);
        String account = CacheUtils.getString(getmActivity(), Constants.Cache.ACCOUNT, "");
        CacheUtils.putBoolean(getmActivity(), account+Constants.Cache.MESSAGE, false);
    }


    @OnClick({R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                back();
                break;

        }
    }

    private void getData() {
        mLoadpagerLayout.setLoadVisable();
        NetworkUtils.isNetWork(this, mLoadpagerLayout, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                mRxManager.add(
                        NetWork.getNetService()
                                .getMessageList(UtilMethod.getAccout(getmActivity()), page + "", Constants.PAGE_SIZE + "")
                                .compose(NetWork.handleResult(new BaseCallModel<List<Messages>>()))
                                .subscribe(new MyObserver<List<Messages>>() {
                                    @Override
                                    protected void onSuccess(List<Messages> data, String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("获取消息列表:" + data);
                                        }
                                        if (data != null) {
                                            parserData(data);
                                        }
                                        UtilMethod.judgeData(list, mLoadpagerLayout);
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("获取消息列表fail:" + resultMsg);
                                        }
                                        if (mLoadpagerLayout != null) {
                                            mLoadpagerLayout.setErrorVisable();
                                        }
                                    }

                                    @Override
                                    public void onExit() {

                                    }
                                })
                );
            }
        });
    }

    private void parserData(List<Messages> data) {
        mMessagesList = data;
        if (list != null) {
            if (page == 1) {
                list.clear();
                list.addAll(data);
            }
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }

    }
}

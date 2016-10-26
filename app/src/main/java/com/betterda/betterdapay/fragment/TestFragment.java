package com.betterda.betterdapay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.betterda.betterdapay.R;
import com.betterda.mylibrary.LoadingPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/4.
 */
public class TestFragment extends BaseFragment {


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_choosepaytype, null);
    }

}

package com.betterda.betterdapay.modules;

import com.betterda.betterdapay.application.MyApplication;

import dagger.Module;

/**
 * Created by Administrator on 2016/8/16.
 */
@Module
public class AppModules {
    private final MyApplication application;

    public AppModules(MyApplication application) {
        this.application = application;
    }


}

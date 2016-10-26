package com.betterda.betterdapay.component;

import android.content.Context;

import com.betterda.betterdapay.application.MyApplication;
import com.betterda.betterdapay.modules.AppModules;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 生命周期跟Application一样的组件,可注入到自定义的Application类中，@Singletion代表各个注入对象为单例
 * Created by Administrator on 2016/8/16.
 */
@Singleton
@Component(modules = AppModules.class)
public interface AppComponent {

}

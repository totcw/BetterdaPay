package com.betterda.betterdapay.application;

import android.app.Activity;
import android.app.Application;

import com.betterda.betterdapay.component.AppComponent;
import com.betterda.betterdapay.component.DaggerAppComponent;
import com.betterda.betterdapay.modules.AppModules;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/28.
 */
public class MyApplication extends Application {

    private List<Activity> list ;



    private static  MyApplication instance ;



    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (null == list){
            list = new ArrayList<>();
        }

        //Fresco初始化
        Fresco.initialize(getApplicationContext());
        //捕获异常
       // CrashHandler.getInstance().init(getApplicationContext());

        inject();
    }

    /**
     * 注入dragger2
     */
    private void inject() {
        appComponent = DaggerAppComponent.builder()
                .appModules(new AppModules(this))
                .build();
    }

    /**
     * 将activity添加到容器中
     * @param activity
     */
    public  void addActivity(Activity activity){
        if (null != list){
            list.add(activity);
        }

    }

    /**
     * 退出程序
     */
    public  void exitProgress(){

        if(null != list) {

            for (Activity activity : list) {
                activity.finish();
            }
            //将容器清空
            list.clear();


        }


    }

    /**
     * 当activity销毁时调用该方法,防止内存泄漏
     * @param activity
     */
    public void removeAcitivty(Activity activity) {
        if (null != list && activity != null) {

            list.remove(activity);
        }
    }


    public static MyApplication getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}

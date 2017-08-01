package com.betterda.betterdapay.http;

import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.Constants;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 封装网络请求
 * Created by Administrator on 2016/7/29.
 */
public class NetWork {
    private static NetService netService; //封装了请求的接口
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    /**
     * 通过retrofit返回接口的实现类
     * @return
     */
    public static NetService getNetService() {

        if (netService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Url.URL)
                    .client(okHttpClient.newBuilder()//添加拦截器
                            .build())
                            .addConverterFactory(gsonConverterFactory)
                            .addCallAdapterFactory(rxJavaCallAdapterFactory)
                            .build();
            netService = retrofit.create(NetService.class);
        }

        return netService;
    }








    /**
     * 用Transformers 预处理observable,比如写好observeOn避免每次调用都要写这个 ,然后使用compose连接
     *这里多一个参数为了确定T
     */

    public static <T> Observable.Transformer<BaseCallModel<T>, BaseCallModel<T>> handleResult(BaseCallModel<T> baseCallModel) {
        return  new Observable.Transformer<BaseCallModel<T>,BaseCallModel<T>>(){

            @Override
            public Observable<BaseCallModel<T>> call(Observable<BaseCallModel<T>> baseCallModelObservable) {
                return  baseCallModelObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 取消订阅
     * @param subscription
     */
    public static void unsubscribe(Subscription subscription) {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
            subscription = null;
        }
    }
}

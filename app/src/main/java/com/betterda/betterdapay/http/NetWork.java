package com.betterda.betterdapay.http;

import android.util.Log;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.Constants;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    private static HashSet<String> cookies   = new HashSet<>();

    /**
     * 通过retrofit返回接口的实现类
     * @return
     */
    public static NetService getNetService() {

        if (netService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Url.URL)
                    .client(okHttpClient.newBuilder()
                            .addInterceptor(new AddCookiesInterceptor())//添加cookie的拦截器
                            .addInterceptor(new ReceivedCookiesInterceptor())//获取cookie的拦截器 ,这2个是当后台需要配置持有的session才需要
                            .build())
                            .addConverterFactory(gsonConverterFactory)
                            .addCallAdapterFactory(rxJavaCallAdapterFactory)
                            .build();
            netService = retrofit.create(NetService.class);
        }

        return netService;
    }

    /**
     * 获取cookie的拦截器
     */
    public static class ReceivedCookiesInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                }
            }
            return originalResponse;
        }
    }

    /**
     * 添加cookie
     */
    public static class AddCookiesInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            for (String cookie : cookies) {
                builder.addHeader("Cookie", cookie);
                if (BuildConfig.LOG_DEBUG) {
                    System.out.println("Header Cookie:"+cookie);
                }
            }

            return chain.proceed(builder.build());
        }
    }





    /**
     * 用Transformers 预处理observable,比如写好observeOn避免每次调用都要写这个 ,然后使用compose连接
     *这里多一个参数为了确定T
     */

    public static <T> Observable.Transformer<BaseCallModel<T>, BaseCallModel<T>> handleResult(BaseCallModel<T> baseCallModel) {
        return baseCallModelObservable -> baseCallModelObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }



}

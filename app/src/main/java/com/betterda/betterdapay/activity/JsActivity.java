package com.betterda.betterdapay.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.UtilMethod;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * html支付界面
 * Created by Administrator on 2016/5/30.
 */
/*一定要加,否则高版本调用不到*/
@SuppressLint("SetJavaScriptEnabled")
public class JsActivity extends BaseActivity {

    @BindView(R.id.index_progressBar)
    ProgressBar mIndexProgressBar;
    @BindView(R.id.slidedetails_behind)
    WebView webView;


    private int money;
    private String paybankcard;//银行卡号

    private String longitude;//经度
    private String latitude;//纬度
    private String province;//省
    private String city;//市
    private String area;//区
    private String street;//街道
    private String channelId;//通道id
    private String typeCode;//通道id
    private String mUrl = "http://www.baidu.com";


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_params);

    }


    @Override
    public void init() {
        super.init();
        getIntentData();
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); //允许加载javascript
        settings.setSupportZoom(true); //允许缩放
        settings.setBuiltInZoomControls(true); //原网页基础上缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        settings.setUseWideViewPort(true);  //任意比例缩放

        settings.setDomStorageEnabled(true);

        //AndroidWebView，这个是JS网页调用Android方法的一个类似ID的东西,和下面 js中调用的方法一样
        webView.addJavascriptInterface(new JsInterce(), "AndroidWebView");
        //显示进度
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (mIndexProgressBar != null) {
                    mIndexProgressBar.setProgress(newProgress);
                    if (newProgress >= 100) {
                        mIndexProgressBar.setVisibility(View.GONE);
                    }
                }

                //  super.onProgressChanged(view, newProgress);
            }

        });

        //设置Web视图
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 在APP内部打开链接，不要调用系统浏览器
                view.loadUrl(url);
                return true; //返回true表示强制使用当前webView 显示网页内容，而不跳转到默认浏览器
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                // 自定义错误提示页面，灰色背景色，带有文字，文字不要输汉字，由于编码问题汉字会变乱码
             /*   String errorHtml = "<html><body style='background-color:#e5e5e5;'><h1>Page Not Found " +
                        "!</h1></body></html>";
                view.loadData(errorHtml, "text/html", "UTF-8");*/
                if (BuildConfig.LOG_DEBUG) {
                    System.out.println("加载失败");
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //处理网页加载成功时
                if (BuildConfig.LOG_DEBUG) {
                    System.out.println("加载完成");
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            new Object() {
                public void setLoadWithOverviewMode(boolean overview) {
                    settings.setLoadWithOverviewMode(overview);
                }
            }.setLoadWithOverviewMode(true);
        }
        //使用默认缓存
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        getWindow().getDecorView().post(() -> {

            if (Constants.UNION_D0.equals(typeCode)) {
                mUrl = Constants.Url.URL + Constants.Url.URL_CHANNEL_D0;
            } else if (Constants.UNION_T1.equals(typeCode)) {
                mUrl = Constants.Url.URL + Constants.Url.URL_CHANNEL_T1;
            }

            String postDate = "account=" + UtilMethod.getAccout(getmActivity()) + "&txnAmt=" + money + "&accNo=" + paybankcard + "&appCode=" + getString(R.string.appCode) + "&channelId=" + channelId
                    + "&longitude=" + longitude + "&latitude=" + latitude + "&province=" + province + "&city=" + city + "&area=" + area + "&street=" + street;

            try {
                webView.postUrl(mUrl, postDate.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        });


    }

    public void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            money = intent.getIntExtra("money", 0);
            paybankcard = intent.getStringExtra("paybankcard");
            channelId = intent.getStringExtra("channelId");
            typeCode = intent.getStringExtra("typeCode");
        }
        longitude = CacheUtils.getString(getmActivity(), "longitude", longitude);
        latitude = CacheUtils.getString(getmActivity(), "latitude", latitude);
        province = CacheUtils.getString(getmActivity(), "province", province);
        city = CacheUtils.getString(getmActivity(), "city", city);
        area = CacheUtils.getString(getmActivity(), "area", area);
        street = CacheUtils.getString(getmActivity(), "street", street);
    }


    public class JsInterce {
        //在js中调用window.AndroidWebView.showInfoFromJs()，便会触发此方法。
        //支付成功
        @JavascriptInterface
        public void showInfoFromJs() {
            UtilMethod.startIntent(getmActivity(), HomeActivity.class);
            finish();
        }

    }

    //在java中调用js代码
    public void sendInfoToJs() {
        String msg = "在android中调用了js的方法";
        //调用js中的函数：showInfoFromJava(msg)
        webView.loadUrl("javascript:showInfoFromJava('" + msg + "')");
    }


    @Override
    public void onBackPressed() {
        finish();
    /*    if (webView.canGoBack()) {
            webView.goBack();
        } else {
            System.out.println("22");
            //看能不能调用 js的逻辑来判断当前页面是否是支付成功,然后返回到不同的界面
            UtilMethod.startIntent(getmActivity(), HomeActivity.class);
            finish();
        }*/

    }
}

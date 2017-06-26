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

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * html支付界面
 * Created by Administrator on 2016/5/30.
 */
@SuppressLint("SetJavaScriptEnabled")
public class JsActivity extends BaseActivity {

    @BindView(R.id.index_progressBar)
    ProgressBar mIndexProgressBar;
    @BindView(R.id.slidedetails_behind)
    WebView webView;

    private String orderId;
    private String url;
    private float money;


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_params);

    }


    @Override
    public void init() {
        super.init();
        Intent intent = getIntent();
        if (intent != null) {
            orderId = intent.getStringExtra("orderId");
            url = intent.getStringExtra("url");
            money = intent.getFloatExtra("money", 0);
        }
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); //允许加载javascript
        settings.setSupportZoom(true); //允许缩放
        settings.setBuiltInZoomControls(true); //原网页基础上缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        settings.setUseWideViewPort(true);  //任意比例缩放

        settings.setDomStorageEnabled(true);
        webView.addJavascriptInterface(new JsInterce(), "android");
        //显示进度
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                System.out.println("进度:"+newProgress);
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
                // setParams("dsaf454","100");
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

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(url)||!url.startsWith("http")) {
                    url = "http://www.baidu.com";
                }
                    webView.loadUrl(url);
            }
        });
    }



    public class JsInterce {
        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public String showInfoFromJs(String name) {
            System.out.println("js中调用了showInforFormJs方法" + name);
            return "调用";
        }

        @JavascriptInterface
        public void isMember(boolean isMember) {
            System.out.println("js中调用了showToast方法");

        }
    }

    //在java中调用js代码
    public void sendInfoToJs() {
        String msg = "在android中调用了js的方法";
        //调用js中的函数：showInfoFromJava(msg)
        webView.loadUrl("javascript:showInfoFromJava('" + msg + "')");
    }

    public void setParams(String orderId, String money) {
        webView.loadUrl(String.format("javascript:setParams(" + orderId + ", " + money + ")"));
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }

    }
}

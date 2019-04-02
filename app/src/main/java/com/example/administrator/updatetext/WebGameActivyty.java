package com.example.administrator.updatetext;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ab.util.AbToastUtil;

public class WebGameActivyty extends Activity {
    private WebView webGame;
    private ProgressBar pbWebLoad;
    //    private String url = "http://typhoon.zjwater.gov.cn/default.aspx";
    private String url = "http://bs6.sdtdtz.com/#/index/home";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_game);

        init();
    }

    private void init() {
        webGame = findViewById(R.id.wv_webGame);
        pbWebLoad = findViewById(R.id.pb_webloading);

        pbWebLoad.setDrawingCacheBackgroundColor(getResources().getColor(R.color.black));
        pbWebLoad.setDrawingCacheBackgroundColor(getResources().getColor(R.color.red));
        WebSettings webSettings = webGame.getSettings();//JavaScript支持
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        //WebView加载页面优先使用缓存加载
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webGame.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webGame.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    pbWebLoad.setVisibility(View.GONE);
                } else {
                    pbWebLoad.setVisibility(View.VISIBLE);
                    pbWebLoad.setProgress(newProgress);

                }
            }
        });
        webGame.loadUrl(url);
        pbWebLoad.setVisibility(View.GONE);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 自动生成的方法存根
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webGame.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                webGame.goBack();
                return true;
            } else {//当webview处于第一页面时,直接退出程序
                System.exit(0);
            }


        }
        return super.onKeyDown(keyCode, event);
    }


}
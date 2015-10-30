package com.example.ray.pubwebview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.ray.utils.PubUtil;


/**
 * Created by Luodianlei on 2015/10/29.
 */
public class PubWebView extends WebView {

    private WebSettings webSettings;//webView设置类
    private OnLoadListener listener;//监听接口
    private Context context;

    public PubWebView(Context context) {
        this(context, null);
    }

    public PubWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PubWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        webSettings =this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        this.setWebViewClient(new SubWebViewClient());
        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                listener.onH5ReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                listener.onH5ProgressChanged(view, newProgress);
            }
        });

    }

    /**
     * 设置webview是否调用本地缓存的方法
     * @param isCache  true 使用本地缓存, false 不使用本地缓存
     */
    public void setCache(boolean isCache) {
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(isCache);
        // 应用可以有数据库
        webSettings.setDatabaseEnabled(isCache);
        // 应用可以有缓存
        webSettings.setAppCacheEnabled(isCache);

        if(isCache){
            String appCaceDir = context.getDir("cache", Context.MODE_PRIVATE).getPath();
            webSettings.setAppCachePath(appCaceDir);

            String dbPath = context.getDir("database", Context.MODE_PRIVATE).getPath();
            webSettings.setDatabasePath(dbPath);

            //根据网路环境更改缓存模式
            if(PubUtil.getInstance().checkNetwork(context)){
                webSettings.setCacheMode(webSettings.LOAD_DEFAULT);
            }else {
                webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
            }
        }else{
            webSettings.setCacheMode(webSettings.LOAD_DEFAULT);
        }
    }

    /**
     * 给webview设置监听
     * @param listener 监听接口
     */
    public void setOnLoadH5Listener(OnLoadListener listener){
          this.listener = listener;
    }

    /**
     * 添加JS接口,实现与Js交互
     */
    @SuppressLint("JavascriptInterface")
    public void setJavasJavascriptInterface(Object object,String interfaceName){
        this.addJavascriptInterface(object,interfaceName);
    }

    /**
     * 自定义的WebViewClient类，将特殊链接从WebView打开，其他链接仍然用默认浏览器打开
     *
     * 在WebView中打开链接（默认行为是使用浏览器，设置此项后都用WebView打开）
     * 更强的打开链接控制：自己覆写一个WebViewClient类：除了指定链接从WebView打开，其他的链接默认打开
     */
    class SubWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                listener.showH5ViewChanged(view,url);
                return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            listener.onH5PageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            listener.onH5PageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

            listener.onH5ReceivedError(view, errorCode, description, failingUrl);
        }
    }

    /**
     * 回调的接口
     */
   public interface OnLoadListener {
       boolean showH5ViewChanged(WebView view, String url);

       boolean onH5PageStarted(WebView view, String url, Bitmap favicon);

       boolean onH5PageFinished(WebView view, String url);

       boolean onH5ReceivedError(WebView view, int errorCode, String description, String failingUrl);

       boolean onH5ReceivedTitle(WebView view, String title);

       boolean onH5ProgressChanged(WebView view, int newProgress);
   }
}






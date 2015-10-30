package com.example.ray.pubwebview;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * create by Ray 2015.10.30
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化webview
        PubWebView mPwb = (PubWebView) findViewById(R.id.webview);
        //加载网页
        mPwb.loadUrl("http://www.baidu.com");
        //支持H5缓存
        mPwb.setCache(true);
        //设置监听
        mPwb.setOnLoadH5Listener(new PubWebView.OnLoadListener() {
            @Override
            public boolean showH5ViewChanged(WebView view, String url) {
                //重定向
                return false;
            }

            @Override
            public boolean onH5PageStarted(WebView view, String url, Bitmap favicon) {
                //开始加载
                return false;
            }

            @Override
            public boolean onH5PageFinished(WebView view, String url) {
                //加载完成
                return false;
            }

            @Override
            public boolean onH5ReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //加载错误
                return false;
            }

            @Override
            public boolean onH5ReceivedTitle(WebView view, String title) {
                //H5返回标题
                return false;
            }

            @Override
            public boolean onH5ProgressChanged(WebView view, int newProgress) {
                //修改进度条
                return false;
            }
        });



    }


}

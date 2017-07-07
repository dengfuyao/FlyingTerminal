package com.flyingogo.flyingterminal.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：dfy on 5/7/2017 16:16
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public class Assistant_Location_Activity extends BaseActivity {
    @BindView(R.id.back)
    ImageView      mBack;
    @BindView(R.id.right)
    TextView       mRight;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.activity_title)
    RelativeLayout mActivityTitle;
    @BindView(R.id.webView)
    WebView        mWebView;

    private AMapLocationClient mClient = null;
    @Override
    protected int getResLayoutID() {

        return R.layout.activity_assistant_location;
    }

    @Override
    protected void onInitMap(Bundle savedInstanceState) {
        mClient = new AMapLocationClient(mContext);

    }

    @Override
    protected void onInit() {

        initWebView();

        if (mClient!=null){
            mClient.startAssistantLocation();
            mWebView.loadUrl(Utils.URL_H5LOCATION);
        }
    }

    private void initWebView(){
        WebSettings webSettings = mWebView.getSettings();
        // 允许webview执行javaScript脚本
        webSettings.setJavaScriptEnabled(true);
        // 设置是否允许定位，这里为了使用H5辅助定位，设置为false。
        //设置为true不一定会进行H5辅助定位，设置为true时只有H5定位失败后才会进行辅助定位
        webSettings.setGeolocationEnabled(false);


        mWebView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            // 处理javascript中的alert
            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result) {
                return true;
            };

            // 处理javascript中的confirm
            public boolean onJsConfirm(WebView view, String url,
                                       String message, final JsResult result) {
                return true;
            };
               // 处理定位权限请求
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
            @Override
            // 设置网页加载的进度条
            public void onProgressChanged(WebView view, int newProgress) {
                Assistant_Location_Activity.this.getWindow().setFeatureInt(
                        Window.FEATURE_PROGRESS, newProgress * 100);
                super.onProgressChanged(view, newProgress);
            }

            // 设置应用程序的标题title
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    @OnClick(R.id.right)
    public void onClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mClient) {
            mClient.stopAssistantLocation();
            mClient.onDestroy();
        }
        if(null != mWebView){
            mWebView.destroy();
        }
    }

}

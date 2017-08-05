package com.flyingogo.flyingterminal.activity;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ServiceGuide extends BaseActivity {

    @BindView(R.id.webView)
    WebView        mWebView;
    @BindView(R.id.right)
    TextView       mRight;
    @Override
    protected int getResLayoutID() {
        return R.layout.activity_service_guide;
    }

    @Override
    protected void onInit() {
        // http://www.nnggzxc.com/upload/vido/ridelybike.mp4
      //  http://180.141.89.241:8860/lyBikeSideMap.aspx
        String url = "http://180.141.89.241:8860/lyBikeSideMap.aspx";
        mWebView.loadUrl(url);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @OnClick(R.id.right)
    public void onClick() {
        finish();
    }
}

package org.bean.jandan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.bean.jandan.R;
import org.bean.jandan.common.C;

/**
 * Created by liuyulong@yixin.im on 2015/6/23.
 */
public class WebViewActivity extends BaseColorActivity {

    private String mUrl;

    private WebView mWebView;

    public static void start(Context context, String url, String title) {
        Intent starter = new Intent(context, WebViewActivity.class);
        starter.putExtra(C.Extra.KEY_URL, url);
        starter.putExtra(C.Extra.KEY_TITLE, title);
        if (!Activity.class.isInstance(context)) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!processIntent()) {
            finish();
            return;
        }
        load();
    }

    @Override
    protected void findViews() {
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient() {});
    }

    private boolean processIntent() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(C.Extra.KEY_URL);
        if (TextUtils.isEmpty(mUrl)) {
            return false;
        }

        String title = intent.getStringExtra(C.Extra.KEY_TITLE);
        setTitle(title);

        return true;
    }

    private void load() {
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

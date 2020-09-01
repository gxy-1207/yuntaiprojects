package com.ytfu.yuntaifawu.ui.qisuzhuang.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.utils.SpUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ActivityQszXinagqing extends BaseActivity {
    @BindView(R.id.iv_fanhui)
    ImageView ivFanhui;

    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;

    @BindView(R.id.qsz_web)
    LinearLayout qszWeb;

    @BindView(R.id.web_view)
    WebView webView;

    private static final String KEY_ID = "ID";
    private static final String KEY_LABLE = "LABLE";
    private static final String KEY_HEARF = "HEARF";
    private static final String KEY_LAWYERID = "LAWYERID";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_qsz_xingqing;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void init() {
        super.init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            //      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent_half));
        }
    }

    public static void start(
            Context context, String hearf, String id, String lawyerId, String lable) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ID, id);
        bundle.putString(KEY_LAWYERID, lawyerId);
        bundle.putString(KEY_LABLE, lable);
        bundle.putString(KEY_HEARF, hearf);
        Intent starter = new Intent(context, ActivityQszXinagqing.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        hideLoading();
        String lable = getBundleString(KEY_LABLE, "");
        tvTopTitle.setText(lable);
        initWebView();
    }

    @SuppressLint("WrongConstant")
    private void initWebView() {
        // 不跳转到其他浏览器
        webView.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Logger.e("will load url is : " + url);
                        view.loadUrl(url);
                        return true;
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                    }
                });
        // webview必须设置支持Javascript才可打开
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置此属性,可任意比例缩放
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.addJavascriptInterface(new JavascriptInterface(this), "android");
    }

    @Override
    protected void initData() {

        String uid = SpUtil.getString(mContext, AppConstant.UID, "");
        String href = getBundleString(KEY_HEARF, "");
        String id = getBundleString(KEY_ID, "");

        // type=%s&id,
        String param = String.format("uid=%s&xt=1&lsid=%s", uid, getBundleString(KEY_LAWYERID, ""));

        String url = href + "&" + param;
        Logger.e("url = " + url);

        webView.loadUrl(url);
    }

    @OnClick(R.id.iv_fanhui)
    public void onViewClicked() {
        onBackPressed();
        //        showRemind("退出后将清空所有内容是否退出");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != webView) {
            webView.destroy();
            webView = null;
        }
    }

    // 共h5调用的跳转方法
    public class JavascriptInterface {
        private Context mContext;

        public JavascriptInterface(Context mContext) {
            this.mContext = mContext;
        }

        @android.webkit.JavascriptInterface
        public void jumpActivity(String id) {
            Log.e("sid", "-------" + id);
            //            Intent intent = new Intent(ActivityQszXinagqing.this,
            // ActivityQszXqClassify.class);
            //            intent.putExtra("id", id);
            //            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            showRemind("退出后将清空所有内容是否退出");
        }

        //        super.onBackPressed();
    }

    //    @Override
    //    public boolean onKeyDown(int keyCode, KeyEvent event) {
    //        if (keyCode == KeyEvent.KEYCODE_BACK) {
    //            showRemind("退出后将清空所有内容是否退出");
    //            return true;
    //        }
    //        return super.onKeyDown(keyCode, event);
    //    }
}

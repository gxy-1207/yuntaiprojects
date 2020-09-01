package com.ytfu.yuntaifawu.ui.qisuzhuang.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.BuildConfig;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.kaitingzhushou.bean.SendEmailBean;
import com.ytfu.yuntaifawu.ui.mine.bean.BdEmailBean;
import com.ytfu.yuntaifawu.ui.qisuzhuang.presenter.QszYlXqPresenter;
import com.ytfu.yuntaifawu.ui.qisuzhuang.view.IQszYlXqView;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.JavaScripClass;
import com.ytfu.yuntaifawu.utils.LoginHelper;
import com.ytfu.yuntaifawu.utils.SnackbarUtils;
import com.ytfu.yuntaifawu.utils.SpUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/** @Auther gxy @Date 2019/12/17 @Des 起诉状预览详情 */
public class ActivityQszYlXq extends BaseActivity<IQszYlXqView, QszYlXqPresenter>
        implements IQszYlXqView {
    @BindView(R.id.iv_fanhui)
    ImageView ivFanhui;

    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;

    @BindView(R.id.qsz_web)
    LinearLayout qszWeb;

    @BindView(R.id.btn_email)
    Button btnEmail;

    @BindView(R.id.snackbar_ll)
    RelativeLayout snackbarLl;

    @BindView(R.id.web_view)
    WebView webView;
    //    private WebView webView;
    private String uid;
    private String id;

    private static final String KEY_URL = "URL";
    private static final String KEY_IS_NEW = "IS_NEW";
    private static final String KEY_SHOW_DOWNLOAD_BUTTON = "SHOW_DOWNLOAD_BUTTON";

    public static void start(Context context, String url, boolean showDownloadButton) {

        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_IS_NEW, true);
        bundle.putBoolean(KEY_SHOW_DOWNLOAD_BUTTON, showDownloadButton);
        bundle.putString(KEY_URL, url);
        Intent starter = new Intent(context, ActivityQszYlXq.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_qsz_xq_yl;
    }

    @Override
    protected QszYlXqPresenter createPresenter() {
        return new QszYlXqPresenter(this);
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

    @Override
    protected void initView() {
        hideLoading();
        uid = SpUtil.getString(mContext, AppConstant.UID, "");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        tvTopTitle.setText("起诉状");
        initWebView();

        int v;
        if (getBundleBoolean(KEY_SHOW_DOWNLOAD_BUTTON, false)) {
            if (LoginHelper.getInstance().isUserLogin()) {
                v = View.VISIBLE;
            } else {
                v = View.GONE;
            }
        } else {
            v = View.GONE;
        }
        findViewById(R.id.cv_complaint_list_add).setVisibility(v);
    }

    @SuppressLint("WrongConstant")
    private void initWebView() {
        //        webView = new WebView(getApplicationContext());
        //        LinearLayout.LayoutParams params = new
        // LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        // ViewGroup.LayoutParams.MATCH_PARENT);
        //        webView.setLayoutParams(params);
        //        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //        webView.setScrollBarStyle(View.AUTOFILL_TYPE_NONE);
        //        webView.setOverScrollMode(0);
        //        qszWeb.addView(webView);
        // 不跳转到其他浏览器
        webView.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Logger.e("url = " + url);
                        view.loadUrl(url);
                        // 其他情况则使用系统浏览器打开网址
                        return true;
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                    }
                });

        webView.setWebChromeClient(
                new WebChromeClient() {
                    @Override
                    public boolean onJsAlert(
                            WebView view, String url, String message, JsResult result) {
                        if (!BuildConfig.IS_DEBUG) {
                            return super.onJsAlert(view, url, message, result);
                        }
                        new XPopup.Builder(mContext)
                                .asConfirm(
                                        webView.getUrl() + "消息",
                                        message,
                                        "取消",
                                        "确定",
                                        new OnConfirmListener() {
                                            @Override
                                            public void onConfirm() {}
                                        },
                                        new OnCancelListener() {

                                            @Override
                                            public void onCancel() {}
                                        },
                                        true)
                                .show();

                        return true; // super.onJsAlert(view, url, message, result);
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
        webView.addJavascriptInterface(new JavaScripClass(this), "android");

        webView.setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void initData() {
        if (getBundleBoolean(KEY_IS_NEW, false)) {
            String url = getBundleString(KEY_URL, "");
            Logger.e("url = " + url);
            webView.loadUrl(url);
        } else {
            String url = "http://yuntaifawu.com/ssz/info?id=" + id + "&uid=" + uid;
            Logger.e("url = " + url);
            webView.loadUrl(url);
        }
        //        webView.evaluateJavascript("javascript:get_type()", new ValueCallback<String>() {
        //            @Override
        //            public void onReceiveValue(String value) {
        //                tvTopTitle.setText(value);
        //            }
        //        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.iv_fanhui, R.id.cv_complaint_list_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_fanhui:
                onBackPressed();
                break;
            case R.id.cv_complaint_list_add:
                webView.evaluateJavascript(
                        "javascript:download()",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                Log.e("value", "----" + value);
                                String url = value.substring(1, value.length() - 1);
                                if (TextUtils.isEmpty(value)) {
                                    showToast("文件地址为空");
                                } else {
                                    showWaitingDialog("发送中...", true);
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("uid", uid);
                                    map.put("url", url);
                                    getPresenter().setQszYlSendEmail(map);
                                }
                            }
                        });
                break;
            default:
                break;
        }
    }

    @Override
    public void onQszYlXqSendEmail(SendEmailBean sendEmailBean) {
        hideWaitingDialog();
        if (sendEmailBean != null) {
            int status = sendEmailBean.getStatus();
            switch (status) {
                case 200:
                case 201:
                    SnackbarUtils.showLongSnackbar(
                            snackbarLl,
                            sendEmailBean.getMsg(),
                            CommonUtil.getColor(R.color.textcolo_299),
                            CommonUtil.getColor(R.color.textcolor_26),
                            "确定",
                            CommonUtil.getColor(R.color.textcolo_299),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SnackbarUtils.dismissSnackbar();
                                }
                            });
                    break;
                case 202:
                    SnackbarUtils.showIndefiniteSnackbar(
                            snackbarLl,
                            sendEmailBean.getMsg(),
                            CommonUtil.getColor(R.color.textcolo_299),
                            CommonUtil.getColor(R.color.textcolor_26),
                            "确定",
                            CommonUtil.getColor(R.color.textcolo_299),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showAlertDialog();
                                }
                            });

                    break;
            }
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        View view = View.inflate(this, R.layout.view_alert_dialog_confirm_email, null);
        TextView tvMsg = view.findViewById(R.id.tv_message_dialog);
        TextView tvCancel = view.findViewById(R.id.tv_cancel_dialog);
        TextView tvConfirm = view.findViewById(R.id.tv_confirm_dialog);
        TextView tv_tishi = view.findViewById(R.id.tv_tishi);
        EditText ed_email = view.findViewById(R.id.ed_email);
        tvCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
        tvConfirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = ed_email.getText().toString().trim();
                        if (TextUtils.isEmpty(email)) {
                            tv_tishi.setVisibility(View.VISIBLE);
                            tv_tishi.setText("邮箱输入为空");
                        } else {
                            tv_tishi.setVisibility(View.GONE);
                            boolean contains = email.contains("@");
                            if (contains == true) {
                                showWaitingDialog("请稍后...", true);
                                HashMap<String, String> map = new HashMap<>();
                                map.put("uid", uid);
                                map.put("mail", email);
                                getPresenter().setQszYlBdEmail(map);
                                alertDialog.dismiss();
                            } else {
                                tv_tishi.setVisibility(View.VISIBLE);
                                tv_tishi.setText("邮箱格式不正确");
                            }
                        }
                    }
                });
        // 只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘

        // =======
        //                }
        //        }
        //        });
        //        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        // >>>>>>> gxy

        // =======
        //                }
        //        }
        //        });
        // 只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘

        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        Window window = alertDialog.getWindow();
        window.setContentView(view);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams attributes = window.getAttributes();
        // 设置宽度
        attributes.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
        attributes.gravity = Gravity.CENTER; // 设置位置
        window.setAttributes(attributes);
    }

    @Override
    public void onQszYlXqBdEmailSuccess(BdEmailBean bdEmailBean) {
        hideWaitingDialog();
        if (bdEmailBean != null) {
            int status = bdEmailBean.getStatus();
            switch (status) {
                case 200:
                case 201:
                case 202:
                    showToast(bdEmailBean.getMsg());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onFiled() {
        hideWaitingDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != webView) {
            webView.destroy();
            webView = null;
        }
    }
}

package com.ytfu.yuntaifawu.ui.lvshiwode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.Eyes;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Auther gxy
 * @Date 2020/5/28
 * @Des 隐私保障协议
 */
public class PrivacyGuaranteeAgreementActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.iv_fanhui)
    ImageView ivFanhui;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    private String url = "http://yuntaifawu.com/portal/index/yinsibaozhang";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_privacy_guarantee_agreement;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public static void startAt(Context context) {
        Intent intent = new Intent(context, PrivacyGuaranteeAgreementActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        super.init();
        Eyes.setStatusBarColor(this, CommonUtil.getColor(R.color.transparent_4c));
    }

    @Override
    protected void initView() {
        hideLoading();
        tvTopTitle.setText("隐私保障协议");
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.loadUrl(url);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.iv_fanhui)
    public void onViewClicked() {
        finish();
    }
}

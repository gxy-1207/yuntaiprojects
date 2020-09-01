package com.ytfu.yuntaifawu.base.x5;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import com.github.lee.annotation.InjectLayout;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.utils.view.OfficePreviewView;

import qiu.niorgai.StatusBarCompat;

@InjectLayout(value = R.layout.activity_preview_office, toolbarLayoutId = R.layout.layout_toolbar_center_title)
public class OfficePreviewActivity extends BaseActivity<BaseView, BasePresenter<BaseView>> {

    private static final String KEY_REMOTE_FILE_URL = "KEY_REMOTE_FILE_URL";


    public static void start(Context context, String remoteFileUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_REMOTE_FILE_URL, remoteFileUrl);
        Intent starter = new Intent(context, OfficePreviewActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }


    //===Desc:================================================================================

    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        changeStatusBarTextColor(true);
        setToolbarLeftImage(R.drawable.fanhui_hui, v -> onBackPressed());
        mToolbar.setBackgroundColor(Color.WHITE);
        setToolbarText(R.id.tv_global_title, "文件预览");
        setToolbarTextColor(R.id.tv_global_title, Color.parseColor("#333333"));

        String fileUrl = getBundleString(KEY_REMOTE_FILE_URL, "");
        if (TextUtils.isEmpty(fileUrl)) {
            showToast("应用程序出现错误，请稍后重试");
            finish();
            return;
        }
        OfficePreviewView opv_preview_content = findViewById(R.id.opv_preview_content);
//        String url = "https://www.adobe.com/content/dam/acom/en/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
        opv_preview_content.loadUrl(fileUrl);
//        opv_preview_content.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OfficePreviewView opv_preview_content = findViewById(R.id.opv_preview_content);
        opv_preview_content.onDestroy();
    }


    //===Desc:================================================================================

}

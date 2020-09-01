package com.ytfu.yuntaifawu.ui.lvshiwenti.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.github.lee.annotation.InjectLayout;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.lvshiwenti.fragment.LawyerCounselingDetailsFragment;

/**
 * @Auther gxy
 * @Date 2020/6/17
 * @Des 律师端咨询问题详情
 */
@InjectLayout(value = R.layout.activity_lawyer_counseline, toolbarLayoutId = R.layout.layout_toolbar_center_title)
public class LawyerCounselineDetailsActivity extends BaseActivity {
    private static final String KEY_CONSULTATION_ID = "CONSULTATION_ID";

    public static void start(Context context, String id) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CONSULTATION_ID, id);
        Intent starter = new Intent(context, LawyerCounselineDetailsActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected void initData() {
        super.initData();
        hideLoading();
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> {
            onBackPressed();
        });
        setToolbarText(R.id.tv_global_title, "咨询详情");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_counseline_count, LawyerCounselingDetailsFragment.newInstance(getBundleString(KEY_CONSULTATION_ID,"")))
                .commitAllowingStateLoss();
    }
}

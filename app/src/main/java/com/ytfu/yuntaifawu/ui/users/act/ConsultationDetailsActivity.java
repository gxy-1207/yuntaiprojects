package com.ytfu.yuntaifawu.ui.users.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.github.lee.annotation.InjectLayout;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.users.fragment.ConsultationDetailsRewardFragment;

/**
 * @Auther gxy
 * @Date 2020/6/15
 * @Des 咨询详情
 */
@InjectLayout(value = R.layout.activity_consultation_details, toolbarLayoutId = R.layout.layout_toolbar_center_title)
public class ConsultationDetailsActivity extends BaseActivity {
    private static final String KEY_CONSULTATION_ID = "CONSULTATION_ID";
    private static final String KEY_TYPE = "TYPE";
    public static void start(Context context,String id, int type) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CONSULTATION_ID,id);
        bundle.putInt(KEY_TYPE,type);
        Intent starter = new Intent(context, ConsultationDetailsActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }
    public static void start(Context context,String id) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CONSULTATION_ID,id);
        Intent starter = new Intent(context, ConsultationDetailsActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }
    @Override
    protected void initData() {
        super.initData();
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai,v -> {onBackPressed();});
        setToolbarText(R.id.tv_global_title,"咨询详情");
            //咨询悬赏详情
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_consultation_count, ConsultationDetailsRewardFragment.newInstance(getBundleString(KEY_CONSULTATION_ID,"")))
                    .commitAllowingStateLoss();
    }

}

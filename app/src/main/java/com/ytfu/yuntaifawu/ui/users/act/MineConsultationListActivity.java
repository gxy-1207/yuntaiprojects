package com.ytfu.yuntaifawu.ui.users.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.github.lee.annotation.InjectLayout;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.UserMainActivity;
import com.ytfu.yuntaifawu.ui.users.fragment.MineConsultationFragment;
import com.ytfu.yuntaifawu.utils.SpUtil;

/** @Auther gxy @Date 2020/6/12 @Des 我的咨询 */
@InjectLayout(
        value = R.layout.activity_mine_consultation_list,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
public class MineConsultationListActivity extends BaseActivity {
    private static final String KEY_USERID = "USERID";
    private static final String KEY_IS_FROM_LOGIN = "isFromLogin";

    public static void start(Context context) {
        Intent starter = new Intent(context, MineConsultationListActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, boolean isFromLogin) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_IS_FROM_LOGIN, isFromLogin);
        Intent starter = new Intent(context, MineConsultationListActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    public static void start(Context context, String uid) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_USERID, uid);
        Intent starter = new Intent(context, MineConsultationListActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected void initData() {
        super.initData();

        hideLoading();
        changeStatusBarTextColor(false);
        setToolbarLeftImage(
                R.drawable.fanhui_bai,
                v -> {
                    onBackPressed();
                });
        String identityType = SpUtil.getString(mContext, AppConstant.SHENFEN, "1");
        if (identityType.equals("1")) {
            setToolbarText(R.id.tv_global_title, "我的咨询");
        } else {
            setToolbarText(R.id.tv_global_title, "他的咨询");
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.fl_consultation_layout,
                        MineConsultationFragment.newInstance(getBundleString(KEY_USERID, "")))
                .commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        if (getBundleBoolean(KEY_IS_FROM_LOGIN, false)) {
            UserMainActivity.Companion.start(mContext, 2);
        }
        super.onBackPressed();
    }
}

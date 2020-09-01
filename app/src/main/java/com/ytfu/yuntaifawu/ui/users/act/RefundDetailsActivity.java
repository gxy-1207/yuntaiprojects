package com.ytfu.yuntaifawu.ui.users.act;

import android.content.Context;
import android.content.Intent;

import com.github.lee.annotation.InjectLayout;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/23
*
*  @Des  退款详情
*
*/
@InjectLayout(value = R.layout.refund_detail_activity,toolbarLayoutId = R.layout.layout_toolbar_center_title)
public class RefundDetailsActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, RefundDetailsActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void initData() {
        super.initData();
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai,view -> onBackPressed());
        setToolbarText(R.id.tv_global_title,"退款详情");
    }
}
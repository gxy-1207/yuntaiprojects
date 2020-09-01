package com.ytfu.yuntaifawu.ui.users.act;

import android.content.Context;
import android.content.Intent;

import com.github.lee.annotation.InjectLayout;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.users.fragment.MyRefundFragment;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/23
*
*  @Des  我的退款
*
*/
@InjectLayout(value = R.layout.activity_my_refund,toolbarLayoutId = R.layout.layout_toolbar_center_title)
public class MyRefundActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MyRefundActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }
    
    @Override
    protected void initData() {
        super.initData();
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai,view -> onBackPressed());
        setToolbarText(R.id.tv_global_title,"退款明细");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_refund_connect, MyRefundFragment.newInstance())
                .commitAllowingStateLoss();
    }
}
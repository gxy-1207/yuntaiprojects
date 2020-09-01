package com.ytfu.yuntaifawu.ui.contract.act;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.lee.annotation.InjectLayout;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.contract.fragment.ContractListFragment;

import qiu.niorgai.StatusBarCompat;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/30
*
*  @Des 合同列表
*
*/
@InjectLayout(value = R.layout.activity_contract_list1,toolbarLayoutId = R.layout.layout_toolbar_center_title)
public class ContractListActivity extends BaseActivity {
    private static final String KEY_ClASSIFICATION_ID = "CLASSIFICATION_ID";
    private static final String KEY_TITLE = "TITLE";
    public static void start(Context context,String classificationId,String tilte) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ClASSIFICATION_ID,classificationId);
        bundle.putString(KEY_TITLE,tilte);
        Intent starter = new Intent(context, ContractListActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        changeStatusBarTextColor(true);
        setToolbarBackgroud(Color.WHITE);
        setToolbarLeftImage(R.drawable.fanhui_hui,view -> onBackPressed());
        setToolbarTextColor(R.id.tv_global_title,getResources().getColor(R.color.textColoe_303030));
        String title = getBundleString(KEY_TITLE, "");
        setToolbarText(R.id.tv_global_title,title);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_contract, ContractListFragment.newInstance(getBundleString(KEY_ClASSIFICATION_ID,"")))
                .commitAllowingStateLoss();
    }
}
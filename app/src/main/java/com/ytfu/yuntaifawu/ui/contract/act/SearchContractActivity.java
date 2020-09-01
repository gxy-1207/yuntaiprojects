package com.ytfu.yuntaifawu.ui.contract.act;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.github.lee.annotation.InjectLayout;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.contract.fragment.SearchContractFragment;

import qiu.niorgai.StatusBarCompat;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/30
*
*  @Des 搜索合同页面
*
*/
@InjectLayout(value = R.layout.activity_search_cintract,toolbarLayoutId = R.layout.layout_toolbar_center_title)
public class SearchContractActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SearchContractActivity.class);
//        starter.putExtra();
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
        setToolbarText(R.id.tv_global_title,"搜索");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_search_contract, SearchContractFragment.newInstance())
                .commitAllowingStateLoss();
    }
}
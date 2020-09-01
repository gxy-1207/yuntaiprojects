package com.ytfu.yuntaifawu.ui.audio.act;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.github.lee.annotation.InjectLayout;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.audio.fragment.SearchAudioFragment;

import qiu.niorgai.StatusBarCompat;

/**
 * @Auther gxy
 * @Date 2020/7/31
 * @Des 搜索音频
 */
@InjectLayout(value = R.layout.activity_search_audio,toolbarLayoutId = R.layout.layout_toolbar_center_title)
public class SearchAudioActivity extends BaseActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, SearchAudioActivity.class);
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
                .replace(R.id.fl_search_audio, SearchAudioFragment.newInstance())
                .commitAllowingStateLoss();
    }
}
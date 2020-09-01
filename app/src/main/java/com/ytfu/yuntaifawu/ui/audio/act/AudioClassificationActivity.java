package com.ytfu.yuntaifawu.ui.audio.act;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.audio.adapter.AudioClassificationPageAdapter;
import com.ytfu.yuntaifawu.ui.audio.p.AudioClassificationPresenter;
import com.ytfu.yuntaifawu.ui.audio.v.AudioClassificationView;
import com.ytfu.yuntaifawu.ui.home.bean.AudioNavBean;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;


@InjectPresenter(AudioClassificationPresenter.class)
@InjectLayout(value = R.layout.activity_audio_classification, toolbarLayoutId = R.layout.layout_toolbar_center_title)
public class AudioClassificationActivity
        extends BaseActivity<AudioClassificationView, AudioClassificationPresenter>
        implements AudioClassificationView {

    private AudioClassificationPageAdapter adapter;

    @BindView(R.id.mi_audio_titles)
    MagicIndicator mi_audio_titles;
    @BindView(R.id.vp_audio_content)
    ViewPager vp_audio_content;

    public static void start(Context context) {
        Intent starter = new Intent(context, AudioClassificationActivity.class);
        context.startActivity(starter);
    }

    //===Desc:================================================================================


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_search){
            //进入音频搜索
            SearchAudioActivity.start(this);
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void setViewListener() {
        super.setViewListener();
        findViewById(R.id.tv_search).setOnClickListener(view -> SearchAudioActivity.start(this));
    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        changeStatusBarTextColor(true);
        setToolbarBackgroud(Color.WHITE);
        setToolbarLeftImage(R.drawable.fanhui_hui, view -> onBackPressed());
        setToolbarTextColor(R.id.tv_global_title, Color.parseColor("#303030"));
        setToolbarText(R.id.tv_global_title, "音频列表");

        adapter = new AudioClassificationPageAdapter(getSupportFragmentManager());
        vp_audio_content.setAdapter(adapter);

        //获取分类
        getPresenter().getAudioClassify();
    }

    //===Desc:================================================================================
    @Override
    public void onGetAudioClassify(List<AudioNavBean.ListBean> list) {
        adapter.setData(list);
        adapter.notifyDataSetChanged();

        //设置控件
        final CommonNavigator cv = new CommonNavigator(this);
        cv.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int i) {
                SimplePagerTitleView view = new SimplePagerTitleView(mContext);
                view.setTextSize(13);
                view.setNormalColor(Color.parseColor("#A1A1A1"));
                view.setSelectedColor(Color.parseColor("#2998F5"));
                view.setText(list.get(i).getLabel());
                view.setOnClickListener(view1 -> vp_audio_content.setCurrentItem(i));
                return view;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setLineHeight(XPopupUtils.dp2px(context, 2));
                indicator.setColors(Color.parseColor("#289AF6"));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        mi_audio_titles.setNavigator(cv);
        ViewPagerHelper.bind(mi_audio_titles, vp_audio_content);
    }
}

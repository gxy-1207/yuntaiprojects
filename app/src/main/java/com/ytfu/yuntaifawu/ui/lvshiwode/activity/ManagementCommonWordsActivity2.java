package com.ytfu.yuntaifawu.ui.lvshiwode.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.ClassificationOfCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.fragment.ManagementCommonWordsFragment;
import com.ytfu.yuntaifawu.ui.lvshiwode.presenter.ManagementCommonWordsPresenter;
import com.ytfu.yuntaifawu.ui.lvshiwode.presenter.ManagementCommonWordsPresenter2;
import com.ytfu.yuntaifawu.ui.lvshiwode.view.ManagementCommonWordsView;
import com.ytfu.yuntaifawu.ui.lvshiwode.view.ManagementCommonWordsView2;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.utils.LogUtils;

/**
 * @Auther gxy
 * @Date 2020/7/14
 * @Des 管理常用语
 */
@InjectLayout(value = R.layout.activity_management_common_words2, toolbarLayoutId = R.layout.layout_toolbar_center_title)
@ InjectPresenter(ManagementCommonWordsPresenter2.class)
public class ManagementCommonWordsActivity2 extends BaseActivity<ManagementCommonWordsView2, ManagementCommonWordsPresenter2> implements ManagementCommonWordsView2 {


    @BindView(R.id.lvshi_mine_common_words)
    MagicIndicator lvshiMineCommonWords;
    @BindView(R.id.vp_common_words)
    ViewPager vpCommonWords;
    private List<Fragment> fragmentList = new ArrayList<>();
    public static void start(Context context) {
        Intent starter = new Intent(context, ManagementCommonWordsActivity2.class);
//        starter.putExtra();
        context.startActivity(starter);

    }

    @Override
    protected void initData() {
        super.initData();
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> {
            onBackPressed();
        });
        setToolbarText(R.id.tv_global_title, "管理常用语");
        getPresenter().getClassificationOfCommonWords();
    }

    @Override
    public void onCategorySuccess(ClassificationOfCommonWordsBean classification) {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return classification.getList().size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int i) {
                CommonPagerTitleView view = new CommonPagerTitleView(mContext);
                view.setContentView(R.layout.layout_title);
                TextView tv_title = view.findViewById(R.id.tv_title);
                tv_title.setText(classification.getList().get(i).getContent());
                view.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                            tv_title.setTextColor(Color.parseColor("#44A2F7"));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        tv_title.setTextColor(Color.parseColor("#666666"));
                    }

                    @Override
                    public void onLeave(int i, int i1, float v, boolean b) {

                    }

                    @Override
                    public void onEnter(int i, int i1, float v, boolean b) {

                    }
                });
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if (findViewById(R.id.rv_common_words_root).isShown()) {
                            vpCommonWords.setCurrentItem(i);
//                        }else{
//                            LogUtils.e("获取数据");
//                        }
                    }
                });
                return view;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        lvshiMineCommonWords.setNavigator(commonNavigator);
        vpCommonWords.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return ManagementCommonWordsFragment.newInstance(classification.getList().get(position).getId());
            }

            @Override
            public int getCount() {
                return classification.getList().size();
            }
        });
        ViewPagerHelper.bind(lvshiMineCommonWords,vpCommonWords);
    }

    @Override
    public void onfiledError(String error) {

    }
}
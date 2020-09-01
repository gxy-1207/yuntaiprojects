package com.ytfu.yuntaifawu.ui.contract.act;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.service.controls.actions.CommandAction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.lee.annotation.InjectLayout;
import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.helper.RxLifecycleUtil;
import com.ytfu.yuntaifawu.helper.ScaleTransitionPagerTitleView;
import com.ytfu.yuntaifawu.ui.contract.fragment.ContractClassificationFragment;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.ContractListFirstBean;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.DensityUtil;
import com.ytfu.yuntaifawu.utils.ToastUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import qiu.niorgai.StatusBarCompat;

/**
 * @Auther gxy
 * @Date 2020/7/30
 * @Des 合同分类
 */
@InjectLayout(value = R.layout.activity_contract_classication, toolbarLayoutId = R.layout.layout_toolbar_center_title)
public class ContractClassificationActivity extends BaseActivity {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_page)
    ViewPager viewPage;

    public static void start(Context context) {

        Intent starter = new Intent(context, ContractClassificationActivity.class);
//    starter.putExtra();
        context.startActivity(starter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.user_home_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.action_search){
//            //跳转搜索页面
//            SearchContractActivity.start(this);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void setViewListener() {
        super.setViewListener();
        findViewById(R.id.tv_search).setOnClickListener(view -> SearchContractActivity.start(this));
    }

    @Override
    protected void initData() {
        super.initData();
        showLoading();
        StatusBarCompat.setStatusBarColor(this,Color.WHITE);
        setToolbarBackgroud(Color.WHITE);
        changeStatusBarTextColor(true);
        setToolbarLeftImage(R.drawable.fanhui_hui,view -> onBackPressed());
        setToolbarTextColor(R.id.tv_global_title, CommonUtil.getColor(R.color.textColoe_303030));
        setToolbarText(R.id.tv_global_title,"合同列表");
        //请求一类分类数据
        setData();
    }

    private void setData() {
        HttpUtil.getInstance().getApiService().getListFirst()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLifecycleUtil.bindLifecycle(this))
                .subscribe(new BaseRxObserver<ContractListFirstBean>() {
                    @Override
                    public void onNextImpl(ContractListFirstBean entity) {
                        if(entity == null){
                            return;
                        }
                        if(entity.getStatus() != 1){
                            return;
                        }
                        if(entity.getList() == null || entity.getList().isEmpty()){
                            return;
                        }
                        onFirstSuccess(entity);
                    }

                    @Override
                    public void onErrorImpl(String errorMessage) {
                        Logger.e("setData" + errorMessage);
                    }
                });
    }

    private void onFirstSuccess(ContractListFirstBean entity) {
        hideLoading();
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return entity.getList().size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int i) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(entity.getList().get(i).getLabel());
                simplePagerTitleView.setTextSize(13);
                simplePagerTitleView.setPadding(DensityUtil.dip2px(20),0,DensityUtil.dip2px(20),0);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.textColoe_2998F5));
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.textColoe_A1A1A1));
                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPage.setCurrentItem(i);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(getResources().getColor(R.color.textColoe_2998F5));
                indicator.setLineWidth(DensityUtil.dip2px(50));
                indicator.setLineHeight(DensityUtil.dip2px(2));
//                indicator.setRoundRadius(DensityUtil.dip2px(2));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        viewPage.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return ContractClassificationFragment.newInstance(entity.getList().get(position).getId());
            }

            @Override
            public int getCount() {
                return entity.getList().size();
            }
        });
        ViewPagerHelper.bind(magicIndicator,viewPage);
    }

}
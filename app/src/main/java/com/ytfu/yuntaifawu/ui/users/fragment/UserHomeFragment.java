package com.ytfu.yuntaifawu.ui.users.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.App;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.ui.home.bean.HomeBannerBean;
import com.ytfu.yuntaifawu.ui.mseeage.activity.LvShiDetailsActivity;
import com.ytfu.yuntaifawu.ui.search.ack.SearchActivity;
import com.ytfu.yuntaifawu.ui.users.adaper.UserHomeAdaper;
import com.ytfu.yuntaifawu.ui.users.bean.LawyerHomeListBean;
import com.ytfu.yuntaifawu.ui.users.header.UserHomeHeader;
import com.ytfu.yuntaifawu.ui.users.p.UserHomePresenter;
import com.ytfu.yuntaifawu.ui.users.v.IUserHomeView;
import com.ytfu.yuntaifawu.utils.CommonUtil;

import java.util.List;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

/** @Auther gxy @Date 2020/6/9 @Des 用户端首页fragment */
@InjectLayout(
        value = R.layout.fragment_base_recycler,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(UserHomePresenter.class)
public class UserHomeFragment
        extends BaseRecyclerViewFragment<
                LawyerHomeListBean.DataBean, IUserHomeView, UserHomePresenter>
        implements IUserHomeView {

    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;

    private UserHomeHeader homeHeader;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.user_home_menu, menu);
        menu.findItem(R.id.action_search)
                .setOnMenuItemClickListener(
                        menuItem -> {
                            // 进入搜索页面
                            SearchActivity.start(mContext);
                            return true;
                        });
    }

    //    @Override
    //    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    //            int itemId = item.getItemId();
    //            if (itemId == R.id.action_search) {
    //                // 进入搜索页面
    //                SearchActivity.start(mContext);
    //            }
    //        return super.onOptionsItemSelected(item);
    //    }

    public static UserHomeFragment newInstance() {

        Bundle args = new Bundle();

        UserHomeFragment fragment = new UserHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        //        getPresenter().refresh();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            FragmentActivity activity = getActivity();
            if (null != activity) {
                StatusBarCompat.setStatusBarColor(activity, Color.WHITE);
            }
        }
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        hideLoading();
        setHasOptionsMenu(true);
        setToolbarText(R.id.tv_global_title, "云台法律咨询");
        setToolbarTextColor(R.id.tv_global_title, Color.parseColor("#222222"));
        mToolbar.setBackgroundColor(CommonUtil.getColor(R.color.transparent_half));
        mToolbar.inflateMenu(R.menu.user_home_menu);
        mToolbar.setOnMenuItemClickListener(
                item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.action_search) {
                        // 进入搜索页面
                        SearchActivity.start(mContext);
                    }
                    return false;
                });
        linearLayout.setBackgroundResource(R.color.textcolor_f2);
        getAdapter()
                .setOnItemClickListener(
                        (adapter, view, position) -> {
                            if (App.getInstance().getLoginFlag()) {
                                Intent intent =
                                        new Intent(getActivity(), LvShiDetailsActivity.class);
                                intent.putExtra("lid", mAdapter.getData().get(position).getId());
                                intent.putExtra("types", 1);
                                intent.putExtra(
                                        "userName",
                                        mAdapter.getData().get(position).getLawyername());
                                startActivity(intent);
                            } else {
                                toLogin();
                            }
                        });
    }

    @Override
    protected void initData() {
        super.initData();
        homeHeader = new UserHomeHeader(getContext());
        getAdapter().addHeaderView(homeHeader.getHeaderView());
        //        getPresenter().getHomeBannerList();
    }

    @Override
    protected BaseQuickAdapter<LawyerHomeListBean.DataBean, BaseViewHolder> createAdapter() {
        return new UserHomeAdaper();
    }

    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        if (isLoadMore) {
            currentPage++;
        } else {
            currentPage = 1;
            getPresenter().getHomeBannerList();
        }
        getPresenter().getLawyerList(isLoadMore, "", "", String.valueOf(currentPage));
    }

    // 获取轮播图
    @Override
    public void userHomeBannerList(HomeBannerBean bannerBean) {
        hideLoading();
        if (null == bannerBean) {
            return;
        }
        List<HomeBannerBean.ListBean> list = bannerBean.getList();
        if (null == list || list.isEmpty()) {
            return;
        }

        //        if (bannerBean != null || bannerBean.getList() != null) {
        homeHeader.render(bannerBean);
    }

    @Override
    public void userLawyerList(LawyerHomeListBean lawyerHomeListBean) {
        if (lawyerHomeListBean != null || lawyerHomeListBean.getData() != null) {
            addData(lawyerHomeListBean.getData());
        }
    }

    @Override
    public void userHomeFiled(String error) {}
}

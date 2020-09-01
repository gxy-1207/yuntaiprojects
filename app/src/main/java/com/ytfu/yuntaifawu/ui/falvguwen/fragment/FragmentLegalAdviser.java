package com.ytfu.yuntaifawu.ui.falvguwen.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.ui.falvguwen.activity.ActivityLegalAdviserLizhiRuzhiList;
import com.ytfu.yuntaifawu.ui.falvguwen.activity.ActivityLegalAdviserZaiZhiList;
import com.ytfu.yuntaifawu.ui.falvguwen.adaper.LegalAdviserAdaper;
import com.ytfu.yuntaifawu.ui.falvguwen.bean.LegalAdviserClassifyBean;
import com.ytfu.yuntaifawu.ui.falvguwen.presenter.LegalAdviserPresenter;
import com.ytfu.yuntaifawu.ui.falvguwen.view.LegalAdviserView;
import com.ytfu.yuntaifawu.utils.CommonUtil;

import qiu.niorgai.StatusBarCompat;

/** @Auther gxy @Date 2020/8/13 @Des 法律顾问模块 */
@InjectLayout(
        value = R.layout.fragment_base_recycler,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(LegalAdviserPresenter.class)
public class FragmentLegalAdviser
        extends BaseRecyclerViewFragment<
                LegalAdviserClassifyBean.ListBean, LegalAdviserView, LegalAdviserPresenter>
        implements LegalAdviserView {

    private TextView tv_tishi;

    public static FragmentLegalAdviser newInstance() {

        Bundle args = new Bundle();

        FragmentLegalAdviser fragment = new FragmentLegalAdviser();
        fragment.setArguments(args);
        return fragment;
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
    protected void initData() {
        super.initData();
        enableLoadMore(false);
        enableRefresh(true);
        setToolbarText(R.id.tv_global_title, "法律顾问");
        setToolbarTextColor(R.id.tv_global_title, Color.parseColor("#222222"));
        mToolbar.setBackgroundColor(CommonUtil.getColor(R.color.transparent_half));
        View headerView = inflateView(R.layout.header_legaladviser);
        tv_tishi = headerView.findViewById(R.id.tv_tishi);
        addHeaderView(headerView);
        addFooterView(inflateView(R.layout.item_fooder_view));
    }

    @Override
    protected BaseQuickAdapter<LegalAdviserClassifyBean.ListBean, BaseViewHolder> createAdapter() {
        return new LegalAdviserAdaper();
    }

    @Override
    protected void setViewListener(View rootView) {
        super.setViewListener(rootView);
        getAdapter()
                .setOnItemClickListener(
                        (adapter, view, position) -> {
                            if ("0".equals(mAdapter.getData().get(position).getType())) {
                                Intent intent =
                                        new Intent(
                                                mContext, ActivityLegalAdviserLizhiRuzhiList.class);
                                intent.putExtra("id", mAdapter.getData().get(position).getId());
                                intent.putExtra(
                                        "label", mAdapter.getData().get(position).getLabel());
                                startActivity(intent);
                            } else {
                                Intent intent =
                                        new Intent(mContext, ActivityLegalAdviserZaiZhiList.class);
                                intent.putExtra("id", mAdapter.getData().get(position).getId());
                                intent.putExtra(
                                        "label", mAdapter.getData().get(position).getLabel());
                                startActivity(intent);
                            }
                        });
    }

    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        getPresenter().setLegalAdviser();
    }

    @Override
    public void onLegalAdSuccess(LegalAdviserClassifyBean classifyBean) {
        tv_tishi.setText(String.format("已有%s人购买", classifyBean.getSum()));
    }

    @Override
    public void onLegalAdFiled() {}
}

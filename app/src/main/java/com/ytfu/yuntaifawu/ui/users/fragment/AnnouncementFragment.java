package com.ytfu.yuntaifawu.ui.users.fragment;

import android.graphics.Color;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.users.act.AnnouncementDetailsActivity;
import com.ytfu.yuntaifawu.ui.users.adaper.AnnouncementAdaper;
import com.ytfu.yuntaifawu.ui.users.bean.AnnouncementBean;
import com.ytfu.yuntaifawu.ui.users.p.AnnouncementPresenter;
import com.ytfu.yuntaifawu.utils.ItemDecoration;

/**
 * @Auther gxy
 * @Date 2020/6/10
 * @Des 公告fragment
 */
@InjectPresenter(AnnouncementPresenter.class)
public class AnnouncementFragment extends BaseRecyclerViewFragment
        <AnnouncementBean.DataBean, BaseRefreshView<AnnouncementBean.DataBean>, AnnouncementPresenter> {

    public static AnnouncementFragment newInstance() {
        Bundle args = new Bundle();
        AnnouncementFragment fragment = new AnnouncementFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        int color = Color.parseColor("#f2f2f2");
        int lineHeight = XPopupUtils.dp2px(mContext, 5);
        addItemDecoration(ItemDecoration.createVertical(color, lineHeight, 0));
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            AnnouncementDetailsActivity.start(mContext, mAdapter.getData().get(position).getJumpurl());
        });
    }

    @Override
    protected BaseQuickAdapter<AnnouncementBean.DataBean, BaseViewHolder> createAdapter() {
        return new AnnouncementAdaper();
    }

    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        if (isLoadMore) {
            currentPage++;
        } else {
            currentPage = 1;
        }
        getPresenter().getAnnouncementList(isLoadMore, currentPage);
    }
}

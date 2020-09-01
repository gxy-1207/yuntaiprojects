package com.ytfu.yuntaifawu.ui.users.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.mseeage.activity.LvShiDetailsActivity;
import com.ytfu.yuntaifawu.ui.users.adaper.UserHomeAdaper;
import com.ytfu.yuntaifawu.ui.users.bean.LawyerHomeListBean;
import com.ytfu.yuntaifawu.ui.users.p.SearchListPresenter;

/**
 * @Auther gxy
 * @Date 2020/6/10
 * @Des 搜索fragment
 */
@InjectPresenter(SearchListPresenter.class)
public class SearchListFragment extends BaseRecyclerViewFragment
        <LawyerHomeListBean.DataBean, BaseRefreshView<LawyerHomeListBean.DataBean>, SearchListPresenter> {
    private static final String KEY_LAWYER_NAME = "LAWYER_NAME";
    private static final String KEY_EXPERT_PLACE = "EXPERT_PLACE";

    public static SearchListFragment newInstance(String lawyername, String expertplace) {

        Bundle args = new Bundle();
        args.putString(KEY_LAWYER_NAME, lawyername);
        args.putString(KEY_EXPERT_PLACE, expertplace);
        SearchListFragment fragment = new SearchListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        int top = XPopupUtils.dp2px(mContext, 10);
        View view1 = new View(mContext);
        view1.setBackgroundColor(Color.parseColor("#f2f2f2"));
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, top);
        view1.setLayoutParams(layoutParams);
        getAdapter().addHeaderView(view1);
//        getSwipeRefreshLayout().setPadding(0, top, 0, 0);
        getSwipeRefreshLayout().setBackgroundColor(Color.parseColor("#f2f2f2"));
        getAdapter().setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), LvShiDetailsActivity.class);
            intent.putExtra("lid",mAdapter.getData().get(position).getId());
            intent.putExtra("types",1);
            intent.putExtra("userName",mAdapter.getData().get(position).getLawyername());
            startActivity(intent);
        });
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
        }
        String lawyername = getArgumentString(KEY_LAWYER_NAME, "");
        String expertplace = getArgumentString(KEY_EXPERT_PLACE, "");
        getPresenter().getSearchList(isLoadMore, lawyername, expertplace, String.valueOf(currentPage));
    }

}

package com.ytfu.yuntaifawu.ui.users.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.users.act.ConsultationDetailsActivity;
import com.ytfu.yuntaifawu.ui.users.act.ConsultationDetailsActivity2;
import com.ytfu.yuntaifawu.ui.users.adaper.MineConsultationAdaper;
import com.ytfu.yuntaifawu.ui.users.bean.MineConsulitatioBean;
import com.ytfu.yuntaifawu.ui.users.p.MineConsultationPresenter;
import com.ytfu.yuntaifawu.utils.SpUtil;

/** @Auther gxy @Date 2020/6/12 @Des 我的咨询fragment */
@InjectPresenter(MineConsultationPresenter.class)
public class MineConsultationFragment
        extends BaseRecyclerViewFragment<
                MineConsulitatioBean.ListBean,
                BaseRefreshView<MineConsulitatioBean.ListBean>,
                MineConsultationPresenter> {
    private static final String KEY_USERID = "USERID";

    public static MineConsultationFragment newInstance(String uid) {

        Bundle args = new Bundle();
        args.putString(KEY_USERID, uid);
        MineConsultationFragment fragment = new MineConsultationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().refresh();
    }

    @Override
    protected void initData() {
        super.initData();
        SwipeRefreshLayout swipeRefreshLayout = getSwipeRefreshLayout();
        swipeRefreshLayout.setBackgroundColor(Color.parseColor("#f2f2f2"));
        //        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
        //            int id = view.getId();
        //            switch (id){
        //                case R.id.iv_urgent:
        //
        // TopPaymentActivity.start(mContext,mAdapter.getData().get(position).getId());
        //                    break;
        //                default:
        //                    break;
        //            }
        //        });
        mAdapter.setOnItemClickListener(
                (adapter, view, position) -> {
                    String identityType = SpUtil.getString(mContext, AppConstant.SHENFEN, "1");
                    if (identityType.equals("1")) {
                        // 我的咨询详情
                        ConsultationDetailsActivity.start(
                                mContext,
                                mAdapter.getData().get(position).getId(),
                                mAdapter.getData().get(position).getType());
                    } else {
                        // 律师端他的咨询详情
                        ConsultationDetailsActivity2.start(
                                mContext, mAdapter.getData().get(position).getId(), 1);
                    }
                });
    }

    @Override
    protected BaseQuickAdapter<MineConsulitatioBean.ListBean, BaseViewHolder> createAdapter() {
        return new MineConsultationAdaper();
    }

    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        if (isLoadMore) {
            currentPage++;
        } else {
            currentPage = 1;
        }
        String userId = getArgumentString(KEY_USERID, "");
        if (TextUtils.isEmpty(userId)) {
            userId = SpUtil.getString(mContext, AppConstant.UID, "");
        }
        getPresenter().getMineConsultationList(isLoadMore, currentPage, userId);
    }
}

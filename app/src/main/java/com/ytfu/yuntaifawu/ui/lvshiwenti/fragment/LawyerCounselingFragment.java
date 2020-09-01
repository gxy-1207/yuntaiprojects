package com.ytfu.yuntaifawu.ui.lvshiwenti.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.lvshiwenti.adaper.LvShiZiXunAdaper;
import com.ytfu.yuntaifawu.ui.lvshiwenti.bean.LawyerConsultingBean;
import com.ytfu.yuntaifawu.ui.lvshiwenti.presenter.LvShiZixunPresenter;
import com.ytfu.yuntaifawu.ui.users.act.ConsultationDetailsActivity2;
import com.ytfu.yuntaifawu.utils.SpUtil;

/** @Auther gxy @Date 2020/6/17 @Des 律师端咨询问题 */
@InjectLayout(
        value = R.layout.fragment_base_recycler,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(LvShiZixunPresenter.class)
public class LawyerCounselingFragment
        extends BaseRecyclerViewFragment<
                LawyerConsultingBean.ListBean,
                BaseRefreshView<LawyerConsultingBean.ListBean>,
                LvShiZixunPresenter> {

    public static LawyerCounselingFragment newInstance() {

        Bundle args = new Bundle();

        LawyerCounselingFragment fragment = new LawyerCounselingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init() {
        super.init();
        autoRefresh = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().refresh();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getPresenter().refresh();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        setToolbarText(R.id.tv_global_title, "咨询问题");
        SwipeRefreshLayout swipeRefreshLayout = getSwipeRefreshLayout();
        swipeRefreshLayout.setBackgroundColor(Color.parseColor("#f2f2f2"));
        mAdapter.setOnItemClickListener(
                (adapter, view, position) -> {
                    ConsultationDetailsActivity2.start(
                            getActivity(), mAdapter.getData().get(position).getId(), 0);
                });
        getAdapter()
                .setOnItemChildClickListener(
                        (adapter, view, position) -> {
                            // 咨询id
                            String consult_id = getAdapter().getData().get(position).getId();
                            // 律师id
                            String lsid = SpUtil.getString(mContext, AppConstant.UID, "");
                            // 用户id
                            String uid = getAdapter().getData().get(position).getUid();
                            LawyerConsultingBean.ListBean listBean =
                                    getAdapter().getData().get(position);
                            listBean.setQiangda(1);
                            getPresenter().getQuickAnswer(position, uid, lsid, consult_id);
                            //            showToast("快速抢答");
                        });
    }

    @Override
    protected BaseQuickAdapter<LawyerConsultingBean.ListBean, BaseViewHolder> createAdapter() {
        return new LvShiZiXunAdaper();
    }

    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        if (isLoadMore) {
            currentPage++;
        } else {
            currentPage = 1;
        }
        String uid = SpUtil.getString(mContext, AppConstant.UID, "");
        getPresenter().getLvShiZiXunList(isLoadMore, currentPage, uid);
    }
}

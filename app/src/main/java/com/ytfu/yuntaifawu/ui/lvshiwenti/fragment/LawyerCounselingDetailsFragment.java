package com.ytfu.yuntaifawu.ui.lvshiwenti.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.lawyer.chat.act.LawyerChatRoomActivity;
import com.ytfu.yuntaifawu.ui.lvshiwenti.activity.IWantToAnswerActivity;
import com.ytfu.yuntaifawu.ui.lvshiwenti.activity.LawyerCounselineDetailsActivity;
import com.ytfu.yuntaifawu.ui.lvshiwenti.activity.LawyerReplyDetailsActivity;
import com.ytfu.yuntaifawu.ui.lvshiwenti.adaper.LawyerConsultationDetailsListAdaper;
import com.ytfu.yuntaifawu.ui.lvshiwenti.header.LawyerConsultationDetailsHeader;
import com.ytfu.yuntaifawu.ui.lvshiwenti.presenter.LawyerCounselingDetailsPresenter;
import com.ytfu.yuntaifawu.ui.lvshiwenti.view.LawyerConsultationDetailsView;
import com.ytfu.yuntaifawu.ui.users.adaper.ConsultationDetailsListAdaper;
import com.ytfu.yuntaifawu.ui.users.bean.ConsultationDetailsBean;

/**
 * @Auther gxy
 * @Date 2020/6/17
 * @Des 律师咨询问题详情
 */
@InjectPresenter(LawyerCounselingDetailsPresenter.class)
public class LawyerCounselingDetailsFragment extends BaseRecyclerViewFragment
        <ConsultationDetailsBean.XiaoxiArrBean, LawyerConsultationDetailsView, LawyerCounselingDetailsPresenter>
            implements LawyerConsultationDetailsView{

    private LawyerConsultationDetailsHeader consultationDetailsHeader;
    private static final String KEY_CONSULTATION_ID = "CONSULTATION_ID";
    private ConsultationDetailsBean.ContentBean contentBean;

    public static LawyerCounselingDetailsFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(KEY_CONSULTATION_ID, id);
        LawyerCounselingDetailsFragment fragment = new LawyerCounselingDetailsFragment();
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
        consultationDetailsHeader = new LawyerConsultationDetailsHeader(mContext);
        getAdapter().addHeaderView(consultationDetailsHeader.getHeaderView());
        View viewBottom = LayoutInflater.from(getContext()).inflate(R.layout.consultation_details_rewared_bottom_button, null, false);
        viewBottom.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        addBottomSuspensionView(viewBottom);
        LinearLayout ll_reward = viewBottom.findViewById(R.id.ll_reward);
        ll_reward.setVisibility(View.GONE);
        LinearLayout ll_unrewarded = viewBottom.findViewById(R.id.ll_unrewarded);
        ll_unrewarded.setVisibility(View.GONE);
        LinearLayout ll_lawyer_bottom = viewBottom.findViewById(R.id.ll_lawyer_bottom);
        ll_lawyer_bottom.setVisibility(View.VISIBLE);
        //立即沟通
        viewBottom.findViewById(R.id.tv_communicate_now).setOnClickListener(v -> {
            String toUserId = contentBean.getUid();
            String toUserName = contentBean.getUser_login();
            String toUserAvatar = contentBean.getAvatar();
            String consultId = contentBean.getId();
            LawyerChatRoomActivity.start(getActivity(),false,toUserId,toUserName,toUserAvatar,consultId);
        });
        //我要回答
        viewBottom.findViewById(R.id.tv_i_want_to_answer).setOnClickListener(v -> {
            IWantToAnswerActivity.start(mContext,contentBean);
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            LawyerReplyDetailsActivity.start(mContext,
                    mAdapter.getData().get(position).getId(),
                    mAdapter.getData().get(position).getUid(),
                    mAdapter.getData().get(position).getCon_id());
        });
    }

    @Override
    protected BaseQuickAdapter<ConsultationDetailsBean.XiaoxiArrBean, BaseViewHolder> createAdapter() {
        return new LawyerConsultationDetailsListAdaper();
    }

    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        String consutationId = getArgumentString(KEY_CONSULTATION_ID, "");
        getPresenter().getConsultationDetails(isLoadMore, consutationId);
        stopRefresh();
        loadMoreEnd(false);
    }

    @Override
    public void onConsultationDetailsSuccess(ConsultationDetailsBean consultationDetailsBean) {
        if(consultationDetailsBean == null){
            return;
        }
        contentBean = consultationDetailsBean.getContent();
        consultationDetailsHeader.render(consultationDetailsBean.getContent());
    }
}

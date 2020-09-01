//package com.ytfu.yuntaifawu.ui.users.fragment;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.viewholder.BaseViewHolder;
//import com.github.lee.annotation.InjectPresenter;
//import com.lxj.xpopup.XPopup;
//import com.lxj.xpopup.interfaces.XPopupCallback;
//import com.ytfu.yuntaifawu.R;
//import com.ytfu.yuntaifawu.app.AppConstant;
//import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
//import com.ytfu.yuntaifawu.ui.chatroom.activity.UserChatRoomActivity;
//import com.ytfu.yuntaifawu.ui.pay.PayHelper;
//import com.ytfu.yuntaifawu.ui.pay.act.WantRewardActivity;
//import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;
//import com.ytfu.yuntaifawu.ui.users.act.TopPaymentActivity;
//import com.ytfu.yuntaifawu.ui.users.adaper.ConsultationDetailsListAdaper;
//import com.ytfu.yuntaifawu.ui.users.bean.ConsultationDetailsBean;
//import com.ytfu.yuntaifawu.ui.users.header.ConsultationDetailsNotUnlockedHeader;
//import com.ytfu.yuntaifawu.ui.users.header.ConsultationDetailsRewardHeader;
//import com.ytfu.yuntaifawu.ui.users.p.ConsultationDetailsRewardPresenter;
//import com.ytfu.yuntaifawu.ui.users.p.ConsultationDetailsRewardPresenter2;
//import com.ytfu.yuntaifawu.ui.users.v.ConsultationDetailsView;
//import com.ytfu.yuntaifawu.ui.users.v.ConsultationDetailsView2;
//import com.ytfu.yuntaifawu.ui.zixun.activity.AdvisoryReplyDetailActivity;
//import com.ytfu.yuntaifawu.utils.MessageEvent;
//import com.ytfu.yuntaifawu.utils.SpUtil;
//import com.ytfu.yuntaifawu.utils.dialog.DialogHelper;
//import com.ytfu.yuntaifawu.utils.dialog.PayDialog;
//import com.ytfu.yuntaifawu.utils.dialog.UnlockDialog;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import static com.ytfu.yuntaifawu.utils.ToastUtil.showCenterToast;
//
///**
// * @Auther gxy
// * @Date 2020/6/15
// * @Des 他的咨询详情悬赏fragment
// */
//@InjectPresenter(ConsultationDetailsRewardPresenter2.class)
//public class ConsultationDetailsRewardFragment2 extends BaseRecyclerViewFragment
//        <ConsultationDetailsBean.XiaoxiArrBean, ConsultationDetailsView2, ConsultationDetailsRewardPresenter2> implements ConsultationDetailsView2 {
//    private static final String KEY_CONSULTATION_ID = "CONSULTATION_ID";
//    private ConsultationDetailsRewardHeader detailsRewardHeader;
////    private ConsultationDetailsNotUnlockedHeader notUnlockedHeader;
////    private LinearLayout ll_reward;
////    private LinearLayout ll_unrewarded;
////    private UnlockDialog unlockDialog;
//
//    public static ConsultationDetailsRewardFragment2 newInstance(String id) {
//
//        Bundle args = new Bundle();
//        args.putString(KEY_CONSULTATION_ID, id);
//        ConsultationDetailsRewardFragment2 fragment = new ConsultationDetailsRewardFragment2();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    protected void initView(View rootView) {
//        super.initView(rootView);
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
////        getPresenter().refresh();
//    }
//
//    @Override
//    protected void initData() {
//        hideLoading();
//        super.initData();
//        //顶部详情header
//        detailsRewardHeader = new ConsultationDetailsRewardHeader(getContext());
//        getAdapter().addHeaderView(detailsRewardHeader.getHeaderView());
//        SwipeRefreshLayout swipeRefreshLayout = getSwipeRefreshLayout();
//        swipeRefreshLayout.setBackgroundColor(Color.parseColor("#f2f2f2"));
//        FrameLayout bottomFrameLayout = getBottomFrameLayout();
//        bottomFrameLayout.setBackgroundColor(Color.parseColor("#f2f2f2"));
////        enableLoadMore(false);
//        //律师回复立即沟通点击
//        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//            if (view.getId() == R.id.tv_communicate_now) {
//                UserChatRoomActivity.start(getActivity(), false, false, mAdapter.getData().get(position).getUid(), mAdapter.getData().get(position).getUser_login(), mAdapter.getData().get(position).getAvatar());
////                showCenterToast("立即咨询");
//            }
//        });
//        //律师回复item点击
//        mAdapter.setOnItemClickListener((adapter, view, position) -> {
//            AdvisoryReplyDetailActivity.start(getContext(),
//                    mAdapter.getData().get(position).getId(),
//                    mAdapter.getData().get(position).getUid(),
//                    mAdapter.getData().get(position).getCon_id());
//        });
//    }
//    @Override
//    protected BaseQuickAdapter<ConsultationDetailsBean.XiaoxiArrBean, BaseViewHolder> createAdapter() {
//        return new ConsultationDetailsListAdaper();
//    }
//
//    @Override
//    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
//        String id = getArgumentString(KEY_CONSULTATION_ID, "");
////        getPresenter().getConsultationDetails2(isLoadMore, id);
//        stopRefresh();
//        loadMoreEnd(false);
//    }
//
//    @Override
//    public void onConsultationDetailsSuccess(ConsultationDetailsBean consultationDetailsBean) {
//        hideLoading();
//        detailsRewardHeader.render(consultationDetailsBean.getContent());
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onWechatPaySuccess(MessageEvent event) {
//        int what = event.getWhat();
//        if (what == AppConstant.WX_PAY_SUCCESS) {
//            //更新支付成功UI显示
////            MineConsultationListActivity.start(mContext);
////            getPresenter().refresh();
//            showCenterToast("支付成功");
//
//        }
//    }
//
//
//
//
//
//    @Override
//    public void onConsultationDetailsFiled() {
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
//}

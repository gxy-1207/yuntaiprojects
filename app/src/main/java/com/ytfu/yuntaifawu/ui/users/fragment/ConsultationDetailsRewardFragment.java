package com.ytfu.yuntaifawu.ui.users.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.ui.chatroom.activity.UserChatRoomActivity;
import com.ytfu.yuntaifawu.ui.pay.PayHelper;
import com.ytfu.yuntaifawu.ui.pay.act.WantRewardActivity;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;
import com.ytfu.yuntaifawu.ui.users.act.TopPaymentActivity;
import com.ytfu.yuntaifawu.ui.users.adaper.ConsultationDetailsListAdaper;
import com.ytfu.yuntaifawu.ui.users.bean.ConsultationDetailsBean;
import com.ytfu.yuntaifawu.ui.users.header.ConsultationDetailsNotUnlockedHeader;
import com.ytfu.yuntaifawu.ui.users.header.ConsultationDetailsRewardHeader;
import com.ytfu.yuntaifawu.ui.users.p.ConsultationDetailsRewardPresenter;
import com.ytfu.yuntaifawu.ui.users.v.ConsultationDetailsView;
import com.ytfu.yuntaifawu.ui.zixun.activity.AdvisoryReplyDetailActivity;
import com.ytfu.yuntaifawu.utils.MessageEvent;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.dialog.DialogHelper;
import com.ytfu.yuntaifawu.utils.dialog.PayDialog;
import com.ytfu.yuntaifawu.utils.dialog.UnlockDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import me.jessyan.autosize.utils.LogUtils;

import static com.ytfu.yuntaifawu.utils.ToastUtil.showCenterToast;

/** @Auther gxy @Date 2020/6/15 @Des 咨询详情悬赏fragment */
@InjectPresenter(ConsultationDetailsRewardPresenter.class)
public class ConsultationDetailsRewardFragment
        extends BaseRecyclerViewFragment<
                ConsultationDetailsBean.XiaoxiArrBean,
                ConsultationDetailsView,
                ConsultationDetailsRewardPresenter>
        implements ConsultationDetailsView {
    private static final String KEY_CONSULTATION_ID = "CONSULTATION_ID";
    private ConsultationDetailsRewardHeader detailsRewardHeader;
    private ConsultationDetailsNotUnlockedHeader notUnlockedHeader;
    private LinearLayout ll_reward;
    private LinearLayout ll_unrewarded;

    public static ConsultationDetailsRewardFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(KEY_CONSULTATION_ID, id);
        ConsultationDetailsRewardFragment fragment = new ConsultationDetailsRewardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean isScrollCancelByUser = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void setViewListener(View rootView) {
        super.setViewListener(rootView);
        getRecycleView()
                .setOnTouchListener(
                        (view, motionEvent) -> {
                            if (null != timer) {
                                isScrollCancelByUser = true;
                                timer.cancel();
                                timer.onFinish();
                                timer = null;
                            }
                            return false;
                        });
    }

    @Override
    protected void initData() {
        super.initData();
        showLoading();
        // 顶部详情header
        detailsRewardHeader = new ConsultationDetailsRewardHeader(getContext());
        getAdapter().addHeaderView(detailsRewardHeader.getHeaderView());
        // 未解锁时的header
        notUnlockedHeader = new ConsultationDetailsNotUnlockedHeader(getContext());
        SwipeRefreshLayout swipeRefreshLayout = getSwipeRefreshLayout();
        swipeRefreshLayout.setBackgroundColor(Color.parseColor("#f2f2f2"));
        FrameLayout bottomFrameLayout = getBottomFrameLayout();
        bottomFrameLayout.setBackgroundColor(Color.parseColor("#f2f2f2"));
        enableLoadMore(false);
        View viewBottom =
                LayoutInflater.from(getContext())
                        .inflate(R.layout.consultation_details_rewared_bottom_button, null, false);
        viewBottom.setLayoutParams(
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT));
        addBottomSuspensionView(viewBottom);
        // 悬赏
        ll_reward = viewBottom.findViewById(R.id.ll_reward);
        // 未悬赏
        ll_unrewarded = viewBottom.findViewById(R.id.ll_unrewarded);
        // 置顶咨询
        viewBottom
                .findViewById(R.id.tv_top_now)
                .setOnClickListener(
                        v -> {
                            String consultationId = getArgumentString(KEY_CONSULTATION_ID, "");
                            int type = 16;
                            TopPaymentActivity.start(getContext(), consultationId, type);
                        });
        viewBottom
                .findViewById(R.id.tv_unrewarded_top_now)
                .setOnClickListener(
                        v -> {
                            String consultationId = getArgumentString(KEY_CONSULTATION_ID, "");
                            int type = 16;
                            TopPaymentActivity.start(getContext(), consultationId, type);
                        });
        // 我要悬赏
        viewBottom
                .findViewById(R.id.tv_unrewarded_i_want_a_reward)
                .setOnClickListener(
                        v -> {
                            String consultationId = getArgumentString(KEY_CONSULTATION_ID, "");
                            int type = 14;
                            WantRewardActivity.start(getContext(), consultationId, type);
                        });
        // 追加悬赏
        viewBottom
                .findViewById(R.id.tv_add_a_reward)
                .setOnClickListener(
                        v -> {
                            String consultationId = getArgumentString(KEY_CONSULTATION_ID, "");
                            int type = 18;
                            WantRewardActivity.start(getContext(), consultationId, type);
                        });
        // 增加进入动画
        //        mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft);
        //        mAdapter.setAnimationEnable(true);

        notUnlockedHeader.getUnlockClickListener(
                new ConsultationDetailsNotUnlockedHeader.UnlockClickListener() {
                    @Override
                    public void unlockClick(ConsultationDetailsBean.ContentBean contentBean) {
                        showDialog(contentBean);
                    }
                });
    }

    private void showDialog(ConsultationDetailsBean.ContentBean contentBean) {
        UnlockDialog unlockDialog = new UnlockDialog(mContext);
        unlockDialog.setUnlockTitle(contentBean.getJiesuo_title());
        unlockDialog.setUnlockDescription(contentBean.getJiesuo_miaoshu());
        unlockDialog.setUnlockViceTitle(contentBean.getJiesuo_futitle());
        unlockDialog.setUnlockViceDescription(contentBean.getJiesuo_fumiaoshu());
        new XPopup.Builder(getContext())
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .setPopupCallback(
                        new XPopupCallback() {
                            @Override
                            public void onCreated() {
                                unlockDialog.prepare();
                                unlockDialog.setCloseClick(v -> unlockDialog.dismiss());
                                unlockDialog.setUnlockNowClick(
                                        v -> {
                                            unlockDialog.dismiss();
                                            showPaySelectDialor(contentBean.getJiesuo_price());
                                        });
                            }

                            @Override
                            public void beforeShow() {}

                            @Override
                            public void onShow() {}

                            @Override
                            public void onDismiss() {}

                            @Override
                            public boolean onBackPressed() {
                                return false;
                            }
                        })
                .asCustom(unlockDialog)
                .show();
    }

    // ===Desc:================================================================================

    // 支付选择框
    private void showPaySelectDialor(String jiesuo_price) {
        double money;
        try {
            money = Double.parseDouble(jiesuo_price);
        } catch (NumberFormatException e) {
            showToast("应用程序出现未知错误,请稍后重试");
            return;
        }
        DialogHelper.showPayDialog(
                getContext(),
                money,
                new PayDialog.OnCommitListener() {
                    @Override
                    public void onCommit(PayDialog.PayMethod payMethod) {
                        String uid = SpUtil.getString(mContext, AppConstant.UID, "");
                        String consultationId = getArgumentString(KEY_CONSULTATION_ID, "");
                        // 解锁咨询 type = 19
                        int type = 19;
                        switch (payMethod) {
                            case PAY_WECHAT:
                                getPresenter().payWatch(uid, consultationId, type, 1, "", "");
                                break;
                            case PAY_ALI:
                                getPresenter().payAli(uid, consultationId, type, 1, "", "");
                                break;
                            case PAY_SELF:
                                getPresenter().payOverage(uid, consultationId, type, 1, "", "");
                                break;
                        }
                    }
                });
    }

    @Override
    protected BaseQuickAdapter<ConsultationDetailsBean.XiaoxiArrBean, BaseViewHolder>
            createAdapter() {
        return new ConsultationDetailsListAdaper();
    }

    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        LogUtils.e("Is load more < --- > " + isLoadMore);

        String id = getArgumentString(KEY_CONSULTATION_ID, "");
        if (!isLoadMore) {
            getPresenter().getConsultationDetails(isLoadMore, id);
        }
        stopRefresh();
        loadMoreEnd(false);
    }

    private CountDownTimer timer;
    int index = 0;

    @Override
    public void onConsultationDetailsSuccess(ConsultationDetailsBean consultationDetailsBean) {
        hideLoading();
        if (consultationDetailsBean == null) {
            onConsultationDetailsFiled();
            return;
        }
        if (consultationDetailsBean.getContent() == null) {
            onConsultationDetailsFiled();
            return;
        }
        detailsRewardHeader.render(consultationDetailsBean.getContent());
        // 追加悬赏
        ll_reward.setVisibility(View.VISIBLE);
        // 我要悬赏
        ll_unrewarded.setVisibility(View.GONE);
        // unlock_type 判断是否解锁添加header  unlock_type ==0时是未解锁

        if ("0".equals(consultationDetailsBean.getContent().getUnlock_type())) {
            // 追加悬赏
            ll_reward.setVisibility(View.GONE);
            // 我要悬赏
            ll_unrewarded.setVisibility(View.VISIBLE);
            notUnlockedHeader.render(consultationDetailsBean.getContent());
            getAdapter().removeHeaderView(notUnlockedHeader.getHeaderView());
            getAdapter().addHeaderView(notUnlockedHeader.getHeaderView());
            // 未解锁  重置点击事件
            getAdapter()
                    .setOnItemClickListener(
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(
                                        @NonNull BaseQuickAdapter<?, ?> adapter,
                                        @NonNull View view,
                                        int position) {
                                    showDialog(consultationDetailsBean.getContent());
                                }
                            });
            getAdapter()
                    .setOnItemChildClickListener(
                            (adapter, view, position) -> {
                                if (view.getId() == R.id.tv_communicate_now) {
                                    showDialog(consultationDetailsBean.getContent());
                                }
                            });
            List<ConsultationDetailsBean.XiaoxiArrBean> xiaoxi_arr =
                    consultationDetailsBean.getXiaoxi_arr();

            if (consultationDetailsBean.getContent().getIs_first() == 0) {
                // 显示动画
                int size = xiaoxi_arr.size();
                long millisInFuture = 1000L * (size - 1);
                if (null == timer) {
                    getAdapter().getData().clear();
                    timer =
                            new CountDownTimer(millisInFuture, 1000) {
                                @Override
                                public void onTick(long l) {
                                    getAdapter().addData(xiaoxi_arr.get(index));
                                    getRecycleView()
                                            .smoothScrollToPosition(getAdapter().getData().size());
                                    index++;
                                }

                                @Override
                                public void onFinish() {
                                    index = 0;
                                    int scrollTo;
                                    if (isScrollCancelByUser) {
                                        scrollTo = getAdapter().getData().size();
                                    } else {
                                        scrollTo = xiaoxi_arr.size();
                                    }
                                    isScrollCancelByUser = false;
                                    getAdapter().setNewInstance(xiaoxi_arr);
                                    getRecycleView().scrollToPosition(scrollTo);
                                    if (null != timer) {
                                        timer.cancel();
                                    }
                                    timer = null;
                                }
                            };
                    timer.start();
                }

            } else {
                getAdapter().setNewInstance(xiaoxi_arr);
            }
        } else {
            getAdapter().removeHeaderView(notUnlockedHeader.getHeaderView());
            // 律师回复立即沟通点击
            mAdapter.setOnItemChildClickListener(
                    (adapter, view, position) -> {
                        if (view.getId() == R.id.tv_communicate_now) {
                            UserChatRoomActivity.start(
                                    getActivity(),
                                    false,
                                    false,
                                    mAdapter.getData().get(position).getUid(),
                                    mAdapter.getData().get(position).getUser_login(),
                                    mAdapter.getData().get(position).getAvatar());
                        }
                    });
            // 律师回复item点击
            mAdapter.setOnItemClickListener(
                    (adapter, view, position) -> {
                        AdvisoryReplyDetailActivity.start(
                                getContext(),
                                mAdapter.getData().get(position).getId(),
                                mAdapter.getData().get(position).getUid(),
                                mAdapter.getData().get(position).getCon_id());
                    });
        }
        // con_type 判断隐藏底部按钮 con_type !=0时隐藏
        String con_type = consultationDetailsBean.getContent().getCon_type();
        if (!"0".equals(con_type)) {
            // 追加悬赏
            ll_reward.setVisibility(View.GONE);
            // 我要悬赏
            ll_unrewarded.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWechatPaySuccess(MessageEvent event) {
        int what = event.getWhat();
        if (what == AppConstant.WX_PAY_SUCCESS) {
            // 更新支付成功UI显示
            //            MineConsultationListActivity.start(mContext);
            getPresenter().refresh();
            showCenterToast("支付成功");
        }
    }

    @Override
    public void onAwakeAli(String sign) {
        PayHelper.getInstance()
                .setIPayListener(
                        new PayHelper.IPayListener() {
                            @Override
                            public void onSuccess() {
                                getPresenter().refresh();
                                showCenterToast("支付成功");
                            }

                            @Override
                            public void onResultConfirmation() {}

                            @Override
                            public void onCancel() {}

                            @Override
                            public void onFail() {
                                showToast("支付出现错误,请稍后重试");
                            }
                        });
        PayHelper.getInstance().AliPay(getActivity(), sign);
    }

    @Override
    public void onPayByAccountSuccess(AccountPayResponseBean payResponseBean) {
        if (payResponseBean.getStatus() == 1) {
            getPresenter().refresh();
            showCenterToast("支付成功");
        } else {
            showCenterToast("支付失败");
        }
    }

    @Override
    public void onConsultationDetailsFiled() {}

    @Override
    public void onDestroy() {
        if (null != timer) {
            timer.cancel();
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        notUnlockedHeader.onDestroy();
    }
}

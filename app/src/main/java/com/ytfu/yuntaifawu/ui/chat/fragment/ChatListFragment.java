package com.ytfu.yuntaifawu.ui.chat.fragment; // package com.ytfu.yuntaifawu.ui.chat.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.ui.eventbus.EventBusManager;
import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.lxj.xpopup.util.XPopupUtils;
import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.MessageService;
import com.ytfu.yuntaifawu.app.App;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseFragment;
import com.ytfu.yuntaifawu.constant.EventBusConstantKt;
import com.ytfu.yuntaifawu.db.LvShiDao;
import com.ytfu.yuntaifawu.domain.EventBusMessage;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.helper.RxLifecycleUtil;
import com.ytfu.yuntaifawu.im.EmChatManager;
import com.ytfu.yuntaifawu.ui.chat.adapter.LawyerListAdapter;
import com.ytfu.yuntaifawu.ui.chat.adapter.MessageUnLockAdapter;
import com.ytfu.yuntaifawu.ui.chat.bean.LawyerListBean;
import com.ytfu.yuntaifawu.ui.chat.bean.UnLockMessage;
import com.ytfu.yuntaifawu.ui.chat.p.ChatListPresenter;
import com.ytfu.yuntaifawu.ui.chat.v.IChatListView;
import com.ytfu.yuntaifawu.ui.chatroom.activity.UserChatRoomActivity;
import com.ytfu.yuntaifawu.ui.lawyer.chat.act.LawyerChatRoomActivity;
import com.ytfu.yuntaifawu.ui.mseeage.activity.ChatActivity;
import com.ytfu.yuntaifawu.ui.mseeage.bean.ConversationBean;
import com.ytfu.yuntaifawu.ui.mseeage.bean.HistoryRecordResponseBean;
import com.ytfu.yuntaifawu.ui.pay.PayHelper;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;
import com.ytfu.yuntaifawu.utils.LoginHelper;
import com.ytfu.yuntaifawu.utils.MessageEvent;
import com.ytfu.yuntaifawu.utils.RecyclerViewItemDecoration;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.dialog.DialogHelper;
import com.ytfu.yuntaifawu.utils.dialog.PayDialog;
import com.ytfu.yuntaifawu.utils.dialog.UnLockMessageClickDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import qiu.niorgai.StatusBarCompat;

import static com.ytfu.yuntaifawu.utils.ToastUtil.showCenterToast;

/** 聊天列表 */
@InjectPresenter(ChatListPresenter.class)
@InjectLayout(
        value = R.layout.fragment_message,
        toolbarLayoutId = R.layout.layout_toolbar_chat_list)
public class ChatListFragment extends BaseFragment<IChatListView, ChatListPresenter>
        implements IChatListView {

    private RecyclerView recyele_view;
    private RecyclerView rv_message_un_lock;
    private SwipeRefreshLayout srl_message_refresh;
    private SwipeRefreshLayout srl_message_unlock;
    //    private TextView tvTitle;
    private LawyerListAdapter adapter;
    private MessageUnLockAdapter adapterUnlock;
    private Handler mHandler = new Handler();

    private View selectAllView;

    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (null != adapter) {
            resetSelectAll();
            adapter.removeSelectAll();
        }
        if (!hidden) {
            FragmentActivity activity = getActivity();
            if (null != activity) {
                if (SpUtil.getString(mContext, AppConstant.SHENFEN, "1").equals("2")) {
                    StatusBarCompat.setStatusBarColor(
                            activity, getResources().getColor(R.color.transparent_4c));
                } else {
                    StatusBarCompat.setStatusBarColor(activity, Color.WHITE);
                    FragmentActivity fragmentActivity = getActivity();
                    if (fragmentActivity != null) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                            int visibility;
                            visibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                            fragmentActivity
                                    .getWindow()
                                    .getDecorView()
                                    .setSystemUiVisibility(visibility);
                        }
                    }
                }
            }
        }
    }

    private TextView tvALl;
    private CheckBox cbALl;
    private boolean clickItem = false;

    @Override
    public void init() {
        super.init();
        selectAllView = inflateView(R.layout.view_chat_list_head);
        adapter = new LawyerListAdapter();
        cbALl = selectAllView.findViewById(R.id.cb_all);
        tvALl = selectAllView.findViewById(R.id.tv_all);

        cbALl.setOnCheckedChangeListener(
                (compoundButton, b) -> {
                    if (b) {
                        tvALl.setText("全不选");
                        adapter.selectAll();
                    } else {
                        tvALl.setText("全选");
                        if (!clickItem) {
                            adapter.removeSelectAll();
                        }
                    }
                });
        tvALl.setOnClickListener(view -> cbALl.setChecked(!cbALl.isChecked()));

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void setViewListener(View rootView) {
        super.setViewListener(rootView);
        if (null != mToolbar) {
            TextView tvEdit = mToolbar.findViewById(R.id.tv_chat_edit);
            TextView tvDelete = mToolbar.findViewById(R.id.tv_chat_delete);
            if (!LoginHelper.getInstance().isUserLogin()) {
                tvEdit.setBackgroundColor(Color.TRANSPARENT);
                tvDelete.setBackgroundColor(Color.TRANSPARENT);
                tvEdit.setTextColor(Color.WHITE);
                tvDelete.setTextColor(Color.WHITE);
            }
            tvEdit.setOnClickListener(
                    view -> {
                        if (adapter.isAnimIsRunning()) {
                            return;
                        }
                        adapter.setEdit(!adapter.isEdit());
                        boolean edit = adapter.isEdit();
                        if (edit) {
                            tvEdit.setText("取消");
                            tvDelete.setVisibility(View.VISIBLE);
                            // 添加头部
                            adapter.removeHeaderView(selectAllView);
                            adapter.addHeaderView(selectAllView);
                        } else {
                            resetSelectAll();
                        }
                    });
            tvDelete.setOnClickListener(
                    view -> {
                        String ids = adapter.getSelect();
                        if (TextUtils.isEmpty(ids)) {
                            return;
                        }
                        getPresenter()
                                .deleteConversation(
                                        LoginHelper.getInstance().getLoginUserId(), ids);
                    });
        }
    }

    @Override
    protected void initView(View rootView) {
        getPresenter().registerMessageListener();
        recyele_view = rootView.findViewById(R.id.rv_message_content);
        rv_message_un_lock = rootView.findViewById(R.id.rv_message_un_lock);
        srl_message_refresh = rootView.findViewById(R.id.srl_message_refresh);
        srl_message_unlock = rootView.findViewById(R.id.srl_message_unlock);

        String type = SpUtil.getString(mContext, AppConstant.SHENFEN, "1");
        if (type.equals("2")) {
            rootView.findViewById(R.id.tl_title_toolbar)
                    .setBackgroundColor(getResources().getColor(R.color.transparent_4c));
            setToolbarTextColor(R.id.tv_toolbar_title, Color.WHITE);
        } else {
            rootView.findViewById(R.id.tl_title_toolbar).setBackgroundColor(Color.WHITE);
            setToolbarTextColor(R.id.tv_toolbar_title, Color.parseColor("#434343"));
        }
    }

    @Override
    public void onResume() {
        reLoadList();
        super.onResume();
    }

    @Override
    protected void initData() {
        setToolbarText(R.id.tv_toolbar_title, "消息");
        // 设置用户信息名字，头像等
        EaseUI.getInstance()
                .setUserProfileProvider(
                        username -> {
                            EaseUser easeUser = new EaseUser(username);
                            if (!TextUtils.isEmpty(username)) {
                                List<ConversationBean.MsgBean> beanList =
                                        LvShiDao.getInstance(getContext()).lvshiSelect();
                                String s = username.toLowerCase();
                                for (int i = 0; i < beanList.size(); i++) {
                                    ConversationBean.MsgBean msgBean = beanList.get(i);
                                    Logger.e("ss = " + s + ", pic = " + msgBean.getPicurl());

                                    if (!TextUtils.isEmpty(msgBean.getLid())) {
                                        String s1 = msgBean.getLid().toLowerCase();
                                        if (s.equals(s1)) {
                                            if (!TextUtils.isEmpty(msgBean.getPicurl())) {
                                                easeUser.setAvatar(msgBean.getPicurl());
                                                Logger.e(
                                                        "s = "
                                                                + s
                                                                + ", pic = "
                                                                + msgBean.getPicurl());
                                            }
                                            easeUser.setNickname(msgBean.getName());
                                            return easeUser;
                                        }
                                    }
                                    EMConversation conversation2 =
                                            EMClient.getInstance()
                                                    .chatManager()
                                                    .getConversation(
                                                            username,
                                                            EMConversation.EMConversationType.Chat,
                                                            true);
                                    if (conversation2 != null) {
                                        EMMessage lastMessage = conversation2.getLastMessage();
                                        if (lastMessage != null) {
                                            conversation2.appendMessage(lastMessage);
                                        }
                                    }
                                }
                            }

                            //            EaseUser user = new
                            // EaseUser(lawyerItemBean.getConversationId().toLowerCase());
                            //            user.setNickname(lawyerItemBean.getNickName());
                            //            user.setAvatar(lawyerItemBean.getHeaderImage());
                            //            DemoHelper.getInstance().getModel().saveContact(user);

                            return null;
                        });

        srl_message_refresh.setColorSchemeColors(
                generateBeautifulColor(),
                generateBeautifulColor(),
                generateBeautifulColor(),
                generateBeautifulColor());
        srl_message_refresh.setOnRefreshListener(
                () -> {
                    if (App.getInstance().getLoginFlag()) {
                        reLoadList();
                    }
                });

        srl_message_unlock.setOnRefreshListener(
                () -> {
                    if (App.getInstance().getLoginFlag()) {
                        reLoadList();
                    }
                });

        int lineColor = Color.parseColor("#F5F5F5");
        recyele_view.addItemDecoration(RecyclerViewItemDecoration.createVertical(lineColor, 1, 0));

        recyele_view.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.getLoadMoreModule().setEnableLoadMore(true);
        recyele_view.setAdapter(adapter);

        adapterUnlock = new MessageUnLockAdapter();
        adapterUnlock.getLoadMoreModule().setEnableLoadMore(true);
        rv_message_un_lock.setLayoutManager(new LinearLayoutManager(getContext()));
        int lineHeight = XPopupUtils.dp2px(mContext, 15);
        rv_message_un_lock.addItemDecoration(
                RecyclerViewItemDecoration.createVertical(lineColor, lineHeight, 0));
        rv_message_un_lock.setAdapter(adapterUnlock);
        adapter.setOnItemClickListener(
                (adapter, view, position) -> {
                    if (ChatListFragment.this.adapter.isEdit()) {
                        ChatListFragment.this.adapter.addOrRemoveSelect(
                                ChatListFragment.this.adapter.getData().get(position));
                        List<LawyerListBean.LawyerItemBean> selected =
                                ChatListFragment.this.adapter.getSelected();
                        clickItem = true;
                        if (LoginHelper.getInstance().isUserLogin()) {
                            if (selected.size()
                                    == ChatListFragment.this.adapter.getData().size() - 1) {
                                cbALl.setChecked(true);
                            } else {
                                cbALl.setChecked(false);
                            }
                        } else {
                            if (selected.size() == ChatListFragment.this.adapter.getData().size()) {
                                cbALl.setChecked(true);
                            } else {
                                cbALl.setChecked(false);
                            }
                        }
                        clickItem = false;
                        return;
                    }
                    LawyerListBean.LawyerItemBean lawyerItemBean =
                            ChatListFragment.this.adapter.getData().get(position);
                    boolean isCustomerService = false;
                    if (lawyerItemBean.getType() == 1) {
                        isCustomerService = true;
                    }
                    String toUserId = lawyerItemBean.getConversationId();
                    String toUserName = lawyerItemBean.getNickName();
                    String toUserAvatar = lawyerItemBean.getHeaderImage();

                    if (isCustomerService) {
                        UserChatRoomActivity.start(
                                mContext, true, false, toUserId, toUserName, toUserAvatar);
                        return;
                    }

                    String type = SpUtil.getString(mContext, AppConstant.SHENFEN, "1");
                    if ("1".equals(type)) {
                        //                LawyerChatRoomActivity.start(mContext, false, toUserId,
                        // toUserName, toUserAvatar, "");
                        Runnable run =
                                () ->
                                        UserChatRoomActivity.start(
                                                mContext,
                                                false,
                                                false,
                                                toUserId,
                                                toUserName,
                                                toUserAvatar);
                        run.run();
                        // getPresenter().getConsultType(LoginHelper.getInstance().getLoginUserId(),
                        // run);
                    } else {
                        LawyerChatRoomActivity.start(
                                mContext, false, toUserId, toUserName, toUserAvatar, "");
                    }
                });
        if (App.getInstance().getLoginFlag()) {
            reLoadList();
        }

        //        getPresenter().test(LoginHelper.getInstance().getLoginUserId());
    }

    @Override
    public void onDestroy() {
        getPresenter().unRegisterMessageListener();
        super.onDestroy();
    }

    // ===Desc:=================================================================

    private void resetSelectAll() {
        adapter.resetSelect();
        cbALl.setChecked(false);
        tvALl.setText("全选");
        if (null != mToolbar) {
            mToolbar.findViewById(R.id.tv_chat_delete).setVisibility(View.INVISIBLE);
            TextView tv = mToolbar.findViewById(R.id.tv_chat_edit);
            tv.setText("编辑");
        }
        adapter.removeHeaderView(selectAllView);
        //        selectAllView.measure(0, 0);
        //        int height = selectAllView.getMeasuredHeight();
        //        ValueAnimator anim = ValueAnimator.ofInt(height, 0);
        //        anim.addListener(new Animator.AnimatorListener() {
        //            @Override
        //            public void onAnimationStart(Animator animator) {
        //                canBeClick = false;
        //            }
        //
        //            @Override
        //            public void onAnimationEnd(Animator animator) {
        //                adapter.removeHeaderView(selectAllView);
        //                canBeClick = true;
        //            }
        //
        //            @Override
        //            public void onAnimationCancel(Animator animator) {
        //
        //            }
        //
        //            @Override
        //            public void onAnimationRepeat(Animator animator) {
        //
        //            }
        //        });
        //        anim.addUpdateListener(valueAnimator -> {
        //            if (null != selectAllView.getLayoutParams()) {
        //                selectAllView.getLayoutParams().height = (int)
        // valueAnimator.getAnimatedValue();
        //                selectAllView.requestLayout();
        //            }
        //        });
        //        anim.setDuration(150);
        //        anim.start();
    }

    @Override
    public void onLawyerListSuccess(List<LawyerListBean.LawyerItemBean> list) {
        resetSelectAll();

        srl_message_unlock.setVisibility(View.GONE);
        srl_message_refresh.setVisibility(View.VISIBLE);

        List<LawyerListBean.LawyerItemBean> data = new ArrayList<>();
        for (LawyerListBean.LawyerItemBean item : list) {
            if (item.getType() == 1) {
                data.add(0, item);
            } else {
                data.add(item);
            }
        }
        if (data.isEmpty()) {
            adapter.setEmptyView(R.layout.layout_empty);
        } else {
            adapter.setNewInstance(data);
        }
        hideLoading();
        srl_message_refresh.setRefreshing(false);
    }

    @Override
    public void onLawyerListSuccess2(
            List<LawyerListBean.LawyerItemBean> list, UnLockMessage unLockMessage) {
        adapter.setOnItemClickListener(
                (adapter, view, position) -> {
                    LawyerListBean.LawyerItemBean lawyerItemBean =
                            ChatListFragment.this.adapter.getData().get(position);
                    if (lawyerItemBean.getType() == 1) {
                        // 客服号
                        String toUserId = lawyerItemBean.getConversationId();
                        String toUserName = lawyerItemBean.getNickName();
                        String toUserAvatar = lawyerItemBean.getHeaderImage();
                        String type = SpUtil.getString(mContext, AppConstant.SHENFEN, "1");
                        if ("1".equals(type)) {
                            UserChatRoomActivity.start(
                                    mContext, true, false, toUserId, toUserName, toUserAvatar);
                        } else {
                            LawyerChatRoomActivity.start(
                                    mContext, false, toUserId, toUserName, toUserAvatar, "");
                        }
                    } else {
                        UnLockMessageClickDialog dialog = new UnLockMessageClickDialog(mContext);
                        new XPopup.Builder(mContext)
                                .dismissOnBackPressed(false)
                                .dismissOnTouchOutside(false)
                                .setPopupCallback(
                                        new XPopupCallback() {
                                            @Override
                                            public void onCreated() {}

                                            @Override
                                            public void beforeShow() {
                                                dialog.setUnlockClickListener(
                                                        contentBean -> {
                                                            dialog.dismiss();
                                                            // 支付
                                                            double price =
                                                                    unLockMessage
                                                                            .getXiangqin()
                                                                            .getJiesuo_price();
                                                            DialogHelper.showPayDialog(
                                                                    mContext,
                                                                    price,
                                                                    new PayDialog
                                                                            .OnCommitListener() {
                                                                        @Override
                                                                        public void onCommit(
                                                                                PayDialog.PayMethod
                                                                                        payMethod) {
                                                                            String uid =
                                                                                    SpUtil
                                                                                            .getString(
                                                                                                    mContext,
                                                                                                    AppConstant
                                                                                                            .UID,
                                                                                                    "");
                                                                            String consultationId =
                                                                                    unLockMessage
                                                                                            .getXiangqin()
                                                                                            .getCon_id();
                                                                            // 解锁咨询 type = 19
                                                                            int type = 19;
                                                                            switch (payMethod) {
                                                                                case PAY_WECHAT:
                                                                                    getPresenter()
                                                                                            .payWatch(
                                                                                                    uid,
                                                                                                    consultationId,
                                                                                                    type,
                                                                                                    1,
                                                                                                    "",
                                                                                                    "");
                                                                                    break;
                                                                                case PAY_ALI:
                                                                                    getPresenter()
                                                                                            .payAli(
                                                                                                    uid,
                                                                                                    consultationId,
                                                                                                    type,
                                                                                                    1,
                                                                                                    "",
                                                                                                    "");
                                                                                    break;
                                                                                case PAY_SELF:
                                                                                    getPresenter()
                                                                                            .payOverage(
                                                                                                    uid,
                                                                                                    consultationId,
                                                                                                    type,
                                                                                                    1,
                                                                                                    "",
                                                                                                    "");
                                                                                    break;
                                                                            }
                                                                        }
                                                                    });
                                                        });

                                                dialog.render(unLockMessage.getXiangqin());
                                            }

                                            @Override
                                            public void onShow() {}

                                            @Override
                                            public void onDismiss() {}

                                            @Override
                                            public boolean onBackPressed() {
                                                return false;
                                            }
                                        })
                                .asCustom(dialog)
                                .show();
                    }
                });
        onLawyerListSuccess(list);
    }

    @Override
    public void onLawyerListFail(String msg) {
        mHandler.post(
                () -> {
                    adapter.setEmptyView(R.layout.layout_error);
                    //  ToastUtil.showToast(msg);
                    srl_message_refresh.setRefreshing(false);
                    hideLoading();
                });
    }

    /** 从服务器获取你是聊天数据显示 */
    private void getHistoryRecord(LawyerListBean.LawyerItemBean lawyerItemBean) {
        String uid = SpUtil.getString(mContext, AppConstant.UID, "");
        String sourceStr = SpUtil.getString(mContext, AppConstant.SHENFEN, "1");
        int source;
        try {
            source = Integer.parseInt(sourceStr);
        } catch (NumberFormatException e) {
            source = 1;
        }
        String type = SpUtil.getString(mContext, AppConstant.SHENFEN, "1");
        String to;
        String from;
        if ("1".equals(type)) {
            // User
            to = lawyerItemBean.getConversationId();
            from = uid;
        } else {
            to = uid;
            from = lawyerItemBean.getConversationId();
        }
        HttpUtil.getInstance()
                .getService(MessageService.class)
                .getHistoryRecord(from, to, source)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLifecycleUtil.bindLifecycle(this))
                .subscribe(
                        new BaseRxObserver<HistoryRecordResponseBean>() {
                            @Override
                            public void onSubscribeImpl(Disposable d) {
                                super.onSubscribeImpl(d);
                                showWaitingDialog("", true);
                            }

                            @Override
                            public void onNextImpl(HistoryRecordResponseBean bean) {
                                if ("200".equals(bean.getCode())) {
                                    List<EMMessage> msgs = new ArrayList<>();
                                    for (HistoryRecordResponseBean.DataBean item : bean.getData()) {
                                        EMMessage message = null;
                                        // 自己发送的
                                        if (item.getDirection() == 0) {
                                            message =
                                                    EMMessage.createTxtSendMessage(
                                                            item.getBody().getText(), item.getTo());
                                            message.setDirection(EMMessage.Direct.SEND);
                                            message.setFrom(item.getFrom());
                                            message.setTo(item.getTo());
                                        } else { // 律师发送的
                                            message =
                                                    EMMessage.createReceiveMessage(
                                                            EMMessage.Type.TXT);
                                            EMTextMessageBody body =
                                                    new EMTextMessageBody(item.getBody().getText());
                                            message.addBody(body);
                                            message.setDirection(EMMessage.Direct.RECEIVE);
                                            message.setChatType(EMMessage.ChatType.GroupChat);
                                            message.setFrom(item.getFrom());
                                            Logger.e("form == " + item.getFrom());
                                            message.setTo(item.getTo());
                                        }
                                        //
                                        // message.setFrom(item.getFrom());
                                        //
                                        // message.setTo(item.getTo());

                                        Logger.e(
                                                "c id = "
                                                        + message.conversationId()
                                                        + ", from : "
                                                        + item.getFrom()
                                                        + ",, to : "
                                                        + item.getTo());
                                        //   EMMessage message =
                                        // EMMessage.createReceiveMessage(EMMessage.Type.TXT);
                                        message.setChatType(EMMessage.ChatType.Chat);
                                        message.setStatus(EMMessage.Status.SUCCESS);
                                        message.setUnread(false);
                                        message.setDelivered(true);
                                        message.setMsgId(item.getMessageId());
                                        message.setMsgTime(item.getTimestamp() * 1000);
                                        message.setLocalTime(item.getTimestamp() * 1000);
                                        message.setChatType(EMMessage.ChatType.Chat);
                                        message.setStatus(EMMessage.Status.SUCCESS);
                                        message.setAcked(item.getIsRead() == 1);
                                        message.setProgress(100);

                                        String conversationId = "";
                                        if (message.direct() == EMMessage.Direct.RECEIVE) {
                                            conversationId = item.getFrom();
                                        } else {
                                            conversationId = item.getTo();
                                        }
                                        EMClient.getInstance()
                                                .chatManager()
                                                .getConversation(
                                                        conversationId,
                                                        EMConversation.EMConversationType.GroupChat,
                                                        true)
                                                .insertMessage(message);
                                        //                                EMClient.getInstance()
                                        //
                                        // .chatManager().saveMessage(message);
                                    }
                                }

                                // ChatRoomActivity.start(getContext(),
                                // lawyerItemBean,bean.getData());
                                Intent intent = new Intent(getActivity(), ChatActivity.class);
                                intent.putExtra("name", lawyerItemBean.getNickName());
                                intent.putExtra(
                                        AppConstant.EXTRA_USER_ID,
                                        lawyerItemBean.getConversationId());
                                intent.putExtra("picurl", lawyerItemBean.getHeaderImage());
                                intent.putExtra("type", String.valueOf(lawyerItemBean.getType()));
                                startActivity(intent);
                                hideWaitingDialog();
                            }

                            @Override
                            public void onErrorImpl(String errorMessage) {
                                hideWaitingDialog();
                            }
                        });
    }

    private @ColorInt int generateBeautifulColor() {
        Random random = new Random();
        int r = random.nextInt(150) + 50;
        int g = random.nextInt(150) + 50;
        int b = random.nextInt(150) + 50;
        return Color.rgb(r, g, b);
    }

    @Override
    public void reLoadList() {
        mHandler.post(
                () -> {
                    hideLoading();
                    String uid = SpUtil.getString(mContext, AppConstant.UID, "");
                    if (LoginHelper.getInstance().isUserLogin()) {
                        getPresenter().loadLawyerList(uid);
                    } else {
                        getPresenter().loadLawyerList(uid);
                    }
                });
    }

    @Override
    public void onShowUnLockMessage(UnLockMessage unLockMessage) {
        srl_message_unlock.setVisibility(View.VISIBLE);
        srl_message_refresh.setVisibility(View.GONE);

        adapterUnlock.getData().clear();
        adapterUnlock.setNewInstance(unLockMessage.getXiangqin().getXiaoxi_arr());

        EventBusMessage eventBusMessage = new EventBusMessage();
        eventBusMessage.what = 100;
        eventBusMessage.obj = adapterUnlock.getData().size();
        EventBus.getDefault().postSticky(eventBusMessage);

        adapterUnlock.setOnItemClickListener(
                (adapter, view, position) -> {
                    UnLockMessageClickDialog dialog = new UnLockMessageClickDialog(mContext);
                    new XPopup.Builder(mContext)
                            .dismissOnBackPressed(false)
                            .dismissOnTouchOutside(false)
                            .setPopupCallback(
                                    new XPopupCallback() {
                                        @Override
                                        public void onCreated() {}

                                        @Override
                                        public void beforeShow() {
                                            dialog.setUnlockClickListener(
                                                    contentBean -> {
                                                        dialog.dismiss();
                                                        // 支付
                                                        double price =
                                                                unLockMessage
                                                                        .getXiangqin()
                                                                        .getJiesuo_price();
                                                        DialogHelper.showPayDialog(
                                                                mContext,
                                                                price,
                                                                new PayDialog.OnCommitListener() {
                                                                    @Override
                                                                    public void onCommit(
                                                                            PayDialog.PayMethod
                                                                                    payMethod) {
                                                                        String uid =
                                                                                SpUtil.getString(
                                                                                        mContext,
                                                                                        AppConstant
                                                                                                .UID,
                                                                                        "");
                                                                        String consultationId =
                                                                                unLockMessage
                                                                                        .getXiangqin()
                                                                                        .getCon_id();
                                                                        // 解锁咨询 type = 19
                                                                        int type = 19;
                                                                        switch (payMethod) {
                                                                            case PAY_WECHAT:
                                                                                getPresenter()
                                                                                        .payWatch(
                                                                                                uid,
                                                                                                consultationId,
                                                                                                type,
                                                                                                1,
                                                                                                "",
                                                                                                "");
                                                                                break;
                                                                            case PAY_ALI:
                                                                                getPresenter()
                                                                                        .payAli(
                                                                                                uid,
                                                                                                consultationId,
                                                                                                type,
                                                                                                1,
                                                                                                "",
                                                                                                "");
                                                                                break;
                                                                            case PAY_SELF:
                                                                                getPresenter()
                                                                                        .payOverage(
                                                                                                uid,
                                                                                                consultationId,
                                                                                                type,
                                                                                                1,
                                                                                                "",
                                                                                                "");
                                                                                break;
                                                                        }
                                                                    }
                                                                });
                                                    });

                                            dialog.render(unLockMessage.getXiangqin());
                                        }

                                        @Override
                                        public void onShow() {}

                                        @Override
                                        public void onDismiss() {}

                                        @Override
                                        public boolean onBackPressed() {
                                            return false;
                                        }
                                    })
                            .asCustom(dialog)
                            .show();
                });
        srl_message_unlock.setRefreshing(false);
    }

    // ===Desc:================================================================================

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWechatPaySuccess(MessageEvent event) {
        int what = event.getWhat();
        if (what == AppConstant.WX_PAY_SUCCESS) {
            reLoadList();
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
                                reLoadList();
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
            reLoadList();
            showCenterToast("支付成功");
        } else {
            showCenterToast("支付失败");
        }
    }

    @Override
    public void onMessageReceived(List<LawyerListBean.LawyerItemBean> list) {}

    @Override
    public void showPayDialog(UnLockMessage unLockMessage) {
        UnLockMessageClickDialog dialog = new UnLockMessageClickDialog(mContext);
        new XPopup.Builder(mContext)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .setPopupCallback(
                        new XPopupCallback() {
                            @Override
                            public void onCreated() {}

                            @Override
                            public void beforeShow() {
                                dialog.setUnlockClickListener(
                                        contentBean -> {
                                            dialog.dismiss();
                                            // 支付
                                            double price =
                                                    unLockMessage.getXiangqin().getJiesuo_price();
                                            DialogHelper.showPayDialog(
                                                    mContext,
                                                    price,
                                                    new PayDialog.OnCommitListener() {
                                                        @Override
                                                        public void onCommit(
                                                                PayDialog.PayMethod payMethod) {
                                                            String uid =
                                                                    SpUtil.getString(
                                                                            mContext,
                                                                            AppConstant.UID,
                                                                            "");
                                                            String consultationId =
                                                                    unLockMessage
                                                                            .getXiangqin()
                                                                            .getCon_id();
                                                            // 解锁咨询 type = 19
                                                            int type = 19;
                                                            switch (payMethod) {
                                                                case PAY_WECHAT:
                                                                    getPresenter()
                                                                            .payWatch(
                                                                                    uid,
                                                                                    consultationId,
                                                                                    type,
                                                                                    1,
                                                                                    "",
                                                                                    "");
                                                                    break;
                                                                case PAY_ALI:
                                                                    getPresenter()
                                                                            .payAli(
                                                                                    uid,
                                                                                    consultationId,
                                                                                    type,
                                                                                    1,
                                                                                    "",
                                                                                    "");
                                                                    break;
                                                                case PAY_SELF:
                                                                    getPresenter()
                                                                            .payOverage(
                                                                                    uid,
                                                                                    consultationId,
                                                                                    type,
                                                                                    1,
                                                                                    "",
                                                                                    "");
                                                                    break;
                                                            }
                                                        }
                                                    });
                                        });

                                dialog.render(unLockMessage.getXiangqin());
                            }

                            @Override
                            public void onShow() {}

                            @Override
                            public void onDismiss() {}

                            @Override
                            public boolean onBackPressed() {
                                return false;
                            }
                        })
                .asCustom(dialog)
                .show();
    }

    @Override
    public void onDeleteSuccess() {

        List<LawyerListBean.LawyerItemBean> selected = adapter.getSelected();
        for (LawyerListBean.LawyerItemBean item : selected) {
            EMConversation conversation =
                    EmChatManager.getInstance().getConversationById(item.getConversationId());
            if (null != conversation) {
                conversation.markAllMessagesAsRead();
            }
            adapter.remove(item);
        }
        resetSelectAll();
        // 通知MainActivity跟新小红点
        EventBusManager.INSTANCE.postEmptySticky(EventBusConstantKt.CODE_RESET_UNREAD_MSG_COUNT);

        EventBus.getDefault().postSticky("delete");
    }
}

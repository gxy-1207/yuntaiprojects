package com.ytfu.yuntaifawu.ui.lawyer.chat.act;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.XPopupUtils;
import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.MessageService;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.db.LvShiDao;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.helper.RxLifecycleUtil;
import com.ytfu.yuntaifawu.im.EmChatManager;
import com.ytfu.yuntaifawu.ui.LvShiMainActivity;
import com.ytfu.yuntaifawu.ui.UserMainActivity;
import com.ytfu.yuntaifawu.ui.complaint.act.ComplaintListActivity;
import com.ytfu.yuntaifawu.ui.lawyer.chat.adapter.LawyerChatRoomAdapter;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.HistoryChatExtBean;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.HistoryChatItemBean;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.HistoryChatItemMultiItem;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.HistoryChatResponse;
import com.ytfu.yuntaifawu.ui.lawyer.chat.p.LawyerChatRoomPresenter;
import com.ytfu.yuntaifawu.ui.lawyer.chat.v.LawyerChatRoomView;
import com.ytfu.yuntaifawu.ui.lvshiwode.activity.CommonWordsActivity;
import com.ytfu.yuntaifawu.ui.lvshiwode.activity.ManagementCommonWordsActivity2;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.ClassificationOfCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.CommonWordsListBean;
import com.ytfu.yuntaifawu.ui.mseeage.activity.LvShiDetailsActivity;
import com.ytfu.yuntaifawu.ui.mseeage.bean.ConversationBean;
import com.ytfu.yuntaifawu.ui.redpackage.act.LawyerRedPackageActivity;
import com.ytfu.yuntaifawu.ui.users.act.MineConsultationListActivity;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.LoginHelper;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.ToastUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/** 律师聊天界面 */
public class LawyerChatRoomActivity
        extends BaseActivity<LawyerChatRoomView, LawyerChatRoomPresenter>
        implements LawyerChatRoomView {

    @BindView(R.id.tl_global_toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_global_title)
    TextView tv_global_title;

    @BindView(R.id.srl_room_refresh)
    SwipeRefreshLayout srl_room_refresh;

    @BindView(R.id.rv_room_content)
    RecyclerView rv_room_content;

    @BindView(R.id.et_room_input)
    EditText et_room_input;

    @BindView(R.id.tv_room_send)
    TextView tv_room_send;

    @BindView(R.id.tv_room_fee)
    TextView tv_room_fee;

    private LawyerChatRoomAdapter adapter;

    // ===Desc:=================================================================
    private static final String KEY_TO_USER_ID = "KEY_TO_USER_ID";
    private static final String KEY_TO_USER_NAME = "KEY_TO_USER_NAME";
    private static final String KEY_TO_USER_AVATAR = "KEY_TO_USER_AVATAR";
    private static final String KEY_CONSULT_ID = "KEY_CONSULT_ID";
    private static final String KEY_IS_FROM_NOTIFICATION = "KEY_IS_FROM_NOTIFICATION";

    public static void start(
            Context context,
            boolean isFromNotification,
            String toUserId,
            String toUserName,
            String toUserAvatar,
            String consultId) {
        context.startActivity(
                getStartIntent(
                        context,
                        isFromNotification,
                        toUserId,
                        toUserName,
                        toUserAvatar,
                        consultId));
    }

    public static Intent getStartIntent(
            Context context,
            boolean isFromNotification,
            String toUserId,
            String toUserName,
            String toUserAvatar,
            String consultId) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TO_USER_ID, toUserId);
        bundle.putString(KEY_TO_USER_NAME, toUserName);
        bundle.putString(KEY_TO_USER_AVATAR, toUserAvatar);
        bundle.putString(KEY_CONSULT_ID, consultId);
        bundle.putBoolean(KEY_IS_FROM_NOTIFICATION, isFromNotification);

        Intent starter = new Intent(context, LawyerChatRoomActivity.class);
        starter.putExtras(bundle);
        return starter;
    }

    // ===Desc:=================================================================

    @Override
    public void init() {
        super.init();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_lawyer_chatroom;
    }

    @Override
    protected LawyerChatRoomPresenter createPresenter() {
        return new LawyerChatRoomPresenter();
    }

    private void hideControllerPanel(boolean withAnim) {
        View view = findViewById(R.id.rv_common_words_root);
        int height = XPopupUtils.getWindowHeight(mContext) / 7 * 3;
        if (withAnim) {
            if (view.isShown()) {
                ValueAnimator anim = ValueAnimator.ofInt(height, 0);
                anim.addListener(
                        new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {}

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                view.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {}

                            @Override
                            public void onAnimationRepeat(Animator animator) {}
                        });
                anim.addUpdateListener(
                        valueAnimator -> {
                            view.getLayoutParams().height = (int) valueAnimator.getAnimatedValue();
                            view.requestLayout();
                        });
                anim.setDuration(150);
                anim.start();
            }
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void showControllerPanel(boolean withAnim) {
        getPresenter()
                .getReplyContent(LoginHelper.getInstance().getLoginUserId(), String.valueOf(0));
    }

    private void showControllerPanel2(int position) {
        CommonUtil.hideSoftInput(this);
        findViewById(R.id.rl_add)
                .setOnClickListener(
                        view -> {
                            CommonWordsActivity.start(mContext);
                            hideControllerPanel(false);
                        });
        findViewById(R.id.rl_manager)
                .setOnClickListener(
                        view -> {
                            ManagementCommonWordsActivity2.start(mContext);
                            hideControllerPanel(false);
                        });

        View view = findViewById(R.id.rv_common_words_root);

        int height = XPopupUtils.getWindowHeight(mContext) / 7 * 3;
        if (!view.isShown()) {
            ValueAnimator anim = ValueAnimator.ofInt(0, height);
            anim.addListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                            view.setVisibility(View.VISIBLE);
                            ViewPager vp = findViewById(R.id.vp);
                            vp.setCurrentItem(position);
                            //                    MagicIndicator mi =
                            // findViewById(R.id.mi_indicator);
                            //                    mi.getNavigator().notifyDataSetChanged();
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {}

                        @Override
                        public void onAnimationCancel(Animator animator) {}

                        @Override
                        public void onAnimationRepeat(Animator animator) {}
                    });
            anim.addUpdateListener(
                    valueAnimator -> {
                        view.getLayoutParams().height = (int) valueAnimator.getAnimatedValue();
                        view.requestLayout();
                    });
            anim.setDuration(150);
            anim.start();
        }
    }

    @Override
    protected void setViewListener() {
        super.setViewListener();
        findViewById(R.id.ll_root).setOnClickListener(v -> hideControllerPanel(true));
        et_room_input.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        hideControllerPanel(false);
                        view.performClick();
                        return false;
                    }
                });
        et_room_input.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (b) {
                            hideControllerPanel(true);
                        }
                    }
                });

        findViewById(R.id.tv_common_terms)
                .setOnClickListener(
                        v -> {
                            View view = findViewById(R.id.rv_common_words_root);
                            CommonUtil.hideSoftInput(this);
                            if (view.isShown()) {
                                hideControllerPanel(true);
                            } else {
                                showControllerPanel2(0);
                            }
                        });

        findViewById(R.id.tv_room_complaint)
                .setOnClickListener(
                        v -> {
                            String owner = getBundleString(KEY_TO_USER_ID, "");
                            String lawyerId = LoginHelper.getInstance().getLoginUserId();
                            ComplaintListActivity.Companion.starter(mContext, owner, lawyerId);
                        });
    }

    @Override
    protected void initView() {
        getPresenter().registerMessageListener();

        // ===Desc:设置Toolbar相关=================================================================
        toolbar.setTitle("");
        toolbar.setBackgroundColor(getResources().getColor(R.color.transparent_4c));
        toolbar.setNavigationIcon(R.drawable.fanhui_bai);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
        String toUserName = getBundleString(KEY_TO_USER_NAME, getString(R.string.app_name));
        tv_global_title.setText(toUserName);
        // ===Desc:顶部按钮相关=================================================================
        tv_room_fee.setOnClickListener(v -> showFwfAlertDialog());
        findViewById(R.id.tv_room_advisory)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 律师端聊天页面顶部他的咨询
                                String toUserId = getBundleString(KEY_TO_USER_ID, "");
                                if (TextUtils.isEmpty(toUserId)) {
                                    ToastUtil.showToast("应用程序出现内部错误,请稍后重试");
                                    return;
                                }
                                //                Intent intent = new
                                // Intent(LawyerChatRoomActivity.this, ActivityMineZiXun.class);
                                ////                intent.putExtra("id", toUserId);
                                ////                startActivity(intent);
                                MineConsultationListActivity.start(
                                        LawyerChatRoomActivity.this, toUserId);
                            }
                        });

        // ===Desc:设置刷新相关=================================================================
        srl_room_refresh.setColorSchemeColors(
                CommonUtil.generateBeautifulColor(),
                CommonUtil.generateBeautifulColor(),
                CommonUtil.generateBeautifulColor(),
                CommonUtil.generateBeautifulColor());
        srl_room_refresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {}
                });
        srl_room_refresh.setEnabled(false);
        // ===Desc:设置聊天列表相关=================================================================
        rv_room_content.setLayoutManager(new LinearLayoutManager(this));
        String toUserAvatar = getBundleString(KEY_TO_USER_AVATAR, "");
        String uid = SpUtil.getString(mContext, AppConstant.UID, "");
        ConversationBean.MsgBean self = LvShiDao.getInstance(this).selectById(uid);
        String selfAvatar;
        if (null == self) {
            selfAvatar = "";
        } else {
            selfAvatar = self.getPicurl();
        }
        adapter = new LawyerChatRoomAdapter(toUserAvatar, selfAvatar);
        //        adapter.bindToRecyclerView(rv_room_content);
        adapter.getLoadMoreModule().setEnableLoadMore(false);
        rv_room_content.setAdapter(adapter);

        rv_room_content.addOnLayoutChangeListener(
                (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
                    if (bottom < oldBottom) {
                        rv_room_content.post(
                                () ->
                                        rv_room_content.smoothScrollToPosition(
                                                adapter.getData().size()));
                    }
                });
        adapter.addChildClickViewIds(R.id.iv_chat_item_avatar);
        // 律师端头像点击事件
        adapter.setOnItemChildClickListener(
                new OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(
                            @NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                        int id = view.getId();
                        switch (id) {
                            case R.id.iv_chat_item_avatar:
                                String shengfen =
                                        SpUtil.getString(mContext, AppConstant.SHENFEN, "1");
                                String uid = SpUtil.getString(mContext, AppConstant.UID, "");
                                if ("2".equals(shengfen)) {
                                    HistoryChatItemMultiItem multiItem =
                                            LawyerChatRoomActivity.this
                                                    .adapter
                                                    .getData()
                                                    .get(position);
                                    Log.e(
                                            "getItemType",
                                            "onItemChildClick: " + multiItem.getItemType());
                                    if (multiItem.getItemType()
                                                    == HistoryChatItemMultiItem.TYPE_SEND_MSG
                                            || multiItem.getItemType()
                                                    == HistoryChatItemMultiItem.TYPE_SEND_FEE) {
                                        String toUserid = getBundleString(KEY_TO_USER_ID, "");
                                        Intent intent =
                                                new Intent(
                                                        LawyerChatRoomActivity.this,
                                                        LvShiDetailsActivity.class);
                                        intent.putExtra("lid", uid);
                                        startActivity(intent);
                                    }
                                }
                                break;

                            case R.id.rl_chat_item_red_package:
                                // 进入用户红包详情
                                HistoryChatItemMultiItem item =
                                        LawyerChatRoomActivity.this.adapter.getData().get(position);
                                if (null == item) {
                                    showToast("应用程序出现未知错误,请稍后重试");
                                    return;
                                }
                                int itemType = item.getItemType();
                                if (itemType == HistoryChatItemMultiItem.TYPE_RECEIVE_RED_PACKAGE) {
                                    HistoryChatExtBean ext = item.getChatItem().getExt();
                                    LawyerRedPackageActivity.start(mContext, ext);
                                }
                                break;
                        }
                    }
                });
        // 长按赋值文本
        adapter.setOnItemChildLongClickListener(
                new OnItemChildLongClickListener() {

                    @Override
                    public boolean onItemChildLongClick(
                            @NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                        if (view.getId() == R.id.tv_chat_item_content) {
                            HistoryChatItemMultiItem item =
                                    LawyerChatRoomActivity.this.adapter.getData().get(position);
                            if (null == item) {
                                showToast("应用程序错误");
                                return false;
                            }
                            if (item.getItemType() == HistoryChatItemMultiItem.TYPE_SEND_MSG
                                    || item.getItemType()
                                            == HistoryChatItemMultiItem.TYPE_RECEIVE_MSG) {
                                HistoryChatItemBean chatItem = item.getChatItem();
                                if (null == chatItem) {
                                    showToast("应用程序错误");
                                    return false;
                                }
                                String showText = chatItem.getBody().getText();
                                new XPopup.Builder(mContext)
                                        .atView(view) // 依附于所点击的View，内部会自动判断在上方或者下方显示
                                        .asAttachList(
                                                new String[] {getString(R.string.txt_copy)},
                                                new int[] {},
                                                (position1, text) -> {
                                                    Logger.e("-----------" + text);
                                                    ClipboardManager cm =
                                                            (ClipboardManager)
                                                                    getSystemService(
                                                                            Context
                                                                                    .CLIPBOARD_SERVICE);
                                                    // 创建普通字符型ClipData
                                                    ClipData mClipData =
                                                            ClipData.newPlainText(
                                                                    "Label", showText);
                                                    // 将ClipData内容放到系统剪贴板里。
                                                    cm.setPrimaryClip(mClipData);
                                                })
                                        .show();
                            }

                            return true;
                        }

                        return false;
                    }
                });
        // ===Desc:=================================================================
        // 发送
        tv_room_send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String input = et_room_input.getText().toString().trim();
                        if (TextUtils.isEmpty(input)) {
                            ToastUtil.showToast(getString(R.string.hint_please_input));
                            return;
                        }
                        String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
                        String toUserId = getBundleString(KEY_TO_USER_ID, "");
                        getPresenter().sendTextMessage(selfId, toUserId, input);
                    }
                });
    }

    @Override
    protected void initData() {
        getPresenter().getWordTypes();

        EMConversation conversation =
                EmChatManager.getInstance()
                        .getConversationById(getBundleString(KEY_TO_USER_ID, ""));
        if (null != conversation) {
            conversation.markAllMessagesAsRead();
        }

        String selfId = SpUtil.getString(mContext, AppConstant.UID, "");

        LoginHelper.getInstance()
                .loginSuccess(
                        selfId,
                        "123456",
                        new LoginHelper.OnEMLoginCallback() {
                            @Override
                            public void onSuccess() {}

                            @Override
                            public void onFail(int code, String msg) {}
                        });
        String toUserId = getBundleString(KEY_TO_USER_ID, "");

        HttpUtil.getInstance()
                .getService(MessageService.class)
                .lawyerGetHistoryRecord(toUserId, selfId, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLifecycleUtil.bindLifecycle(this))
                .subscribe(
                        new BaseRxObserver<HistoryChatResponse>() {
                            @Override
                            public void onNextImpl(HistoryChatResponse historyRecordResponseBean) {
                                List<HistoryChatItemMultiItem> data = new ArrayList<>();
                                List<HistoryChatItemBean> list =
                                        historyRecordResponseBean.getData();
                                for (HistoryChatItemBean item : list) {
                                    HistoryChatItemMultiItem bean =
                                            new HistoryChatItemMultiItem(item);
                                    data.add(bean);
                                }
                                adapter.setNewData(data);
                                rv_room_content.scrollToPosition(adapter.getData().size() - 1);
                            }

                            @Override
                            public void onErrorImpl(String errorMessage) {}
                        });

        hideLoading();
    }

    @Override
    protected void onDestroy() {
        getPresenter().unregisterMessageListener();
        // 重置未读消息数量
        EMConversation conversation =
                EmChatManager.getInstance()
                        .getConversationById(getBundleString(KEY_TO_USER_ID, ""));
        if (null != conversation) {
            conversation.markAllMessagesAsRead();

            EMClient.getInstance()
                    .chatManager()
                    .getConversation(getBundleString(KEY_TO_USER_ID, ""))
                    .markAllMessagesAsRead();

            int unreadMsgCount = conversation.getUnreadMsgCount();
            Logger.e("未读消息数量 -> " + unreadMsgCount);
        }

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        boolean shown = findViewById(R.id.rv_common_words_root).isShown();
        if (shown) {
            hideControllerPanel(true);
            return;
        }
        boolean isFromNotification = getBundleBoolean(KEY_IS_FROM_NOTIFICATION, false);
        if (isFromNotification) {
            String type = SpUtil.getString(mContext, AppConstant.SHENFEN, "1");
            if ("1".equals(type)) {
                UserMainActivity.Companion.start(this, 1);
            } else {
                LvShiMainActivity.start(this, 1);
            }
        } else {
            super.onBackPressed();
        }
    }

    // ===Desc:=================================================================

    private void showFwfAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        View view = View.inflate(this, R.layout.view_alert_dialog_fwf, null);
        TextView tv_qrsq = view.findViewById(R.id.tv_qrsq);
        final EditText ed_email = view.findViewById(R.id.ed_email);
        ed_email.setFocusable(true);
        ed_email.setFocusableInTouchMode(true);
        ed_email.requestFocus();
        new Handler()
                .postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                CommonUtil.openSoftInput(mContext, ed_email);
                            }
                        },
                        200);
        alertDialog.setOnShowListener(dialog -> {});

        tv_qrsq.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String edPrice = ed_email.getText().toString().trim();
                        if (TextUtils.isEmpty(edPrice)) {
                            showCenterToast("输入金额不能为空");
                            return;
                        }
                        try {
                            double money = Double.parseDouble(edPrice);
                            if (money < 39) {
                                showCenterToast("服务费不能低于39元");
                                return;
                            }
                            String toUserId = getBundleString(KEY_TO_USER_ID, "");
                            String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
                            getPresenter().sendFeeMessage(toUserId, selfId, edPrice);
                            alertDialog.dismiss();
                        } catch (NumberFormatException e) {
                            showCenterToast("应用程序错误,请稍后再试");
                            e.printStackTrace();
                        }
                        //                if (!TextUtils.isEmpty(edPrice)) {
                        //                    String toUserId = getBundleString(KEY_TO_USER_ID, "");
                        //                    String selfId =
                        // SpUtil.getString(mContext,AppConstant.UID, "");
                        //                    getPresenter().sendFeeMessage(toUserId, selfId,
                        // edPrice);
                        //                    alertDialog.dismiss();
                        //                } else {

                        //                }
                    }
                });
        //
        // alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        // 只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        Window window = alertDialog.getWindow();
        window.setContentView(view);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams attributes = window.getAttributes();
        // 设置宽度
        attributes.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
        attributes.gravity = Gravity.CENTER; // 设置位置
        window.setAttributes(attributes);
        CommonUtil.openSoftInput(mContext, ed_email);
    }

    // ===Desc:View接口相关的回调=================================================================
    @Override
    public void onSendTxtPre(HistoryChatItemBean itemBean) {
        HistoryChatItemMultiItem item = new HistoryChatItemMultiItem(itemBean);
        adapter.addData(item);
        // 清空输入 并滚动
        et_room_input.setText("");
        rv_room_content.smoothScrollToPosition(adapter.getData().size() - 1);
    }

    @Override
    public void onSendTxtSuccess(String toUserId, String fromUserId, HistoryChatItemBean itemBean) {
        runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        // 更新adapter中ui显示
                        int index = adapter.indexOfByMessageId(itemBean.getMessageId());
                        if (index != -1) {
                            HistoryChatItemMultiItem item = adapter.getData().get(index);
                            item.getChatItem().setStatus(2);
                            adapter.notifyItemChanged(index);
                        }

                        // 同步消息到服务器
                        String consultId = getBundleString(KEY_CONSULT_ID, "");
                        getPresenter()
                                .lawyerSyncMessageToService(
                                        consultId,
                                        toUserId,
                                        fromUserId,
                                        itemBean.getBody().getText());
                    }
                });
    }

    @Override
    public void onSendTxtFail(HistoryChatItemBean itemBean) {
        // 显示消息失败
    }

    @Override
    public void onSendFeePre(HistoryChatItemBean itemBean) {
        HistoryChatItemMultiItem item = new HistoryChatItemMultiItem(itemBean);
        adapter.addData(item);
        // 清空输入 并滚动
        rv_room_content.smoothScrollToPosition(adapter.getData().size() - 1);
    }

    @Override
    public void onSendFeeSuccess(String toUserId, String fromUserId, HistoryChatItemBean itemBean) {
        //        String content = "收取服务费" + itemBean.getExt().getPrice() + "元";
        //        String consultId = getBundleString(KEY_CONSULT_ID, "");
        //        getPresenter().lawyerSyncMessageToService(consultId, toUserId, fromUserId,
        // content);
    }

    @Override
    public void onSendFeeFail(HistoryChatItemBean itemBean) {}

    // ===Desc:=================================================================

    @Override
    public void onSyncMessageSuccess() {
        Logger.e("同步消息到服务器成功");
    }

    @Override
    public void onSyncMessageFail(String errorMsg) {
        Logger.e("同步消息到服务器失败:" + errorMsg);
    }

    // ===Desc:环信消息相关=================================================================

    @Override
    public void onTextReceived(List<HistoryChatItemMultiItem> list) {
        Logger.e("接收到消息");
        List<HistoryChatItemMultiItem> data = new ArrayList<>();
        for (HistoryChatItemMultiItem item : list) {
            Logger.e("onTextReceived---------------->" + item);
            String from = item.getChatItem().getFrom();
            Logger.e(
                    "From user id : "
                            + from
                            + ", Current to user id = "
                            + getBundleString(KEY_TO_USER_ID, ""));
            String toUserId = getBundleString(KEY_TO_USER_ID, "");
            if (from.equals(toUserId)) {
                data.add(item);
            }
        }
        runOnUiThread(
                () -> {
                    adapter.addData(data);
                    rv_room_content.scrollToPosition(adapter.getData().size());
                });
        //        Logger.e("接收到消息");
        //        adapter.addData(list);
        //        rv_room_content.scrollToPosition(adapter.getData().size() - 1);
    }

    @Override
    public void onMessageRead(List<HistoryChatItemMultiItem> list) {}

    // ===Desc:================================================================================

    //    @BindView(R.id.mi_indicator)
    //    MagicIndicator mi_indicator;
    @Override
    public void onGetReplyContentSuccess(List<CommonWordsListBean.DataBean> list) {

        findViewById(R.id.rl_add)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CommonWordsActivity.start(mContext);
                                hideControllerPanel(false);
                            }
                        });
        findViewById(R.id.rl_manager)
                .setOnClickListener(
                        view -> {
                            //            ManagementCommonWordsActivity.start(mContext);
                            hideControllerPanel(false);
                        });

        View view = findViewById(R.id.rv_common_words_root);

        //        RecyclerView rv = findViewById(R.id.rv_chat_words);
        //        QuickAdapter<CommonWordsListBean.DataBean> quickAdapter =
        //                new
        // QuickAdapter<CommonWordsListBean.DataBean>(R.layout.item_chat_word_item) {
        //                    @Override
        //                    protected void convert(@NotNull BaseViewHolder baseViewHolder,
        // CommonWordsListBean.DataBean dataBean) {
        //                        baseViewHolder.setText(R.id.tv_text1, dataBean.getContent());
        //                    }
        //                };
        //        quickAdapter.setOnItemClickListener((adapter, view1, position) ->
        // et_room_input.setText(quickAdapter.getData().get(position).getContent()));
        //        rv.setLayoutManager(new LinearLayoutManager(mContext));
        //        rv.addItemDecoration(ItemDecoration.createVertical(Color.parseColor("#FFE5E5E5"),
        // 1, XPopupUtils.dp2px(mContext, 16)));
        //        rv.setAdapter(quickAdapter);
        //
        //        if (list.isEmpty()) {
        //            quickAdapter.setEmptyView(R.layout.layout_empty);
        //        } else {
        //            quickAdapter.setNewInstance(list);
        //        }

        int height = XPopupUtils.getWindowHeight(mContext) / 7 * 3;
        if (!view.isShown()) {
            ValueAnimator anim = ValueAnimator.ofInt(0, height);
            anim.addListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                            view.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {}

                        @Override
                        public void onAnimationCancel(Animator animator) {}

                        @Override
                        public void onAnimationRepeat(Animator animator) {}
                    });
            anim.addUpdateListener(
                    valueAnimator -> {
                        view.getLayoutParams().height = (int) valueAnimator.getAnimatedValue();
                        view.requestLayout();
                    });
            anim.setDuration(150);
            anim.start();
        }
    }

    @Override
    public void onGetWordTypesFail() {}

    @Override
    public void onGetWordTypesSuccess(List<ClassificationOfCommonWordsBean.ListBean> list) {
        MagicIndicator mi = findViewById(R.id.mi_indicator);
        ViewPager vp = findViewById(R.id.vp);
        final CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(
                new CommonNavigatorAdapter() {
                    @Override
                    public int getCount() {
                        return list.size();
                    }

                    @Override
                    public IPagerTitleView getTitleView(Context context, int i) {
                        ClassificationOfCommonWordsBean.ListBean bean = list.get(i);
                        CommonPagerTitleView view = new CommonPagerTitleView(mContext);
                        view.setContentView(R.layout.layout_title);
                        TextView tv_title = view.findViewById(R.id.tv_title);
                        tv_title.setText(list.get(i).getContent());
                        view.setOnPagerTitleChangeListener(
                                new CommonPagerTitleView.OnPagerTitleChangeListener() {
                                    @Override
                                    public void onSelected(int index, int totalCount) {
                                        if (findViewById(R.id.rv_common_words_root).isShown()) {
                                            tv_title.setTextColor(Color.parseColor("#44A2F7"));
                                        } else {
                                            tv_title.setTextColor(Color.parseColor("#666666"));
                                        }
                                    }

                                    @Override
                                    public void onDeselected(int index, int totalCount) {
                                        tv_title.setTextColor(Color.parseColor("#666666"));
                                    }

                                    @Override
                                    public void onLeave(int i, int i1, float v, boolean b) {}

                                    @Override
                                    public void onEnter(int i, int i1, float v, boolean b) {}
                                });
                        view.setOnClickListener(
                                view1 -> {
                                    if (findViewById(R.id.rv_common_words_root).isShown()) {
                                        vp.setCurrentItem(i);
                                    } else {
                                        // 显示底部
                                        showControllerPanel2(i);
                                        //
                                        // getPresenter().getReplyContent(LoginHelper.getInstance().getLoginUserId(),
                                        //                                bean.getId());
                                    }
                                });
                        return view;
                    }

                    @Override
                    public IPagerIndicator getIndicator(Context context) {
                        return null;
                    }
                });

        mi.setNavigator(commonNavigator);
        vp.setAdapter(
                new FragmentStatePagerAdapter(
                        getSupportFragmentManager(),
                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                    @NonNull
                    @Override
                    public Fragment getItem(int position) {
                        WordListFragment fragment =
                                WordListFragment.newInstance(list.get(position).getId());
                        fragment.setListener(
                                new WordListFragment.OnWordClickListener() {
                                    @Override
                                    public void onWordCLick(CommonWordsListBean.DataBean item) {
                                        et_room_input.setText(item.getContent());
                                    }
                                });
                        return fragment;
                    }

                    @Override
                    public int getCount() {
                        return list.size();
                    }
                });
        ViewPagerHelper.bind(mi, vp);
    }
}

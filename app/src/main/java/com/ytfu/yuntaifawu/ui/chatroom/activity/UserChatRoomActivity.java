package com.ytfu.yuntaifawu.ui.chatroom.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener;
import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.model.EaseCompat;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.XPopupUtils;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yanzhenjie.permission.Permission;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.db.LvShiDao;
import com.ytfu.yuntaifawu.im.EmChatManager;
import com.ytfu.yuntaifawu.ui.UserMainActivity;
import com.ytfu.yuntaifawu.ui.chatroom.bean.FeeWechatOrderResponse;
import com.ytfu.yuntaifawu.ui.chatroom.bean.RoomLawyerCardInfoResponse;
import com.ytfu.yuntaifawu.ui.chatroom.header.ChatRoomCardHeaderController;
import com.ytfu.yuntaifawu.ui.chatroom.header.ChatRoomCustomerHeaderController;
import com.ytfu.yuntaifawu.ui.chatroom.p.UserChatRoomPresenter;
import com.ytfu.yuntaifawu.ui.chatroom.v.UserChatRoomView;
import com.ytfu.yuntaifawu.ui.complaint.act.ComplaintClassifyActivity;
import com.ytfu.yuntaifawu.ui.contract.act.ContractClassificationActivity;
import com.ytfu.yuntaifawu.ui.custom.CopyButtonLibrary;
import com.ytfu.yuntaifawu.ui.falvguwen.activity.ActivityLegalAdviser;
import com.ytfu.yuntaifawu.ui.kaitingzhushou.activity.ActivityOpenHelper;
import com.ytfu.yuntaifawu.ui.lawyer.chat.act.ChatBigImageActivity;
import com.ytfu.yuntaifawu.ui.lawyer.chat.adapter.LawyerChatRoomAdapter;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.GetQiniuTokenBean;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.HistoryChatBodyBean;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.HistoryChatExtBean;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.HistoryChatItemBean;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.HistoryChatItemMultiItem;
import com.ytfu.yuntaifawu.ui.mseeage.activity.ComplaintActivity;
import com.ytfu.yuntaifawu.ui.mseeage.activity.EvaluateActivity;
import com.ytfu.yuntaifawu.ui.mseeage.activity.LvShiDetailsActivity;
import com.ytfu.yuntaifawu.ui.mseeage.bean.ConversationBean;
import com.ytfu.yuntaifawu.ui.mseeage.bean.ExChangeWeiXinBean;
import com.ytfu.yuntaifawu.ui.mseeage.bean.ToCheckPayBean;
import com.ytfu.yuntaifawu.ui.mseeage.bean.WhetherToPayBean;
import com.ytfu.yuntaifawu.ui.mseeage.bean.ZiXunSendMessageBean;
import com.ytfu.yuntaifawu.ui.pay.PayBottomDialog;
import com.ytfu.yuntaifawu.ui.pay.PayHelper;
import com.ytfu.yuntaifawu.ui.pay.bean.PayBean;
import com.ytfu.yuntaifawu.ui.pay.bean.WxPayBean;
import com.ytfu.yuntaifawu.ui.redpackage.act.SendRedPackageActivity;
import com.ytfu.yuntaifawu.ui.redpackage.act.UserRedPackageActivity;
import com.ytfu.yuntaifawu.ui.users.bean.RefundButtonVisibleBean;
import com.ytfu.yuntaifawu.utils.AndPermissionUtil;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.DialorUtil;
import com.ytfu.yuntaifawu.utils.Eyes;
import com.ytfu.yuntaifawu.utils.LoginHelper;
import com.ytfu.yuntaifawu.utils.MessageEvent;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.ToastUtil;
import com.ytfu.yuntaifawu.utils.dialog.DialogHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

/** 聊天室 */
@InjectPresenter(UserChatRoomPresenter.class)
@InjectLayout(
        value = R.layout.activity_chat_room,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
public class UserChatRoomActivity extends BaseActivity<UserChatRoomView, UserChatRoomPresenter>
        implements UserChatRoomView {

    //    @BindView(R.id.tl_room_toolbar)
    //    Toolbar tl_room_toolbar;
    //    @BindView(R.id.tv_room_title)
    //    TextView tv_room_title;

    @BindView(R.id.ll_room_service_top)
    LinearLayout ll_room_service_top;

    @BindView(R.id.ll_room_lawyer_top)
    LinearLayout ll_room_lawyer_top;

    @BindView(R.id.rv_room_content)
    RecyclerView rv_room_content;

    @BindView(R.id.et_room_input)
    EditText et_room_input;

    @BindView(R.id.srv_room_refresh)
    SwipeRefreshLayout srv_room_refresh;

    @BindView(R.id.tv_room_complaint)
    TextView tv_room_complaint;

    @BindView(R.id.tv_room_refund)
    TextView tv_room_refund;

    @BindView(R.id.tv_room_send)
    TextView tv_room_send;

    @BindView(R.id.iv_room_select)
    ImageView iv_room_select;

    private LawyerChatRoomAdapter adapter;
    /** 显示支付的底部dialog */
    private PayBottomDialog dialog;

    private ChatRoomCardHeaderController cardHeaderController;
    private ChatRoomCustomerHeaderController customerHeaderController;

    /** 交换微信的请求码 */
    private final int codeExchangeWeChat = 0;
    /** 评价 */
    private final int codeAssess = 1;
    /** 投诉 */
    private final int codeComplaint = 2;
    /** 退款 */
    private final int refundint = 3;

    protected static final int REQUEST_CODE_LOCAL = 4;
    private static final String KEY_IS_CUSTOMER_SERVICE = "KEY_IS_CUSTOMER_SERVICE";
    private static final String KEY_TO_USER_ID = "KEY_TO_USER_ID";
    private static final String KEY_TO_USER_NAME = "KEY_TO_USER_NAME";
    private static final String KEY_TO_USER_AVATAR = "KEY_TO_USER_AVATAR";
    private static final String KEY_IS_FROM_NOTIFICATION = "KEY_IS_FROM_NOTIFICATION";
    private String qiNiuToken;
    private UploadManager uploadManager;

    public static void start(
            Context context,
            boolean isCustomerService,
            boolean isFromNotification,
            String toUserId,
            String toUserName,
            String toUserAvatar) {
        context.startActivity(
                getStartIntent(
                        context,
                        isCustomerService,
                        isFromNotification,
                        toUserId,
                        toUserName,
                        toUserAvatar));
    }

    public static Intent getStartIntent(
            Context context,
            boolean isCustomerService,
            boolean isFromNotification,
            String toUserId,
            String toUserName,
            String toUserAvatar) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_IS_CUSTOMER_SERVICE, isCustomerService);
        bundle.putString(KEY_TO_USER_ID, toUserId);
        bundle.putString(KEY_TO_USER_NAME, toUserName);
        bundle.putString(KEY_TO_USER_AVATAR, toUserAvatar);
        bundle.putBoolean(KEY_IS_FROM_NOTIFICATION, isFromNotification);
        Intent starter = new Intent(context, UserChatRoomActivity.class);
        starter.putExtras(bundle);
        return starter;
    }

    // ===Desc:=================================================================

    private HistoryChatItemMultiItem clickPayItem;

    private static final int CODE_SEND_RED_PACKAGE = 1;

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getRefundBtnVisibe();
    }

    @Override
    protected void initView() {
        // 注册eventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        getPresenter().registerMessageListener();

        EMConversation conversation =
                EmChatManager.getInstance()
                        .getConversationById(getBundleString(KEY_TO_USER_ID, ""));
        if (null != conversation) {
            conversation.markAllMessagesAsRead();
        }
        Eyes.setStatusBarColor(this, Color.parseColor("#44A2F7"));
        // ===Desc:Toolbar=================================================================
        // 初始化Toolbar
        srv_room_refresh.setEnabled(false);

        setToolbarLeftImage(
                R.drawable.fanhui_bai,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
        String toUserName = getBundleString(KEY_TO_USER_NAME, "");
        setToolbarText(R.id.tv_global_title, toUserName);

        // ===Desc:=================================================================
        String toUserAvatar = getBundleString(KEY_TO_USER_AVATAR, "");
        ConversationBean.MsgBean msgBean =
                LvShiDao.getInstance(this)
                        .selectById(SpUtil.getString(mContext, AppConstant.UID, ""));
        String selfAvatar = "";
        if (null == msgBean) {
            ToastUtil.showToast("应用程序出现内部错误,请稍后重试");
            //            finish();
            //            return;
        } else {
            selfAvatar = msgBean.getPicurl();
        }

        adapter = new LawyerChatRoomAdapter(toUserAvatar, selfAvatar);

        adapter.setOnItemChildLongClickListener(
                new OnItemChildLongClickListener() {

                    @Override
                    public boolean onItemChildLongClick(
                            @NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                        if (view.getId() == R.id.tv_chat_item_content) {
                            HistoryChatItemMultiItem item =
                                    UserChatRoomActivity.this.adapter.getData().get(position);
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

        adapter.setOnItemChildClickListener(
                (adapter, view, position) -> {
                    int id = view.getId();
                    switch (id) {
                        case R.id.rl_chat_item_pay:
                            HistoryChatItemMultiItem clickItem =
                                    UserChatRoomActivity.this.adapter.getData().get(position);
                            if (clickItem.getItemType()
                                    != HistoryChatItemMultiItem.TYPE_RECEIVE_FEE) {
                                ToastUtil.showToast("应用程序出现内部错误,请稍后重试");
                                return;
                            }
                            int type = clickItem.getChatItem().getExt().getType();
                            if (type == 2) {
                                ToastUtil.showToast("该笔订单已经支付,无需重复支付");
                                return;
                            }
                            String toUserId = clickItem.getChatItem().getExt().getLvshiid();
                            String feeId = clickItem.getChatItem().getExt().getJiluid();
                            // 效验当前订单是否已经支付,请求服务器获取状态
                            String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
                            getPresenter().checkMessagePayed(selfId, toUserId, feeId);
                            clickPayItem = clickItem;
                            break;
                        case R.id.iv_chat_item_avatar:
                            boolean bundleBoolean = getBundleBoolean(KEY_IS_CUSTOMER_SERVICE, true);
                            if (bundleBoolean) {
                                return;
                            }
                            String shengfen = SpUtil.getString(mContext, AppConstant.SHENFEN, "1");
                            HistoryChatItemMultiItem multiItem =
                                    UserChatRoomActivity.this.adapter.getData().get(position);
                            if (multiItem.getItemType() == HistoryChatItemMultiItem.TYPE_RECEIVE_MSG
                                    || multiItem.getItemType()
                                            == HistoryChatItemMultiItem.TYPE_RECEIVE_FEE
                                    || multiItem.getItemType()
                                            == HistoryChatItemMultiItem.TYPE_RECEIVE_IMG) {
                                // >>>>>>>bba25dc7fc4488dc4610f2af616162868a929041
                                String toUserid = getBundleString(KEY_TO_USER_ID, "");
                                Intent intent =
                                        new Intent(
                                                UserChatRoomActivity.this,
                                                LvShiDetailsActivity.class);
                                intent.putExtra("lid", toUserid);
                                intent.putExtra("userName", toUserName);
                                startActivity(intent);
                            }

                            break;

                        case R.id.rl_chat_item_red_package:
                            // 进入用户红包详情
                            HistoryChatItemMultiItem item =
                                    UserChatRoomActivity.this.adapter.getData().get(position);
                            if (null == item) {
                                showToast("应用程序出现未知错误,请稍后重试");
                                return;
                            }
                            int itemType = item.getItemType();
                            if (itemType == HistoryChatItemMultiItem.TYPE_SEND_RED_PACKAGE) {
                                HistoryChatExtBean ext = item.getChatItem().getExt();
                                String lawyerName = getBundleString(KEY_TO_USER_NAME, "");
                                String lawyerAvatar = getBundleString(KEY_TO_USER_AVATAR, "");
                                long timestamp = item.getChatItem().getTimestamp();
                                UserRedPackageActivity.start(
                                        mContext, ext, lawyerName, lawyerAvatar, timestamp);
                            }
                            break;
                        case R.id.iv_chat_item_content:
                            // 点击图片放大
                            HistoryChatItemMultiItem itemImg =
                                    UserChatRoomActivity.this.adapter.getData().get(position);
                            if (null == itemImg) {
                                showToast("应用程序出现未知错误,请稍后重试");
                                return;
                            }
                            int typeImg = itemImg.getItemType();
                            if (typeImg == HistoryChatItemMultiItem.TYPE_SEND_IMG
                                    || typeImg == HistoryChatItemMultiItem.TYPE_RECEIVE_IMG) {
                                HistoryChatBodyBean body = itemImg.getChatItem().getBody();
                                String messageId = itemImg.getChatItem().getMessageId();
                                Log.e("messageId", "------" + messageId);
                                String path = body.getText();
                                Log.e("path", "------" + path);
                                ImageView imageView = view.findViewById(R.id.iv_chat_item_content);
                                int width = imageView.getWidth();
                                int height = imageView.getHeight();
                                int[] location = new int[2];
                                imageView.getLocationOnScreen(location);

                                ChatBigImageActivity.start(
                                        UserChatRoomActivity.this,
                                        path,
                                        location[0],
                                        location[1],
                                        width,
                                        height);
                            }

                            //                                EaseShowBigImageActivity

                            break;
                    }
                });
        LinearLayoutManager manager = new LinearLayoutManager(this);

        rv_room_content.setLayoutManager(manager);
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

        // ===Desc:=================================================================
        findViewById(R.id.tv_room_send)
                .setOnClickListener(
                        v -> {
                            String input = et_room_input.getText().toString().trim();
                            if (TextUtils.isEmpty(input)) {
                                ToastUtil.showToast(getString(R.string.hint_please_input));
                                return;
                            }
                            String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
                            String toUserId = getBundleString(KEY_TO_USER_ID, "");
                            getPresenter().sendTextMessage(selfId, toUserId, input);
                        });

        // 合同
        findViewById(R.id.tv_room_contract)
                .setOnClickListener(
                        v ->
                                //                        startActivity(new
                                // Intent(UserChatRoomActivity.this, ActivityContractList.class))
                                ContractClassificationActivity.start(this));

        //        //写诉状
        //        findViewById(R.id.tv_room_statement).
        //                setOnClickListener(v ->
        //                        ComplaintClassifyActivity.Companion.start(this));

        // 开庭助手
        findViewById(R.id.tv_room_helper)
                .setOnClickListener(
                        v ->
                                startActivity(
                                        new Intent(
                                                UserChatRoomActivity.this,
                                                ActivityOpenHelper.class)));

        // 顾问
        findViewById(R.id.tv_room_advisor)
                .setOnClickListener(
                        v ->
                                startActivity(
                                        new Intent(
                                                UserChatRoomActivity.this,
                                                ActivityLegalAdviser.class)));

        // 微信号
        findViewById(R.id.tv_room_wechat)
                .setOnClickListener(
                        v -> {
                            String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
                            String toUserId = getBundleString(KEY_TO_USER_ID, "");
                            getPresenter().checkPay(codeExchangeWeChat, selfId, toUserId);
                        });

        // 评价ta
        findViewById(R.id.tv_room_assess)
                .setOnClickListener(
                        v -> {
                            String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
                            String toUserId = getBundleString(KEY_TO_USER_ID, "");
                            getPresenter().checkPay(codeAssess, selfId, toUserId);
                        });

        // 退款
        findViewById(R.id.tv_room_refund)
                .setOnClickListener(
                        v -> {
                            //                    RefundCommitActivity.Companion.start(mContext,
                            // getBundleString(KEY_TO_USER_ID, ""));
                            String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
                            String toUserId = getBundleString(KEY_TO_USER_ID, "");
                            getPresenter().checkPay(refundint, selfId, toUserId);
                        });
        // 投诉
        findViewById(R.id.tv_room_complaint)
                .setOnClickListener(
                        v -> {
                            //                    RefundCommitActivity.Companion.start(mContext,
                            // getBundleString(KEY_TO_USER_ID, ""));
                            String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
                            String toUserId = getBundleString(KEY_TO_USER_ID, "");
                            getPresenter().checkPay(codeComplaint, selfId, toUserId);
                        });

        // 起诉状
        findViewById(R.id.tv_room_sue)
                .setOnClickListener(
                        v -> {
                            String ownerId = LoginHelper.getInstance().getLoginUserId();
                            String lawyerId = getBundleString(KEY_TO_USER_ID, "");
                            ComplaintClassifyActivity.Companion.start(this, ownerId, lawyerId);
                        });

        // 红包
        findViewById(R.id.tv_room_red)
                .setOnClickListener(
                        v -> {
                            // 进入发红包界面
                            String lawyerId = getBundleString(KEY_TO_USER_ID, "");
                            String lawyerAvatar = getBundleString(KEY_TO_USER_AVATAR, "");
                            //            SendRedPackageActivity.start(mContext, lawyerId,
                            // lawyerAvatar);
                            SendRedPackageActivity.startActivityForResult(
                                    this, CODE_SEND_RED_PACKAGE, lawyerId, lawyerAvatar);
                        });
        //        findViewById(R.id.tv_room_helper1).
        //                setOnClickListener(v ->
        //                        startActivity(new Intent(UserChatRoomActivity.this,
        // ActivityOpenHelper.class)));
    }

    @Override
    protected void setViewListener() {
        super.setViewListener();
        et_room_input.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        hideImgControllerPanel(false);
                        view.performClick();
                        return false;
                    }
                });
        et_room_input.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (b) {
                            hideImgControllerPanel(true);
                        }
                    }
                });
        et_room_input.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String input = et_room_input.getText().toString();
                        if (!input.isEmpty()) {
                            tv_room_send.setVisibility(View.VISIBLE);
                            iv_room_select.setVisibility(View.GONE);
                        } else {
                            tv_room_send.setVisibility(View.GONE);
                            iv_room_select.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {}
                });
        // 选择图片
        findViewById(R.id.iv_room_select)
                .setOnClickListener(
                        view -> {
                            View viewById = findViewById(R.id.rv_common_root);
                            CommonUtil.hideSoftInput(this);
                            if (viewById.isShown()) {
                                hideImgControllerPanel(true);
                            } else {
                                showImgControllerPanel2(0);
                            }
                        });
    }

    private void hideImgControllerPanel(boolean b) {
        View view = findViewById(R.id.rv_common_root);
        int height = XPopupUtils.getWindowHeight(mContext) / 8 * 2;
        if (b) {
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

    private void showImgControllerPanel2(int i) {
        CommonUtil.hideSoftInput(this);
        findViewById(R.id.tv_album)
                .setOnClickListener(
                        view -> {
                            //                            showToast("相册");
                            selectPic();
                        });
        View view = findViewById(R.id.rv_common_root);

        int height = XPopupUtils.getWindowHeight(mContext) / 8 * 2;
        if (!view.isShown()) {
            ValueAnimator anim = ValueAnimator.ofInt(0, height);
            anim.addListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                            view.setVisibility(View.VISIBLE);
                            //                            ViewPager vp = findViewById(R.id.vp);
                            //                            vp.setCurrentItem(position);
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
    // ===Desc:调用后台接口获取七牛云token=================================================================
    private void selectPic() {
        getPresenter().getQiNiuToken();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_SEND_RED_PACKAGE) {
            if (resultCode != RESULT_OK) {
                return;
            }
            String toUserId = getBundleString(KEY_TO_USER_ID, "");
            String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
            getPresenter().getHistoryRecord(toUserId, selfId);
            double money = getBundleDouble(data, SendRedPackageActivity.KEY_RESULT_MONEY, 0.0);
        } else if (requestCode == REQUEST_CODE_LOCAL) {
            if (data != null) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    sendPicUri(selectedImage);
                }
            }
        }
        Logger.e("onActivityResult requestCode = " + requestCode + ",,resultCode = " + resultCode);
    }

    private void sendPicUri(Uri selectedImage) {
        // 设置图片名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String key =
                "icon_"
                        + sdf.format(new Date())
                        + LoginHelper.getInstance().getLoginUserId()
                        + ".png";
        String path = EaseCompat.getPath(this, selectedImage);
        //        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        //        Cursor cursor =
        //                this.getContentResolver().query(selectedImage, filePathColumn, null, null,
        // null);
        //        if (cursor != null) {
        //            cursor.moveToFirst();
        //            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        //            String picturePath = cursor.getString(columnIndex);
        //            cursor.close();
        //            cursor = null;
        //
        //            if (picturePath == null || picturePath.equals("null")) {
        //                Toast toast =
        //                        Toast.makeText(
        //                                this,
        //                                com.hyphenate.easeui.R.string.cant_find_pictures,
        //                                Toast.LENGTH_SHORT);
        //                toast.setGravity(Gravity.CENTER, 0, 0);
        //                toast.show();
        //                return;
        //            }
        //            uploadImgQiNiu(picturePath, key, qiNiToken);
        //            //            sendImageMessage(picturePath);
        //        } else {
        //            File file = new File(selectedImage.getPath());
        //            if (!file.exists()) {
        //                Toast toast =
        //                        Toast.makeText(
        //                                this,
        //                                com.hyphenate.easeui.R.string.cant_find_pictures,
        //                                Toast.LENGTH_SHORT);
        //                toast.setGravity(Gravity.CENTER, 0, 0);
        //                toast.show();
        //                return;
        //            }
        uploadImgQiNiu(path, key, qiNiuToken);
        Log.e("path", "-------" + path);
        Log.e("key", "-------" + key);
        //            sendImageMessage(file.getAbsolutePath());
        //        }
    }

    public void uploadImgQiNiu(String imagePath, String key, String token) {
        Configuration configuration =
                new Configuration.Builder().connectTimeout(10).responseTimeout(60).build();
        if (uploadManager == null) {
            uploadManager = new UploadManager(configuration);
        }
        uploadManager.put(
                imagePath,
                key,
                token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(
                            String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                        if (responseInfo.isOK()) {
                            Log.e("qiniu", "-----" + s);
                            String imageUrl = "http://chat.yuntaifawu.com/" + s;
                            Logger.e(imageUrl);
                            String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
                            String toUserId = getBundleString(KEY_TO_USER_ID, "");
                            getPresenter().sendImgMessage(selfId, toUserId, imagePath, imageUrl);
                        } else {
                            Logger.e(responseInfo.error);
                        }
                    }
                },
                null);
    }

    @Override
    protected void initData() {
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.transparent_4c));

        boolean isCustomer = getBundleBoolean(KEY_IS_CUSTOMER_SERVICE, false);
        String toUserId = getBundleString(KEY_TO_USER_ID, "");
        String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
        // 强制登录聊天
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
        if (isCustomer) {
            ll_room_service_top.setVisibility(View.VISIBLE);
            ll_room_lawyer_top.setVisibility(View.GONE);
            customerHeaderController = new ChatRoomCustomerHeaderController(this);
            getPresenter().getCustomerServiceMessage();
        } else {
            ll_room_service_top.setVisibility(View.GONE);
            ll_room_lawyer_top.setVisibility(View.VISIBLE);
            cardHeaderController = new ChatRoomCardHeaderController(this);
            // 获取律师卡片信息
            getPresenter().getLawyerCardInfo(toUserId);
        }
        // 获取历史消息
        getPresenter().getHistoryRecord(toUserId, selfId);

        hideLoading();
    }

    @Override
    protected void onDestroy() {
        // 反注册
        getPresenter().unregisterMessageListener();
        EventBus.getDefault().unregister(this);
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
        boolean isFromNotification = getBundleBoolean(KEY_IS_FROM_NOTIFICATION, false);
        if (isFromNotification) {
            UserMainActivity.Companion.start(this, 1);
        } else {
            super.onBackPressed();
        }
    }

    // ===Desc:=================================================================

    @Override
    public void onGetHistoryRecordSuccess(List<HistoryChatItemMultiItem> data) {
        runOnUiThread(
                () -> {
                    adapter.setNewData(data);
                    rv_room_content.scrollToPosition(adapter.getData().size());
                });
    }

    @Override
    public void onGetHistoryRecordFail(String errorMsg) {}

    // ===Desc:=================================================================
    @Override
    public void onSendTxtPre(HistoryChatItemBean itemBean) {
        et_room_input.setText("");
        HistoryChatItemMultiItem bean = new HistoryChatItemMultiItem(itemBean);
        adapter.addData(bean);
        rv_room_content.smoothScrollToPosition(adapter.getData().size());
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

                        getPresenter()
                                .userSyncMessageToService(
                                        toUserId, fromUserId, itemBean.getBody().getText(), "");
                    }
                });
    }

    @Override
    public void onSendImgSuccess(
            String toUserId, String fromUserId, HistoryChatItemBean itemBean, String imageUrl) {
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
                        //                        String consultId = getBundleString(KEY_CONSULT_ID,
                        // "");
                        getPresenter().userSyncMessageToService(toUserId, fromUserId, "", imageUrl);
                    }
                });
    }

    @Override
    public void onSendTxtFail(HistoryChatItemBean itemBean) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWechatPaySuccess(MessageEvent event) {
        int what = event.getWhat();
        if (what == AppConstant.WX_PAY_SUCCESS) {
            // 更新支付成功UI显示
            // 更新UI
            if (null != clickPayItem) {
                String toUserId = getBundleString(KEY_TO_USER_ID, "");
                String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
                getPresenter().getHistoryRecord(toUserId, selfId);
                //
                //                HistoryChatExtBean ext = clickPayItem.getChatItem().getExt();
                //                if (null != ext) {
                //                    ext.setType(2);
                //                }
                //                adapter.notifyDataSetChanged();
            }
            clickPayItem = null;
        }
    }

    //     EventBus.getDefault().post(new MessageEvent(AppConstant.WX_PAY_SUCCESS));
    // ===Desc:获取订单相关=================================================================
    @Override
    public void onGetFeeWechatOrderSuccess(FeeWechatOrderResponse bean) {
        if (null != dialog) {
            dialog.cancel();
        }
        if (null != bean) {
            WxPayBean.SignBean sign = bean.getSign();
            if (null != sign) {
                PayHelper.getInstance().payByWechat(sign);
            } else {
                onGetFeeWechatOrderFail("应用程序出现内部错误,请稍后重试");
            }
        } else {
            onGetFeeWechatOrderFail("应用程序出现内部错误,请稍后重试");
        }
    }

    @Override
    public void onGetFeeWechatOrderFail(String errorMsg) {
        showCenterToast(errorMsg);
    }

    @Override
    public void onGetFeeAliOrderSuccess(PayBean bean) {
        if (null == bean) {
            onGetFeeAliOrderFail("支付出现错误,请稍后重试");
            return;
        }
        String sign = bean.getSign();
        if (TextUtils.isEmpty(sign)) {
            onGetFeeAliOrderFail("支付出现错误,请稍后重试");
            return;
        }
        if (null != dialog) {
            dialog.cancel();
        }
        getPresenter()
                .arouseAlipay(
                        this,
                        sign,
                        new PayHelper.IPayListener() {
                            @Override
                            public void onSuccess() {
                                // 支付成功
                                Logger.e("!!! onSuccess");
                                // 支付宝支付成功   更新UI显示
                                if (null != clickPayItem) {
                                    String toUserId = getBundleString(KEY_TO_USER_ID, "");
                                    String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
                                    getPresenter().getHistoryRecord(toUserId, selfId);
                                    //                    HistoryChatExtBean ext =
                                    // clickPayItem.getChatItem().getExt();
                                    //                    if (null != ext) {
                                    //                        ext.setType(2);
                                    //                    }
                                    //                    adapter.notifyDataSetChanged();
                                }
                                clickPayItem = null;
                            }

                            @Override
                            public void onResultConfirmation() {
                                showToast("支付结果确认中");
                            }

                            @Override
                            public void onCancel() {
                                showToast("您取消支付");
                            }

                            @Override
                            public void onFail() {
                                showToast("支付出现错误,请联系客服");
                            }
                        });
    }

    @Override
    public void onGetFeeAliOrderFail(String errorMsg) {
        showCenterToast(errorMsg);
    }

    // ===Desc:=================================================================
    @Override
    public void onGetLawyerCardInfoSuccess(RoomLawyerCardInfoResponse.LawyerCardInfo info) {
        adapter.addHeaderView(cardHeaderController.getHeaderView());
        cardHeaderController.render(info);
    }

    @Override
    public void onGetLawyerCardInfoFail(String errorMsg) {}

    // ===Desc:=================================================================

    @Override
    public void onGetCustomerServiceMessageSuccess(List<ZiXunSendMessageBean.ListBean> list) {
        adapter.addHeaderView(customerHeaderController.getHeaderView());
        customerHeaderController.render(list);
    }

    @Override
    public void onGetCustomerServiceMessageFail(String errorMsg) {}

    // ===Desc:=================================================================

    @Override
    public void onCheckPaySuccess(int requestCode, WhetherToPayBean bean) {
        if (null == bean) {
            onCheckPayFail(requestCode, "应用程序出现未知错误,请稍后充实");
            return;
        }
        String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
        String toUserId = getBundleString(KEY_TO_USER_ID, "");

        int type = bean.getType();
        switch (requestCode) {
            case codeExchangeWeChat:
                if (type == 0) {
                    // 未支付

                    DialorUtil.showImAlertDialog(
                            UserChatRoomActivity.this,
                            "咨询付费后提供律师微信号",
                            new DialorUtil.OnButtonClickListener() {
                                @Override
                                public void onPositiveButtonClick(AlertDialog dialog) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void onNegativeButtonClick(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                } else if (type == 1) {
                    // 已支付  交换
                    getPresenter().exchangeWeChat(selfId, toUserId);
                }

                break;
            case codeAssess:
                if (type == 0) {
                    // 未支付

                    DialorUtil.showImAlertDialog(
                            UserChatRoomActivity.this,
                            "咨询付费后才可评价",
                            new DialorUtil.OnButtonClickListener() {
                                @Override
                                public void onPositiveButtonClick(AlertDialog dialog) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void onNegativeButtonClick(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                } else if (type == 1) {
                    // 已支付 评价
                    startActivity(new Intent(UserChatRoomActivity.this, EvaluateActivity.class));
                }
                break;
            case codeComplaint:
                if (type == 0) {
                    // 未支付
                    //                    RefundCommitActivity.Companion.start(mContext,
                    // getBundleString(KEY_TO_USER_ID, ""));

                    DialorUtil.showImAlertDialog(
                            UserChatRoomActivity.this,
                            "咨询付费后才可投诉",
                            new DialorUtil.OnButtonClickListener() {
                                @Override
                                public void onPositiveButtonClick(AlertDialog dialog) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void onNegativeButtonClick(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                } else if (type == 1) {
                    // 已支付 投诉
                    //                    RefundCommitActivity.Companion.start(mContext,
                    // getBundleString(KEY_TO_USER_ID, ""));
                    ComplaintActivity.start(this, getBundleString(KEY_TO_USER_ID, ""));
                    //                    startActivity(new Intent(UserChatRoomActivity.this,
                    // ComplaintActivity.class));
                }
                break;
            case refundint:
                if (type == 0) {
                    // 未支付
                    //                    RefundCommitActivity.Companion.start(mContext,
                    // getBundleString(KEY_TO_USER_ID, ""));

                    DialorUtil.showImAlertDialog(
                            UserChatRoomActivity.this,
                            "咨询付费后才可退款",
                            new DialorUtil.OnButtonClickListener() {
                                @Override
                                public void onPositiveButtonClick(AlertDialog dialog) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void onNegativeButtonClick(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                } else if (type == 1) {
                    // 已支付 投诉
                    RefundCommitActivity.Companion.start(
                            mContext, getBundleString(KEY_TO_USER_ID, ""));
                    //                    startActivity(new Intent(UserChatRoomActivity.this,
                    // ComplaintActivity.class));
                }
                break;
        }
    }

    @Override
    public void onCheckPayFail(int requestCode, String errorMsg) {}

    // ===Desc:=================================================================

    @Override
    public void onExchangeWeChatSuccess(ExChangeWeiXinBean data) {
        String lawyerId = data.getLvshiid();
        String userId = data.getUserid();
        String id = data.getId();
        String wxid = data.getWxid();
        String price = data.getPrice();
        if (TextUtils.isEmpty(wxid)) {

            DialorUtil.showExChangeWxDialog(
                    UserChatRoomActivity.this,
                    "获取微信需另外支付" + price + "元",
                    new DialorUtil.OnButtonClickListener() {
                        @Override
                        public void onPositiveButtonClick(AlertDialog dialog) {
                            // 支付

                        }

                        @Override
                        public void onNegativeButtonClick(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
        } else {
            DialorUtil.txteviewSelectDialog(
                    UserChatRoomActivity.this,
                    wxid,
                    (dialog, textView) -> {
                        // 传入需要复制的文字的控件
                        CopyButtonLibrary copyButtonLibrary =
                                new CopyButtonLibrary(getApplicationContext(), textView);
                        copyButtonLibrary.init();
                        dialog.dismiss();
                    });
        }
    }

    @Override
    public void onExchangeWeChatFail(String errorMsg) {
        showCenterToast(errorMsg);
    }

    // ===Desc:=================================================================
    private static final int PAY_TYPE_WECHAT = 0; // 微信支付,默认支付方式
    private static final int PAY_TYPE_ALIPAY = 1;
    private int payMethod = PAY_TYPE_WECHAT;

    private void showPayDialor(String toUserId, String feeId) {
        View dialogView = getLayoutInflater().inflate(R.layout.pay_view_alert_dialor2, null);
        CheckBox cb_pay_wechat = dialogView.findViewById(R.id.cb_pay_wechat);
        CheckBox cb_pay_ali = dialogView.findViewById(R.id.cb_pay_ali);
        cb_pay_wechat.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            payMethod = PAY_TYPE_WECHAT;
                            cb_pay_ali.setChecked(false);
                        }
                    }
                });
        cb_pay_ali.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            payMethod = PAY_TYPE_ALIPAY;
                            cb_pay_wechat.setChecked(false);
                        }
                    }
                });
        dialog =
                new PayBottomDialog(
                        this,
                        dialogView,
                        new int[] {
                            R.id.ll_pay_weichat, R.id.ll_pay_zhifubao, R.id.pay, R.id.iv_guanbi
                        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setOnBottomItemClickListener(
                new PayBottomDialog.OnBottomItemClickListener() {
                    @Override
                    public void onBottomItemClick(PayBottomDialog payBottomDialog, View view) {
                        switch (view.getId()) {
                            case R.id.iv_guanbi:
                                dialog.cancel();
                                break;
                            case R.id.ll_pay_weichat:
                                cb_pay_wechat.setChecked(true);
                                cb_pay_ali.setChecked(false);
                                break;
                            case R.id.ll_pay_zhifubao:
                                cb_pay_wechat.setChecked(false);
                                cb_pay_ali.setChecked(true);
                                break;
                            case R.id.pay:
                                switch (payMethod) {
                                    case PAY_TYPE_WECHAT:
                                        wechatPay(toUserId, feeId);
                                        break;
                                    case PAY_TYPE_ALIPAY:
                                        aliPay(toUserId, feeId);
                                        break;
                                    default:
                                        ToastUtil.showToast("应用程序出现内部错误,请稍后重试");
                                        break;
                                }
                                break;
                        }
                    }
                });
        cb_pay_wechat.setChecked(true);
        dialog.bottmShow();
    }

    /**
     * 微信支付
     *
     * @param redPackageId 红包id
     */
    private void wechatPay(String toUserId, String redPackageId) {
        String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
        getPresenter().getFeePayOrder(true, selfId, toUserId, redPackageId);
    }

    /** 支付宝 */
    private void aliPay(String toUserId, String redPackageId) {
        String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
        getPresenter().getFeePayOrder(false, selfId, toUserId, redPackageId);
    }

    private void payByAccount(String toUserId, String redPackageId) {
        String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
        getPresenter().payFeeByAccount(selfId, toUserId, redPackageId);
    }
    ///////////////////////////////////////////////////////////////////////////
    // 环信聊天接收消息的监听回调
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onTextReceived(List<HistoryChatItemMultiItem> list) {
        Logger.e("接收到消息");
        List<HistoryChatItemMultiItem> data = new ArrayList<>();
        for (HistoryChatItemMultiItem item : list) {
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
    }

    @Override
    public void onMessageRead(List<HistoryChatItemMultiItem> list) {}

    // ===Desc:=================================================================

    @Override
    public void onPayByAccountSuccess() {
        String toUserId = getBundleString(KEY_TO_USER_ID, "");
        String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
        getPresenter().getHistoryRecord(toUserId, selfId);
    }

    @Override
    public void onPayByAccountFail() {
        showToast("应用程序异常");
    }

    @Override
    public void onCheckMessagePayedSuccess(ToCheckPayBean data) {
        String type = data.getType();
        // 3是已经支付，4是未支付
        if ("3".equals(type)) {
            showToast("该笔订单已经支付,无需重复支付");
            return;
        }
        if (null != clickPayItem) {
            String toUserId = clickPayItem.getChatItem().getExt().getLvshiid();
            String feeId = clickPayItem.getChatItem().getExt().getJiluid();
            // 效验当前订单是否已经支付,请求服务器获取状态
            String price = clickPayItem.getChatItem().getExt().getPrice();
            double payMoney = 0.0;
            try {
                payMoney = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                showToast("应用程序出现异常,请稍后重试");
                return;
            }
            DialogHelper.showPayDialog(
                    mContext,
                    payMoney,
                    payMethod -> {
                        switch (payMethod) {
                            case PAY_WECHAT:
                                wechatPay(toUserId, feeId);
                                break;
                            case PAY_ALI:
                                aliPay(toUserId, feeId);
                                break;
                            case PAY_SELF:
                                payByAccount(toUserId, feeId);
                                break;
                            default:
                                ToastUtil.showToast("应用程序出现内部错误,请稍后重试");
                                break;
                        }
                    });
            //   showPayDialor(toUserId, feeId);
        } else {
            showToast("应用程序出现未知错误,请稍后重试");
        }
    }

    @Override
    public void onGetRefundBtnVisible(RefundButtonVisibleBean visibleBean) {
        int status = visibleBean.getData().getStatus();
        if (status == 1) {
            tv_room_refund.setVisibility(View.VISIBLE);
            tv_room_complaint.setVisibility(View.GONE);
        } else {
            tv_room_complaint.setVisibility(View.VISIBLE);
            tv_room_refund.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetQiNiuToken(GetQiniuTokenBean tokenBean) {
        qiNiuToken = tokenBean.getToken();
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        // 请求权限
        AndPermissionUtil.getInstance()
                .requestPermissions(
                        this,
                        () -> {
                            // permission are allowed.
                            startActivityForResult(intent, REQUEST_CODE_LOCAL);
                        },
                        Permission.Group.STORAGE);
    }
}

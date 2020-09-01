package com.ytfu.yuntaifawu.ui.zixun.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.chatroom.activity.UserChatRoomActivity;
import com.ytfu.yuntaifawu.ui.zixun.bean.AdoptionBean;
import com.ytfu.yuntaifawu.ui.zixun.bean.ReplyDetailBean;
import com.ytfu.yuntaifawu.ui.zixun.p.AdoptionAndReplyDetailPresenter;
import com.ytfu.yuntaifawu.ui.zixun.v.AdoptionAndReplyDetailView;
import com.ytfu.yuntaifawu.utils.GlideManager;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.dialog.AdoptionDialog;

import butterknife.BindView;
import butterknife.OnClick;

/** 咨询回复详情 */
@InjectLayout(
        value = R.layout.activity_advisory_reply_detail,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(AdoptionAndReplyDetailPresenter.class)
public class AdvisoryReplyDetailActivity
        extends BaseActivity<AdoptionAndReplyDetailView, AdoptionAndReplyDetailPresenter>
        implements AdoptionAndReplyDetailView {
    // 评价id
    private static final String KEY_REVIEW_ID = "REVIEW_ID";
    // 评价用户的id
    private static final String KEY_REVIEW_UID = "REVIEW_UID";
    // 咨询id
    private static final String KEY_CONSULTATION_ID = "CONSULTATION_ID";

    @BindView(R.id.iv_header)
    ImageView ivHeader;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.tv_adoption)
    TextView tvAdoption;

    @BindView(R.id.tv_count)
    TextView tvCount;

    @BindView(R.id.tv_communication)
    TextView tvCommunication;

    private AdoptionDialog adoptionDialog;
    private String avatar;
    private String user_login;
    private String toUserId;

    public static void start(
            Context context, String reviewId, String reviewUid, String consultationId) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_REVIEW_ID, reviewId);
        bundle.putString(KEY_REVIEW_UID, reviewUid);
        bundle.putString(KEY_CONSULTATION_ID, consultationId);
        Intent starter = new Intent(context, AdvisoryReplyDetailActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    // ===Desc:================================================================================
    @Override
    public void init() {
        super.init();
    }

    @Override
    protected void initData() {
        super.initData();
        changeStatusBarTextColor(false);
        //        StatusBarCompat.setStatusBarColor(this,
        // getResources().getColor(R.color.transparent_4c));
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> onBackPressed());
        setToolbarText(R.id.tv_global_title, "回复详情");
        String reviewId = getBundleString(KEY_REVIEW_ID, "");
        getPresenter().getReplyDetails(reviewId);
        adoptionDialog = new AdoptionDialog(this);
    }

    // 详情成功回调
    @Override
    public void onReplyDetailsSuccess(ReplyDetailBean replyDetailBean) {
        if (replyDetailBean == null) {
            return;
        }
        if (replyDetailBean.getFind() == null) {
            return;
        }
        ReplyDetailBean.FindBean find = replyDetailBean.getFind();
        GlideManager.loadImageByUrl(this, find.getAvatar(), ivHeader);
        avatar = find.getAvatar();
        user_login = find.getUser_login();
        toUserId = find.getUid();
        tvName.setText(find.getUser_login());
        tvTime.setText(find.getCon_date());
        tvCount.setText(find.getContent());
        int con_type = find.getCon_type();
        if (con_type != 0) {
            tvAdoption.setVisibility(View.GONE);
        } else {
            // 目前采纳隐藏，后期改动
            tvAdoption.setVisibility(View.GONE);
        }
    }

    // 采纳成功回调
    @Override
    public void onAdoptionSuccess(AdoptionBean adoptionBean) {
        if (adoptionBean == null) {
            return;
        }
        int status = adoptionBean.getStatus();
        if (status == 200) {
            showCenterToast(adoptionBean.getMsg());
            adoptionDialog.dismiss();
            finish();
        }
    }

    @Override
    public void onFiled() {}

    @OnClick({R.id.tv_adoption, R.id.tv_communication})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_adoption:
                // 采纳
                new XPopup.Builder(this)
                        .dismissOnBackPressed(false)
                        .dismissOnTouchOutside(false)
                        .setPopupCallback(
                                new XPopupCallback() {
                                    @Override
                                    public void onCreated() {
                                        // 确定
                                        adoptionDialog.setOkClick(
                                                v -> {
                                                    String uid =
                                                            SpUtil.getString(
                                                                    mContext, AppConstant.UID, "");
                                                    String reviewUid =
                                                            getBundleString(KEY_REVIEW_UID, "");
                                                    String reviewId =
                                                            getBundleString(KEY_REVIEW_ID, "");
                                                    String consultationId =
                                                            getBundleString(
                                                                    KEY_CONSULTATION_ID, "");
                                                    getPresenter()
                                                            .getAdoption(
                                                                    uid,
                                                                    reviewUid,
                                                                    reviewId,
                                                                    consultationId);
                                                });
                                        // 取消
                                        adoptionDialog.setCancelClick(
                                                v -> adoptionDialog.dismiss());
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
                        .asCustom(adoptionDialog)
                        .show();
                break;
            case R.id.tv_communication:
                // 立即沟通
                UserChatRoomActivity.start(this, false, false, toUserId, user_login, avatar);
                break;
        }
    }
}

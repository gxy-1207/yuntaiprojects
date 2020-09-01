package com.ytfu.yuntaifawu.ui.lvshiwenti.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.zixun.bean.AdoptionBean;
import com.ytfu.yuntaifawu.ui.zixun.bean.ReplyDetailBean;
import com.ytfu.yuntaifawu.ui.zixun.p.AdoptionAndReplyDetailPresenter;
import com.ytfu.yuntaifawu.ui.zixun.v.AdoptionAndReplyDetailView;
import com.ytfu.yuntaifawu.utils.GlideManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther gxy
 * @Date 2020/6/17
 * @Des 律师端咨询回复详情
 */
@InjectLayout(value = R.layout.activity_lawyer_advisory_reply_detail, toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(AdoptionAndReplyDetailPresenter.class)
public class LawyerReplyDetailsActivity extends BaseActivity
        <AdoptionAndReplyDetailView
                , AdoptionAndReplyDetailPresenter> implements AdoptionAndReplyDetailView {
    //评价id
    private static final String KEY_REVIEW_ID = "REVIEW_ID";
    //评价用户的id
    private static final String KEY_REVIEW_UID = "REVIEW_UID";
    //咨询id
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

    public static void start(Context context, String reviewId, String reviewUid, String consultationId) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_REVIEW_ID, reviewId);
        bundle.putString(KEY_REVIEW_UID, reviewUid);
        bundle.putString(KEY_CONSULTATION_ID, consultationId);
        Intent starter = new Intent(context, LawyerReplyDetailsActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);

    }

    @Override
    protected void initData() {
        super.initData();
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> onBackPressed());
        setToolbarText(R.id.tv_global_title, "回复详情");
        String reviewId = getBundleString(KEY_REVIEW_ID, "");
        getPresenter().getReplyDetails(reviewId);
    }

    @Override
    public void onReplyDetailsSuccess(ReplyDetailBean replyDetailBean) {
        ReplyDetailBean.FindBean find = replyDetailBean.getFind();
        GlideManager.loadImageByUrl(this,find.getAvatar(),ivHeader);
        tvName.setText(find.getUser_login());
        tvTime.setText(find.getCon_date());
        tvCount.setText(find.getContent());
    }

    @Override
    public void onAdoptionSuccess(AdoptionBean adoptionBean) {

    }

    @Override
    public void onFiled() {

    }

}

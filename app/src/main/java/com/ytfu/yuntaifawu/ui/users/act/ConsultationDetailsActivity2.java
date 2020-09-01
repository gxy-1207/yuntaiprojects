package com.ytfu.yuntaifawu.ui.users.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.lawyer.chat.act.LawyerChatRoomActivity;
import com.ytfu.yuntaifawu.ui.users.bean.ConsultationDetailsBean;
//import com.ytfu.yuntaifawu.ui.users.fragment.ConsultationDetailsRewardFragment2;
import com.ytfu.yuntaifawu.ui.users.p.ConsultationDetailsRewardPresenter2;
import com.ytfu.yuntaifawu.ui.users.v.ConsultationDetailsView2;
import com.ytfu.yuntaifawu.utils.GlideManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther gxy
 * @Date 2020/7/6
 * @Des 他的咨询详情
 */
@InjectLayout(value = R.layout.activity_consultation_details2, toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(ConsultationDetailsRewardPresenter2.class)
public class ConsultationDetailsActivity2 extends BaseActivity<ConsultationDetailsView2, ConsultationDetailsRewardPresenter2>
        implements ConsultationDetailsView2 {
    private static final String KEY_CONSULTATION_ID = "CONSULTATION_ID";
    private static final String KEY_JUMP_TYPE = "JUMP_TYPE";
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_urgent)
    ImageView ivUrgent;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.iv_status_solve)
    ImageView ivStatusSolve;
    @BindView(R.id.iv_status_closed)
    ImageView ivStatusClosed;
    @BindView(R.id.tv_connect)
    TextView tvConnect;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.tv_communicate_now)
    TextView tv_communicate_now;
    private ConsultationDetailsBean.ContentBean data;

    public static void start(Context context, String id, int jumpType) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CONSULTATION_ID, id);
        bundle.putInt(KEY_JUMP_TYPE, jumpType);
        Intent starter = new Intent(context, ConsultationDetailsActivity2.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected void initData() {
        super.initData();
        showLoading();
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> {
            onBackPressed();
        });
        setToolbarText(R.id.tv_global_title, "咨询详情");
//        //咨询悬赏详情
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fl_consultation_count, ConsultationDetailsRewardFragment2.newInstance(getBundleString(KEY_CONSULTATION_ID, "")))
//                .commitAllowingStateLoss();
        //从别的页面传过来的值  jumpType == 0为首页进入 jumpType == 1为我的页面他的咨询进入
        int jumpType = getBundleInt(KEY_JUMP_TYPE, -1);
        if (jumpType == 0) {
            tv_communicate_now.setVisibility(View.VISIBLE);
        } else {
            tv_communicate_now.setVisibility(View.GONE);
        }
        String consultationId = getBundleString(KEY_CONSULTATION_ID, "");
        getPresenter().getConsultationDetails2(consultationId);
        //立即沟通
        tv_communicate_now.setOnClickListener(v -> {
            String toUserId = data.getUid();
            String toUserName = data.getUser_login();
            String toUserAvatar = data.getAvatar();
            String consultId = data.getId();
            LawyerChatRoomActivity.start(
                    this, false, toUserId, toUserName, toUserAvatar, consultId);
        });
    }

    @Override
    public void onConsultationDetailsSuccess(ConsultationDetailsBean consultationDetailsBean) {
        hideLoading();
        if (consultationDetailsBean.getContent() != null) {
            data = consultationDetailsBean.getContent();
            GlideManager.loadImageByUrl(mContext, data.getAvatar(), ivHeader);
            tvName.setText(data.getUser_login());
            tvTime.setText(data.getConsult_date());
            tvType.setText(data.getConsult_type());
            tvConnect.setText(data.getConsult_content());
        }
    }

    @Override
    public void onConsultationDetailsFiled() {

    }
}


package com.ytfu.yuntaifawu.ui.users.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.XPopup;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseFragment;
import com.ytfu.yuntaifawu.db.LvShiDao;
import com.ytfu.yuntaifawu.ui.complaint.act.ComplaintListActivity;
import com.ytfu.yuntaifawu.ui.lawyer.wallet.act.WalletActivity;
import com.ytfu.yuntaifawu.ui.lawyer.wallet.bean.WalletResponseBean;
import com.ytfu.yuntaifawu.ui.lawyer.withdraw.act.WithdrawActivity;
import com.ytfu.yuntaifawu.ui.mine.activity.ActivityGrzx;
import com.ytfu.yuntaifawu.ui.mine.activity.ActivityLvShiRenZheng;
import com.ytfu.yuntaifawu.ui.mine.activity.ActivitySetting;
import com.ytfu.yuntaifawu.ui.mine.activity.ActivitySheHeJinDu;
import com.ytfu.yuntaifawu.ui.mine.bean.MineBean;
import com.ytfu.yuntaifawu.ui.mine.bean.ShenHeJInduBean;
import com.ytfu.yuntaifawu.ui.mseeage.activity.UserEvaluateActivityNew;
import com.ytfu.yuntaifawu.ui.users.act.AnnouncementActivity;
import com.ytfu.yuntaifawu.ui.users.act.MineConsultationListActivity;
import com.ytfu.yuntaifawu.ui.users.act.MyRefundActivity;
import com.ytfu.yuntaifawu.ui.users.bean.RefundButtonVisibleBean;
import com.ytfu.yuntaifawu.ui.users.p.UserPersonalPresenter;
import com.ytfu.yuntaifawu.ui.users.v.UserPersonalView;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.GlideManager;
import com.ytfu.yuntaifawu.utils.LoginHelper;
import com.ytfu.yuntaifawu.utils.SpUtil;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

/** 用户我的界面 */
@InjectLayout(value = R.layout.fragment_personal)
@InjectPresenter(UserPersonalPresenter.class)
public class UserPersonalFragment extends BaseFragment<UserPersonalView, UserPersonalPresenter>
        implements UserPersonalView {
    public static int CODE_WITHDRAW = 100;

    @BindView(R.id.tl_personal_toolbar)
    Toolbar tl_personal_toolbar;

    @BindView(R.id.tv_personal_title)
    TextView tv_personal_title;

    @BindView(R.id.iv_personal_avatar)
    ImageView iv_personal_avatar;

    @BindView(R.id.tv_personal_name)
    TextView tv_personal_name;

    @BindView(R.id.tv_personal_balance)
    TextView tv_personal_balance;

    @BindView(R.id.tv_personal_refund)
    TextView tv_personal_refund;

    @BindView(R.id.red_dot)
    ImageView red_dot;

    public static UserPersonalFragment newInstance() {
        return new UserPersonalFragment();
    }

    // ===Desc:================================================================================
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            FragmentActivity activity = getActivity();
            if (null != activity) {
                StatusBarCompat.setStatusBarColor(activity, Color.parseColor("#44A2F7"));
            }
        }
    }

    @Override
    protected void setViewListener(View rootView) {
        super.setViewListener(rootView);
        // 个人中心
        rootView.findViewById(R.id.rl_personal_center)
                .setOnClickListener(
                        v -> startActivity(new Intent(getActivity(), ActivityGrzx.class)));
        // 钱包
        rootView.findViewById(R.id.tv_personal_wallet)
                .setOnClickListener(v -> WalletActivity.start(mContext, true));

        // 提现
        rootView.findViewById(R.id.tv_personal_withdraw)
                .setOnClickListener(
                        v -> {
                            // 请求网络获取账户信息  进入体现
                            //                    String userId =
                            // SpUtil.getString(mContext,AppConstant.UID,
                            // "");
                            //                    getPresenter().getWalletInfo(userId);
                            WalletActivity.start(mContext, true);
                        });

        // 评价
        rootView.findViewById(R.id.tv_personal_comment)
                .setOnClickListener(
                        v -> {
                            //            Intent intentPingJia = new Intent(mContext,
                            // UserEvaluateActivity.class);
                            //            intentPingJia.putExtra("lid",
                            // SpUtil.getString(mContext,AppConstant.UID, ""));
                            //            startActivity(intentPingJia);
                            UserEvaluateActivityNew.Companion.start(
                                    getActivity(),
                                    SpUtil.getString(mContext, AppConstant.UID, ""),
                                    "我的评价");
                        });
        // 我的起诉状
        rootView.findViewById(R.id.tv_personal_complaint)
                .setOnClickListener(
                        v ->
                                ComplaintListActivity.Companion.starter(
                                        mContext, LoginHelper.getInstance().getLoginUserId(), ""));
        // 我的咨询
        rootView.findViewById(R.id.tv_personal_inquiry)
                .setOnClickListener(v -> MineConsultationListActivity.start(mContext));
        // 我的退款
        rootView.findViewById(R.id.tv_personal_refund)
                .setOnClickListener(
                        view -> {
                            MyRefundActivity.start(getActivity());
                        });
        // 认证
        rootView.findViewById(R.id.tv_personal_oauth)
                .setOnClickListener(
                        v -> {
                            String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
                            getPresenter().getAuthInfo(selfId);
                        });

        // 公告
        rootView.findViewById(R.id.rl_personal_announcement)
                .setOnClickListener(v -> AnnouncementActivity.start(mContext));
        // 设置
        rootView.findViewById(R.id.tv_personal_setting)
                .setOnClickListener(
                        v -> startActivity(new Intent(getActivity(), ActivitySetting.class)));
    }

    @Override
    protected void initData() {
        super.initData();
        tl_personal_toolbar.setTitle("");
        //        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
        // tl_personal_toolbar.getLayoutParams();
        //        layoutParams.topMargin = getStatusHeight();
        //        tl_personal_toolbar.requestLayout();
        tv_personal_title.setText(getString(R.string.txt_mine));

        String s = CommonUtil.doubleFormat(0, "0.00");
        tv_personal_balance.setText(s);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 获取个人数据
        String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
        getPresenter().getUserInfo(selfId);
        getPresenter().getAccountBalance(selfId);
        // 控制退款按钮显示隐藏
        getPresenter().getRefundButtonVisible();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_WITHDRAW) {
            String selfId = SpUtil.getString(mContext, AppConstant.UID, "");
            getPresenter().getAccountBalance(selfId);
        }
    }

    // ===Desc:================================================================================

    @Override
    public void onGetInfoSuccess(MineBean.FindBean bean) {
        String uid = SpUtil.getString(mContext, AppConstant.UID, "");
        LvShiDao.getInstance(getContext())
                .lvShiAdd(uid, bean.getUser_login(), bean.getAvatar(), null, null);

        GlideManager.loadRadiusImage(getContext(), bean.getAvatar(), iv_personal_avatar, 4);
        //        RoundedCorners rc = new RoundedCorners(XPopupUtils.dp2px(mContext, 4));
        //        RequestOptions options = new RequestOptions()//.bitmapTransform(rc)
        //                .transform(rc)
        //                .centerCrop()
        //                .placeholder(R.drawable.touxiang)//图片加载出来前，显示的图片
        //                .fallback(R.drawable.touxiang) //url为空的时候,显示的图片
        //                .error(R.drawable.touxiang);//图片加载失败后，显示的图片
        //        Glide.with(mContext).load(bean.getAvatar())
        //                .apply(options)
        //                .into(iv_personal_avatar);
        tv_personal_name.setText(bean.getUser_login());
        tv_personal_balance.setText(bean.getIncome());
        // 是否有新公告
        if (bean.getRand_type() == 1) {
            red_dot.setVisibility(View.VISIBLE);
        } else {
            red_dot.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetInfoFail(String errorMsg) {
        RequestOptions options =
                new RequestOptions()
                        .placeholder(R.drawable.touxiang) // 图片加载出来前，显示的图片
                        .fallback(R.drawable.touxiang) // url为空的时候,显示的图片
                        .error(R.drawable.touxiang); // 图片加载失败后，显示的图片
        Glide.with(mContext).load(R.drawable.touxiang).apply(options).into(iv_personal_avatar);
        tv_personal_name.setText("登录/注册");
    }

    @Override
    public void onGetAuthInfo(ShenHeJInduBean bean) {
        if (bean != null) {
            int status = bean.getStatus();
            if (status == 0) {
                startActivity(new Intent(getActivity(), ActivityLvShiRenZheng.class));
            } else {
                Intent intent = new Intent(getActivity(), ActivitySheHeJinDu.class);
                intent.putExtra("status", status);
                intent.putExtra("name", bean.getName());
                intent.putExtra("photo", bean.getPhoto());
                intent.putExtra("liyou", bean.getLiyou());
                intent.putExtra("yuanyin", bean.getYuanyin());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onGetAccountBalance(double balance) {
        String s = CommonUtil.doubleFormat(balance, "0.00");
        tv_personal_balance.setText(s);
    }

    @Override
    public void onGetWalletInfoSuccess(WalletResponseBean bean) {
        // 获取钱包数据成功
        hideProgress();
        WalletResponseBean.DataBean data = bean.getData();
        int status = data.getBindingStatus();
        if (status == 1) { // 已绑定
            double b;
            try {
                b = Double.parseDouble(data.getBalance());
            } catch (NumberFormatException e) {
                b = 0;
            }
            // 进入体现界面
            if (b < 100) {
                new XPopup.Builder(mContext)
                        .asConfirm(
                                getString(R.string.txt_bind_tips_title),
                                getString(R.string.txt_withdraw_insufficient_balance),
                                () -> {})
                        .hideCancelBtn()
                        .show();
            } else {
                // 进入提现页面
                WithdrawActivity.startActivityForResult(
                        getActivity(), CODE_WITHDRAW, bean.getData());
            }
        } else {
            // 进入绑定界面
            WalletActivity.start(mContext, true);
        }
    }

    @Override
    public void onRefundButtonVisibleSuccess(RefundButtonVisibleBean visibleBean) {
        int status = visibleBean.getData().getStatus();
        if (status == 1) {
            tv_personal_refund.setVisibility(View.VISIBLE);
        } else {
            tv_personal_refund.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetWalletInfoFail(String errorMsg) {
        hideProgress();
        showToast(errorMsg);
    }

    // ===Desc:================================================================================
    private int getStatusHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}

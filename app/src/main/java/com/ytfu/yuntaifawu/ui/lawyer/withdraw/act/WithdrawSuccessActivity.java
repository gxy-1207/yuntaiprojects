package com.ytfu.yuntaifawu.ui.lawyer.withdraw.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.lawyer.withdraw.bean.WithdrawSuccessBean;
import com.ytfu.yuntaifawu.ui.lawyer.withdraw.p.WithdrawSuccessPresenter;
import com.ytfu.yuntaifawu.ui.lawyer.withdraw.v.WithdrawSuccessView;
import com.ytfu.yuntaifawu.utils.SpUtil;

import butterknife.BindView;

/** @Auther gxy @Date 2020/7/9 @Des 提现成功页面 */
@InjectLayout(
        value = R.layout.activity_embody_success,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(WithdrawSuccessPresenter.class)
public class WithdrawSuccessActivity
        extends BaseActivity<WithdrawSuccessView, WithdrawSuccessPresenter>
        implements WithdrawSuccessView {
    private static final String KEY_WITHDRAW_ID = "";

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.tv_service_fee_price)
    TextView tvServiceFeePrice;

    @BindView(R.id.tv_arrive_price)
    TextView tvArrivePrice;

    public static void start(Context context, String withdrawId) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_WITHDRAW_ID, withdrawId);
        Intent starter = new Intent(context, WithdrawSuccessActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected void initData() {
        super.initData();
        showLoading();
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai, view -> onBackPressed());
        setToolbarText(R.id.tv_global_title, "提现成功");
        String uid = SpUtil.getString(mContext, AppConstant.UID, "");
        String withdrawId = getBundleString(KEY_WITHDRAW_ID, "");
        getPresenter().getWithdrawSuccess(uid, withdrawId);
    }

    @Override
    public void onWithdrawSuccess(WithdrawSuccessBean withdrawSuccessBean) {
        hideLoading();
        WithdrawSuccessBean.DataBean data = withdrawSuccessBean.getData();
        tvPrice.setText("¥ " + data.getTixian());
        tvArrivePrice.setText("¥" + data.getTixian_shiji());
        tvServiceFeePrice.setText("¥" + data.getTixian_fuwu());
    }

    @Override
    public void onWithdrawFiled() {}
}

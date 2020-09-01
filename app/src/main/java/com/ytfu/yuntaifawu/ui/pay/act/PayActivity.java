package com.ytfu.yuntaifawu.ui.pay.act;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.pay.PayHelper;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountInfoBean;
import com.ytfu.yuntaifawu.ui.pay.p.PayPresenter;
import com.ytfu.yuntaifawu.ui.pay.v.PayView;
import com.ytfu.yuntaifawu.ui.users.act.MineConsultationListActivity;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.LoginHelper;
import com.ytfu.yuntaifawu.utils.MessageEvent;
import com.ytfu.yuntaifawu.utils.SpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

/** 支付界面 */
@InjectLayout(value = R.layout.activity_pay, toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(PayPresenter.class)
public class PayActivity extends BaseActivity<PayView, PayPresenter> implements PayView {

    @BindView(R.id.tv_pay_money)
    TextView tv_pay_money;

    @BindView(R.id.tv_pay_appendix_tip)
    TextView tv_pay_appendix_tip;

    @BindView(R.id.tv_pay_appendix_desc)
    TextView tv_pay_appendix_desc;

    @BindView(R.id.tv_pay_total)
    TextView tv_pay_total;

    @BindView(R.id.rg_pay_types)
    RadioGroup rg_pay_types;

    @BindView(R.id.rb_pay_self)
    RadioButton rb_pay_self;

    private static final String KEY_MONEY = "MONEY";
    private static final String KEY_ADVISORY_ID = "ADVISORY_ID";

    public static void start(Context context, String advisoryId, double money) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ADVISORY_ID, advisoryId);
        bundle.putDouble(KEY_MONEY, money);
        Intent starter = new Intent(context, PayActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    // ===Desc:================================================================================

    @Override
    public void init() {
        super.init();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        hideLoading();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.transparent_4c));
        changeStatusBarTextColor(false);
        setToolbarText(R.id.tv_global_title, "悬赏咨询");
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> onBackPressed());

        rg_pay_types.check(R.id.rb_pay_ali);
        // 获取悬赏支付信息
        String userId = SpUtil.getString(mContext, AppConstant.UID, "");
        getPresenter().getAdvisoryInfo(userId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWechatPaySuccess(MessageEvent event) {
        int what = event.getWhat();
        if (what == AppConstant.WX_PAY_SUCCESS) {
            // 更新支付成功UI显示
            getPresenter()
                    .requestSendMessage(
                            LoginHelper.getInstance().getLoginUserId(),
                            new Runnable() {
                                @Override
                                public void run() {
                                    MineConsultationListActivity.start(PayActivity.this);
                                    //                    MainActivity.start(mContext, 1);
                                    finish();
                                }
                            });
        }
    }

    // ===Desc:================================================================================

    @Override
    public void onGetAdvisoryInfoSuccess(AccountInfoBean data) {
        if (null == data) {
            showError();
            return;
        }
        tv_pay_money.setText(getBundleDouble(KEY_MONEY, 0.0) + "元");
        tv_pay_appendix_tip.setText(data.getZhuijia_title());
        tv_pay_appendix_desc.setText(data.getZhuijia_descript());

        double total = getBundleDouble(KEY_MONEY, 0.0) + data.getJiaji();
        tv_pay_total.setText("合计:" + total + "元");
        rb_pay_self.setText("我的余额(¥" + CommonUtil.doubleFormat(data.getIncome(), "0.00") + "元)");
        if (data.getIncome() < total) {
            rb_pay_self.setEnabled(false);
            rb_pay_self.setTextColor(Color.parseColor("#ECE8E8"));
        }
        // 监听事件
        CheckBox cb_pay_appendix = findViewById(R.id.cb_pay_appendix);
        cb_pay_appendix.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        double payMoney = 0.0;
                        if (isChecked) {
                            payMoney = getBundleDouble(KEY_MONEY, 0.0) + data.getJiaji();
                        } else {
                            payMoney = getBundleDouble(KEY_MONEY, 0.0);
                        }
                        tv_pay_total.setText("合计:" + payMoney + "元");
                        if (data.getIncome() < payMoney) {
                            rb_pay_self.setEnabled(false);
                            rb_pay_self.setTextColor(Color.parseColor("#ECE8E8"));
                        } else {
                            rb_pay_self.setEnabled(true);
                            rb_pay_self.setTextColor(Color.parseColor("#3B3B3B"));
                        }
                    }
                });

        findViewById(R.id.tv_pay_commit)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 请求参数
                                String userId = SpUtil.getString(mContext, AppConstant.UID, "");
                                String advisoryId = getBundleString(KEY_ADVISORY_ID, "");
                                int type = 14;
                                boolean expedited = cb_pay_appendix.isChecked();
                                String additionalReward =
                                        String.valueOf(getBundleDouble(KEY_MONEY, 0.0));

                                // 判断支付方式  进行支付
                                int checkedId = rg_pay_types.getCheckedRadioButtonId();
                                if (checkedId == R.id.rb_pay_wechat) {
                                    getPresenter()
                                            .getWechatPayOrder(
                                                    userId,
                                                    advisoryId,
                                                    type,
                                                    expedited,
                                                    additionalReward);
                                } else if (checkedId == R.id.rb_pay_ali) {
                                    getPresenter()
                                            .getAliPayOrder(
                                                    userId,
                                                    advisoryId,
                                                    type,
                                                    expedited,
                                                    additionalReward);
                                } else if (checkedId == R.id.rb_pay_self) {
                                    // 余额支付
                                    getPresenter()
                                            .payByAccount(
                                                    userId,
                                                    advisoryId,
                                                    type,
                                                    expedited,
                                                    additionalReward);
                                } else {
                                    showToast("应用程序出现错误,请稍后重试");
                                }
                            }
                        });
    }

    @Override
    public void onAwakeAli(String sign) {
        PayHelper.getInstance()
                .setIPayListener(
                        new PayHelper.IPayListener() {
                            @Override
                            public void onSuccess() {
                                getPresenter()
                                        .requestSendMessage(
                                                LoginHelper.getInstance().getLoginUserId(),
                                                new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        MineConsultationListActivity.start(
                                                                PayActivity.this);
                                                        //
                                                        // MainActivity.start(mContext, 1);
                                                        finish();
                                                    }
                                                });
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
        PayHelper.getInstance().AliPay(this, sign);
    }

    @Override
    public void onPayByAccountSuccess() {
        getPresenter()
                .requestSendMessage(
                        LoginHelper.getInstance().getLoginUserId(),
                        new Runnable() {
                            @Override
                            public void run() {
                                MineConsultationListActivity.start(PayActivity.this);
                                //                MainActivity.start(mContext, 1);
                                finish();
                            }
                        });
    }
}

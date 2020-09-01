package com.ytfu.yuntaifawu.ui.topup.act;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.pay.PayHelper;
import com.ytfu.yuntaifawu.ui.pay.bean.WxPayBean;
import com.ytfu.yuntaifawu.ui.topup.p.TopUpPresenter;
import com.ytfu.yuntaifawu.ui.topup.v.TopUpView;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.MessageEvent;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.dialog.DialogHelper;
import com.ytfu.yuntaifawu.utils.dialog.PayDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

@InjectLayout(
        value = R.layout.activity_topup,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(TopUpPresenter.class)
public class TopUpActivity extends BaseActivity<TopUpView, TopUpPresenter> implements TopUpView {
    @BindView(R.id.et_top_money)
    EditText et_top_money;

    @BindView(R.id.tv_top_commit)
    TextView tv_top_commit;

    public static void startActivityForResult(Activity activity, int requestCode) {
        Intent starter = new Intent(activity, TopUpActivity.class);
        activity.startActivityForResult(starter, requestCode);
    }

    // ===Desc:================================================================================

    @Override
    protected void setViewListener() {
        super.setViewListener();
        et_top_money.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        String inputMoney = et_top_money.getText().toString().trim();
                        tv_top_commit.setEnabled(!TextUtils.isEmpty(inputMoney));
                    }
                });

        tv_top_commit.setOnClickListener(
                v -> {
                    String inputMoney = et_top_money.getText().toString().trim();
                    if (TextUtils.isEmpty(inputMoney)) {
                        showToast("请输入充值金额");
                        return;
                    }
                    double money;
                    try {
                        money = Double.parseDouble(inputMoney);
                    } catch (NumberFormatException e) {
                        showToast("请输入正确的充值金额");
                        return;
                    }
                    CommonUtil.hideSoftInput(this);
                    DialogHelper.showPayDialogWithoutBalance(
                            mContext,
                            payMethod -> {
                                String userId = SpUtil.getString(mContext, AppConstant.UID, "");
                                if (payMethod == PayDialog.PayMethod.PAY_WECHAT) {
                                    getPresenter().topUpWechat(userId, money);
                                } else if (payMethod == PayDialog.PayMethod.PAY_ALI) {
                                    getPresenter().topUpAli(userId, money);
                                }
                            });
                });
    }

    @Override
    protected void initData() {
        super.initData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> onBackPressed());
        setToolbarText(R.id.tv_global_title, "充值");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWechatPaySuccess(MessageEvent event) {
        int what = event.getWhat();
        if (what == AppConstant.WX_PAY_SUCCESS) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onTopUpWechatSuccess(WxPayBean.SignBean sign) {
        PayHelper.getInstance().payByWechat(sign);
    }

    @Override
    public void onTopUpAliSuccess(String sign) {
        PayHelper.getInstance()
                .setIPayListener(
                        new PayHelper.IPayListener() {
                            @Override
                            public void onSuccess() {
                                showToast("充值成功");
                                setResult(RESULT_OK);
                                finish();
                            }

                            @Override
                            public void onResultConfirmation() {}

                            @Override
                            public void onCancel() {}

                            @Override
                            public void onFail() {}
                        });
        PayHelper.getInstance().AliPay(this, sign);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

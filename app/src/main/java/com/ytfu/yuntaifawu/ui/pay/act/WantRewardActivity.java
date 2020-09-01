package com.ytfu.yuntaifawu.ui.pay.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.home.adaper.RewardAdapter;
import com.ytfu.yuntaifawu.ui.pay.PayHelper;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountInfoBean;
import com.ytfu.yuntaifawu.ui.pay.p.PayPresenter;
import com.ytfu.yuntaifawu.ui.pay.v.PayView;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.MessageEvent;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.dialog.DialogHelper;
import com.ytfu.yuntaifawu.utils.dialog.PayDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

@InjectLayout(
        value = R.layout.activity_want_reward,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(PayPresenter.class)
public class WantRewardActivity extends BaseActivity<PayView, PayPresenter> implements PayView {

    //    @BindView(R.id.rg_advisory_money_group)
    //    RadioGroup rg_advisory_money_group;
    @BindView(R.id.rv_advisory_money)
    RecyclerView rv_advisory_money;

    private RewardAdapter adapter = new RewardAdapter();

    private static final String KEY_ADVISORY_ID = "ADVISORY_ID";
    private static final String KEY_PAY_TYPE = "PAY_TYPE";

    public static void start(Context context, String advisoryId, int type) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ADVISORY_ID, advisoryId);
        bundle.putInt(KEY_PAY_TYPE, type);
        Intent starter = new Intent(context, WantRewardActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    public void init() {
        super.init();
        String advisoryId = getBundleString(KEY_ADVISORY_ID, "");
        if (TextUtils.isEmpty(advisoryId)) {
            finish();
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void setViewListener() {
        super.setViewListener();
        findViewById(R.id.tv_advisory_commit)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CommonUtil.hideSoftInput(WantRewardActivity.this);
                                Integer selected = adapter.getSelected();
                                double money = 0;
                                if (null == selected) {
                                    money = 0;
                                } else {
                                    money = selected * 1.0;
                                }
                                //                int checkedId =
                                // rg_advisory_money_group.getCheckedRadioButtonId();
                                //                switch (checkedId) {
                                //                    default:
                                //                    case R.id.rb_advisory_money_one:
                                //                        money = 5;
                                //                        break;
                                //                    case R.id.rb_advisory_money_two:
                                //                        money = 15;
                                //                        break;
                                //                    case R.id.rb_advisory_money_three:
                                //                        money = 25;
                                //                        break;
                                //                    case R.id.rb_advisory_money_four:
                                //                        money = 35;
                                //                        break;
                                //                }

                                EditText et_advisory_money = findViewById(R.id.et_advisory_money);
                                String input = et_advisory_money.getText().toString().trim();
                                if (TextUtils.isEmpty(input)) {
                                    showToast(getString(R.string.hint_input_money));

                                    return;
                                }

                                try {
                                    money = Double.parseDouble(input);
                                    if (money < PayPresenter.getDefaultMinMoney()) {
                                        showToast(
                                                "最低输入金额不能低于"
                                                        + PayPresenter.getDefaultMinMoney()
                                                        + "元");
                                        return;
                                    }
                                } catch (NumberFormatException e) {
                                    showToast("请输入有效的金额");
                                    return;
                                }
                                String advisoryId = getBundleString(KEY_ADVISORY_ID, "");
                                int type = getBundleInt(KEY_PAY_TYPE, -1);
                                if (type == 14 || type == 18) {
                                    showPaySelectDialog(money);
                                } else {
                                    PayActivity.start(mContext, advisoryId, money);
                                    finish();
                                }
                            }
                        });

        adapter.setOnItemClickListener(
                (a, view, position) -> {
                    adapter.setSelected(adapter.getData().get(position));
                    EditText et_advisory_money = findViewById(R.id.et_advisory_money);
                    et_advisory_money.setText(String.valueOf(adapter.getData().get(position)));
                });
        EditText et_advisory_money = findViewById(R.id.et_advisory_money);
        et_advisory_money.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        try {
                            double inputMoney =
                                    Double.parseDouble(
                                            et_advisory_money.getText().toString().trim());
                            Integer selected = adapter.getSelected();
                            if (null == selected) {
                                adapter.cleanSelected();
                            } else {
                                if (inputMoney != selected) {
                                    adapter.cleanSelected();
                                }
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    // 支付方式选择
    private void showPaySelectDialog(double money) {
        DialogHelper.showPayDialog(
                this,
                money,
                new PayDialog.OnCommitListener() {
                    @Override
                    public void onCommit(PayDialog.PayMethod payMethod) {
                        String consultationId = getBundleString(KEY_ADVISORY_ID, "");
                        int type = getBundleInt(KEY_PAY_TYPE, -1);
                        String uid = SpUtil.getString(mContext, AppConstant.UID, "");
                        String price = String.valueOf(money);
                        switch (payMethod) {
                            case PAY_WECHAT:
                                getPresenter()
                                        .getWechatPayOrder(uid, consultationId, type, false, price);
                                break;
                            case PAY_ALI:
                                getPresenter()
                                        .getAliPayOrder(uid, consultationId, type, false, price);
                                break;
                            case PAY_SELF:
                                getPresenter()
                                        .payByAccount(uid, consultationId, type, false, price);
                                break;
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        hideLoading();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.transparent_4c));
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> onBackPressed());
        setToolbarText(R.id.tv_global_title, "我要悬赏");

        rv_advisory_money.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv_advisory_money.setAdapter(adapter);
        List<Integer> reward6 = PayPresenter.getReward6();
        adapter.setNewInstance(reward6);
        adapter.setSelected(reward6.get(0));
        EditText et_advisory_money = findViewById(R.id.et_advisory_money);
        et_advisory_money.setText(String.valueOf(reward6.get(0)));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWechatPaySuccess(MessageEvent event) {
        int what = event.getWhat();
        if (what == AppConstant.WX_PAY_SUCCESS) {
            // 更新支付成功UI显示
            //            MineConsultationListActivity.start(mContext);
            showCenterToast("支付成功");
            finish();
        }
    }

    @Override
    public void onGetAdvisoryInfoSuccess(AccountInfoBean data) {}

    @Override
    public void onAwakeAli(String sign) {
        PayHelper.getInstance()
                .setIPayListener(
                        new PayHelper.IPayListener() {
                            @Override
                            public void onSuccess() {
                                showCenterToast("支付成功");
                                finish();
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
        showCenterToast("支付成功");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

package com.ytfu.yuntaifawu.utils.dialog;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.utils.CommonUtil;

/** 支付弹窗 */
public class PayDialog extends BottomPopupView {
    private PayMethod payMethod = PayMethod.PAY_WECHAT;
    private OnCommitListener listener;

    public PayDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_bottom_pay;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        RadioGroup rg_dialog_pay_method = findViewById(R.id.rg_dialog_pay_method);
        findViewById(R.id.iv_dialog_pay_close)
                .setOnClickListener(
                        new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dismiss();
                            }
                        });

        rg_dialog_pay_method.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            default:
                            case R.id.rb_dialog_pay_wechat:
                                payMethod = PayMethod.PAY_WECHAT;
                                break;
                            case R.id.rb_dialog_pay_ali:
                                payMethod = PayMethod.PAY_ALI;
                                break;
                            case R.id.rb_dialog_pay_self:
                                payMethod = PayMethod.PAY_SELF;
                                break;
                        }
                    }
                });

        findViewById(R.id.tv_dialog_pay_commit)
                .setOnClickListener(
                        new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dismissWith(
                                        () -> {
                                            if (null != listener) {
                                                listener.onCommit(PayDialog.this.payMethod);
                                            }
                                        });
                            }
                        });
        // 默认选中微信支付
        rg_dialog_pay_method.check(R.id.rb_dialog_pay_ali);
    }

    // ===Desc:================================================================================

    /** 设置余额支付不可点击,但是显示 */
    public void enableSelfPay(boolean enable) {
        RadioButton rb_dialog_pay_self = findViewById(R.id.rb_dialog_pay_self);
        rb_dialog_pay_self.setEnabled(enable);
        if (enable) {
            rb_dialog_pay_self.setText(rb_dialog_pay_self.getText());
        } else {
            rb_dialog_pay_self.setText(rb_dialog_pay_self.getText().toString());
        }
    }

    /** 隐藏或者显示余额支付 */
    public void setVisibilitySelfPay(boolean isGone) {
        int visibility;
        if (isGone) {
            visibility = View.GONE;
        } else {
            visibility = View.VISIBLE;
        }
        findViewById(R.id.rb_dialog_pay_self).setVisibility(visibility);
    }

    /** 设置余额显示 */
    public void setBalanceShow(double balance) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        String myBalance = getContext().getResources().getString(R.string.txt_my_balance);
        SpannableString preStr = new SpannableString(myBalance);
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#FF3B3B3B"));
        preStr.setSpan(fcs, 0, myBalance.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(preStr);

        String monetFormat = CommonUtil.doubleFormat(balance, "0.00");
        String money = "(¥" + monetFormat + "元)";
        SpannableString moneySpan = new SpannableString(money);
        ForegroundColorSpan moneyFcs = new ForegroundColorSpan(Color.parseColor("#FF44A2F7"));
        moneySpan.setSpan(moneyFcs, 0, money.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(moneySpan);

        RadioButton rb_dialog_pay_self = findViewById(R.id.rb_dialog_pay_self);
        rb_dialog_pay_self.setText(ssb);
    }

    public void setOnCommitListener(OnCommitListener listener) {
        this.listener = listener;
    }

    // ===Desc:================================================================================
    public interface OnCommitListener {
        void onCommit(PayMethod payMethod);
    }

    public enum PayMethod {
        PAY_WECHAT,
        PAY_ALI,
        PAY_SELF
    }
}

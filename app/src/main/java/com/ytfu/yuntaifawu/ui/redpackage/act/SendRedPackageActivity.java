package com.ytfu.yuntaifawu.ui.redpackage.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.pay.PayHelper;
import com.ytfu.yuntaifawu.ui.pay.bean.WxPayBean;
import com.ytfu.yuntaifawu.ui.redpackage.p.SendRedPackagePresenter;
import com.ytfu.yuntaifawu.ui.redpackage.v.SendRedPackageView;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.MessageEvent;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.dialog.DialogHelper;
import com.ytfu.yuntaifawu.utils.dialog.PayDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/** 用户发送红包界面 */
@InjectLayout(
        value = R.layout.activity_send_package,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(SendRedPackagePresenter.class)
public class SendRedPackageActivity
        extends BaseActivity<SendRedPackageView, SendRedPackagePresenter>
        implements SendRedPackageView {

    /** */
    public static final String KEY_RESULT_MONEY = "RESULT_MONEY";

    private static final int PAY_TYPE_WECHAT = 0; // 微信支付,默认支付方式
    private static final int PAY_TYPE_ALIPAY = 1;
    private int payMethod = PAY_TYPE_WECHAT;

    @BindView(R.id.et_red_money)
    EditText et_red_money;

    @BindView(R.id.tv_red_commit)
    TextView tv_red_commit;

    private static final String KEY_LAWYER_ID = "LAWYER_ID";
    private static final String KEY_LAWYER_AVATAR = "LAWYER_AVATAR";

    public static void start(Context context, String lawyerId, String lawyerAvatar) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_LAWYER_ID, lawyerId);
        bundle.putString(KEY_LAWYER_AVATAR, lawyerAvatar);
        Intent starter = new Intent(context, SendRedPackageActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    public static void startActivityForResult(
            Activity activity, int requestCOde, String lawyerId, String lawyerAvatar) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_LAWYER_ID, lawyerId);
        bundle.putString(KEY_LAWYER_AVATAR, lawyerAvatar);
        Intent starter = new Intent(activity, SendRedPackageActivity.class);
        starter.putExtras(bundle);
        activity.startActivityForResult(starter, requestCOde);
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
    protected void setViewListener() {
        super.setViewListener();
        et_red_money.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        String inputMoney = et_red_money.getText().toString().trim();
                        tv_red_commit.setEnabled(!TextUtils.isEmpty(inputMoney));
                        double money;
                        try {
                            money = Double.parseDouble(inputMoney);
                        } catch (NumberFormatException e) {
                            money = 0.0;
                        }
                        TextView tvShow = findViewById(R.id.tv_red_money_show);
                        tvShow.setText(CommonUtil.doubleFormat(money, "0.00"));
                    }
                });

        tv_red_commit.setOnClickListener(
                v -> {
                    String inputMoney = et_red_money.getText().toString().trim();
                    if (TextUtils.isEmpty(inputMoney)) {
                        showToast(getString(R.string.hint_input_money));
                        return;
                    }
                    double money;
                    try {
                        money = Double.parseDouble(inputMoney);
                    } catch (NumberFormatException e) {
                        showToast("应用程序出现未知错误，请稍后重试");
                        return;
                    }
                    // 弹出选择框选择支付方式
                    String userId = SpUtil.getString(mContext, AppConstant.UID, "");
                    String lawyerId = getBundleString(KEY_LAWYER_ID, "");

                    showPayDialog(userId, lawyerId, money);
                });
    }

    @Override
    protected void initData() {
        super.initData();
        hideLoading();
        setToolbarText(R.id.tv_global_title, R.string.txt_send_red_package);
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> onBackPressed());

        TextView tvShow = findViewById(R.id.tv_red_money_show);
        tvShow.setText(CommonUtil.doubleFormat(0.0, "0.00"));

        String avatar = getBundleString(KEY_LAWYER_AVATAR, "");
        ImageView ivAvatar = findViewById(R.id.iv_red_avatar);
        CircleCrop cc = new CircleCrop();
        RequestOptions options =
                RequestOptions.bitmapTransform(cc)
                        .placeholder(R.drawable.touxiang) // 图片加载出来前，显示的图片
                        .fallback(R.drawable.touxiang) // url为空的时候,显示的图片
                        .error(R.drawable.touxiang); // 图片加载失败后，显示的图片
        Glide.with(mContext).load(avatar).apply(options).into(ivAvatar);
    }

    // ===Desc:================================================================================

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWechatPaySuccess(MessageEvent event) {
        int what = event.getWhat();
        if (what == AppConstant.WX_PAY_SUCCESS) {
            setResult(RESULT_OK);
            finish();
            //            //更新支付成功UI显示
            //            //更新UI
            //            if (null != clickPayItem) {
            //                HistoryChatExtBean ext = clickPayItem.getChatItem().getExt();
            //                if (null != ext) {
            //                    ext.setType(2);
            //                }
            //                adapter.notifyDataSetChanged();
            //            }
            //            clickPayItem = null;
        }
    }

    @Override
    public void onSendRedPackageSuccess(double money) {
        Logger.e("发送红包" + money + "元");
        finish();
    }

    @Override
    public void onGetWechatOrder(WxPayBean.SignBean sign, double money) {
        PayHelper.getInstance().payByWechat(sign);
    }

    @Override
    public void onGetAliOrder(String sign, double money) {
        PayHelper.getInstance()
                .setIPayListener(
                        new PayHelper.IPayListener() {
                            @Override
                            public void onSuccess() {
                                Bundle bundle = new Bundle();
                                bundle.putDouble(KEY_RESULT_MONEY, money);
                                Intent intent = new Intent();
                                intent.putExtras(bundle);
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
    public void onGetBalance(double money) {
        setResult(RESULT_OK);
        finish();
    }

    // ===Desc:================================================================================

    private void showPayDialog(String selfId, String lawyerId, double money) {
        DialogHelper.showPayDialog(
                this,
                money,
                new PayDialog.OnCommitListener() {
                    @Override
                    public void onCommit(PayDialog.PayMethod payMethod) {
                        switch (payMethod) {
                            case PAY_WECHAT:
                                getPresenter().getWechatPayOrder(selfId, lawyerId, money);
                                break;
                            case PAY_ALI:
                                getPresenter().aliPay(selfId, lawyerId, money);
                                break;
                            case PAY_SELF:
                                getPresenter().getBlance(selfId, lawyerId, money);
                                break;
                        }
                    }
                });
        //        View dialogView = getLayoutInflater().inflate(R.layout.pay_view_alert_dialor2,
        // null);
        //        CheckBox cb_pay_wechat = dialogView.findViewById(R.id.cb_pay_wechat);
        //        CheckBox cb_pay_ali = dialogView.findViewById(R.id.cb_pay_ali);
        //        cb_pay_wechat.setOnCheckedChangeListener(new
        // CompoundButton.OnCheckedChangeListener() {
        //            @Override
        //            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //                if (isChecked) {
        //                    payMethod = PAY_TYPE_WECHAT;
        //                    cb_pay_ali.setChecked(false);
        //                }
        //            }
        //        });
        //        cb_pay_ali.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        // {
        //            @Override
        //            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //                if (isChecked) {
        //                    payMethod = PAY_TYPE_ALIPAY;
        //                    cb_pay_wechat.setChecked(false);
        //                }
        //            }
        //        });
        //        PayBottomDialog dialog = new PayBottomDialog(this, dialogView, new
        // int[]{R.id.ll_pay_weichat, R.id.ll_pay_zhifubao, R.id.pay, R.id.iv_guanbi});
        //        dialog.setCancelable(true);
        //        dialog.setCanceledOnTouchOutside(true);
        //
        //        dialog.setOnBottomItemClickListener(new
        // PayBottomDialog.OnBottomItemClickListener() {
        //            @Override
        //            public void onBottomItemClick(PayBottomDialog payBottomDialog, View view) {
        //                switch (view.getId()) {
        //                    case R.id.iv_guanbi:
        //                        dialog.cancel();
        //                        break;
        //                    case R.id.ll_pay_weichat:
        //                        cb_pay_wechat.setChecked(true);
        //                        cb_pay_ali.setChecked(false);
        //                        break;
        //                    case R.id.ll_pay_zhifubao:
        //                        cb_pay_wechat.setChecked(false);
        //                        cb_pay_ali.setChecked(true);
        //                        break;
        //                    case R.id.pay:
        //                        dialog.dismiss();
        //                        switch (payMethod) {
        //                            case PAY_TYPE_WECHAT:
        //                                getPresenter().getWechatPayOrder(selfId, lawyerId, money);
        //                                break;
        //                            case PAY_TYPE_ALIPAY:
        //                                getPresenter().aliPay(selfId, lawyerId, money);
        //                                break;
        //                            default:
        //                                ToastUtil.showToast("应用程序出现内部错误,请稍后重试");
        //                                break;
        //
        //                        }
        //                        break;
        //                }
        //            }
        //        });
        //        cb_pay_wechat.setChecked(true);
        //        dialog.bottmShow();
    }
}

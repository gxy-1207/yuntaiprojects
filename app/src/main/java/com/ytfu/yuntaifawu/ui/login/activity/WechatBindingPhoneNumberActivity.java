package com.ytfu.yuntaifawu.ui.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.LvShiMainActivity;
import com.ytfu.yuntaifawu.ui.UserMainActivity;
import com.ytfu.yuntaifawu.ui.login.bean.CodeBean;
import com.ytfu.yuntaifawu.ui.login.bean.WechatBindingnumBean;
import com.ytfu.yuntaifawu.ui.login.presenter.WechatBindingNumberPresenter;
import com.ytfu.yuntaifawu.ui.login.view.WechatBindingNumberView;
import com.ytfu.yuntaifawu.ui.users.act.MineConsultationListActivity;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.JPushUtils;
import com.ytfu.yuntaifawu.utils.LoginHelper;
import com.ytfu.yuntaifawu.utils.MyCountDownTimer;
import com.ytfu.yuntaifawu.utils.Regexs;
import com.ytfu.yuntaifawu.utils.SpUtil;

import butterknife.BindView;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;

/** @Auther gxy @Date 2020/7/8 @Des 微信登录绑定手机号 */
@InjectLayout(
        value = R.layout.activity_wechat_binding_phone_number,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(WechatBindingNumberPresenter.class)
public class WechatBindingPhoneNumberActivity
        extends BaseActivity<WechatBindingNumberView, WechatBindingNumberPresenter>
        implements WechatBindingNumberView {
    private static final String KEY_WECHAT_UID = "WECHAT_UID";

    @BindView(R.id.icon_finish)
    ImageView iconFinish;

    @BindView(R.id.ll_title)
    LinearLayout llTitle;

    @BindView(R.id.text_login)
    TextView textLogin;

    @BindView(R.id.et_num)
    EditText etNum;

    @BindView(R.id.view_name)
    View viewName;

    @BindView(R.id.et_code)
    EditText etCode;

    @BindView(R.id.text_yzm)
    TextView textYzm;

    @BindView(R.id.ll_code)
    LinearLayout llCode;

    @BindView(R.id.view_code)
    View viewCode;

    @BindView(R.id.btn_login)
    Button btnLogin;
    /** 是否正在倒计时 */
    private boolean onTick = false;

    private String second;
    private MyCountDownTimer countDownTimer;

    public static void start(Context context, String uid) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_WECHAT_UID, uid);
        Intent starter = new Intent(context, WechatBindingPhoneNumberActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));
        changeStatusBarTextColor(true);
        setToolbarBackgroud(getResources().getColor(R.color.white));
        // 倒计时
        initTimer();
    }

    @OnClick({R.id.icon_finish, R.id.text_yzm, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icon_finish:
                finish();
                break;
            case R.id.text_yzm:
                // 获取验证码
                getVerificationCode();
                break;
            case R.id.btn_login:
                gitCommitData();
                break;
        }
    }

    // 获取验证码
    private void getVerificationCode() {
        String mobile = etNum.getText().toString().trim();
        String wechatUid = getBundleString(KEY_WECHAT_UID, "");
        if (TextUtils.isEmpty(mobile)) {
            showCenterToast("请输入手机号");
            return;
        }
        if (!mobile.matches(Regexs.REG_MOBILE)) {
            showCenterToast("请输入正确的手机号格式");
            return;
        }
        if (TextUtils.isEmpty(wechatUid)) {
            showCenterToast("应用程序内部出错，请稍后再试");
            return;
        }
        getPresenter().getWechatBindingCode(wechatUid, mobile);
    }
    // 倒计时
    private void initTimer() {
        countDownTimer = new MyCountDownTimer(AppConstant.GETCODEDURATION, 1000);
        countDownTimer.setCountListener(
                new MyCountDownTimer.CountListener() {
                    @Override
                    public void onTick(Long millisUntilFinished) {
                        second = String.valueOf(millisUntilFinished / 1000).concat("s");
                        textYzm.setText(
                                String.format(
                                        CommonUtil.getString(R.string.resend_code_tips), second));
                        textYzm.setEnabled(false);
                        if (!onTick) {
                            onTick = true;
                        }
                    }

                    @Override
                    public void onFinish() {
                        textYzm.setText("获取验证码");
                        textYzm.setEnabled(true);
                        if (onTick) {
                            onTick = false;
                        }
                    }
                });
    }
    // 提交
    private void gitCommitData() {
        showWaitingDialog("", true);
        String mobile = etNum.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        String wetchatUid = getBundleString(KEY_WECHAT_UID, "");
        if (TextUtils.isEmpty(mobile)) {
            showCenterToast("请输入手机号");
            return;
        }
        if (!mobile.matches(Regexs.REG_MOBILE)) {
            showCenterToast("手机号格式输入不正确");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            showCenterToast("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(wetchatUid)) {
            showCenterToast("应用程序内部出现问题，请稍后再试");
            return;
        }
        getPresenter().getWechatBindingNum(wetchatUid, mobile, code);
    }

    @Override
    public void onBindingSuccess(CodeBean codeBean) {
        if (codeBean != null) {
            if (codeBean.getStatus() == 1) {
                countDownTimer.start();
            }
        }
    }

    @Override
    public void onBindingMobileSuccess(WechatBindingnumBean wechatBindingnumBean) {
        if (wechatBindingnumBean != null) {
            int status = wechatBindingnumBean.getStatus();
            switch (status) {
                case 0:
                    showCenterToast("绑定失败");
                    finish();
                    break;
                case 1:
                    String uid = wechatBindingnumBean.getUid();
                    String shenfen = wechatBindingnumBean.getShenfen();
                    String token = wechatBindingnumBean.getToken();
                    handlerLoginSuccess(uid, shenfen, token);
                    LoginHelper.OnEMLoginCallback callback =
                            new LoginHelper.OnEMLoginCallback() {
                                @Override
                                public void onSuccess() {}

                                @Override
                                public void onFail(int code, String msg) {
                                    Logger.e("2233code ---> " + code + ",,, msg ---> " + msg);
                                }
                            };
                    LoginHelper.getInstance().loginSuccess(uid, "123456", callback);

                    JPushUtils.setAliasAndTag(this);

                    // 调用注册接口
                    LoginHelper.getInstance().ShareInstallSuccess();
                    break;
                case 2:
                    showCenterToast(wechatBindingnumBean.getMsg());
                    break;
            }
        }
    }

    private void handlerLoginSuccess(String uid, String shenfen, String token) {
        SpUtil.putString(mContext, AppConstant.UID, uid);
        SpUtil.putString(mContext, AppConstant.SHENFEN, shenfen);
        LoginHelper.getInstance().login(mContext, uid, shenfen, token);
        if (shenfen.equals("1")) {
            boolean isHello = SpUtil.getBoolean(mContext, "isHello", false);
            if (!isHello) {
                LoginHelper.getInstance()
                        .LoginGoToPae(
                                new BaseRxObserver<Integer>() {
                                    @Override
                                    public void onNextImpl(Integer data) {
                                        // 1是发布页面 2是进消息页面 3是进入我的咨询
                                        switch (data) {
                                            case -1:
                                            case 1:
                                            default:
                                                UserMainActivity.Companion.start(mContext, 2);
                                                break;
                                            case 2:
                                                UserMainActivity.Companion.start(mContext, 1);
                                                break;
                                            case 3:
                                                MineConsultationListActivity.start(mContext, true);
                                                break;
                                        }
                                        finish();
                                    }

                                    @Override
                                    public void onErrorImpl(String errorMessage) {}
                                });
                SpUtil.putBoolean(mContext, "isHello", true);
            } else {
                UserMainActivity.Companion.start(mContext, 2);
                finish();
            }
        } else if (shenfen.equals("2")) {
            LvShiMainActivity.start(this, 0);
            //            startActivity(
            //                    new Intent(WechatBindingPhoneNumberActivity.this,
            // LvShiMainActivity.class));
            finish();
        }
    }

    @Override
    public void onBindngFiled() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}

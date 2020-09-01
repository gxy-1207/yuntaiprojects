package com.ytfu.yuntaifawu.ui.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.App;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.LvShiMainActivity;
import com.ytfu.yuntaifawu.ui.UserMainActivity;
import com.ytfu.yuntaifawu.ui.login.bean.CodeBean;
import com.ytfu.yuntaifawu.ui.login.bean.CodeLoginBean;
import com.ytfu.yuntaifawu.ui.login.bean.WxLoginBean;
import com.ytfu.yuntaifawu.ui.login.presenter.CodePresent;
import com.ytfu.yuntaifawu.ui.login.view.ICodeView;
import com.ytfu.yuntaifawu.ui.register.activity.PrivacyAgreementActivity;
import com.ytfu.yuntaifawu.ui.register.activity.SetPwdActivity;
import com.ytfu.yuntaifawu.ui.users.act.MineConsultationListActivity;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.JPushUtils;
import com.ytfu.yuntaifawu.utils.LoginHelper;
import com.ytfu.yuntaifawu.utils.MessageEvent;
import com.ytfu.yuntaifawu.utils.MobileUtil;
import com.ytfu.yuntaifawu.utils.MyCountDownTimer;
import com.ytfu.yuntaifawu.utils.Regexs;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

/** @Auther gxy @Date 2019/11/14 @Des 验证码登录Activity */
public class LoginCodeActivity extends BaseActivity<ICodeView, CodePresent>
        implements ICodeView, View.OnClickListener {

    private EditText et_name;
    private EditText et_code;
    private TextView text_yzm;
    private TextView tv_zhnaghao;
    private Button btn_login;
    private ImageView icon_finish, iv_weixin;
    /** 手机号是否正确 */
    private boolean trueMobile = false;
    /** 是否正在倒计时 */
    private boolean onTick = false;

    private String second;
    private MyCountDownTimer countDownTimer;
    private int status;
    //    private IWXAPI wxapi;
    private boolean checked;
    private TextView tv_tiaokuan, tv_yhxy, tv_ys;
    private CheckBox cb_ty;
    // ===Desc:================================================================================

    public static void startNewTask(Context context) {
        Intent intent = new Intent(context, LoginCodeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // ===Desc:================================================================================

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_code_login;
    }

    @Override
    protected CodePresent createPresenter() {
        return new CodePresent(this);
    }

    @Override
    public void init() {
        super.init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            //      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent_half));
        }
        LoginHelper.getInstance().cleanLoginInfo();
    }

    @Override
    protected void initView() {
        hideLoading();
        EventBus.getDefault().register(this);
        //                wxapi = WXAPIFactory.createWXAPI(this, AppConstant.WX_APP_ID, true);
        //        wxapi.registerApp(AppConstant.WX_APP_ID);
        et_name = findViewById(R.id.et_name);
        et_code = findViewById(R.id.et_code);
        text_yzm = findViewById(R.id.text_yzm);
        tv_zhnaghao = findViewById(R.id.tv_zhnaghao);
        btn_login = findViewById(R.id.btn_login);
        icon_finish = findViewById(R.id.icon_finish);
        iv_weixin = findViewById(R.id.iv_weixin);

        tv_zhnaghao.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        icon_finish.setOnClickListener(this);
        iv_weixin.setOnClickListener(this);
        text_yzm.setOnClickListener(this);

        tv_yhxy = findViewById(R.id.tv_yhxy);
        tv_ys = findViewById(R.id.tv_ys);
        cb_ty = findViewById(R.id.cb_ty);
        tv_yhxy.setOnClickListener(this);
        tv_ys.setOnClickListener(this);
        cb_ty.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            //                    btn_queren.setEnabled(true);
                            checked = true;
                        } else {
                            //                    btn_queren.setEnabled(false);
                            checked = false;
                        }
                    }
                });
        initTimer();
    }

    @Override
    protected void initData() {}

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        App.getInstance().finishAllActivity();
        //        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_zhnaghao:
                Intent intent = new Intent(LoginCodeActivity.this, LoginPhonePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                // 验证码登录
                //                initGetLoginCode();
                initGetLoginCode2();
                break;
            case R.id.icon_finish:
                finish();
                break;
            case R.id.text_yzm:
                // 获取验证码
                //                initGetCode();
                initGetCode2();
                break;
            case R.id.iv_weixin:
                // 微信登录
                iv_weixin.setEnabled(false);
                showWaitingDialog("登录中...", true);
                wxLogin();
                break;
            case R.id.tv_yhxy:
                String fuwuUrl = "http://www.yuntaifawu.com/index/xieyi";
                PrivacyAgreementActivity.Companion.start(this, "用户服务协议", fuwuUrl);
                //                startActivity(new Intent(LoginCodeActivity.this,
                // ActivityYhxy.class));
                break;
            case R.id.tv_ys:
                String yinsiUrl = "http://www.yuntaifawu.com/index/yinsi";
                PrivacyAgreementActivity.Companion.start(this, "隐私协议", yinsiUrl);
                //                startActivity(new Intent(LoginCodeActivity.this,
                // ActivityYs.class));
                break;
            default:
                break;
        }
    }

    private void wxLogin() {
        // 判断手机是否安装了微信
        boolean installed = App.wxapi.isWXAppInstalled();
        if (!installed) {
            hideWaitingDialog();
            showCenterToast("未安装微信客户端,请先安装微信客户端");
            iv_weixin.setEnabled(true);
            return;
        }

        //        if (!wxapi.isWXAppInstalled()) {
        //            showToast("您的设备未安装微信客户端");
        //        } else {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = String.valueOf(System.currentTimeMillis());
        App.wxapi.sendReq(req);
        iv_weixin.setEnabled(true);
        //        }
    }

    // 接受event事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getWxLoginEvent(MessageEvent messageEvent) {
        switch (messageEvent.getWhat()) {
            case AppConstant.WX_LOGIN:
                String wxCode = messageEvent.getMessage();
                //                showToast(wxCode);
                HashMap<String, String> map = new HashMap<>();
                map.put("code", wxCode);
                getPresenter().getWxLogin(map);
                break;
            default:
                break;
        }
    }

    // 验证码登录
    private void initGetLoginCode() {
        String mobile = et_name.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(code)) {
            ToastUtil.showToast("输入为空");
        } else {
            if (mobile.length() >= 11) {
                if (MobileUtil.isMobileNO(mobile)) {
                    trueMobile = true;
                    if (code.length() >= 6) {
                        //                        if(checked == false){
                        //                            showCenterToast("请同意阅读用户协议及隐私协议");
                        //                        }else {
                        showWaitingDialog("登录中...", true);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("mobile", mobile);
                        map.put("code", code);
                        map.put("xt", String.valueOf(1));
                        getPresenter().getLoginCode(map);
                        //                        }
                    } else {
                        ToastUtil.showToast("验证码输入错误");
                    }
                } else {
                    trueMobile = false;
                    ToastUtil.showToast("手机号格式不正确");
                }
            }
        }
    }

    // 验证码登录
    private void initGetLoginCode2() {
        String mobile = et_name.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            showCenterToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            showCenterToast("请输入验证码");
            return;
        }
        if (!mobile.matches(Regexs.REG_MOBILE)) {
            showCenterToast("请输入正确的的手机号");
            return;
        }
        showWaitingDialog("登录中...", true);
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("xt", String.valueOf(1));
        getPresenter().getLoginCode(map);
    }

    // 去注册
    private void goReg(String mobile, String code) {
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.showToast("手机号输入为空");
        } else {
            if (mobile.length() >= 11) {
                if (MobileUtil.isMobileNO(mobile)) {
                    trueMobile = true;
                    if (TextUtils.isEmpty(code)) {
                        ToastUtil.showCenterToast("请输入验证码");
                    } else {
                        if (code.length() >= 6) {
                            Intent intent =
                                    new Intent(LoginCodeActivity.this, SetPwdActivity.class);
                            intent.putExtra("mobile", mobile);
                            intent.putExtra("code", code);
                            startActivity(intent);
                        } else {
                            ToastUtil.showCenterToast("请输入6位数据验证码");
                        }
                    }
                } else {
                    trueMobile = false;
                    ToastUtil.showToast("手机号格式不正确");
                }
            }
        }
    }

    // 去登录
    private void goLogin(String mobile, String code) {}

    // 获取验证码
    private void initGetCode() {
        String pho = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(pho)) {
            ToastUtil.showToast("手机号输入为空");
        } else {
            if (pho.length() >= 11) {
                if (MobileUtil.isMobileNO(pho)) {
                    trueMobile = true;
                    //                    countDownTimer.start();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("mobile", pho);
                    getPresenter().getSendCode(map);
                    //                    initTimer();
                    //                    ToastUtil.showToast("手机号格式正确");
                } else {
                    trueMobile = false;
                    ToastUtil.showToast("手机号格式不正确");
                }
            }
        }
    }

    // 获取验证码
    private void initGetCode2() {
        String pho = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(pho)) {
            ToastUtil.showCenterToast("请输入手机号");
            return;
        }
        if (!pho.matches(Regexs.REG_MOBILE)) {
            ToastUtil.showCenterToast("请输入正确的手机号");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", pho);
        getPresenter().getSendCode(map);
    }

    // 倒计时
    private void initTimer() {
        countDownTimer = new MyCountDownTimer(AppConstant.GETCODEDURATION, 1000);
        countDownTimer.setCountListener(
                new MyCountDownTimer.CountListener() {
                    @Override
                    public void onTick(Long millisUntilFinished) {
                        second = String.valueOf(millisUntilFinished / 1000).concat("s");
                        text_yzm.setText(
                                String.format(
                                        CommonUtil.getString(R.string.resend_code_tips), second));
                        text_yzm.setEnabled(false);
                        if (!onTick) {
                            onTick = true;
                        }
                    }

                    @Override
                    public void onFinish() {
                        text_yzm.setText("获取验证码");
                        text_yzm.setEnabled(true);
                        if (onTick) {
                            onTick = false;
                        }
                    }
                });
    }

    // 获取验证码成功回调
    @Override
    public void onCodeSuccess(CodeBean codeBean) {
        if (codeBean != null) {
            status = codeBean.getStatus();
            if (status == AppConstant.LOGIN_REEOR) {
                ToastUtil.showToast("验证码发送失败");
            }
            countDownTimer.start();
        }
    }

    @Override
    public void onCodeFiled() {}

    // 验证码登录成功回调
    @Override
    public void onCodeLoginSuccess(CodeLoginBean codeLoginBean) {
        hideWaitingDialog();
        if (codeLoginBean != null) {
            int status = codeLoginBean.getStatus();
            String uid = codeLoginBean.getUid();
            String shenfen = codeLoginBean.getShenfen();
            String token = codeLoginBean.getToken();
            switch (status) {
                case 1:
                    if (codeLoginBean.getRegister() == 1) {
                        LoginHelper.getInstance()
                                .oppoCallback(LoginCodeActivity.this, CommonUtil.getChannel());
                    }
                    handlerLonginSuccess(uid, shenfen, token);
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
                    ToastUtil.showCenterToast(codeLoginBean.getMsg());
                    break;
                case 0:
                    ToastUtil.showCenterToast("登录失败");
                    break;
                default:
                    break;
            }
        }
    }

    // 微信登录成功回调
    @Override
    public void onWxLoginSuccess(WxLoginBean wxLoginBean) {
        hideWaitingDialog();
        if (wxLoginBean != null) {
            int status = wxLoginBean.getStatus();
            String uid = wxLoginBean.getUid();
            String shenfen = wxLoginBean.getShenfen();
            String token = wxLoginBean.getToken();
            switch (status) {
                case 1:
                    if (wxLoginBean.getRegister() == 1) {
                        // 调用oppo接口，统计注册量
                        LoginHelper.getInstance()
                                .oppoCallback(LoginCodeActivity.this, CommonUtil.getChannel());
                    }
                    if (wxLoginBean.getMobile() == 0) {
                        // 去绑定手机号
                        WechatBindingPhoneNumberActivity.start(this, uid);
                    } else {
                        handlerLonginSuccess(uid, shenfen, token);
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
                    }
                    break;
                case 0:
                    ToastUtil.showCenterToast("登录失败");
                    break;
                default:
                    break;
            }
        }
    }

    private void handlerLonginSuccess(String uid, String shenfen, String token) {
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
            //            startActivity(new Intent(LoginCodeActivity.this,
            // LvShiMainActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}

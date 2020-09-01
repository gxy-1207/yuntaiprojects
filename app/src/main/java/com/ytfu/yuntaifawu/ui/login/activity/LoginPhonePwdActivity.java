package com.ytfu.yuntaifawu.ui.login.activity;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.LvShiMainActivity;
import com.ytfu.yuntaifawu.ui.UserMainActivity;
import com.ytfu.yuntaifawu.ui.login.bean.LoginPwdBean;
import com.ytfu.yuntaifawu.ui.login.presenter.NumPwdPresenter;
import com.ytfu.yuntaifawu.ui.login.view.INumPwdView;
import com.ytfu.yuntaifawu.ui.users.act.MineConsultationListActivity;
import com.ytfu.yuntaifawu.utils.JPushUtils;
import com.ytfu.yuntaifawu.utils.LoginHelper;
import com.ytfu.yuntaifawu.utils.MobileUtil;
import com.ytfu.yuntaifawu.utils.Regexs;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.ToastUtil;

import java.util.HashMap;

/** @Auther gxy @Date 2019/11/14 @Des 账号密码登录 */
public class LoginPhonePwdActivity extends BaseActivity<INumPwdView, NumPwdPresenter>
        implements INumPwdView, View.OnClickListener {

    private ImageView icon_finish;
    private EditText et_name;
    private EditText et_pwd;
    private ImageView im_bi_pwd;
    private TextView tv_wangji_pwd;
    private TextView tv_code_l;
    private Button btn_login;
    private boolean isHideFirst = true; // 输入框密码是否是隐藏的，默认为true

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_zhanghao_pwd_login;
    }

    @Override
    protected NumPwdPresenter createPresenter() {
        return new NumPwdPresenter(this);
    }

    @Override
    public void init() {
        super.init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();

            window.setStatusBarColor(getResources().getColor(R.color.transparent_half));
        }
    }

    @Override
    protected void initView() {
        hideLoading();
        icon_finish = findViewById(R.id.icon_finish);
        et_name = findViewById(R.id.et_name);
        et_pwd = findViewById(R.id.et_pwd);
        im_bi_pwd = findViewById(R.id.im_bi_pwd);
        tv_wangji_pwd = findViewById(R.id.tv_wangji_pwd);
        tv_code_l = findViewById(R.id.tv_code_l);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);
        icon_finish.setOnClickListener(this);
        im_bi_pwd.setOnClickListener(this);
        tv_wangji_pwd.setOnClickListener(this);
        tv_code_l.setOnClickListener(this);
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
                // 验证码登录
            case R.id.tv_code_l:
                Intent intent1 = new Intent(LoginPhonePwdActivity.this, LoginCodeActivity.class);
                startActivity(intent1);
                break;
                // 忘记密码
            case R.id.tv_wangji_pwd:
                Intent intent = new Intent(LoginPhonePwdActivity.this, ForgetPwdActivity.class);
                startActivity(intent);
                break;
                // 密码明文密文
            case R.id.im_bi_pwd:
                if (isHideFirst == true) {
                    im_bi_pwd.setImageResource(R.drawable.xiaoyanjingkai);
                    et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isHideFirst = false;
                } else {
                    im_bi_pwd.setImageResource(R.drawable.xiaoyanjingbi);
                    et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isHideFirst = true;
                }
                // 光标的位置
                int index = et_pwd.getText().toString().length();
                et_pwd.setSelection(index);
                break;

                // 关闭
            case R.id.icon_finish:
                finish();
                break;
                // 登录
            case R.id.btn_login:
                //                initPwdNum();
                initPwdNum2();
                break;
            default:
                break;
        }
    }

    private void initPwdNum() {

        String mobile = et_name.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();

        if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(pwd)) {
            if (mobile.length() >= 11) {
                if (MobileUtil.isMobileNO(mobile)) {
                    if (pwd.length() >= 6 && pwd.length() <= 12) {
                        showWaitingDialog("登录中...", true);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("mobile", mobile);
                        map.put("pwd", pwd);
                        getPresenter().longinNumPwd(map);
                    } else {
                        ToastUtil.showToast("密码长度为6到12位数字字母下滑线");
                    }

                } else {
                    ToastUtil.showToast("手机号格式不正确");
                }
            }
        } else {
            ToastUtil.showToast("输入不能为空");
        }
    }

    private void initPwdNum2() {

        String mobile = et_name.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            showCenterToast("请输入手机号");
            return;
        }
        if (!mobile.matches(Regexs.REG_MOBILE)) {
            showCenterToast("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            showCenterToast("请输入密码");
            return;
        }
        showWaitingDialog("登录中...", true);
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("pwd", pwd);
        getPresenter().longinNumPwd(map);
    }

    @Override
    public void onNumPwdSuccess(LoginPwdBean pwdBean) {
        hideWaitingDialog();
        if (pwdBean != null) {
            int status = pwdBean.getStatus();
            String uid = pwdBean.getUid();
            String shenfen = pwdBean.getShenfen();
            String token = pwdBean.getToken();
            switch (status) {
                case 1:
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
                    ToastUtil.showCenterToast("账号或者密码错误");
                    break;
                case 3:
                    ToastUtil.showCenterToast("此手机号还未注册！请先快捷登录");

                    Intent intent = new Intent(LoginPhonePwdActivity.this, LoginCodeActivity.class);
                    startActivity(intent);
                    break;
                default:
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
            //            startActivity(new Intent(LoginPhonePwdActivity.this,
            // LvShiMainActivity.class));
            finish();
        }
    }

    @Override
    public void onNumPwdFiled() {}
}

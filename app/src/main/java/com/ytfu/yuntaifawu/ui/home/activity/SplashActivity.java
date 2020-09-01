package com.ytfu.yuntaifawu.ui.home.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.ytfu.yuntaifawu.BuildConfig;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.App;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.LvShiMainActivity;
import com.ytfu.yuntaifawu.ui.UserMainActivity;
import com.ytfu.yuntaifawu.ui.login.activity.LoginCodeActivity;
import com.ytfu.yuntaifawu.ui.register.activity.PrivacyAgreementActivity;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.SpUtil;

import qiu.niorgai.StatusBarCompat;

/** @Auther gxy @Date 2019/11/25 @Des 启动页 */
// @InjectLayout(R.layout.activity_splash)
public class SplashActivity extends BaseActivity<BaseView, BasePresenter<BaseView>> {

    private Handler handler;

    @Override
    public void init() {
        super.init();

        StatService.setAppChannel(this, CommonUtil.getChannel(), true);
        // setSendLogStrategy已经@deprecated，建议使用新的start接口
        // 如果没有页面和自定义事件统计埋点，此代码一定要设置，否则无法完成统计
        // 进程第一次执行此代码，会导致发送上次缓存的统计数据；若无上次缓存数据，则发送空启动日志
        // 由于多进程等可能造成Application多次执行，建议此代码不要埋点在Application中，否则可能造成启动次数偏高
        // 建议此代码埋点在统计路径触发的第一个页面中，若可能存在多个则建议都埋点
        StatService.start(this);
        //        if (BuildConfig.IS_DEBUG) {
        //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
        //                shortcut();
        //            }
        //        }
        handler = new Handler();
        // 第一次缓存渠道号  避免应用内跟新导致渠道号丢失
        String channel = SpUtil.getString(mContext, "channel", "");
        if (TextUtils.isEmpty(channel)) {
            channel = CommonUtil.getMetaData("UMENG_CHANNEL", "channel_00");
            SpUtil.putString(mContext, "channel", channel);
        }
    }

    @Override
    protected void initView() {
        hideLoading();
    }

    @Override
    protected void initData() {
        Window w = getWindow();
        WindowManager.LayoutParams params = w.getAttributes();
        params.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        w.setAttributes(params);

        StatusBarCompat.translucentStatusBar(this, true);
        changeStatusBarTextColor(true);

        boolean aBoolean = SpUtil.getBoolean(mContext, AppConstant.TONGYI, false);
        if (!aBoolean) {
            showDelog();
        } else {
            handler.postDelayed(
                    () -> {
                        if (App.getInstance().getLoginFlag()) {
                            String shenfen = SpUtil.getString(mContext, AppConstant.SHENFEN, "");
                            if (TextUtils.isEmpty(shenfen)) {
                                startActivity(
                                        new Intent(SplashActivity.this, LoginCodeActivity.class));
                            } else {
                                if (shenfen.equals("2")) {
                                    startActivity(
                                            new Intent(
                                                    SplashActivity.this, LvShiMainActivity.class));
                                } else {
                                    UserMainActivity.Companion.start(mContext, 2);
                                }
                            }
                        } else {
                            startActivity(new Intent(SplashActivity.this, LoginCodeActivity.class));
                        }
                        finish();
                    },
                    2000);
        }
    }

    @Override
    public void onBackPressed() {
        if (BuildConfig.IS_DEBUG) {
            if (App.getInstance().getLoginFlag()) {
                String shenfen = SpUtil.getString(mContext, AppConstant.SHENFEN, "");
                if (TextUtils.isEmpty(shenfen)) {
                    startActivity(new Intent(SplashActivity.this, LoginCodeActivity.class));
                } else {
                    if (shenfen.equals("2")) {
                        startActivity(new Intent(SplashActivity.this, LvShiMainActivity.class));
                    } else {
                        UserMainActivity.Companion.start(mContext, 2);
                    }
                }
            } else {
                startActivity(new Intent(SplashActivity.this, LoginCodeActivity.class));
            }
            finish();
        }
    }

    private void showDelog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
        View view = View.inflate(this, R.layout.view_alert_dialog_confirm_xy, null);
        TextView tv_yhxy = view.findViewById(R.id.tv_yhxy);
        TextView tv_yisi = view.findViewById(R.id.tv_yisi);
        TextView tv_cancel_dialog = view.findViewById(R.id.tv_cancel_dialog);
        TextView tv_confirm_dialog = view.findViewById(R.id.tv_confirm_dialog);
        // 用户协议
        tv_yhxy.setOnClickListener(
                v -> {
                    String fuwuUrl = "http://www.yuntaifawu.com/index/xieyi";
                    PrivacyAgreementActivity.Companion.start(this, "用户服务协议", fuwuUrl);
                });
        // 隐私协议
        tv_yisi.setOnClickListener(
                v -> {
                    String yinsiUrl = "http://www.yuntaifawu.com/index/yinsi";
                    PrivacyAgreementActivity.Companion.start(this, "隐私协议", yinsiUrl);
                });
        // 取消
        tv_cancel_dialog.setOnClickListener(
                v -> {
                    alertDialog.dismiss();
                    finish();
                });
        // 确定
        tv_confirm_dialog.setOnClickListener(
                v -> {
                    StatService.setAuthorizedState(SplashActivity.this, true);
                    SpUtil.putBoolean(mContext, AppConstant.TONGYI, true);
                    if (App.getInstance().getLoginFlag()) {
                        String shenfen = SpUtil.getString(mContext, AppConstant.SHENFEN, "");
                        if (shenfen.equals("1")) {
                            UserMainActivity.Companion.start(mContext, 2);
                            finish();
                        } else if (shenfen.equals("2")) {
                            startActivity(new Intent(SplashActivity.this, LvShiMainActivity.class));
                            finish();
                        }
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginCodeActivity.class));
                        finish();
                    }
                    alertDialog.dismiss();
                });
        Window window = alertDialog.getWindow();
        if (null != window) {
            window.setContentView(view);
        }
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams attributes = window != null ? window.getAttributes() : null;
        // 设置宽度
        if (null != attributes) {
            //noinspection deprecation
            attributes.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
            attributes.gravity = Gravity.CENTER; // 设置位置
            window.setAttributes(attributes);
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}

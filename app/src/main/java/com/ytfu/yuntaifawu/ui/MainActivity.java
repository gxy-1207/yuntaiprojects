package com.ytfu.yuntaifawu.ui;

/*17734939481
 * 111111
 *
 * */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.baidu.android.common.security.MD5Util;
import com.github.lee.annotation.InjectLayout;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMClientListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.lxj.xpopup.XPopup;
import com.orhanobut.logger.Logger;
import com.uber.autodispose.AutoDisposeConverter;
import com.yanzhenjie.permission.Permission;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.UserInfoService;
import com.ytfu.yuntaifawu.app.App;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.domain.EventBusMessage;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.helper.RxLifecycleUtil;
import com.ytfu.yuntaifawu.im.EmChatManager;
import com.ytfu.yuntaifawu.ui.chat.fragment.ChatListFragment;
import com.ytfu.yuntaifawu.ui.falvguwen.fragment.FragmentLegalAdviser;
import com.ytfu.yuntaifawu.ui.home.fragment.AdvisoryFragment;
import com.ytfu.yuntaifawu.ui.home.fragment.OpenFragment;
import com.ytfu.yuntaifawu.ui.mine.bean.ShenHeJInduBean;
import com.ytfu.yuntaifawu.ui.mseeage.bean.HuanXinLoginBean;
import com.ytfu.yuntaifawu.ui.updatapk.UpDateApkBean;
import com.ytfu.yuntaifawu.ui.users.fragment.UserHomeFragment;
import com.ytfu.yuntaifawu.ui.users.fragment.UserPersonalFragment;
import com.ytfu.yuntaifawu.utils.AndPermissionUtil;
import com.ytfu.yuntaifawu.utils.ApkUtil;
import com.ytfu.yuntaifawu.utils.DemoHelper;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import constacne.UiType;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import listener.UpdateDownloadListener;
import model.UiConfig;
import model.UpdateConfig;
import qiu.niorgai.StatusBarCompat;
import update.UpdateAppUtils;

@InjectLayout(
        value = R.layout.activity_main,
        loadingLayoutId = android.R.layout.preference_category)
public class MainActivity extends BaseActivity implements View.OnClickListener, EMMessageListener {

    //    private FragmentTransaction beginTransaction;
    /** 是否强制更新 */
    private boolean force = false;
    /** 版本code 当前版本Code 强制更新code */
    private int localVersionCode, allowCode = 0;

    private MyHandler myHandler;
    private RelativeLayout rl_main_bottom_home,
            rl_main_bottom_message,
            rl_main_bottom_suzhuang,
            rl_main_bottom_wode;
    private ImageView iv_main_bottom_zixun,
            iv_main_bottom_home,
            iv_main_bottom_message,
            iv_main_bottom_suzhuang,
            iv_main_bottom_wode;
    private TextView tv_main_bottom_home,
            tv_main_bottom_message,
            tv_main_bottom_suzhuang,
            tv_main_bottom_wode,
            tv_main_bottom_jia;
    private OpenFragment openFragment;
    private String uid;
    // 首页
    //    private HomeFragment1 homeFragment1;
    private UserHomeFragment userHomeFragment;
    // 消息
    //    private MessageFragment messageFragment;
    private ChatListFragment messageFragment;
    // 咨询
    private AdvisoryFragment consultFragment;
    // 诉状
    //    private IndictmentFragment indictmentFragment;
    //    private ComplaintFragment complaintFragment;
    private FragmentLegalAdviser fragmentLegalAdviser;
    // 我的
    private UserPersonalFragment mineFragment;
    //        private LawyerContractFragment mineFragment;

    private static final String KEY_CURRENT_POSITION = "frid";

    //    private UserHomeFragment userHomeFragment;

    public static void start(Context context, int position) {
        UserMainActivity.Companion.start(context, position);
        //        Bundle bundle = new Bundle();
        //        bundle.putInt(KEY_CURRENT_POSITION, position);
        //        Intent starter = new Intent(context, UserMainActivity.class);
        //        starter.putExtras(bundle);
        //        context.startActivity(starter);
    }

    //    @Override
    //    public void init() {
    //        super.init();
    //        changeStatusBarTextColor(true);
    //        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
    //        if(getBundleInt(KEY_CURRENT_POSITION,0)==2){
    //            changeStatusBarTextColor(false);
    //            StatusBarCompat.setStatusBarColor(this,
    // getResources().getColor(R.color.transparent_4c));
    //        }
    //        //        Window window = getWindow();
    //        //        //
    // window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    //        //        window.setStatusBarColor(getResources().getColor(R.color.transparent_half));
    //    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int position = intent.getIntExtra("frid", -1);
        if (position != -1) {
            position = getBundleInt(intent, KEY_CURRENT_POSITION, 0);
            changeTagStatus(position);
            //            Eyes.setStatusBarColor(this, CommonUtil.getColor(R.color.transparent_4c));
        }
    }

    @Override
    public void init() {
        super.init();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initView() {
        //        home = findViewById(R.id.home);
        //        mine = findViewById(R.id.mine);
        //        ico_home = findViewById(R.id.ico_home);
        //        ico_mine = findViewById(R.id.ico_mine);
        //        text_home = findViewById(R.id.text_home);
        //        text_mine = findViewById(R.id.text_mine);
        uid = SpUtil.getString(mContext, AppConstant.UID, "");
        // 首页
        rl_main_bottom_home = findViewById(R.id.rl_main_bottom_home);
        iv_main_bottom_home = findViewById(R.id.iv_main_bottom_home);
        tv_main_bottom_home = findViewById(R.id.tv_main_bottom_home);
        rl_main_bottom_home.setOnClickListener(this);
        // 消息
        rl_main_bottom_message = findViewById(R.id.rl_main_bottom_message);
        iv_main_bottom_message = findViewById(R.id.iv_main_bottom_message);
        tv_main_bottom_message = findViewById(R.id.tv_main_bottom_message);
        rl_main_bottom_message.setOnClickListener(this);
        // 诉状
        rl_main_bottom_suzhuang = findViewById(R.id.rl_main_bottom_suzhuang);
        iv_main_bottom_suzhuang = findViewById(R.id.iv_main_bottom_suzhuang);
        tv_main_bottom_suzhuang = findViewById(R.id.tv_main_bottom_suzhuang);
        rl_main_bottom_suzhuang.setOnClickListener(this);
        // 我的
        rl_main_bottom_wode = findViewById(R.id.rl_main_bottom_wode);
        iv_main_bottom_wode = findViewById(R.id.iv_main_bottom_wode);
        tv_main_bottom_wode = findViewById(R.id.tv_main_bottom_wode);
        rl_main_bottom_wode.setOnClickListener(this);
        // 咨询
        iv_main_bottom_zixun = findViewById(R.id.iv_main_bottom_zixun);
        tv_main_bottom_jia = findViewById(R.id.tv_main_bottom_jia);
        iv_main_bottom_zixun.setOnClickListener(this);
        //        home.setOnClickListener(this);
        //        mine.setOnClickListener(this);

        EmChatManager.getInstance().registerMessageListener(this);
    }

    @Override
    protected void initData() {

        hideLoading();
        // 检测是否有新版本
        checkUpdate();
        // 检测律师认证是否通过
        TestingAttorneyCertification();
        myHandler = new MyHandler(MainActivity.this);
        AndPermissionUtil.getInstance()
                .requestPermissions(
                        this,
                        new AndPermissionUtil.OnPermissionGranted() {
                            @Override
                            public void onPermissionGranted() {}
                        },
                        Permission.Group.STORAGE);
        initFragment();
        // 在Application或者MainActivity中调用，以达到安装成功启动后删除已安装apk
        UpdateAppUtils.getInstance().deleteInstalledApk();
        if (App.getInstance().getLoginFlag()) {
            EMClient.getInstance()
                    .login(
                            uid,
                            "123456",
                            new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    new Thread(
                                                    new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Log.e("hx", "-------" + uid);
                                                        }
                                                    })
                                            .start();
                                    EMClient.getInstance().chatManager().loadAllConversations();
                                }

                                @Override
                                public void onError(int i, String s) {
                                    Log.e("onError", "-------" + s);
                                    new Thread(
                                                    new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            HashMap<String, String> map =
                                                                    new HashMap<>();
                                                            map.put("username", uid);
                                                            runOnUiThread(
                                                                    new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            getHuanxinLogin(map);
                                                                        }
                                                                    });
                                                        }
                                                    })
                                            .start();
                                }

                                @Override
                                public void onProgress(int i, String s) {
                                    Log.e("onProgress", "-------" + s);
                                }
                            });
        }

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.transparent_4c));

        // 初始化加载第几个Tab标签
        int position = getBundleInt(KEY_CURRENT_POSITION, 0);
        changeTagStatus(position);
        changeStatusBarTextColor(true);
    }

    // 检测律师认证是否通过
    private void TestingAttorneyCertification() {
        String uid = SpUtil.getString(mContext, AppConstant.UID, "");
        if (TextUtils.isEmpty(uid)) {
            return;
        }
        Observable<ShenHeJInduBean> observable =
                HttpUtil.getInstance().getService(UserInfoService.class).setAuthInfo(uid);
        AutoDisposeConverter<ShenHeJInduBean> converter = RxLifecycleUtil.bindLifecycle(this);
        HttpUtil.getInstance()
                .enqueue(
                        observable,
                        converter,
                        new BaseRxObserver<ShenHeJInduBean>() {
                            @Override
                            public void onNextImpl(ShenHeJInduBean data) {
                                if (data == null) {
                                    return;
                                }
                                int status = data.getStatus();
                                if (status == 1) {
                                    SuccessfulAuthenticationDialog();
                                }
                            }

                            @Override
                            public void onErrorImpl(String errorMessage) {}
                        });
    }

    private void SuccessfulAuthenticationDialog() {
        new XPopup.Builder(mContext)
                .dismissOnTouchOutside(false)
                .dismissOnBackPressed(false)
                .asConfirm(
                        "律师认证",
                        "您的律师认证已审核通过,请点击重新登录按钮切换律师身份。",
                        "",
                        "重新登录",
                        () -> {
                            toLogin();
                        },
                        () -> {},
                        true)
                .show();
    }

    private void getHuanxinLogin(HashMap<String, String> map) {
        HttpUtil.getInstance()
                .getApiService()
                .setHxLogin(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLifecycleUtil.bindLifecycle(this))
                .subscribe(
                        new BaseRxObserver<HuanXinLoginBean>() {
                            @Override
                            public void onNextImpl(HuanXinLoginBean huanXinLoginBean) {
                                if (huanXinLoginBean.getCode() == 200) {
                                    Log.e("success", "------" + huanXinLoginBean.getMsg());
                                    showToast("环信登录成功");
                                    EMClient.getInstance().chatManager().loadAllConversations();
                                }
                            }

                            @Override
                            public void onErrorImpl(String errorMessage) {
                                Logger.e(errorMessage);
                            }
                        });
    }

    // 检测是否有新版本
    private void checkUpdate() {
        HttpUtil.getInstance()
                .getApiService()
                .checkUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLifecycleUtil.bindLifecycle(this))
                .subscribe(
                        new BaseRxObserver<UpDateApkBean>() {
                            @Override
                            public void onNextImpl(UpDateApkBean updateBean) {
                                if (null != updateBean) {
                                    localVersionCode = ApkUtil.getVersionCode();
                                    int serverVersionCode =
                                            Integer.parseInt(
                                                    null != updateBean.getCode()
                                                            ? updateBean.getCode()
                                                            : "0");
                                    allowCode =
                                            Integer.parseInt(
                                                    null != updateBean.getAllow_code()
                                                            ? updateBean.getAllow_code()
                                                            : "0");

                                    // 当前版本低于服务器最低版本，强制更新
                                    if (localVersionCode < allowCode) {
                                        force = true;
                                    }

                                    // 有新版本提示更新
                                    if (localVersionCode < serverVersionCode) {
                                        Message msg = new Message();
                                        msg.what = 0;
                                        msg.obj = updateBean;
                                        myHandler.sendMessage(msg);
                                    }
                                }
                            }

                            @Override
                            public void onErrorImpl(String errorMessage) {
                                Logger.e(errorMessage);
                            }
                        });
    }

    private void initFragment() {
        //        homeFragment = new HomeFragment();
        // 首页
        //        homeFragment1 = HomeFragment1.newInstance();
        userHomeFragment = UserHomeFragment.newInstance();
        // 消息
        //        messageFragment = new MessageFragment();
        messageFragment = ChatListFragment.newInstance();
        // 咨询
        consultFragment = AdvisoryFragment.newInstance();
        // 诉状
        fragmentLegalAdviser = FragmentLegalAdviser.newInstance();
        //        openFragment = new OpenFragment();
        // 我的
        mineFragment = UserPersonalFragment.newInstance();
        //        EMClient.getInstance().chatManager().loadAllConversations();
        EMClient.getInstance().chatManager().loadAllConversations();
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().addClientListener(clientListener);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fram_layout, userHomeFragment) // 首页
                .add(R.id.fram_layout, messageFragment) // 消息
                .add(R.id.fram_layout, consultFragment) // 咨询
                .add(R.id.fram_layout, fragmentLegalAdviser) // 顾问
                .add(R.id.fram_layout, mineFragment)
                .commitNowAllowingStateLoss();
        //        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        //        for (int index = 0; index < fragments.size(); index++) {
        //            transaction.hide(fragments.get(index));
        //        }
        //        transaction.commitNowAllowingStateLoss();

        // 默认首页
        changeTagStatus(getBundleInt(KEY_CURRENT_POSITION, 0));
    }

    EMClientListener clientListener =
            new EMClientListener() {
                @Override
                public void onMigrate2x(boolean success) {
                    Toast.makeText(
                                    MainActivity.this,
                                    "onUpgradeFrom 2.x to 3.x " + (success ? "success" : "fail"),
                                    Toast.LENGTH_LONG)
                            .show();
                    if (success) {
                        refreshUIWithMessage();
                    }
                }
            };
    private int currentTabIndex;

    private void refreshUIWithMessage() {
        runOnUiThread(
                new Runnable() {
                    public void run() {
                        // refresh unread count
                        //                updateUnreadLabel();
                        if (currentTabIndex == 0) {
                            // refresh conversation list
                            if (messageFragment != null) {
                                //
                                // messageFragment.getConversationListFragment().refresh();
                            }
                        }
                    }
                });
    }

    public void changeTagStatus(int i) {
        switch (i) {
            case 0:
                changeStatus(i, iv_main_bottom_home, tv_main_bottom_home);
                break;
            case 1:
                changeStatus(i, iv_main_bottom_message, tv_main_bottom_message);
                break;
            case 2:
                changeStatus(i, iv_main_bottom_zixun, tv_main_bottom_jia);
                break;
            case 3:
                changeStatus(i, iv_main_bottom_suzhuang, tv_main_bottom_suzhuang);
                break;
            case 4:
                changeStatus(i, iv_main_bottom_wode, tv_main_bottom_wode);
                break;
            default:
                break;
        }
    }

    private void changeStatus(int i, ImageView img, TextView text) {
        changeFragment(i);
        if (i != 0) {
            iv_main_bottom_home.setEnabled(true);
            tv_main_bottom_home.setTextColor(getResources().getColor(R.color.textColor_66));
        }
        if (i != 1) {
            iv_main_bottom_message.setEnabled(true);
            tv_main_bottom_message.setTextColor(getResources().getColor(R.color.textColor_66));
        }
        if (i != 2) {
            iv_main_bottom_zixun.setEnabled(true);
            tv_main_bottom_jia.setTextColor(getResources().getColor(R.color.textColor_66));
        }
        if (i != 3) {
            iv_main_bottom_suzhuang.setEnabled(true);
            tv_main_bottom_suzhuang.setTextColor(getResources().getColor(R.color.textColor_66));
        }
        if (i != 4) {
            iv_main_bottom_wode.setEnabled(true);
            tv_main_bottom_wode.setTextColor(getResources().getColor(R.color.textColor_66));
        }
        img.setEnabled(false);
        text.setTextColor(getResources().getColor(R.color.textColor_collect_audio_Select));
    }

    private void changeFragment(int i) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(beginTransaction);
        switch (i) {
            case 0:
                // 首页
                beginTransaction.show(userHomeFragment);
                break;
            case 1:
                // 消息
                beginTransaction.show(messageFragment);
                break;
            case 2:
                // 咨询
                beginTransaction.show(consultFragment);
                break;
            case 3:
                // 诉状
                beginTransaction.show(fragmentLegalAdviser);
                break;
            case 4:
                // 我的
                beginTransaction.show(mineFragment);
                break;
            default:
                //  beginTransaction.show(homeFragment1);
                beginTransaction.show(userHomeFragment);
                break;
        }
        beginTransaction.commitNowAllowingStateLoss();
    }

    private void hideFragment(FragmentTransaction beginTransaction) {
        // 首页

        //        if (homeFragment1 != null) {
        //            beginTransaction.hide(homeFragment1);

        if (userHomeFragment != null) {
            beginTransaction.hide(userHomeFragment);
        }
        // 消息
        if (messageFragment != null) {
            beginTransaction.hide(messageFragment);
        }
        // 咨询
        if (consultFragment != null) {
            beginTransaction.hide(consultFragment);
        }
        // 诉状
        if (fragmentLegalAdviser != null) {
            beginTransaction.hide(fragmentLegalAdviser);
        }
        // 我的
        if (mineFragment != null) {
            beginTransaction.hide(mineFragment);
        }
    }

    @Override
    public void onClick(View v) {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        changeStatusBarTextColor(true);
        switch (v.getId()) {
            case R.id.rl_main_bottom_home:
                changeTagStatus(0);
                changeStatusBarTextColor(true);
                break;
            case R.id.rl_main_bottom_message:
                if (App.getInstance().getLoginFlag() && DemoHelper.getInstance().isLoggedIn()) {
                    changeTagStatus(1);
                } else {
                    toLogin();
                }
                changeStatusBarTextColor(true);
                break;
            case R.id.iv_main_bottom_zixun:
                if (App.getInstance().getLoginFlag()) {
                    changeTagStatus(2);
                    //                    Eyes.setStatusBarColor(this,
                    // CommonUtil.getColor(R.color.transparent_4c));
                    changeStatusBarTextColor(false);
                    StatusBarCompat.setStatusBarColor(
                            this, getResources().getColor(R.color.transparent_4c));
                } else {
                    toLogin();
                }
                //                changeStatusBarTextColor(false);
                break;
            case R.id.rl_main_bottom_suzhuang:
                if (App.getInstance().getLoginFlag()) {
                    changeTagStatus(3);
                } else {
                    toLogin();
                }
                changeStatusBarTextColor(true);
                break;
            case R.id.rl_main_bottom_wode:
                if (App.getInstance().getLoginFlag()) {
                    changeTagStatus(4);
                    changeStatusBarTextColor(false);
                    StatusBarCompat.setStatusBarColor(this, Color.parseColor("#44A2F7"));
                } else {
                    toLogin();
                }
                break;
            default:
                break;
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //                        showRemind("确认退出应用么?");
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > AppConstant.EXIT_INTERVAL_TIME) {
            ToastUtil.showToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            //            System.exit(0);
            //                        App.getInstance().finishAllActivity();
            App.getInstance().exitApp();
        }
    }

    public static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = mActivity.get();
            if (mainActivity != null) {
                if (0 == msg.what) {
                    // 版本更新
                    mainActivity.showUpdate((UpDateApkBean) msg.obj);
                }
            }
        }
    }

    private void showUpdate(UpDateApkBean apkBean) {
        // UI配置
        UiConfig uiConfig = new UiConfig();
        uiConfig.setUiType(UiType.CUSTOM);
        uiConfig.setCustomLayoutId(R.layout.pop_update_new);
        uiConfig.setCancelBtnText("残忍拒绝");
        uiConfig.setUpdateBtnText("立即升级");
        // 更新配置
        UpdateConfig updateConfig = new UpdateConfig();
        updateConfig.setDebug(AppConstant.DEBUG);
        // 自动添加后缀.apk
        String fileName =
                MD5Util.toMd5(
                        (apkBean.getUrl() + apkBean.getCode() + apkBean.getCode_str()).getBytes(),
                        true);
        //        updateConfig.setApkSaveName("云台法律咨询");
        updateConfig.setApkSaveName(fileName);
        updateConfig.setNotifyImgRes(R.mipmap.icon_app_logo);
        // 是否强制更新，强制时无取消按钮
        if (force) {
            updateConfig.setForce(true);
        }
        UpdateAppUtils.getInstance()
                .apkUrl(apkBean.getUrl())
                // title设置为版本号
                .updateTitle("v" + apkBean.getCode_str())
                .updateContent(apkBean.getMiaoshu())
                .uiConfig(uiConfig)
                // 自定义布局中控件内容填充
                .setOnInitUiListener(
                        (view, updateConfig1, uiConfig1) -> {
                            // 是否强制更新，强制时无取消按钮
                            if (null != view && force) {
                                // 隐藏分割线
                                view.findViewById(R.id.view_update_line).setVisibility(View.GONE);
                            }
                        })
                .setCancelBtnClickListener(
                        () -> {
                            return false;
                        })
                .setUpdateDownloadListener(
                        new UpdateDownloadListener() {
                            @Override
                            public void onStart() {
                                if (!force) {
                                    // 非强制更新
                                }
                            }

                            @Override
                            public void onDownload(int i) {}

                            @Override
                            public void onFinish() {}

                            @Override
                            public void onError(@NonNull Throwable throwable) {
                                Logger.e(
                                        "UpdateDownloadListener -> onError() :"
                                                + throwable.getMessage());
                            }
                        })
                .updateConfig(updateConfig)
                .update();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /** 设置未读消息小红点显示 */
    private void setUnreadCound(int count) {
        runOnUiThread(
                () -> {
                    TextView tvRed = findViewById(R.id.tv_main_red_point);
                    if (count <= 0) {
                        tvRed.setVisibility(View.INVISIBLE);
                    } else if (count > 9) {
                        tvRed.setVisibility(View.VISIBLE);
                        tvRed.setText("...");
                    } else {
                        tvRed.setVisibility(View.VISIBLE);
                        tvRed.setText(String.valueOf(count));
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int count = EmChatManager.getInstance().getAllUnreadCount();
        setUnreadCound(count);
    }

    // ===Desc:=================================================================
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        int count = EmChatManager.getInstance().getAllUnreadCount();
        Logger.e("MainActivity接收消息,count = " + count);
        setUnreadCound(count);
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {}

    @Override
    public void onMessageRead(List<EMMessage> list) {}

    @Override
    public void onMessageDelivered(List<EMMessage> list) {}

    @Override
    public void onMessageRecalled(List<EMMessage> list) {}

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetUnReadCount(String s) {
        if ("delete".equals(s)) {
            int count = EmChatManager.getInstance().getAllUnreadCount();
            setUnreadCound(count);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUnLockCount(EventBusMessage msg) {
        Logger.e("$$$$$$$$$$$$$$$$$$$$$" + msg);
        if (msg.what == 100) {
            try {
                int count = Integer.parseInt(String.valueOf(msg.obj));
                setUnreadCound(count);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    //    private void test() {
    ////        String url =
    // "https://yuntaifawu.com/data/upload/hetong/kaitingwenshu/2488shangshizc/仲裁答辩书（农业部2016版）
    // (3).docx";
    //        String url =
    // "http://192.168.0.92/%E4%BB%B2%E8%A3%81%E7%AD%94%E8%BE%A9%E4%B9%A6%EF%BC%88%E5%86%9C%E4%B8%9A%E9%83%A82016%E7%89%88%EF%BC%89%20(3).docx";
    //
    //        OfficePreviewActivity.start(mContext, url);
    //
    //    }
}

//                       .::::.
//                     .::::::::.
//                    :::::::::::
//                 ..:::::::::::'
//              '::::::::::::'
//                .::::::::::
//           '::::::::::::::..
//                ..::::::::::::.
//              ``::::::::::::::::
//               ::::``:::::::::'        .:::.
//              ::::'   ':::::'       .::::::::.
//            .::::'      ::::     .:::::::'::::.
//           .:::'       :::::  .:::::::::' ':::::.
//          .::'        :::::.:::::::::'      ':::::.
//         .::'         ::::::::::::::'         ``::::.
//     ...:::           ::::::::::::'              ``::.
//    ```` ':.          ':::::::::'                  ::::..
//                       '.:::::'                    ':'````..
//

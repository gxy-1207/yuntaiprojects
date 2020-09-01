package com.ytfu.yuntaifawu.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.supplier.IdSupplier;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.BuildConfig;
import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.OppoService;
import com.ytfu.yuntaifawu.app.App;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.login.bean.GoToPageBean;

import org.apaches.commons.codec.binary.Base64;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.autosize.utils.LogUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/** @Auther gxy @Date 2020/1/9 @Des */
public class LoginHelper {

    private static LoginHelper instance = null;
    private final int MAX_HX_LOGIN_COUNT = 3;
    private int hxLoginCount;

    public static synchronized LoginHelper getInstance() {
        if (instance == null) {
            instance = new LoginHelper();
        }
        return instance;
    }

    public void login(Context context, String uid, String shenfen, String token) {
        SpUtil.putString(context, AppConstant.UID, uid);
        SpUtil.putString(context, AppConstant.SHENFEN, shenfen);
        SpUtil.putString(context, AppConstant.TOKEN, token);
    }

    // 判断环信是否登录
    public void loginSuccess(String uid, String pwd) {
        if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(pwd)) {
            EMClient.getInstance()
                    .login(
                            uid,
                            pwd,
                            new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    EMClient.getInstance().groupManager().loadAllGroups();
                                    EMClient.getInstance().chatManager().loadAllConversations();
                                    DemoHelper.getInstance()
                                            .getUserProfileManager()
                                            .asyncGetCurrentUserInfo();
                                    Log.d("main", "登录聊天服务器成功！");
                                }

                                @Override
                                public void onError(int i, String s) {
                                    Logger.e("22code ---> " + i + ",,, msg ---> " + s);
                                    Log.d("main", "登录聊天服务器失败！");
                                }

                                @Override
                                public void onProgress(int i, String s) {}
                            });
        }
    }

    public interface OnEMLoginCallback {
        void onSuccess();

        void onFail(int code, String msg);
    }

    // 判断环信是否登录
    public void loginSuccess(String uid, String pwd, OnEMLoginCallback callback) {
        if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(pwd)) {
            EMClient.getInstance()
                    .login(
                            uid,
                            pwd,
                            new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    Log.d(
                                            "main",
                                            "登录聊天服务器成功！ ---->" + Thread.currentThread().getName());
                                    callback.onSuccess();
                                    EMClient.getInstance().groupManager().loadAllGroups();
                                    EMClient.getInstance().chatManager().loadAllConversations();
                                    DemoHelper.getInstance()
                                            .getUserProfileManager()
                                            .asyncGetCurrentUserInfo();
                                }

                                @Override
                                public void onError(int i, String s) {
                                    callback.onFail(i, s);
                                    Logger.e("22code ---> " + i + ",,, msg ---> " + s);
                                    Log.d("main", "登录聊天服务器失败！");
                                }

                                @Override
                                public void onProgress(int i, String s) {}
                            });
        }
    }

    public void ShareInstallSuccess() {
        //        ShareInstall.getInstance().reportRegister(new OnReportRegisterListener() {
        //            @Override
        //            public void onSuccess() {
        //                Logger.e("ShareInstallSuccess", "---------onSuccess");
        //            }
        //
        //            @Override
        //            public void onError() {
        //                Logger.e("ShareInstallSuccess", "---------onError");
        //            }
        //        });
    }
    //    public <T extends BaseActivity> void loginSuccess(T activity, LoginBean bean) {
    //        if (null != activity) {
    //            SpUtil.putString(AppConstant.TOKEN, bean.getToken());
    ////            SpUtil.putString(AppConstant.MOBILE, bean.getMobile());
    ////            SpUtil.putString(AppConstant.USERNAME, bean.getUser_name());
    ////            SpUtil.putString(AppConstant.USER_PORTRAIT, bean.getThumb());
    ////            SpUtil.putString(AppConstant.OPEN_MID, bean.getOpen_mid());
    //
    //            // 用户类型：1 普通用户  2 规划师
    //            // 2.判断是否是规划师
    //            if (AppConstant.TWO.equals(bean.getUser_type())) {
    //                // 2.1保存规划师类型
    //                SpUtil.putInteger(AppConstant.PLANNER_TYPE,
    // Integer.parseInt(bean.getPlanner_type()));
    //            } else {
    //                // 2.2默认规划师类型为普通
    //                SpUtil.putInteger(AppConstant.PLANNER_TYPE, AppConstant.PLANNER_COMMON);
    //            }
    //
    //            // APP登录成功通知
    //            EventBus.getDefault().postSticky(new MessageEvent(AppConstant.LOGIN_SUCCESS));
    //
    //            if (!TextUtils.isEmpty(bean.getHx_username()) &&
    // !TextUtils.isEmpty(bean.getHx_password())) {
    //                DemoDBManager.getInstance().closeDB();
    //                DemoHelper.getInstance().setCurrentUserName(bean.getHx_username());
    //                EMClient.getInstance().login(bean.getHx_username(), bean.getHx_password(), new
    // EMCallBack() {
    //                    //回调
    //                    @Override
    //                    public void onSuccess() {
    //                        activity.runOnUiThread(new Runnable() {
    //                            @Override
    //                            public void run() {
    //                                SpUtil.putString(AppConstant.USERHXNAME,
    // bean.getHx_username());
    //                                EMClient.getInstance().groupManager().loadAllGroups();
    //                                EMClient.getInstance().chatManager().loadAllConversations();
    //
    // DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
    //                                EventBus.getDefault().postSticky(new
    // MessageEvent(AppConstant.LOGINSUCCESS));
    //                                Log.d("main", "登录聊天服务器成功！");
    //                            }
    //                        });
    //
    //                    }
    //
    //                    @Override
    //                    public void onProgress(int progress, String status) {
    //
    //                    }
    //
    //                    @Override
    //                    public void onError(int code, String message) {
    //                        Log.d("main", "登录聊天服务器失败！" + code + "-=-=-=-=-" + message);
    //                        SpUtil.putString(AppConstant.USERHXNAME, "");
    //                        EMClient.getInstance().chatManager().loadAllConversations();
    //                        DemoHelper.getInstance().setCurrentUserName("");
    //
    // DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
    //                    }
    //                });
    //            }
    //        }
    //        // 设置JPush别名
    //        if (!SpUtil.getBoolean(AppConstant.JIGUANG_ALIAS, false)) {
    ////            String alias = StringUtil.getStringRandom(32);
    //            setAlias(bean.getToken());
    //        }
    //    }

    /*public void loginSuccess(LoginBean bean, BaseActivity activity) {
        this.appLogin(bean, activity, null);
    }

    public void loginSuccess(LoginBean bean, BaseActivity activity, Boolean notLogin) {
        this.appLogin(bean, activity, notLogin);
    }

    */
    /** App登录 */
    /*
    private void appLogin(LoginBean bean, BaseActivity activity, Boolean notLogin) {
        SpUtil.putString(AppConstant.TOKEN, bean.getToken());

        // 用户类型：1 普通用户  2 规划师
        // 2.判断是否是规划师
        if (AppConstant.TWO.equals(bean.getUser_type())) {
            // 2.1保存规划师类型
            SpUtil.putInteger(AppConstant.PLANNER_TYPE, Integer.parseInt(bean.getPlanner_type()));
        } else {
            // 2.2默认规划师类型为普通
            SpUtil.putInteger(AppConstant.PLANNER_TYPE, AppConstant.PLANNER_COMMON);
        }

        hxLogin(bean, activity, notLogin);

        // 设置JPush别名
        */
    /*if (!SpUtil.getBoolean(AppConstant.JIGUANG_ALIAS, false)) {
        String alias = StringUtil.getStringRandom(32);
        setAlias(alias);
    }*/
    /*
    }

    */
    /** 环信登录 */
    /*
    private void hxLogin(LoginBean bean, BaseActivity activity, Boolean notLogin) {
        if (TextUtils.isEmpty(bean.getHx_username()) || TextUtils.isEmpty(bean.getHx_password())) {
            Logger.d("main", "username or password is null or empty!");

            appLoginSuccess(activity, notLogin);
            ToastUtil.showToast("登录聊天服务器失败！");
            return;
        }

        activity.showWaitingDialog("登录聊天服务器...");
        DemoDBManager.getInstance().closeDB();
        DemoHelper.getInstance().setCurrentUserName(bean.getHx_username());
        EMClient.getInstance().login(bean.getHx_username(), bean.getHx_password(), new EMCallBack() {
            //回调
            @Override
            public void onSuccess() {
                Log.d("main", "登录聊天服务器成功！");

                SpUtil.putString(AppConstant.USERHXNAME, bean.getHx_username());
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                // 环信登录成功
                EventBus.getDefault().postSticky(new MessageEvent(AppConstant.LOGINSUCCESS));

                appLoginSuccess(activity, notLogin);
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！" + code + "-=-=-=-=-" + message);

                if (hxLoginCount++ < MAX_HX_LOGIN_COUNT) {
                    hxLogin(bean, activity, notLogin);
                } else {
                    SpUtil.putString(AppConstant.USERHXNAME, "");
                    EMClient.getInstance().chatManager().loadAllConversations();
                    DemoHelper.getInstance().setCurrentUserName("");
                    DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                    ToastUtil.showToast("登录聊天服务器失败！");

                    appLoginSuccess(activity, notLogin);
                }
            }
        });
    }

    */

    /** App登录成功 */
    /*
    private void appLoginSuccess(BaseActivity activity, Boolean notLogin) {
        activity.hideWaitingDialog();

        // APP登录成功
        EventBus.getDefault().postSticky(new MessageEvent(AppConstant.LOGIN_SUCCESS));

        if (null != notLogin) {
            // 登录页登录
            if (!notLogin) {
                App.getInstance().finishActivity(MainActivity.class);
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
            }
            activity.finish();
        } else {
            // 注册成功自动登录
            App.getInstance().finishActivity(LoginActivity.class);
            Intent intent = new Intent(activity, AuthenticateActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }*/
    public void cleanLoginInfo() {
        clearCache(App.getContext());
        EventBus.getDefault().post(new MessageEvent(AppConstant.LOGOUT));
        EMClient.getInstance().logout(true);
    }

    /** 退出登录 */
    public void logout() {
        clearCache(App.getContext());

        EventBus.getDefault().post(new MessageEvent(AppConstant.LOGOUT));
        EMClient.getInstance()
                .logout(
                        true,
                        new EMCallBack() {
                            @Override
                            public void onSuccess() {}

                            @Override
                            public void onError(int i, String s) {}

                            @Override
                            public void onProgress(int i, String s) {}
                        });
    }

    //    /**
    //     * 退出登录
    //     *
    //     * @param activity 继承了{@link BaseActivity}的activity or null
    //     * @deprecated
    //     */
    //    public <T extends BaseActivity> void logout(T activity) {
    //        clearCache();
    //        EventBus.getDefault().post(new MessageEvent(AppConstant.LOGOUT));
    //
    //        /*
    //         * 使用第三方推送时需要在退出登录时解绑设备 token，
    //         *
    // 调用EMClient#getInstance()#logout(true)或者EMClient#getInstance()#logout(true,callback)方法，
    //         * 如果是被踢的情况下，则要求设置为 false。
    //         * */
    //        activity.runOnUiThread(
    //                () -> {
    //                    if (!activity.isDestroyed()) {
    //                        activity.hideWaitingDialog();
    //                    }
    //                    Intent intent = new Intent(activity, LoginCodeActivity.class);
    //                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 关键的一句，将新的activity置为栈顶
    //                    activity.startActivity(intent);
    //                    activity.finish();
    //                });
    //
    //        if (null != activity) {
    //            EMClient.getInstance()
    //                    .logout(
    //                            true,
    //                            new EMCallBack() {
    //                                @Override
    //                                public void onSuccess() {
    //                                    activity.runOnUiThread(
    //                                            () -> {
    //                                                if (!activity.isDestroyed()) {
    //                                                    activity.hideWaitingDialog();
    //                                                }
    //                                                activity.finish();
    //                                            });
    //                                }
    //
    //                                @Override
    //                                public void onError(int i, String s) {
    //                                    activity.runOnUiThread(
    //                                            () -> {
    //                                                if (!activity.isDestroyed()) {
    //                                                    activity.hideWaitingDialog();
    //                                                }
    //                                                activity.finish();
    //                                                Logger.e("logout:onError" + i + s);
    //                                            });
    //                                }
    //
    //                                @Override
    //                                public void onProgress(int i, String s) {
    //                                    activity.runOnUiThread(
    //                                            () -> {
    //                                                if (!activity.isDestroyed()) {
    //                                                    activity.hideWaitingDialog();
    //                                                }
    //                                                activity.finish();
    //                                                Logger.e("logout:onProgress" + i + s);
    //                                            });
    //                                }
    //                            });
    //        } else {
    //            // 账号被踢的情况
    //            EMClient.getInstance().logout(false);
    //        }
    //    }

    /** 清除必要 清除/重置 的缓存数据 */
    private void clearCache(Context context) {
        SpUtil.putString(context, AppConstant.UID, "");
        SpUtil.putString(context, AppConstant.SHENFEN, "");
        SpUtil.putString(context, AppConstant.TOKEN, "");
        SpUtil.removeSpKey(context, AppConstant.UID);
        SpUtil.removeSpKey(context, AppConstant.SHENFEN);
        SpUtil.removeSpKey(context, AppConstant.TOKEN);
        /*SpUtil.putString(AppConstant.TOKEN, "");
        SpUtil.putString(AppConstant.MOBILE, "");
        SpUtil.putString(AppConstant.USERNAME, "");
        SpUtil.putString(AppConstant.USERHXNAME, "");
        SpUtil.putString(AppConstant.USER_PORTRAIT, "");
        SpUtil.putBoolean(AppConstant.JIGUANG_ALIAS, false);
        SpUtil.putBoolean(AppConstant.NOTIFICATION_TIPS, false);
        SpUtil.putInteger(AppConstant.PLANNER_TYPE, AppConstant.PLANNER_COMMON);
        SpUtil.putString(AppConstant.HAS_COMPLETE, AppConstant.ZERO);
        SpUtil.putString(AppConstant.COMPANY_STATUS, "");
        SpUtil.putString(AppConstant.HAS_CHECK_REAL_NAME, AppConstant.ZERO);
        SpUtil.putString(AppConstant.IS_LEADER, "");
        SpUtil.putString(AppConstant.COMPANY_AUDITED, "");
        SpUtil.putString(AppConstant.MOBILE, "");
        SpUtil.putString(AppConstant.HAS_PASSWORD, "");
        SpUtil.putString(AppConstant.WALLET_BALANCE, "");*/
        //        SpUtil.putBoolean(AppConstant.HAS_JUMP_TO_JOIN_TEAM,false);

        // TODO 待测试
        //        SpUtil.clearSp();
    }

    /** 这是来自 JPush Example 的设置别名的 ActivityZhiYeJiGou 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。 */
    //    private void setAlias(String alias) {
    //        if (TextUtils.isEmpty(alias)) {
    //            ToastUtil.showToast(CommonUtil.getString(R.string.error_alias_empty));
    //            return;
    //        }
    //        if (!StringUtil.isValidTagAndAlias(alias)) {
    //            ToastUtil.showToast(CommonUtil.getString(R.string.error_tag_gs_empty));
    //            return;
    //        }
    //
    //        // 从 3.0.7 版本开始支持，新版本别名设置
    //        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new
    // TagAliasOperatorHelper.TagAliasBean();
    //        tagAliasBean.setAction(ACTION_SET);
    //        sequence++;
    //        tagAliasBean.setAlias(alias);
    //        tagAliasBean.setAliasAction(true);
    //        TagAliasOperatorHelper.getInstance().handleAction(App.getContext(), sequence,
    // tagAliasBean);
    //    }
    //    private void setAlias(String alias) {
    //        if (TextUtils.isEmpty(alias)) {
    //            ToastUtil.showToast(CommonUtil.getString(R.string.error_alias_empty));
    //            return;
    //        }
    //        if (!StringUtil.isValidTagAndAlias(alias)) {
    //            ToastUtil.showToast(CommonUtil.getString(R.string.error_tag_gs_empty));
    //            return;
    //        }
    //
    //        // 从 3.0.7 版本开始支持，新版本别名设置
    //        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new
    // TagAliasOperatorHelper.TagAliasBean();
    //        tagAliasBean.setAction(ACTION_SET);
    //        sequence++;
    //        tagAliasBean.setAlias(alias);
    //        tagAliasBean.setAliasAction(true);
    //        TagAliasOperatorHelper.getInstance().handleAction(App.getContext(), sequence,
    // tagAliasBean);
    //    }

    /** 是否是用户登录 */
    public boolean isUserLogin() {
        String s = SpUtil.getString(App.getContext(), AppConstant.SHENFEN, "1");
        return "1".equals(s);
    }

    /** 获取登录用户的uid */
    public String getLoginUserId() {
        return SpUtil.getString(App.getContext(), AppConstant.UID, "");
    }
    /** 获取token */
    public String getLoginToken(Context context) {
        return SpUtil.getString(context, AppConstant.TOKEN, "");
    }

    /** 获取请求头需要的加密token */
    public String getRequestTokenHeader() {
        String token = getLoginToken(App.getContext());
        String uid = getLoginUserId();
        String s = token + "," + uid;
        return RSAUtilKt.rsaEncrypt(s, BuildConfig.UUID);
    }

    public void oppoCallback(Context context, String channel) {

        MiIdHelper miIdHelper =
                new MiIdHelper(
                        new MiIdHelper.OnMiIdCallback() {
                            @Override
                            public void onSupport(IdSupplier idSupplier) {
                                String ouid = oppoDataEncode(idSupplier.getOAID().getBytes());
                                long currentTime = System.currentTimeMillis();
                                Map<String, String> map = new HashMap<>();
                                map.put("ouid", ouid); //
                                //                map.put("mac", "");
                                map.put("clientIp", "");
                                map.put("timestamp", String.valueOf(currentTime)); //
                                map.put("pkg", context.getPackageName());
                                map.put("dataType", String.valueOf(2)); //
                                //        map.put("customType", String.valueOf(2));

                                // 根据渠道判断上传channel
                                String uploadChannel;
                                if ("channel_04".equals(channel)) {
                                    uploadChannel = "1";
                                } else {
                                    uploadChannel = "0";
                                }
                                map.put("channel", uploadChannel); //
                                JSONObject jo = new JSONObject(map);
                                String json = jo.toString();

                                String signature =
                                        md5(json + currentTime + "e0u6fnlag06lc3pl").toLowerCase();
                                //                String test =
                                // "{\"payAmount\":100,\"adId\":101097648,\"appType\":1,\"clientIp\":\"127.0.0.1\",\"dataType\":1,\"ascribeType\":1,\"channel\":1,\"imei\":\"XJMyaLt8fDlv4a9b8/0RNQ==\",\"type\":1,\"pkg\":\"com.oppo.test\",\"mac\":\"TEViR6jSgD/lECBl3Ah70eNy2gUQrQlekHkWqEGkZsU=\",\"timestamp\":1571995483916}1571995483916e0u6fnlag06lc3pl";
                                //                LogUtils.e("signature = " + md5(test));
                                RequestBody requestBody =
                                        RequestBody.create(
                                                MediaType.parse("application/json"), json);
                                HttpUtil.getInstance()
                                        .getOppoService(OppoService.class)
                                        .uploadActiveData(
                                                signature, String.valueOf(currentTime), requestBody)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(
                                                new SingleObserver<ResponseBody>() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {}

                                                    @Override
                                                    public void onSuccess(
                                                            ResponseBody responseBody) {
                                                        try {
                                                            String s = responseBody.string();
                                                            LogUtils.e("=============" + s);
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {}
                                                });
                            }

                            @Override
                            public void onUnSupport(IdSupplier idSupplier) {
                                LogUtils.e("不支持获取");
                            }
                        });
        MdidSdkHelper.InitSdk(context, true, miIdHelper);
    }

    private static String oppoDataEncode(byte[] data) {
        try {
            String base64Key = "XGAXicVG5GMBsx5bueOe4w==";
            final Key dataKey = new SecretKeySpec(Base64.decodeBase64(base64Key), "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, dataKey);
            byte[] encryptData = cipher.doFinal(data);
            return Base64.encodeBase64String(encryptData).replaceAll("\r", "").replaceAll("\n", "");
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | BadPaddingException
                | IllegalBlockSizeException
                | InvalidKeyException e) {
            e.printStackTrace();
        }
        return new String(data);
    }

    private String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /** 登陆成功后判断进入页面 */
    public void LoginGoToPae(BaseRxObserver<Integer> callback) {
        HttpUtil.getInstance()
                .getService(ApiService.class)
                .getLoginSuccessPage(getLoginUserId())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        new BaseRxObserver<GoToPageBean>() {
                            @Override
                            public void onNextImpl(GoToPageBean data) {
                                if (data == null) {
                                    callback.onNextImpl(-1);
                                    return;
                                }
                                if (data.getStatus() != 200) {
                                    callback.onNextImpl(-1);
                                    return;
                                }
                                callback.onNextImpl(data.getType());
                            }

                            @Override
                            public void onErrorImpl(String errorMessage) {
                                callback.onNextImpl(-1);
                            }
                        });
    }
}

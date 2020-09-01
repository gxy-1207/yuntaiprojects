package com.ytfu.yuntaifawu.utils.dialog;

import android.content.Context;

import androidx.annotation.Nullable;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.UserInfoService;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.users.bean.AccountBalanceBean;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DialogHelper {

    private DialogHelper() throws IllegalAccessException {
        throw new IllegalAccessException("You can not instantiation of DialogHelper class");
    }

    // ===Desc:================================================================================

    /**
     * 显示带余额支付的支付dialog
     *
     * @param balance 个人账户余额
     * @param enableSelfPay 个人账户余额是否可点击
     * @param commitListener 提交监听回调
     */
    public static void showPayDialog(
            Context context,
            double balance,
            boolean enableSelfPay,
            @Nullable PayDialog.OnCommitListener commitListener) {

        PayDialog dialog = new PayDialog(context);
        new XPopup.Builder(context)
                .dismissOnTouchOutside(false)
                .dismissOnBackPressed(false)
                .setPopupCallback(
                        new XPopupCallback() {
                            @Override
                            public void onCreated() {
                                dialog.setOnCommitListener(commitListener);
                            }

                            @Override
                            public void beforeShow() {
                                dialog.setBalanceShow(balance);
                                dialog.enableSelfPay(enableSelfPay);
                            }

                            @Override
                            public void onShow() {}

                            @Override
                            public void onDismiss() {}

                            @Override
                            public boolean onBackPressed() {
                                return true;
                            }
                        })
                .asCustom(dialog)
                .show();
    }

    /** @param payMoney 需要支付的金额,使用该金额判读是否大于账户余额,来设置账户支付是否可用 */
    public static void showPayDialog(
            Context context, double payMoney, @Nullable PayDialog.OnCommitListener commitListener) {
        LoadingPopupView loading = new XPopup.Builder(context).asLoading();
        String userId = SpUtil.getString(context, AppConstant.UID, "");
        HttpUtil.getInstance()
                .getService(UserInfoService.class)
                .getAccountBalance(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new BaseRxObserver<AccountBalanceBean>() {
                            @Override
                            public void onSubscribeImpl(Disposable d) {
                                super.onSubscribeImpl(d);
                                loading.show();
                            }

                            @Override
                            public void onNextImpl(AccountBalanceBean data) {
                                if (null == data) {
                                    ToastUtil.showToast("获取账户余额失败");
                                    loading.dismiss();
                                    return;
                                }
                                if (data.getStatus() != 200) {
                                    ToastUtil.showToast("获取账户余额失败");
                                    loading.dismiss();
                                    return;
                                }
                                loading.dismissWith(
                                        () -> {
                                            double balance = data.getIncome();
                                            showPayDialog(
                                                    context,
                                                    balance,
                                                    balance >= payMoney,
                                                    commitListener);
                                        });
                            }

                            @Override
                            public void onErrorImpl(String errorMessage) {
                                loading.dismiss();
                                ToastUtil.showToast("获取账户余额失败");
                            }
                        });
    }

    /** 不带个人账户的支付dialog */
    public static void showPayDialogWithoutBalance(
            Context context, @Nullable PayDialog.OnCommitListener commitListener) {
        PayDialog dialog = new PayDialog(context);
        new XPopup.Builder(context)
                .dismissOnTouchOutside(false)
                .dismissOnBackPressed(false)
                .setPopupCallback(
                        new XPopupCallback() {
                            @Override
                            public void onCreated() {
                                dialog.setOnCommitListener(commitListener);
                            }

                            @Override
                            public void beforeShow() {
                                dialog.setVisibilitySelfPay(true);
                            }

                            @Override
                            public void onShow() {}

                            @Override
                            public void onDismiss() {}

                            @Override
                            public boolean onBackPressed() {
                                return true;
                            }
                        })
                .asCustom(dialog)
                .show();
    }
}

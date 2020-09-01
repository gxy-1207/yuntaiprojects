package com.ytfu.yuntaifawu.ui.redpackage.p;

import android.os.SystemClock;
import android.text.TextUtils;

import com.ytfu.yuntaifawu.apis.PayService;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.chatroom.bean.FeeWechatOrderResponse;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;
import com.ytfu.yuntaifawu.ui.pay.bean.PayBean;
import com.ytfu.yuntaifawu.ui.pay.bean.WxPayBean;
import com.ytfu.yuntaifawu.ui.redpackage.v.SendRedPackageView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 发送红包业务层实现
 */
public class SendRedPackagePresenter extends BasePresenter<SendRedPackageView> {
    /**
     * 用户发送红包
     */
    public void sendRedPackage(double money) {
        if (money <= 0.0) {
            getView().showToast("请输入金额");
            return;
        }
        getView().showProgress();
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                getView().runOnUi(() -> {
                    getView().hideProgress();
                    getView().onSendRedPackageSuccess(money);
                });
            }
        }.start();
    }


    /**
     *
     * 微信支付红包
     */
    public void getWechatPayOrder(String selfId, String lawyerId, double money) {
        Observable<FeeWechatOrderResponse> ob = createService(PayService.class)
                .userSendRedPackageByWechat(selfId, lawyerId, 15, 1, money);
        requestRemote(ob, new BaseRxObserver<FeeWechatOrderResponse>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(FeeWechatOrderResponse data) {
                getView().hideProgress();
                if (null == data) {
                    getView().showToast("获取数据失败,请稍后重试");
                    return;
                }

                int status = data.getStatus();
                if (status != 1) {
                    getView().showToast("系统错误,请稍后重试");
                    return;
                }
                WxPayBean.SignBean sign = data.getSign();
                if (null == sign) {
                    getView().showToast("数据解析失败,请稍后重试");
                    return;
                }
                getView().onGetWechatOrder(sign, money);

            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().hideProgress();
            }
        });
    }

    /**
     * 支付宝支付红包
     */
    public void aliPay(String selfId, String lawyerId, double money) {
        Observable<PayBean> ob = createService(PayService.class)
                .userSendRedPackageByAli(selfId, lawyerId, 15, 1, money);
        requestRemote(ob, new BaseRxObserver<PayBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(PayBean data) {
                getView().hideProgress();
                if (null == data) {
                    getView().showToast("获取数据失败,请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if (status != 1) {
                    getView().showToast("系统错误,请稍后重试");
                    return;
                }
                String sign = data.getSign();
                if (TextUtils.isEmpty(sign)) {
                    getView().showToast("数据解析失败,请稍后重试");
                    return;
                }
                getView().onGetAliOrder(sign, money);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().hideProgress();
            }
        });
    }


    /**
     *余额支付
     * */
    public void getBlance(String selfId, String lawyerId, double money){
        Observable<AccountPayResponseBean> ob = createService(PayService.class)
                .userSendRedPackageByBlance(selfId, lawyerId, 15, 1, money);
        requestRemote(ob, new BaseRxObserver<AccountPayResponseBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(AccountPayResponseBean data) {
                if (null == data) {
                    getView().showToast("获取数据失败,请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if (status != 1) {
                    getView().showToast("系统错误,请稍后重试");
                    return;
                }
                getView().onGetBalance(money);
            }

            @Override
            public void onErrorImpl(String errorMessage) {

            }
        });
    }
}

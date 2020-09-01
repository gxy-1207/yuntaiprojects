package com.ytfu.yuntaifawu.ui.topup.p;

import android.text.TextUtils;

import com.ytfu.yuntaifawu.apis.PayService;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.chatroom.bean.FeeWechatOrderResponse;
import com.ytfu.yuntaifawu.ui.pay.bean.PayBean;
import com.ytfu.yuntaifawu.ui.pay.bean.WxPayBean;
import com.ytfu.yuntaifawu.ui.topup.v.TopUpView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class TopUpPresenter extends BasePresenter<TopUpView> {

    public void topUpWechat(String userId, double money) {
        Observable<FeeWechatOrderResponse> ob = createService(PayService.class)
                .topUpByWechat(userId, 20, 1, money);

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
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                WxPayBean.SignBean sign = data.getSign();
                if (null == sign) {
                    getView().showToast("数据解析失败,请稍后重试");
                    return;
                }
                getView().onTopUpWechatSuccess(sign);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().hideProgress();
            }
        });
    }

    public void topUpAli(String userId, double money) {
        Observable<PayBean> ob = createService(PayService.class)
                .topUpByAli(userId, 20, 1, money);

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
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                String sign = data.getSign();
                if (TextUtils.isEmpty(sign)) {
                    getView().showToast("数据解析失败,请稍后重试");
                    return;
                }
                getView().onTopUpAliSuccess(sign);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().hideProgress();
            }
        });
    }
}

package com.ytfu.yuntaifawu.ui.pay.p;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.apis.AdvisoryApi;
import com.ytfu.yuntaifawu.apis.ChatService;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.PayService;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.chatroom.bean.FeeWechatOrderResponse;
import com.ytfu.yuntaifawu.ui.pay.PayHelper;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountInfoBean;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;
import com.ytfu.yuntaifawu.ui.pay.bean.PayBean;
import com.ytfu.yuntaifawu.ui.pay.bean.WxPayBean;
import com.ytfu.yuntaifawu.ui.pay.v.PayView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PayPresenter extends BasePresenter<PayView> {
    public void requestSendMessage(String uid,Runnable call) {
        call.run();
//        HttpUtil.getInstance().getService(ChatService.class)
//                .test(uid)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .as(bindLifecycle())
//                .subscribe(new BaseRxObserver<Object>() {
//                    @Override
//                    public void onNextImpl(Object lawyerListBean) {
//                        call.run();
//                    }
//
//                    @Override
//                    public void onErrorImpl(String errorMessage) {
//                        call.run();
//                        Logger.e("============");
//                    }
//                });
    }
    /**
     * 获取6个的悬赏支付
     */
    public static List<Integer> getReward6() {
        List<Integer> list = new ArrayList<>();
        list.add(20);
        list.add(35);
        list.add(55);
        list.add(66);
        list.add(88);
        list.add(99);
        return list;
    }

    /**
     * 获取最低输入金额
     */
    public static double getDefaultMinMoney() {
        return getReward6().get(0) * 1.0;
    }

    //===Desc:================================================================================

    public void getAdvisoryInfo(String userId) {
        Observable<AccountInfoBean> ob = createService(AdvisoryApi.class)
                .getAdvisoryInfo(userId);

        requestRemote(ob, new BaseRxObserver<AccountInfoBean>() {
            @Override
            public void onNextImpl(AccountInfoBean data) {
                if (null == data) {
                    getView().showError();
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if (status != 200) {
                    getView().showError();
                    getView().showToast(data.getState());
                    return;
                }
                getView().onGetAdvisoryInfoSuccess(data);

            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().showError();
                getView().showToast(errorMessage);
            }
        });


    }

    /**
     * 获取微信支付订单
     *
     * @param userId           用户id
     * @param advisoryId       咨询id
     * @param type             14是咨询悬赏16是置顶咨询18是追加悬赏19是咨询解锁
     * @param isExpedited      是否加急
     * @param additionalReward 悬赏的金额
     */
    public void getWechatPayOrder(String userId,
                                  String advisoryId,
                                  int type,
                                  boolean isExpedited,
                                  String additionalReward) {
        Observable<FeeWechatOrderResponse> ob = createService(PayService.class)
                .getBountyPaymentWechatOrder(userId,
                        advisoryId,
                        type, 1,
                        isExpedited ? 1 : 2,
                        additionalReward);
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
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if (1 != status) {
                    getView().showToast("请求数据出现错误，请稍后重试");
                    return;
                }
                WxPayBean.SignBean sign = data.getSign();
                if (null == sign) {
                    getView().showToast("数据解析失败，请稍后重试");
                    return;
                }
                //唤起微信支付
                PayHelper.getInstance().payByWechat(sign);
            }


            @Override
            public void onErrorImpl(String errorMessage) {
                getView().showToast(errorMessage);
            }
        });
    }

    /**
     * 获取支付宝付订单
     */
    public void getAliPayOrder(String userId,
                               String advisoryId,
                               int type,
                               boolean isExpedited,
                               String additionalReward) {
        Observable<PayBean> ob = createService(PayService.class)
                .getBountyPaymentAliOrder(userId,
                        advisoryId,
                        type, 1,
                        isExpedited ? 1 : 2,
                        additionalReward);
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
                    getView().showToast(data.getState());
                    return;
                }
                if (TextUtils.isEmpty(data.getSign())) {
                    getView().showToast("数据异常,请稍后重试");
                    return;
                }
                //唤起支付宝
                getView().onAwakeAli(data.getSign());
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().hideProgress();
                getView().showToast(errorMessage);
            }
        });
    }

    /**
     * 余额支付
     */
    public void payByAccount(String userId,
                             String advisoryId,
                             int type,
                             boolean isExpedited,
                             String additionalReward) {

        Observable<AccountPayResponseBean> ob = createService(PayService.class)
                .payByAccount(userId,
                        advisoryId,
                        type, 1,
                        isExpedited ? 1 : 2,
                        additionalReward);
        requestRemote(ob, new BaseRxObserver<AccountPayResponseBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(AccountPayResponseBean data) {
                getView().hideProgress();
                if (null == data) {
                    getView().showToast("获取数据失败,请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if (status != 1) {
                    getView().showToast(data.getState());
                    return;
                }
                getView().onPayByAccountSuccess();

            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().hideProgress();
                getView().showToast(errorMessage);
            }
        });
    }

}

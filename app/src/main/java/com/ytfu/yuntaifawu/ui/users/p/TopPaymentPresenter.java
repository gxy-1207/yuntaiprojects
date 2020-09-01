package com.ytfu.yuntaifawu.ui.users.p;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.TopPaymentApi;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.pay.PayHelper;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;
import com.ytfu.yuntaifawu.ui.pay.bean.PayBean;
import com.ytfu.yuntaifawu.ui.pay.bean.WxPayBean;
import com.ytfu.yuntaifawu.ui.users.bean.OverageBean;
import com.ytfu.yuntaifawu.ui.users.bean.PublicPriceBean;
import com.ytfu.yuntaifawu.ui.users.v.TopPaymentView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @Auther gxy
 * @Date 2020/6/17
 * @Des 置顶支付presenter
 */
public class TopPaymentPresenter extends BasePresenter<TopPaymentView> {
    /**
     * 获取置顶金额
     */
    public void getPublicPrice() {
        Observable<PublicPriceBean> observable = createService(TopPaymentApi.class).setPublicPrice();
        requestRemote(observable, new BaseRxObserver<PublicPriceBean>() {
            @Override
            public void onNextImpl(PublicPriceBean data) {
                if (data == null) {
                    getView().showError();
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if (status != 1) {
                    getView().showError();
                    getView().showToast(data.getState());
                    return;
                }
                getView().getTopPayPriec(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().showError();
                Logger.e("getPublicPrice" + errorMessage);
            }
        });
    }


    /**
     * 微信支付
     */
    public void payWatch(String uid, String id, int type, int xitong, String consult_jiaji, String price) {
        Observable<WxPayBean> observable = createService(TopPaymentApi.class).setPayWatch(uid, id, type, xitong, consult_jiaji, price);
        requestRemote(observable, new BaseRxObserver<WxPayBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(WxPayBean data) {
                getView().hideProgress();
                if (null == data) {
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if(status!=1){
                    getView().showToast("请求数据出现错误，请稍后重试");
                    return;
                }
                WxPayBean.SignBean sign = data.getSign();
                if(sign == null){
                    getView().showToast("数据解析失败，请稍后重试");
                    return;
                }
                //唤起微信
                PayHelper.getInstance().payByWechat(sign);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("payWatch",errorMessage);
            }
        });
    }

    /**
     * 支付宝支付
     * */
    public void payAli(String uid, String id, int type, int xitong, String consult_jiaji, String price){
        Observable<PayBean> observable = createService(TopPaymentApi.class).stePayAli(uid, id, type, xitong, consult_jiaji, price);
        requestRemote(observable, new BaseRxObserver<PayBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(PayBean data) {
                getView().hideProgress();
                if(data == null){
                    getView().showToast("获取数据失败,请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if(status!=1){
                    getView().showToast(data.getState());
                    return;
                }
                if(TextUtils.isEmpty(data.getSign())){
                    getView().showToast("数据异常,请稍后重试");
                    return;
                }
                //唤起支付宝
                getView().onAwakeAli(data.getSign());
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("payWatch",errorMessage);
            }
        });
    }
    /**
     * 余额
     * */
    public void payOverage(String uid, String id, int type, int xitong, String consult_jiaji, String price){
        Observable<AccountPayResponseBean> observable = createService(TopPaymentApi.class).setPayOverage(uid, id, type, xitong, consult_jiaji, price);
        requestRemote(observable, new BaseRxObserver<AccountPayResponseBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(AccountPayResponseBean data) {
                getView().hideProgress();
                if(data == null){
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if(status!=1){
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                getView().onPayByAccountSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("payWatch",errorMessage);
            }
        });
    }
}

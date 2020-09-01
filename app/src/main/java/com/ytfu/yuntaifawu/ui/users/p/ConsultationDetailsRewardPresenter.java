package com.ytfu.yuntaifawu.ui.users.p;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.BuildConfig;
import com.ytfu.yuntaifawu.apis.ConsultationApi;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.TopPaymentApi;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.pay.PayHelper;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;
import com.ytfu.yuntaifawu.ui.pay.bean.PayBean;
import com.ytfu.yuntaifawu.ui.pay.bean.WxPayBean;
import com.ytfu.yuntaifawu.ui.users.bean.ConsultationDetailsBean;
import com.ytfu.yuntaifawu.ui.users.v.ConsultationDetailsView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import me.jessyan.autosize.utils.LogUtils;

/**
 * @Auther gxy
 * @Date 2020/6/15
 * @Des 咨询详情已悬赏presenter
 */
public class ConsultationDetailsRewardPresenter extends BaseRefreshPresenter<ConsultationDetailsView> {
    //获取咨询详情评价列表
    public void getConsultationDetails(boolean isLoadMore, String id) {
        ConsultationApi service = HttpUtil.getInstance().getService(ConsultationApi.class);
        Observable<ConsultationDetailsBean> observable = service.setConsultationDetail(id);
        BaseRxObserver<ConsultationDetailsBean> cb = new BaseRxObserver<ConsultationDetailsBean>() {
            @Override
            public void onNextImpl(ConsultationDetailsBean data) {
                int status = data.getStatus();
                if (status == 200) {
//                    if (BuildConfig.IS_DEBUG) {
//                        data.getContent().setIs_first(0);
//                    }
                    getView().onConsultationDetailsSuccess(data);
                    if (data == null) {
                        return;
                    }
                    if ("0".equals(data.getContent().getUnlock_type())) {
                        return;
                    }
                    if (data.getXiaoxi_arr() == null || data.getXiaoxi_arr().isEmpty()) {
                        return;
                    }
                    getView().stopRefresh();

                    if (data.getContent().getIs_first() == 0) {
                        //显示动画
                        if (getView().getAdapter().getData().isEmpty()) {
                            Observable.interval(0, 1, TimeUnit.SECONDS)
                                    .as(bindLifecycle())
                                    .subscribe(new BaseRxObserver<Long>() {
                                        private Disposable disposable;

                                        @Override
                                        public void onSubscribeImpl(Disposable d) {
                                            super.onSubscribeImpl(d);
                                            this.disposable = d;
                                        }

                                        @Override
                                        public void onNextImpl(Long aLong) {
                                            LogUtils.e("along = " + aLong);
                                            int index = Integer.parseInt(String.valueOf(aLong));
                                            if (index >= data.getXiaoxi_arr().size()) {
                                                disposable.dispose();
                                                return;
                                            }
                                            ConsultationDetailsBean.XiaoxiArrBean xiaoxiArrBean = data.getXiaoxi_arr().get(index);
                                            getView().runOnUi(() -> {
                                                if (!getView().getAdapter().getData().contains(xiaoxiArrBean)) {
                                                    if (getView() != null) {
                                                        getView().addData(xiaoxiArrBean);
                                                        getView().getRecycleView().smoothScrollToPosition(getView().getAdapter().getData().size());
                                                    }
                                                }
                                            });
                                        }

                                        @Override
                                        public void onErrorImpl(String errorMessage) {

                                        }
                                    });

                        } else {
                            getView().setNewData(data.getXiaoxi_arr());
                        }
                    } else {
                        getView().setNewData(data.getXiaoxi_arr());
                    }
                    getView().loadMoreEnd(true);
                } else {
                    getView().onConsultationDetailsSuccess(data);
                }
                getView().hideLoading();
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().onConsultationDetailsFiled();
                getView().hideLoading();
            }
        };
        HttpUtil.getInstance().enqueue(observable, bindLifecycle(), cb);

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
                if (status != 1) {
                    getView().showToast("请求数据出现错误，请稍后重试");
                    return;
                }
                WxPayBean.SignBean sign = data.getSign();
                if (sign == null) {
                    getView().showToast("数据解析失败，请稍后重试");
                    return;
                }
                //唤起微信
                PayHelper.getInstance().payByWechat(sign);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("payWatch", errorMessage);
            }
        });
    }

    /**
     * 支付宝支付
     */
    public void payAli(String uid, String id, int type, int xitong, String consult_jiaji, String price) {
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
                if (data == null) {
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
                Logger.e("payWatch", errorMessage);
            }
        });
    }

    /**
     * 余额
     */
    public void payOverage(String uid, String id, int type, int xitong, String consult_jiaji, String price) {
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
                if (data == null) {
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if (status != 1) {
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                getView().onPayByAccountSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("payWatch", errorMessage);
            }
        });
    }
}

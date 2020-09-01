package com.ytfu.yuntaifawu.ui.users.p;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.RefundService;
import com.ytfu.yuntaifawu.apis.TransactionService;
import com.ytfu.yuntaifawu.apis.UserInfoService;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.lawyer.wallet.bean.WalletResponseBean;
import com.ytfu.yuntaifawu.ui.mine.bean.MineBean;
import com.ytfu.yuntaifawu.ui.mine.bean.ShenHeJInduBean;
import com.ytfu.yuntaifawu.ui.users.bean.AccountBalanceBean;
import com.ytfu.yuntaifawu.ui.users.bean.RefundButtonVisibleBean;
import com.ytfu.yuntaifawu.ui.users.v.UserPersonalView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserPersonalPresenter extends BasePresenter<UserPersonalView> {

    /**
     * 获取用户个人信息
     */
    public void getUserInfo(String userId) {
        Observable<MineBean> ob = createService(UserInfoService.class)
                .getUserInfo(userId);
        requestRemote(ob, new BaseRxObserver<MineBean>() {
            @Override
            public void onNextImpl(MineBean data) {
                getView().hideLoading();
                if (null == data) {
                    getView().onGetInfoFail("获取数据失败，请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if (status != 200) {
                    getView().onGetInfoFail("获取数据失败，请稍后重试");
                    return;
                }
                MineBean.FindBean find = data.getFind();
                if (null == find) {
                    getView().onGetInfoFail("解析数据失败，请稍后重试");
                    return;
                }
                getView().onGetInfoSuccess(find);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().hideLoading();
                getView().onGetInfoFail(errorMessage);
            }
        });


    }

    /**
     * 点击认证信息，获取当前状态
     */
    public void getAuthInfo(String userId) {
        Observable<ShenHeJInduBean> ob = createService(UserInfoService.class)
                .setAuthInfo(userId);
        requestRemote(ob, new BaseRxObserver<ShenHeJInduBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(ShenHeJInduBean entity) {
                if (null == entity) {
                    getView().hideProgress();
                    getView().showToast("获取数据是失败，请稍后再试");
                    return;
                }
                getView().hideProgress();
                getView().onGetAuthInfo(entity);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().hideProgress();
                getView().showToast(errorMessage);
            }
        });

    }


    public void getAccountBalance(String userId) {
        Observable<AccountBalanceBean> ob = createService(UserInfoService.class)
                .getAccountBalance(userId);

        requestRemote(ob, new BaseRxObserver<AccountBalanceBean>() {
            @Override
            public void onNextImpl(AccountBalanceBean data) {
                if (null == data) {
                    getView().onGetAccountBalance(0.0);
                    return;
                }
                if (data.getStatus() != 200) {
                    getView().onGetAccountBalance(0.0);
                    return;
                }
                getView().onGetAccountBalance(data.getIncome());
            }

            @Override
            public void onErrorImpl(String errorMessage) {

            }
        });
    }


    /**
     * 获取个人账户钱包信息
     */
    public void getWalletInfo(String uid) {
        HttpUtil.getInstance().getService(TransactionService.class)
                .getWalletInfo(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle())
                .subscribe(new BaseRxObserver<WalletResponseBean>() {
                    @Override
                    public void onSubscribeImpl(Disposable d) {
                        super.onSubscribeImpl(d);
                        getView().showProgress();
                    }

                    @Override
                    public void onNextImpl(WalletResponseBean bean) {
                        if (null == bean) {
                            getView().onGetWalletInfoFail("获取数据失败");
                            return;
                        }
                        int code = bean.getCode();
                        if (code != 200) {
                            getView().onGetWalletInfoFail(bean.getMsg());
                            return;
                        }
                        WalletResponseBean.DataBean data = bean.getData();
                        if (null == data) {
                            getView().onGetWalletInfoFail("解析数据失败");
                            return;
                        }
                        getView().onGetWalletInfoSuccess(bean);
                    }

                    @Override
                    public void onErrorImpl(String errorMessage) {
                        getView().onGetWalletInfoFail(errorMessage);
                    }
                });


    }


    /**
     *
     * 控制退款按钮显示
     *https://yuntaifawu.com/api/Refund/SwitcthRefund
     * */
    public void getRefundButtonVisible(){
        Observable<RefundButtonVisibleBean> observable = createService(RefundService.class).setRefundButtonVisible();
        requestRemote(observable, new BaseRxObserver<RefundButtonVisibleBean>() {
            @Override
            public void onNextImpl(RefundButtonVisibleBean data) {
                if(data == null){
                    return;
                }
                if(data.getCode() !=200){
                    return;
                }
                if(data.getData() == null){
                    return;
                }
                getView().onRefundButtonVisibleSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("getRefundButtonVisible",errorMessage);
            }
        });
    }
}

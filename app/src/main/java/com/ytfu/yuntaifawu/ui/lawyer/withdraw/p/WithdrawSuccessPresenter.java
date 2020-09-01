package com.ytfu.yuntaifawu.ui.lawyer.withdraw.p;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.apis.TransactionService;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.lawyer.withdraw.bean.WithdrawSuccessBean;
import com.ytfu.yuntaifawu.ui.lawyer.withdraw.v.WithdrawSuccessView;

import io.reactivex.Observable;

/**
 * @Auther gxy
 * @Date 2020/7/9
 * @Des 体现成功presenter
 */
public class WithdrawSuccessPresenter extends BasePresenter<WithdrawSuccessView> {
    /**
     * 提现成功
     */

    public void getWithdrawSuccess(String user_id, String tixian_id) {
        Observable<WithdrawSuccessBean> observable = createService(TransactionService.class).setWithdrawSucccess(user_id, tixian_id);
        requestRemote(observable, new BaseRxObserver<WithdrawSuccessBean>() {
            @Override
            public void onNextImpl(WithdrawSuccessBean data) {
                if(data == null){
                    getView().showToast("应用程序内部出现问题，请稍后再试");
                    return;
                }
                if(data.getStatus() != 200){
                    return;
                }
                if(data.getData() == null){
                    return;
                }
                getView().onWithdrawSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().hideLoading();
                Logger.e("getWithdrawSuccess",errorMessage);
            }
        });
    }
} 
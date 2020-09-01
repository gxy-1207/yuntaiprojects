package com.ytfu.yuntaifawu.ui.lvshiwenti.presenter;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.lvshiwenti.bean.IWantToAnswerBean;
import com.ytfu.yuntaifawu.ui.lvshiwenti.view.IWantToAnswerView;

import io.reactivex.Observable;

/**
 * @Auther gxy
 * @Date 2020/6/18
 * @Des 我要回答presenter
 */
public class IWantToAnswerPresenter extends BasePresenter<IWantToAnswerView> {

    public void getIWantToAnswer(String consultid, String lsid, String content) {
        Observable<IWantToAnswerBean> observable = createService(ApiService.class).setIWantToAnswer(consultid, lsid, content);
        requestRemote(observable, new BaseRxObserver<IWantToAnswerBean>() {
            @Override
            public void onNextImpl(IWantToAnswerBean data) {
                getView().iWantToAnswerSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("IWantToAnswerPresenter",errorMessage);
            }
        });
    }
}

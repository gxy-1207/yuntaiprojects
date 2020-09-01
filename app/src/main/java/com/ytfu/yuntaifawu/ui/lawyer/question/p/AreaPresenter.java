package com.ytfu.yuntaifawu.ui.lawyer.question.p;

import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.lawyer.question.bean.AreaResponseBean;
import com.ytfu.yuntaifawu.ui.lawyer.question.v.AreaView;

import io.reactivex.Observable;

public class AreaPresenter extends BasePresenter<AreaView> {


    public void getAllArea() {
        Observable<AreaResponseBean> ob = createService(ApiService.class)
                .getAllArea();
        requestRemote(ob, new BaseRxObserver<AreaResponseBean>() {
            @Override
            public void onNextImpl(AreaResponseBean data) {
                getView().onGetAllAreaSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().onGetAllAreaSuccess(null);
            }
        });

    }
}

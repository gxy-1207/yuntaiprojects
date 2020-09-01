package com.ytfu.yuntaifawu.ui.mseeage.presenter;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.mseeage.bean.ComplintBean;
import com.ytfu.yuntaifawu.ui.mseeage.view.ComplaintView;

import io.reactivex.Observable;

public class ComplaintPresenter extends BasePresenter<ComplaintView> {

    public void setComplaint(String uid,String lid,String content){
        Observable<ComplintBean> observable = createService(ApiService.class).getComplaint(uid, lid, content);
        requestRemote(observable, new BaseRxObserver<ComplintBean>() {
            @Override
            public void onNextImpl(ComplintBean data) {
                if(data.getStatus()!=1){
                    getView().showToast("投诉失败");
                    return;
                }
                getView().onComplaintSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("setComplaint",errorMessage);
            }
        });
    }
} 
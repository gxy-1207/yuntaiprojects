package com.ytfu.yuntaifawu.ui.contract.p;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.contract.v.ContractClassificationView;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.ContractListSecondBean;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ContractClassificationPresenter extends BaseRefreshPresenter<ContractClassificationView> {

    /**
     * 合同二级列表
     */
    public void contractSecondList(String id) {
        Observable<ContractListSecondBean> observable = createService(ApiService.class).getListSecond(id);
        requestRemote(observable, new BaseRxObserver<ContractListSecondBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showLoading();
            }

            @Override
            public void onNextImpl(ContractListSecondBean data) {
                getView().hideLoading();
                if(data == null){
                    getView().setEmptyView(R.layout.layout_empty);
                    return;
                }
                if(data.getStatus() !=1){
                    getView().setEmptyView(R.layout.layout_empty);
                    return;
                }
                if(data.getList() == null || data.getList().isEmpty()){
                    getView().setEmptyView(R.layout.layout_empty);
                    return;
                }
                getView().setNewData(data.getList());
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("contractSecondList",errorMessage);
            }
        });
    }
} 
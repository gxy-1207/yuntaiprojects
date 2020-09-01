package com.ytfu.yuntaifawu.ui.contract.p;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.ContractDatalistBean;

import io.reactivex.Observable;

public class ContractListPresenter extends BaseRefreshPresenter<BaseRefreshView<ContractDatalistBean.ListBean>> {
    public void contractListData(boolean isLoadMor, String id, int page) {
        Observable<ContractDatalistBean> observable = createService(ApiService.class).getContractList(id, page);
        requestRemote(observable, new BaseRxObserver<ContractDatalistBean>() {
            @Override
            public void onNextImpl(ContractDatalistBean data) {
                if(data == null){
                    if(!isLoadMor){
                        getView().setEmptyView(R.layout.layout_empty);
                        getView().stopRefresh();
                    }else{
                        getView().loadMoreComplete();
                    }
                    getView().resetCurrentPage(isLoadMor,1);
                    return;
                }
                if(data.getStatus() !=1 ){
                    getView().setEmptyView(R.layout.layout_empty);
                    getView().resetCurrentPage(isLoadMor,1);
                    getView().loadMoreEnd(false);
                    return;
                }
                if(data.getList() == null || data.getList().isEmpty()){
                    if(!isLoadMor){
                        getView().setEmptyView(R.layout.layout_empty);
                        getView().stopRefresh();
                    }else{
                        getView().loadMoreComplete();
                    }
                    getView().resetCurrentPage(isLoadMor,1);
                    return;
                }
                if(isLoadMor){
                    getView().addData(data.getList());
                    getView().loadMoreComplete();
                }else{
                    getView().setNewData(data.getList());
                }
                getView().stopRefresh();
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("contractListData",errorMessage);
            }
        });
    }
}
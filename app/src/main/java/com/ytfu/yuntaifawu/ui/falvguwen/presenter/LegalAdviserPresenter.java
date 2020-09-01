package com.ytfu.yuntaifawu.ui.falvguwen.presenter;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.falvguwen.bean.LegalAdviserClassifyBean;
import com.ytfu.yuntaifawu.ui.falvguwen.view.LegalAdviserView;

import io.reactivex.Observable;

public class LegalAdviserPresenter extends BaseRefreshPresenter<LegalAdviserView> {
   public void setLegalAdviser(){
       Observable<LegalAdviserClassifyBean> observable = createService(ApiService.class).getFLGW();
       requestRemote(observable, new BaseRxObserver<LegalAdviserClassifyBean>() {
           @Override
           public void onCompleteImpl() {
               super.onCompleteImpl();
               getView().stopRefresh();
           }

           @Override
           public void onNextImpl(LegalAdviserClassifyBean data) {
               if(data == null){
                   getView().setEmptyView(R.layout.layout_empty);
                   return;
               }
               if(data.getStatus() != 200){
                   getView().setEmptyView(R.layout.layout_empty);
                   return;
               }
               if(data.getList() == null || data.getList().isEmpty()){
                   getView().setEmptyView(R.layout.layout_empty);
                   return;
               }
               getView().setNewData(data.getList());
               getView().onLegalAdSuccess(data);
           }

           @Override
           public void onErrorImpl(String errorMessage) {
               getView().setEmptyView(R.layout.layout_empty);
               getView().stopRefresh();
               Logger.e("setLegalAdviser",errorMessage);
           }
       });
   }
}
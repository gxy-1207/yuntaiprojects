package com.ytfu.yuntaifawu.ui.lvshiwenti.presenter;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.apis.ConsultationApi;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.lvshiwenti.view.LawyerConsultationDetailsView;
import com.ytfu.yuntaifawu.ui.users.bean.ConsultationDetailsBean;

import io.reactivex.Observable;

/**
*
*  @Auther  gxy
*
*  @Date    2020/6/17
*
*  @Des  律师端咨询问题详情
*
*/
public class LawyerCounselingDetailsPresenter extends BaseRefreshPresenter
        <LawyerConsultationDetailsView> {
    //获取咨询详情评价列表
    public void getConsultationDetails(boolean isLoadMore,String id){
        ConsultationApi service = HttpUtil.getInstance().getService(ConsultationApi.class);
        Observable<ConsultationDetailsBean> observable = service.setConsultationDetail(id);
        HttpUtil.getInstance().enqueue(observable, bindLifecycle(), new BaseRxObserver<ConsultationDetailsBean>() {
            @Override
            public void onNextImpl(ConsultationDetailsBean data) {
                int status = data.getStatus();
                if(data == null){
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                if(status != 200){
                    getView().showToast("获取数据失败，请稍后重试");
                }else{
                    getView().onConsultationDetailsSuccess(data);
                }
                if(data.getXiaoxi_arr() == null || data.getXiaoxi_arr().isEmpty()){
//                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                getView().stopRefresh();
                getView().setNewData(data.getXiaoxi_arr());
                getView().loadMoreEnd(true);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("getConsultationDetails",errorMessage);
            }
        });
    }

}

package com.ytfu.yuntaifawu.ui.users.p;

import com.ytfu.yuntaifawu.apis.ConsultationApi;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.users.bean.ConsultationDetailsBean;
import com.ytfu.yuntaifawu.ui.users.v.ConsultationDetailsView;
import com.ytfu.yuntaifawu.ui.users.v.ConsultationDetailsView2;

import io.reactivex.Observable;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/6
*
*  @Des  他的咨询详情p
*
*/
public class ConsultationDetailsRewardPresenter2 extends BasePresenter<ConsultationDetailsView2> {
    //获取咨询详情评价列表
    public void getConsultationDetails2(String id){

        ConsultationApi service = HttpUtil.getInstance().getService(ConsultationApi.class);
        Observable<ConsultationDetailsBean> observable = service.setConsultationDetail(id);
        HttpUtil.getInstance().enqueue(observable, bindLifecycle(), new BaseRxObserver<ConsultationDetailsBean>() {
            @Override
            public void onNextImpl(ConsultationDetailsBean data) {
                int status = data.getStatus();
                if(status == 200){
                    getView().onConsultationDetailsSuccess(data);
                    if(data == null){
                        return;
                    }
                    if("0".equals(data.getContent().getUnlock_type())){
                        return;
                    }
                    if(data.getXiaoxi_arr() == null || data.getXiaoxi_arr().isEmpty()){
                        return;
                    }
//                    getView().stopRefresh();
////                    getView().setNewData(data.getXiaoxi_arr());
////                    getView().loadMoreEnd(true);
                }else{
                    getView().onConsultationDetailsSuccess(data);
                }
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().onConsultationDetailsFiled();
            }
        });
    }
}


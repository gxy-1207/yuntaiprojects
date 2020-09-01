package com.ytfu.yuntaifawu.ui.lvshiwenti.view;

import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;
import com.ytfu.yuntaifawu.ui.users.bean.ConsultationDetailsBean;

/**
*
*  @Auther  gxy
*
*  @Date    2020/6/15
*
*  @Des  咨询详情view
*
*/
public interface LawyerConsultationDetailsView extends BaseRefreshView<ConsultationDetailsBean.XiaoxiArrBean> {

    void onConsultationDetailsSuccess(ConsultationDetailsBean consultationDetailsBean);

}

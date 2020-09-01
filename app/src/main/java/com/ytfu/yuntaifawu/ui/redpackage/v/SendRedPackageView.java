package com.ytfu.yuntaifawu.ui.redpackage.v;


import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.pay.bean.WxPayBean;

public interface SendRedPackageView extends BaseView {

    void onSendRedPackageSuccess(double money);


    void onGetWechatOrder(WxPayBean.SignBean sign, double money);


    void onGetAliOrder(String sign, double money);

    void onGetBalance(double money);
}

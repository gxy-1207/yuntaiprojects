package com.ytfu.yuntaifawu.ui.topup.v;

import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.pay.bean.WxPayBean;

public interface TopUpView extends BaseView {

    void onTopUpWechatSuccess(WxPayBean.SignBean sign);
    void onTopUpAliSuccess(String sign);
}

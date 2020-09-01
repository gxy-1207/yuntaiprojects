package com.ytfu.yuntaifawu.ui.pay.v;

import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountInfoBean;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;

public interface PayView extends BaseView {

    void onGetAdvisoryInfoSuccess(AccountInfoBean data);

    /**唤起支付宝支付*/
    void onAwakeAli(String sign);

    /**账户余额支付成功的回调*/
    void onPayByAccountSuccess();

}

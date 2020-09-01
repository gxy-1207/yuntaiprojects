package com.ytfu.yuntaifawu.ui.users.v;

import com.ytfu.yuntaifawu.base.BaseView;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/23
*
*  @Des 退款详情v
*
*/
public interface RefundDetailsView extends BaseView {
    void onRefundSuccess();
    void onRefundFiled(String error);
}

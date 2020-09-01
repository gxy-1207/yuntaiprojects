package com.ytfu.yuntaifawu.ui.chat.v;

import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.chat.bean.LawyerListBean;
import com.ytfu.yuntaifawu.ui.chat.bean.UnLockMessage;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;

import java.util.List;

public interface IChatListView extends BaseView {

    void showPayDialog(UnLockMessage unLockMessage);


    void onLawyerListSuccess(List<LawyerListBean.LawyerItemBean> list);

    void onLawyerListSuccess2(List<LawyerListBean.LawyerItemBean> list, UnLockMessage unLockMessage);

    void onLawyerListFail(String msg);

    void reLoadList();

    void onShowUnLockMessage(UnLockMessage unLockMessage);


    //===Desc:================================================================================

    /**
     * 唤起支付宝支付
     */
    void onAwakeAli(String sign);

    /**
     * 账户余额支付成功的回调
     */
    void onPayByAccountSuccess(AccountPayResponseBean payResponseBean);

    void onMessageReceived(List<LawyerListBean.LawyerItemBean> list);

    void onDeleteSuccess();
}

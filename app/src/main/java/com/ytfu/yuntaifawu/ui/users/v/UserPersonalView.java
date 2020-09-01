package com.ytfu.yuntaifawu.ui.users.v;

import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.lawyer.wallet.bean.WalletResponseBean;
import com.ytfu.yuntaifawu.ui.mine.bean.MineBean;
import com.ytfu.yuntaifawu.ui.mine.bean.ShenHeJInduBean;
import com.ytfu.yuntaifawu.ui.users.bean.RefundButtonVisibleBean;

public interface UserPersonalView extends BaseView {


    void onGetInfoSuccess(MineBean.FindBean bean);

    void onGetInfoFail(String errorMsg);
    //===Desc:================================================================================

    void onGetAuthInfo(ShenHeJInduBean bean);

    void onGetAccountBalance(double balance);

    //===Desc:================================================================================


    void onGetWalletInfoSuccess(WalletResponseBean bean);

    void onRefundButtonVisibleSuccess(RefundButtonVisibleBean visibleBean);

    void onGetWalletInfoFail(String errorMsg);

}

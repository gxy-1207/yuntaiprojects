package com.ytfu.yuntaifawu.ui.contract.v;

import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.ContractListSecondBean;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/30
*
*  @Des 合同分类v
*
*/
public interface ContractClassificationView extends BaseRefreshView<ContractListSecondBean.ListBean> {

    void onContractSuccess(ContractListSecondBean listSecondBean);
    void onContractFiled();
}

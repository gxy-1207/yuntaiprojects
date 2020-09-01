package com.ytfu.yuntaifawu.ui.lvshihetong.view;

import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.HomeContractBean;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.VipPageInfo;

public interface LawyerContractView extends BaseRefreshView<HomeContractBean> {

    void onGetVipPageInfo(VipPageInfo data);
}

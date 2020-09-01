package com.ytfu.yuntaifawu.ui.falvguwen.view;

import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.falvguwen.bean.LegalAdviserClassifyBean;

public interface LegalAdviserView extends BaseRefreshView<LegalAdviserClassifyBean.ListBean> {
    void onLegalAdSuccess(LegalAdviserClassifyBean classifyBean);
    void onLegalAdFiled();
}

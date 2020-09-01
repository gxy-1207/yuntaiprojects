package com.ytfu.yuntaifawu.ui.mseeage.view;

import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.mseeage.bean.ComplintBean;

public interface ComplaintView extends BaseView {
    void onComplaintSuccess(ComplintBean complintBean);
    void onComplaintFiled();
}

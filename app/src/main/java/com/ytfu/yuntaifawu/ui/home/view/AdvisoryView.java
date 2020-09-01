package com.ytfu.yuntaifawu.ui.home.view;

import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.consult.bean.ZiXunNavBean;
import com.ytfu.yuntaifawu.ui.home.bean.RandomLawyerResponse;

import java.util.List;

public interface AdvisoryView extends BaseView {

    void onGetAdvisoryInfo(ZiXunNavBean data);

    /**
     * 进入付费列表界面
     */
    void onGotoPayActivity(String advisoryId, double money);

    /**
     * 进入发布成功之后的列表界面
     */
    void onGotoPostSuccessActivity(String advisoryId);

    void cleanUserInput();


    void onGetRandomLawyers( RandomLawyerResponse.D data);

    void onGetRandomLawyersFail();

}

package com.ytfu.yuntaifawu.ui.users.v;

import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.home.bean.HomeBannerBean;
import com.ytfu.yuntaifawu.ui.users.bean.LawyerHomeListBean;

/**
*
*  @Auther  gxy
*
*  @Date    2020/6/9
*
*  @Des   用户端首页view
*
*/
public interface IUserHomeView extends BaseRefreshView<LawyerHomeListBean.DataBean> {
    //获取轮播图集合
    void userHomeBannerList(HomeBannerBean bannerBean);
    //律师列表
    void userLawyerList(LawyerHomeListBean lawyerHomeListBean);
    void userHomeFiled(String error);
}

package com.ytfu.yuntaifawu.ui.lvshiwode.view;

import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.ClassificationOfCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.CommonWordsListBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.EditDeleteCommonWordsBean;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/15
*
*  @Des 常用语管理
*
*/
public interface ManagementCommonWordsView2 extends BaseView {
     void onCategorySuccess(ClassificationOfCommonWordsBean classification);
     void onfiledError(String error);
}

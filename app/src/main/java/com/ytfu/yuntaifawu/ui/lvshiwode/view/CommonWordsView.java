package com.ytfu.yuntaifawu.ui.lvshiwode.view;

import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.AddCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.ClassificationOfCommonWordsBean;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/14
*
*  @Des 常用语v
*
*/
public interface CommonWordsView extends BaseView {
    void onAddCommonWordsSuccess(AddCommonWordsBean addCommonWordsBean);
    void onAddCommonWordsFiled(String errorMsg);
    void onClassificationOfCommonWordsSuccess(ClassificationOfCommonWordsBean classificationOfCommonWordsBean);
}

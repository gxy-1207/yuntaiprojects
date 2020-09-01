package com.ytfu.yuntaifawu.ui.lvshiwode.view;

import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.AddCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.EditDeleteCommonWordsBean;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/14
*
*  @Des 常用语v
*
*/
public interface EditCommonWordsView extends BaseView {
    void onEditCommonWordsSuccess(EditDeleteCommonWordsBean deleteCommonWordsBean);
    void onEditCommonWordsFiled(String errorMsg);
}

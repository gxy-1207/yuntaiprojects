package com.ytfu.yuntaifawu.ui.lawyer.chat.v;

import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.GetQiniuTokenBean;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.HistoryChatItemBean;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.HistoryChatItemMultiItem;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.ClassificationOfCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.CommonWordsListBean;

import java.util.List;

public interface LawyerChatRoomView extends BaseView {

    /** 发送消息之前 */
    void onSendTxtPre(HistoryChatItemBean itemBean);

    void onSendTxtSuccess(String toUserId, String fromUserId, HistoryChatItemBean itemBean);

    void onSendImgSuccess(
            String toUserId, String fromUserId, HistoryChatItemBean itemBean, String imageUrl);

    void onSendTxtFail(HistoryChatItemBean itemBean);

    void onSendFeePre(HistoryChatItemBean itemBean);

    void onSendFeeSuccess(String toUserId, String fromUserId, HistoryChatItemBean itemBean);

    void onSendFeeFail(HistoryChatItemBean itemBean);

    // ===Desc:=================================================================

    void onSyncMessageSuccess();

    void onSyncMessageFail(String errorMsg);

    // ===Desc:环信消息监听相关=================================================================

    void onTextReceived(List<HistoryChatItemMultiItem> list);

    void onMessageRead(List<HistoryChatItemMultiItem> list);

    // ===Desc:================================================================================

    void onGetReplyContentSuccess(List<CommonWordsListBean.DataBean> list);

    void onGetWordTypesFail();

    void onGetWordTypesSuccess(List<ClassificationOfCommonWordsBean.ListBean> list);

    // ===Desc:=================================================================
    void onGetQiNiuToken(GetQiniuTokenBean tokenBean);
}

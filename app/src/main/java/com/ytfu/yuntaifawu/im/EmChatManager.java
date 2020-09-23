package com.ytfu.yuntaifawu.im;

import android.util.Log;

import androidx.annotation.Nullable;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import org.jetbrains.annotations.NotNull;

/** */
public class EmChatManager {

    private static EmChatManager manager;

    public static EmChatManager getInstance() {
        if (null == manager) {
            synchronized (EMChatManager.class) {
                if (null == manager) {
                    manager = new EmChatManager();
                }
            }
        }
        return manager;
    }

    // ===Desc:=================================================================

    public void login(String userId, Runnable errorCallback) {
        EMClient.getInstance()
                .login(
                        userId,
                        "123456",
                        new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                EMClient.getInstance().chatManager().loadAllConversations();
                            }

                            @Override
                            public void onError(int i, String s) {
                                Log.e("onError", "-------" + s);
                                errorCallback.run();
                            }

                            @Override
                            public void onProgress(int i, String s) {
                                Log.e("onProgress", "-------" + s);
                            }
                        });
    }

    /** 获取回话 */
    public EMConversation getConversationById(String conversationId) {
        return EMClient.getInstance().chatManager().getConversation(conversationId);
    }

    /** 删除本地回话 */
    public boolean deleteConversation(String conversationId, boolean keep) {
        // 删除和某个user会话，如果需要保留聊天记录，传false
        return EMClient.getInstance().chatManager().deleteConversation(conversationId, keep);
    }

    /** 删除绘画中对应的message */
    public void deleteMessage(String conversationId, String messageId) {
        // 删除当前会话的某条聊天记录
        EMConversation conversation =
                EMClient.getInstance().chatManager().getConversation(conversationId);
        conversation.removeMessage(messageId);
    }
    // ===Desc:发送文本=================================================================
    public EMMessage createTxtMessage(String toUserId, String content) {
        return EMMessage.createTxtSendMessage(content, toUserId);
    }
    // ===Desc:发送图片=================================================================
    public EMMessage createImgMessage(String imageUri, Boolean aBoolean, String toUserId) {
        return EMMessage.createImageSendMessage(imageUri, aBoolean, toUserId);
    }

    public void sendTxt(EMMessage message) {}

    /** 发送消息 */
    public void sendTxt(
            @NotNull String text, @NotNull String username, @Nullable OnMessageStatusCallback cb) {
        // 创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(text, username);
        message.setMessageStatusCallback(
                new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        if (null != cb) {
                            cb.onSuccess();
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        if (null != cb) {
                            cb.onError(i, s);
                        }
                    }

                    @Override
                    public void onProgress(int i, String s) {}
                });
        // 发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /** 获取未读消息数量 */
    public int getAllUnreadCount() {
        return EMClient.getInstance().chatManager().getUnreadMessageCount();
    }

    // ===Desc:=================================================================

    /** 注册接收消息的坚挺 */
    public void registerMessageListener(EMMessageListener listener) {
        EMClient.getInstance().chatManager().addMessageListener(listener);
    }

    /** 反注册消息接收 */
    public void unRegisterMessageListener(EMMessageListener listener) {
        EMClient.getInstance().chatManager().removeMessageListener(listener);
    }
}

package com.ytfu.yuntaifawu.receiver;

import android.content.Context;

import com.orhanobut.logger.Logger;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * 极光推送接受者
 */
public class JPushReceiver extends JPushMessageReceiver {

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onTagOperatorResult(context, jPushMessage);
        Logger.e("<<<<<<<<<操作Tag回调>>>>>>>>>" + jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        Logger.e("<<<<<<<<<操作Alias回调>>>>>>>>>" + jPushMessage);
    }

    @Override
    public void onConnected(Context context, boolean b) {
        super.onConnected(context, b);
        Logger.e("<<<<<<<<<onConnected>>>>>>>>>" + b);
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        Logger.e("<<<<<<<<<onNotifyMessageArrived>>>>>>>>>" + notificationMessage);
    }
}

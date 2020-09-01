package com.ytfu.yuntaifawu.utils;

import android.text.TextUtils;

import com.ytfu.yuntaifawu.app.App;

public class ChannelUtils {

    /** 获取ShareInstall携带的参数 */
    public static String getShareInstallChannel() {
        String wakeUpChannel = SpUtil.getString(App.getContext(), "wake_up_channel", "");
        if (!TextUtils.isEmpty(wakeUpChannel)) {
            return wakeUpChannel;
        }
        return SpUtil.getString(App.getContext(), "install_channel", "");
    }
}

package com.ytfu.yuntaifawu.utils;

import android.content.Context;

import com.ytfu.yuntaifawu.BuildConfig;
import com.ytfu.yuntaifawu.app.AppConstant;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

public class JPushUtils {

    /** 获取律师相关的tag */
    public static Set<String> getLawyerTags() {
        Set<String> tags = new LinkedHashSet<>();
        String mode = BuildConfig.IS_DEBUG ? "Development" : "Release";
        tags.add(mode);
        tags.add("Lawyer");
        return tags;
    }

    /** 获取用户相关的tag */
    public static Set<String> getUserTags() {
        Set<String> tags = new LinkedHashSet<>();
        String mode = BuildConfig.IS_DEBUG ? "Development" : "Release";
        tags.add(mode);
        tags.add("user");
        return tags;
    }

    public static void setAliasAndTag(Context context) {
        String type = SpUtil.getString(context, AppConstant.SHENFEN, "1");
        Set<String> tags;
        if ("2".equals(type)) {
            // 律师
            tags = JPushUtils.getLawyerTags();
        } else {
            // 用户
            tags = JPushUtils.getUserTags();
        }
        JPushInterface.setTags(context, 100, tags);
        String loginUserId = SpUtil.getString(context, AppConstant.UID, "noLoginUser");
        JPushInterface.setAlias(context, 200, "user_" + loginUserId);
    }
}

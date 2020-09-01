package com.ytfu.yuntaifawu.utils;

import android.content.Context;

import com.github.lee.core.utils.CacheUtils;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * @author gxy
 * @description SharedPreferences工具类
 */
public class SpUtil {

    private static String CONFIG = "ytfu_sp";

    public static String getConfigName() {
        return CONFIG;
    }

    /** 存储boolean类型的属性 */
    public static void putBoolean(Context context, String key, boolean value) {
        CacheUtils.Companion.getInstance(context, context.getPackageName()).putBoolean(key, value);
        //        SharedPreferences sp = App.getContext().getSharedPreferences(CONFIG, 0);
        //        sp.edit().putBoolean(key, value).apply();
    }

    /** 取得boolean类型的属性值 */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return CacheUtils.Companion.getInstance(context, context.getPackageName())
                .getBoolean(key, defValue);
        //        SharedPreferences sp = App.getContext().getSharedPreferences(CONFIG, 0);
        //        return sp.getBoolean(key, defValue);
    }

    /** 存储String类型的属性 */
    public static void putString(Context context, String key, String value) {
        CacheUtils.Companion.getInstance(context, context.getPackageName()).putString(key, value);
        //        SharedPreferences sp = App.getContext().getSharedPreferences(CONFIG, 0);
        //        sp.edit().putString(key, value).apply();
    }

    /** 取得String类型的属性值 */
    public static String getString(Context context, String key, String defValue) {
        return CacheUtils.Companion.getInstance(context, context.getPackageName())
                .getString(key, defValue);
        //        SharedPreferences sp = App.getContext().getSharedPreferences(CONFIG, 0);
        //        return sp.getString(key, defValue);
    }

    /** 存储int类型的属性 */
    public static void putInteger(Context context, String key, int value) {
        CacheUtils.Companion.getInstance(context, context.getPackageName()).putInt(key, value);
        //        SharedPreferences sp = App.getContext().getSharedPreferences(CONFIG, 0);
        //        sp.edit().putInt(key, value).apply();
    }

    /** 取得int类型的属性值 */
    public static int getInteger(Context context, String key, int defValue) {
        return CacheUtils.Companion.getInstance(context, context.getPackageName())
                .getInt(key, defValue);
        //        SharedPreferences sp = App.getContext().getSharedPreferences(CONFIG, 0);
        //        return sp.getInt(key, defValue);
    }

    /** 存储float类型的属性 */
    public static void putFloat(Context context, String key, float value) {
        CacheUtils.Companion.getInstance(context, context.getPackageName()).putFloat(key, value);
        //        SharedPreferences sp = App.getContext().getSharedPreferences(CONFIG, 0);
        //        sp.edit().putFloat(key, value).apply();
    }

    /** 取得float类型的属性 */
    public static float getFloate(Context context, String key, float defValue) {
        return CacheUtils.Companion.getInstance(context, context.getPackageName())
                .getFloat(key, defValue);
        //        SharedPreferences sp = App.getContext().getSharedPreferences(CONFIG, 0);
        //        return sp.getFloat(key, defValue);
    }

    /** 删除指定key的数据 */
    public static void removeSpKey(Context context, String key) {
        CacheUtils.Companion.getInstance(context, context.getPackageName()).remove(key);
        //        SharedPreferences sp = App.getContext().getSharedPreferences(CONFIG, 0);
        //        sp.edit().remove(key).apply();
    }

    /** 清除所有数据 */
    public static void clearSp(Context context) {
        CacheUtils.Companion.getInstance(context, context.getPackageName()).clean();

        //        SharedPreferences sp = App.getContext().getSharedPreferences(CONFIG,
        // Context.MODE_PRIVATE);
        //        sp.edit().clear().apply();
        //        File file =
        //                new File(
        //                        "/data/data/"
        //                                + App.getContext().getPackageName().toString()
        //                                + "/shared_prefs",
        //                        CONFIG);
        //        if (file.exists()) {
        //            file.delete();
        //            ToastUtil.showCenterToast("删除成功");
        //        }
    }

    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                Logger.e(item.getAbsolutePath().toString() + " exists=" + item.exists());
                item.delete();
                Logger.e(item.getAbsolutePath().toString() + " exists=" + item.exists());
            }
        }
    }
}

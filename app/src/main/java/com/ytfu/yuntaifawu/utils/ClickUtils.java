package com.ytfu.yuntaifawu.utils;
/**
*
*  @Auther  gxy
*
*  @Date    2020/7/28
*
*  @Des  按钮仿暴击
*
*/
public class ClickUtils  {

    // 两次点击按钮之间的点击间隔
    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;

        return flag;
    }
}
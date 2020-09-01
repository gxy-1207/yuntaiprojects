package com.ytfu.yuntaifawu.mvp.view

import com.core.ui.mvp.BasicMVPView
import com.ytfu.yuntaifawu.ui.updatapk.UpDateApkBean

interface UserHomeView : BasicMVPView {
    /**更新主页面底部小红点显示*/
    fun setRedPoint(count: Int) {}

    /**用户认证律师成功*/
    fun onUserAuthSuccess() {}

    /**检测更新网络回调*/
    fun onCheckUpdate(data: UpDateApkBean) {}


}
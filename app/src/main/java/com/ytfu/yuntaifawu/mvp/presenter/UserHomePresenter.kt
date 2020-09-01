package com.ytfu.yuntaifawu.mvp.presenter

import com.core.ui.mvp.BasicMVPPresenter
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMMessage
import com.ytfu.yuntaifawu.apis.CommonApi
import com.ytfu.yuntaifawu.im.EmChatManager
import com.ytfu.yuntaifawu.mvp.view.UserHomeView
import com.ytfu.yuntaifawu.utils.LoginHelper

/**用户端端主页面*/
class UserHomePresenter : BasicMVPPresenter<UserHomeView>(), EMMessageListener {


    init {
        //监听环信消息接受，更新未读消息小圆点
        EmChatManager.getInstance().registerMessageListener(this)
    }

    //===Desc:================================================================================

    /** 检测律师是否认真通过*/
    fun checkAttorneyCertified() {
        val loginUserId = LoginHelper.getInstance().loginUserId
        requestRemote(
                run = { createService(CommonApi::class.java).userAuthInfo(loginUserId) },
                success = {
                    val status = it.status
                    if (status == 1) {
                        //显示重新登录弹窗
                        getView().onUserAuthSuccess()
                    }
                }
        )
    }

    /**检测版本升级*/
    fun checkUpdate() {
        requestRemote(
                run = { createService(CommonApi::class.java).checkUpdate() },
                success = {
                    val status = it.status
                    if (status == 1) {
                        //显示重新登录弹窗
                        getView().onCheckUpdate(it)
                    }
                }
        )
        //        HttpUtil.getInstance()
        //                .getApiService()
        //                .checkUpdate()
        //                .subscribeOn(Schedulers.io())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .as(RxLifecycleUtil.bindLifecycle(this))
        //                .subscribe(
        //                        new BaseRxObserver < UpDateApkBean >() {
        //                            @Override
        //                            public void onNextImpl(UpDateApkBean updateBean) {
        //                                if (null != updateBean) {
        //                                    localVersionCode = ApkUtil.getVersionCode();
        //                                    int serverVersionCode =
        //                                    Integer.parseInt(
        //                                            null != updateBean.getCode()
        //                                            ? updateBean . getCode ()
        //                                    : "0");
        //                                    allowCode =
        //                                            Integer.parseInt(
        //                                                    null != updateBean.getAllow_code()
        //                                                    ? updateBean . getAllow_code ()
        //                                    : "0");
        //
        //                                    // 当前版本低于服务器最低版本，强制更新
        //                                    if (localVersionCode < allowCode) {
        //                                        force = true;
        //                                    }
        //
        //                                    // 有新版本提示更新
        //                                    if (localVersionCode < serverVersionCode) {
        //                                        Message msg = new Message();
        //                                        msg.what = 0;
        //                                        msg.obj = updateBean;
        //                                        myHandler.sendMessage(msg);
        //                                    }
        //                                }
        //                            }
        //
        //                            @Override
        //                            public void onErrorImpl(String errorMessage) {
        //                                Logger.e(errorMessage);
        //                            }
        //                        });
    }

    fun destroy() {
        //监听环信消息接受，更新未读消息小圆点
        EmChatManager.getInstance().unRegisterMessageListener(this)
    }

    //===Desc:环信新消息接受监听================================================================================
    override fun onMessageReceived(p0: MutableList<EMMessage>?) {
        getView().runMainThread {
            getView().setRedPoint(EmChatManager.getInstance().allUnreadCount)
        }
    }

    override fun onCmdMessageReceived(p0: MutableList<EMMessage>?) {
    }

    override fun onMessageRead(p0: MutableList<EMMessage>?) {
    }

    override fun onMessageDelivered(p0: MutableList<EMMessage>?) {
    }

    override fun onMessageRecalled(p0: MutableList<EMMessage>?) {
    }

    override fun onMessageChanged(p0: EMMessage?, p1: Any?) {
    }

}
package com.ytfu.yuntaifawu.ui.mseeage.presenter

import com.core.ui.mvp.BasicMVPRefreshPresenter
import com.core.ui.mvp.view.BasicMVPRefreshView
import com.ytfu.yuntaifawu.apis.HttpUtil
import com.ytfu.yuntaifawu.apis.LawyerAnswerApi
import com.ytfu.yuntaifawu.ui.mseeage.bean.LawyerAnswerData

class LawyerAnswerPresenter : BasicMVPRefreshPresenter<BasicMVPRefreshView<LawyerAnswerData>>() {
    // ===Desc:获取回答列表=================================================================
    fun setLawyerAmswer(isLoadMor: Boolean, lid: String, p: Int) {
        var hasMore = true
        requestRemote(
                run = {
                    HttpUtil.getInstance().getService(LawyerAnswerApi::class.java).getLawyerAnswer(lid, p)
                },
                success = {
                    if (it.code != 200) {
                        resetPageStatus(isLoadMor)
                        if (it.data.isNullOrEmpty()) {
                            hasMore = false
                        }
                        return@requestRemote
                    }
                    if (it.data.isEmpty()) {
                        resetPageStatus(isLoadMor)
                        return@requestRemote
                    }
                    if (isLoadMor) {
                        if (it.data.isNullOrEmpty()) {
                            hasMore = false
                        }
                        getView().addData(it.data)
                    } else {
                        getView().setNewData(it.data)
                    }
                },
                finish = {
                    getView().hideLoading()
                    stopRefreshOrLoadMore(isLoadMor, hasMore)
                }
        )
    }


}
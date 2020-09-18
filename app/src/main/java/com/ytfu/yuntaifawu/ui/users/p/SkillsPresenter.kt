package com.ytfu.yuntaifawu.ui.users.p

import com.core.ui.mvp.BasicMVPRefreshPresenter
import com.core.ui.mvp.view.BasicMVPRefreshView
import com.ytfu.yuntaifawu.apis.HttpUtil
import com.ytfu.yuntaifawu.apis.SkillsApi
import com.ytfu.yuntaifawu.ui.users.bean.DataSkills

class SkillsPresenter : BasicMVPRefreshPresenter<BasicMVPRefreshView<DataSkills>>() {

    fun setSkillsList(isLoadMore: Boolean, page: Int, uid: String) {
        var hasMore = true
        requestRemote(
                run = {
                    HttpUtil.getInstance().getService(SkillsApi::class.java).getSkillsList(page, uid)
                },
                success = {
                    if (it.code != 200) {
                        resetPageStatus(isLoadMore)
                        if (it.data.isNullOrEmpty()) {
                            hasMore = false
                        }
                        return@requestRemote
                    }
                    if (it.data.isNullOrEmpty()) {
                        resetPageStatus(isLoadMore)
                        return@requestRemote
                    }
                    if (isLoadMore) {
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
                    stopRefreshOrLoadMore(isLoadMore, hasMore)
                })
    }
}
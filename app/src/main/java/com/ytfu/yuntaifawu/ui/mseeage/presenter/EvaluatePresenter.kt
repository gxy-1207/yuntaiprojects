package com.ytfu.yuntaifawu.ui.mseeage.presenter

import com.core.ui.mvp.BasicMVPRefreshPresenter
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.apis.EvaluateService
import com.ytfu.yuntaifawu.apis.HttpUtil
import com.ytfu.yuntaifawu.ui.mseeage.bean.Shuju
import com.ytfu.yuntaifawu.ui.mseeage.view.EvaluateView

class EvaluatePresenter : BasicMVPRefreshPresenter<EvaluateView>() {

    // ===Desc:评价列表分类=================================================================
    fun setEvaluate(lid: String) {
        requestRemote(
                run = { HttpUtil.getInstance().getService(EvaluateService::class.java).getEvaluateClass(lid) },
                success = {
                    if (it.status != 1) {
                        return@requestRemote
                    }
                    getView().onEvaluateClass(it)
                },
                error = {
                    getView().onEvaluateError(it.toString())
                    it.printStackTrace()
                }
        )
    }

    // ===Desc:评价列表=================================================================
    fun EvaluateList(isLoadMore: Boolean, lid: String, pingjia: String, page: Int) {
        var hasMore = true

        requestRemote(
                run = {
                    HttpUtil.getInstance().getService(EvaluateService::class.java).getEvaluateList(lid, pingjia, page)
                },
                success = {
                    if (it.code != 200) {
                        if (!isLoadMore) {
                            getView().setRecyclerViewEmptyView(R.layout.layout_empty)
                        } else {
                            hasMore = false
                        }
                        return@requestRemote
                    }
                    if (it.shuju.isEmpty()) {
                        if (!isLoadMore) {
                            getView().setRecyclerViewEmptyView(R.layout.layout_empty)
                        } else {
                            hasMore = false
                        }
                        return@requestRemote
                    }
                    val data = mutableListOf<Shuju>()
                    data.addAll(it.shuju)
                    if (isLoadMore) {
                        getView().addData(data)
                    } else {
                        getView().setNewData(data)
                    }
                },
                finish = {
                    getView().hideLoading()
                    if (isLoadMore) {
                        if (hasMore) {
                            getView().loadMoreComplete()
                        } else {
                            getView().loadMoreEnd()
                        }
                    } else {
                        getView().stopRefresh()
                    }
                }
        )
    }
}
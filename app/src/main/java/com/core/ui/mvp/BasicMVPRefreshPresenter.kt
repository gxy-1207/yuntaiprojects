package com.core.ui.mvp

import com.core.ui.mvp.view.BasicMVPRefreshView
import com.ytfu.yuntaifawu.R

open class BasicMVPRefreshPresenter<V : BasicMVPRefreshView<*>> : BasicMVPPresenter<V>() {
    fun refresh() {
        getView().onRefresh()
    }

    fun loadMore() {
        getView().onLoadMore()
    }

    //==============
    /***/
    protected fun resetPageStatus(isLoadMor: Boolean) {
        if (!isLoadMor) {
            getView().setRecyclerViewEmptyView(R.layout.layout_empty)
        } else {
            getView().resetCurrentPage(isLoadMor, 1)
            getView().loadMoreEnd(false)
        }
    }

    /**停止刷新或者加載更多*/
    protected fun stopRefreshOrLoadMore(isLoadMor: Boolean, hasMore: Boolean) {
        if (isLoadMor) {
            if (hasMore) {
                getView().loadMoreComplete()
            } else {
                getView().loadMoreEnd()
            }
        } else {
            getView().stopRefresh()
        }
    }

}
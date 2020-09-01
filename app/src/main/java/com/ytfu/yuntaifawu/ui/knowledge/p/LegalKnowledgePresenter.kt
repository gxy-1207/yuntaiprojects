package com.ytfu.yuntaifawu.ui.knowledge.p

import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.apis.LegalKnoeledgeService
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter
import com.ytfu.yuntaifawu.base.BaseRefreshView
import com.ytfu.yuntaifawu.helper.BaseRxObserver
import com.ytfu.yuntaifawu.ui.knowledge.bean.LegalKnowledgeBean
import com.ytfu.yuntaifawu.ui.knowledge.bean.LegalKnowledgeItemBean

class LegalKnowledgePresenter : BaseRefreshPresenter<BaseRefreshView<LegalKnowledgeItemBean>>() {
    fun setLegalKmowedge() {
        val ob = createService(LegalKnoeledgeService::class.java).getLegalKnoeledge()
        requestRemote(ob, object : BaseRxObserver<LegalKnowledgeBean>() {
            override fun onCompleteImpl() {
                super.onCompleteImpl()
                view.stopRefresh()
            }

            override fun onNextImpl(data: LegalKnowledgeBean?) {
                if (data == null) {
                    view.setEmptyView(R.layout.layout_empty)
                    return
                }
                if (data.status != 200) {
                    view.setEmptyView(R.layout.layout_empty)
                    return
                }
                if (data.list.isEmpty()) {
                    view.setEmptyView(R.layout.layout_empty)
                    return
                }
                view.setNewData(data.list)
            }

            override fun onErrorImpl(errorMessage: String?) {
                view.setEmptyView(R.layout.layout_empty)
                view.stopRefresh()
            }

        })
    }
}
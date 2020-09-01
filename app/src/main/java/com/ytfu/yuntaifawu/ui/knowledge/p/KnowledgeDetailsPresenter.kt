package com.ytfu.yuntaifawu.ui.knowledge.p

import com.orhanobut.logger.Logger
import com.ytfu.yuntaifawu.apis.LegalKnoeledgeService
import com.ytfu.yuntaifawu.base.BasePresenter
import com.ytfu.yuntaifawu.helper.BaseRxObserver
import com.ytfu.yuntaifawu.ui.knowledge.bean.KnowledgeDetailsBean
import com.ytfu.yuntaifawu.ui.knowledge.v.KnowledgeDetailsView


class KnowledgeDetailsPresenter : BasePresenter<KnowledgeDetailsView>() {
    fun setKnowledgeDetails(id: String) {
        val ob = createService(LegalKnoeledgeService::class.java).getKnowledgeDetails(id)
        requestRemote(ob, object : BaseRxObserver<KnowledgeDetailsBean>() {
            override fun onNextImpl(data: KnowledgeDetailsBean?) {
                if (data == null) {
                    view.showEmpty()
                    return
                }
                if (data.status != 200) {
                    view.showEmpty()
                    return
                }
                view.onKnoeledgeSucess(data)
            }

            override fun onErrorImpl(errorMessage: String?) {
                view.showEmpty()
                Logger.e("setKnowledgeDetails", errorMessage)
            }

        })
    }
}
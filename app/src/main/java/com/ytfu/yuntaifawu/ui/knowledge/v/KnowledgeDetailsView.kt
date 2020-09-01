package com.ytfu.yuntaifawu.ui.knowledge.v

import com.ytfu.yuntaifawu.base.BaseView
import com.ytfu.yuntaifawu.ui.knowledge.bean.KnowledgeDetailsBean

interface KnowledgeDetailsView :BaseView{
    fun onKnoeledgeSucess( knowledgeDetailsBean: KnowledgeDetailsBean)
    fun onKnoeledgeFlied()
}
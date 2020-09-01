package com.ytfu.yuntaifawu.ui.knowledge.adaper

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter
import com.ytfu.yuntaifawu.ui.knowledge.bean.LegalKnowledgeItemBean
import java.util.*

class LegalKnowledgeAdaper: QuickAdapter<LegalKnowledgeItemBean>(R.layout.legalknowledge_item) {
    override fun convert(holder: BaseViewHolder, item: LegalKnowledgeItemBean?) {
        super.convert(holder, item)
        holder.setText(R.id.tv_content,item?.title)
    }
}
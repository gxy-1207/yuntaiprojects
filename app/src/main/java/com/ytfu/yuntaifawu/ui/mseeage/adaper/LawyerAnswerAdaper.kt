package com.ytfu.yuntaifawu.ui.mseeage.adaper

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter
import com.ytfu.yuntaifawu.ui.mseeage.bean.LawyerAnswerData
import com.ytfu.yuntaifawu.utils.GlideManager

class LawyerAnswerAdaper : QuickAdapter<LawyerAnswerData>(R.layout.tahuida_recycle_item) {

    override fun convert(holder: BaseViewHolder, item: LawyerAnswerData?) {
        super.convert(holder, item)
        holder.setText(R.id.tv_huida_name, item?.uname)
        holder.setText(R.id.tv_huida_time, item?.date)
        holder.setText(R.id.tv_content, item?.content)
        holder.setText(R.id.tv_leixing, item?.cid)
        holder.setText(R.id.tv_num, item?.sum)
        GlideManager.loadImageByUrl(context, item?.picurl, holder.getView(R.id.iv_ta_icon))
    }
}
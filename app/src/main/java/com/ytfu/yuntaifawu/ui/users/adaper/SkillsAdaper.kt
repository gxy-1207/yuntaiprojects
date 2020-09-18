package com.ytfu.yuntaifawu.ui.users.adaper

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter
import com.ytfu.yuntaifawu.ui.users.bean.DataSkills

class SkillsAdaper : QuickAdapter<DataSkills>(R.layout.skills_list_item) {

    override fun convert(holder: BaseViewHolder, item: DataSkills?) {
        super.convert(holder, item)
        holder.setText(R.id.tv_skills_title, item?.title)
        holder.setText(R.id.tv_skill_data, item?.addtime)
        holder.setGone(R.id.iv_red_dot, item?.randType != 1)
    }

}
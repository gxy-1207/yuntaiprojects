package com.ytfu.yuntaifawu.ui.complaint.adaper

import android.graphics.Color
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter
import com.ytfu.yuntaifawu.ui.complaint.bean.ComplaintBean
import com.ytfu.yuntaifawu.utils.LoginHelper

class ComplaintListAdapter : QuickAdapter<ComplaintBean>(R.layout.item_complaint_list) {

    init {

        addChildClickViewIds(R.id.tv_proxy_word, R.id.tv_list_of_evidence)
    }

    override fun convert(holder: BaseViewHolder, item: ComplaintBean?) {
        if (null == item) {
            return
        }
        //类别
        holder.setText(R.id.tv_item_category, "类别：" + item.typeNmae)
        //状态 0 律师待修改 1是律师已修改，能下载了
        val showText = when (item.newStatus) {
            0 -> {
                holder.setTextColor(R.id.tv_item_status, Color.parseColor("#EC9B0D"))
                if (LoginHelper.getInstance().isUserLogin) {
                    "待律师查看"
                } else {
                    "待查看"
                }
            }
            1 -> {
                holder.setTextColor(R.id.tv_item_status, Color.parseColor("#50C023"))
                "已完成"
            }
            else -> {
                if (LoginHelper.getInstance().isUserLogin) {
                    "待律师查看"
                } else {
                    "待查看"
                }
            }
        }
        holder.setText(R.id.tv_item_status, showText)
        //原告 被告
        holder.setText(R.id.tv_item_plaintiff_name, item.yuangaoName)
        holder.setText(R.id.tv_item_defendant_name, item.beigaoName)

        //holder.setGone(R.id.tv_item_content, TextUtils.isEmpty(item.qingqiuList) || "null" == item.qingqiuList)
        holder.setText(R.id.tv_item_content, item.qingqiuList)

        //动态显示用餐用户端的证据清单和代理词按钮
        if (LoginHelper.getInstance().isUserLogin) {
            if ("0" == item.qingdanType || item.qingdanType.isEmpty()) {
                holder.setGone(R.id.tv_list_of_evidence, true)
            } else {
                holder.setGone(R.id.tv_list_of_evidence, false)
            }

            if ("0" == item.dailiciType || item.dailiciType.isEmpty()) {
                holder.setGone(R.id.tv_proxy_word, true)
            } else {
                holder.setGone(R.id.tv_proxy_word, false)
            }
            if (("0" == item.qingdanType || item.qingdanType.isEmpty()) && ("0" == item.dailiciType || item.dailiciType.isEmpty())) {
                holder.setGone(R.id.ll_bottom, true)
            } else {
                holder.setGone(R.id.ll_bottom, false)
            }

        }


    }

}
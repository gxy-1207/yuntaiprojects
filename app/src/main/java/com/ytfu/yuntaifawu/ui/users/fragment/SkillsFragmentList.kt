package com.ytfu.yuntaifawu.ui.users.fragment

import android.graphics.Color
import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.core.ui.fragment.BaseEmptyRefreshFragment
import com.core.ui.mvp.view.Autowrite
import com.lxj.xpopup.util.XPopupUtils
import com.ytfu.yuntaifawu.ui.users.act.AnnouncementDetailsActivity
import com.ytfu.yuntaifawu.ui.users.adaper.SkillsAdaper
import com.ytfu.yuntaifawu.ui.users.bean.DataSkills
import com.ytfu.yuntaifawu.ui.users.p.SkillsPresenter
import com.ytfu.yuntaifawu.utils.ItemDecoration
import com.ytfu.yuntaifawu.utils.LoginHelper

/**
 * @Author:         gxy
 * @CreateDate:     2020/9/17
 * @Description:     使用技巧
 */
class SkillsFragmentList : BaseEmptyRefreshFragment<DataSkills>() {
    @Autowrite
    private lateinit var skillsPresenter: SkillsPresenter

    companion object {
        fun newInstance(): SkillsFragmentList {
            val args = Bundle()

            val fragment = SkillsFragmentList()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        skillsPresenter.refresh()
    }

    override fun onSetViewListener() {
        super.onSetViewListener()
        getAdapter().setOnItemClickListener { _, _, position ->
            val jumpurl = getAdapter().data[position].jumpurl
            AnnouncementDetailsActivity.start(context, "使用技巧", jumpurl)
        }
    }

    override fun onSetViewData() {
        super.onSetViewData()
        val color = Color.parseColor("#f2f2f2")
        val lineHeight = XPopupUtils.dp2px(mContext, 5f)
        addItemDecoration(ItemDecoration.createVertical(color, lineHeight, 0))
    }


    override fun onRefreshOrLoadMore(isLoadMore: Boolean) {
        super.onRefreshOrLoadMore(isLoadMore)
        if (isLoadMore) {
            currentPage++
        } else {
            currentPage = 1
        }
        val loginUserId = LoginHelper.getInstance().loginUserId
        skillsPresenter.setSkillsList(isLoadMore, currentPage, loginUserId)
    }

    override fun onCreateAdapter(): BaseQuickAdapter<DataSkills, BaseViewHolder> {
        return SkillsAdaper()
    }
}
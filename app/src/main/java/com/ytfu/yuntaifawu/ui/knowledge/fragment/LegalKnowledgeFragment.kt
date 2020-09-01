package com.ytfu.yuntaifawu.ui.knowledge.fragment

import android.graphics.Color
import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.lee.annotation.InjectPresenter
import com.lxj.xpopup.util.XPopupUtils
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment
import com.ytfu.yuntaifawu.base.BaseRefreshView
import com.ytfu.yuntaifawu.ui.knowledge.act.KnowledgeDetailsActivity
import com.ytfu.yuntaifawu.ui.knowledge.adaper.LegalKnowledgeAdaper
import com.ytfu.yuntaifawu.ui.knowledge.bean.LegalKnowledgeItemBean
import com.ytfu.yuntaifawu.ui.knowledge.p.LegalKnowledgePresenter
import com.ytfu.yuntaifawu.utils.ItemDecoration

/**
 *
 *  @Auther  gxy
 *
 *  @Date    2020/8/11
 *
 *  @Des 法律知识
 *
 */
@InjectPresenter(LegalKnowledgePresenter::class)
class LegalKnowledgeFragment : BaseRecyclerViewFragment<LegalKnowledgeItemBean,
        BaseRefreshView<LegalKnowledgeItemBean>, LegalKnowledgePresenter>() {

    companion object {
        fun newInstance(): LegalKnowledgeFragment {
            val args = Bundle()

            val fragment = LegalKnowledgeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initData() {
        super.initData()
        enableLoadMore(false)
        enableRefresh(true)
       val color = Color.parseColor("#f5f5f5")
        val height = XPopupUtils.dp2px(context, 0.5f)
        addItemDecoration(ItemDecoration.createVertical(color, height, 0))
        adapter.setOnItemClickListener(OnItemClickListener { _, _, position ->
            KnowledgeDetailsActivity.start(mContext,adapter.data[position].id)
        })
    }


    override fun createAdapter(): BaseQuickAdapter<LegalKnowledgeItemBean, BaseViewHolder> {
        return LegalKnowledgeAdaper()
    }

    override fun onLoadMoreOrRefresh(isLoadMore: Boolean) {
        presenter.setLegalKmowedge()
    }
}
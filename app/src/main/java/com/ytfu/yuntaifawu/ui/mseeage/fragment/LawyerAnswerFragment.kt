package com.ytfu.yuntaifawu.ui.mseeage.fragment

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.core.ui.ext.getArgumentsValue
import com.core.ui.fragment.BaseEmptyRefreshFragment
import com.core.ui.mvp.view.Autowrite
import com.github.lee.annotation.InjectLayout
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.ui.mseeage.activity.HuiDaDetailsActivity
import com.ytfu.yuntaifawu.ui.mseeage.adaper.LawyerAnswerAdaper
import com.ytfu.yuntaifawu.ui.mseeage.bean.LawyerAnswerData
import com.ytfu.yuntaifawu.ui.mseeage.presenter.LawyerAnswerPresenter

/**
 * @Author:         gxy
 * @CreateDate:     2020/8/27
 * @Description:     律师回答
 */
@InjectLayout(loadingLayoutId = R.layout.tahuida_recycle_loding_item)
class LawyerAnswerFragment : BaseEmptyRefreshFragment<LawyerAnswerData>() {
    @Autowrite
    private lateinit var lawyerAnswerPresenter: LawyerAnswerPresenter

    companion object {
        private const val KEY_LID = "LID"
        fun newInstance(lid: String): LawyerAnswerFragment {
            val args = Bundle()
            args.putString(KEY_LID, lid)
            val fragment = LawyerAnswerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onSetViewListener() {
        super.onSetViewListener()
        getAdapter().setOnItemClickListener { _, _, position ->
            val lid = getAdapter().data[position].lid
            val anId = getAdapter().data[position].id
            HuiDaDetailsActivity.start(mContext, lid, anId)
        }
    }

    override fun onCreateAdapter(): BaseQuickAdapter<LawyerAnswerData, BaseViewHolder> {
        return LawyerAnswerAdaper()
    }

    override fun onRefreshOrLoadMore(isLoadMore: Boolean) {
        super.onRefreshOrLoadMore(isLoadMore)
        val lid = getArgumentsValue(KEY_LID, "")
        if (isLoadMore) {
            currentPage++
        } else {
            currentPage = 1
        }
        lawyerAnswerPresenter.setLawyerAmswer(isLoadMore, lid, currentPage)
    }
}
package com.ytfu.yuntaifawu.ui.complaint.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.lee.annotation.InjectPresenter
import com.lxj.xpopup.util.XPopupUtils
import com.orhanobut.logger.Logger
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment
import com.ytfu.yuntaifawu.ui.complaint.adaper.ComplaintListAdapter
import com.ytfu.yuntaifawu.ui.complaint.bean.ComplaintBean
import com.ytfu.yuntaifawu.ui.complaint.p.ComplaintPresenter
import com.ytfu.yuntaifawu.ui.complaint.v.ComplaintView
import com.ytfu.yuntaifawu.ui.kaitingzhushou.activity.ActivityDaiLiCi
import com.ytfu.yuntaifawu.ui.qisuzhuang.activity.ActivityQszYlXq
import com.ytfu.yuntaifawu.ui.qisuzhuang.activity.ActivityZjqdAddList
import com.ytfu.yuntaifawu.ui.qisuzhuang.activity.ActivityZjqdXq
import com.ytfu.yuntaifawu.utils.ItemDecoration
import com.ytfu.yuntaifawu.utils.LoginHelper

@InjectPresenter(ComplaintPresenter::class)
class ComplaintListFragment :
        BaseRecyclerViewFragment<ComplaintBean, ComplaintView, ComplaintPresenter>(),
        ComplaintView {

    companion object {
        private const val KEY_BOTTOM_HEIGHT = "KEY_BOTTOM_HEIGHT"
        private const val KEY_OWNER = "OWNER"
        private const val KEY_LAWYER = "LAWYER"
        fun newInstance(bottomHeight: Int, ownerId: String, lawyerId: String?): ComplaintListFragment {
            val bundle = Bundle()
            bundle.putInt(KEY_BOTTOM_HEIGHT, bottomHeight)
            bundle.putString(KEY_OWNER, ownerId)
            bundle.putString(KEY_LAWYER, lawyerId)
            val f = ComplaintListFragment()
            f.arguments = bundle
            return f
        }
    }

    //===Desc:================================================================================

    override fun init() {
        super.init()
        if (!LoginHelper.getInstance().isUserLogin) {
            autoRefresh = false
        }
    }

    override fun createAdapter(): BaseQuickAdapter<ComplaintBean, BaseViewHolder> =
            ComplaintListAdapter()


    override fun setViewListener(rootView: View?) {
        super.setViewListener(rootView)
        adapter.setOnItemChildClickListener { _, view, position ->
            Logger.e(adapter.data[position].toString())
            val complaintBean = mAdapter.data[position]
            when (view.id) {
                R.id.tv_list_of_evidence -> {
                    //证据清单详情
                    if (LoginHelper.getInstance().isUserLogin) {
                        //没有证据清单id
                        ActivityZjqdXq.start(mContext, complaintBean.qingdanType, complaintBean)
                    } else {
                        ActivityZjqdAddList.start(mContext, complaintBean)
                    }
                }
                R.id.tv_proxy_word -> {
                    ActivityDaiLiCi.start(mContext, complaintBean.id, complaintBean.lsid, complaintBean.uid)
                }
            }
        }
        adapter.setOnItemClickListener { _, _, position ->
            Logger.e(adapter.data[position].toString())
            val url = adapter.data[position].href
            val showDownloadButton = adapter.data[position].newStatus == 1
            ActivityQszYlXq.start(mContext, url, showDownloadButton)
        }

    }

    override fun initData() {
        super.initData()
        recycleView.setBackgroundColor(Color.parseColor("#F5F5F5"))
        enableLoadMore(false)
        addItemDecoration(ItemDecoration.createVertical(
                Color.parseColor("#F5F5F5"),
                XPopupUtils.dp2px(mContext, 10F), 0))

        val bottomHeight = getArgumentValue(KEY_BOTTOM_HEIGHT, 0)
        val footer = View(mContext)
        val params = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, bottomHeight)
        footer.layoutParams = params
        addFooterView(footer)
    }

    override fun onLoadMoreOrRefresh(isLoadMore: Boolean) {
        val ownerId = getArgumentValue(KEY_OWNER, LoginHelper.getInstance().loginUserId)
        val lawyerId = getArgumentValue(KEY_LAWYER, "")
        presenter.getComplaintList(ownerId, lawyerId, LoginHelper.getInstance().isUserLogin)

    }

    override fun onResume() {
        super.onResume()
        if (!LoginHelper.getInstance().isUserLogin) {
            presenter.refresh()
        }
    }

}
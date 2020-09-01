package com.ytfu.yuntaifawu.ui.audio.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.lee.annotation.InjectPresenter
import com.lxj.xpopup.util.XPopupUtils
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment
import com.ytfu.yuntaifawu.base.BaseRefreshView
import com.ytfu.yuntaifawu.ui.audio.act.AudioDetailActivity
import com.ytfu.yuntaifawu.ui.audio.adapter.AudioListAdapter
import com.ytfu.yuntaifawu.ui.audio.p.AudioListPresenter
import com.ytfu.yuntaifawu.ui.home.bean.AudioListBean


@InjectPresenter(AudioListPresenter::class)
class AudioListFragment : BaseRecyclerViewFragment<AudioListBean.ListBean, BaseRefreshView<AudioListBean.ListBean>, AudioListPresenter>() {

    companion object {
        private const val KEY_CLASS_ID = "CLASS_ID"
        fun newInstance(classId: String): AudioListFragment {
            val args = Bundle()
            args.putString(KEY_CLASS_ID, classId)
            val fragment = AudioListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    //===Desc:================================================================================

    override fun createAdapter(): BaseQuickAdapter<AudioListBean.ListBean, BaseViewHolder> = AudioListAdapter()

    override fun onLoadMoreOrRefresh(isLoadMore: Boolean) {
        if (isLoadMore) {
            currentPage++
        } else {
            currentPage = 1
        }
        presenter.getAudioList(isLoadMore, currentPage, getArgumentString(KEY_CLASS_ID, ""), "")
    }

    override fun setViewListener(rootView: View?) {
        super.setViewListener(rootView)
        adapter.setOnItemClickListener { _, _, position -> AudioDetailActivity.start(mContext, getAdapter().data[position].id) }
    }

    override fun initData() {
        super.initData()
        val v1 = View(mContext)
        v1.setBackgroundColor(Color.parseColor("#F2F2F2"))
        val v2 = View(mContext)
        v2.setBackgroundColor(Color.WHITE)

        val p = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, XPopupUtils.dp2px(mContext, 6F))
        v1.layoutParams = p
        v2.layoutParams = p
        addHeaderView(v1)
        addHeaderView(v2)
    }
    //===Desc:================================================================================


}
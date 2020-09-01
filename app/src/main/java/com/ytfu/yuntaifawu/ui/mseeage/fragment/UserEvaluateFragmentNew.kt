package com.ytfu.yuntaifawu.ui.mseeage.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.core.ui.ext.getArgumentsValue
import com.core.ui.fragment.BaseEmptyRefreshFragment
import com.core.ui.mvp.view.Autowrite
import com.github.lee.annotation.InjectLayout
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.ui.custom.MyRadioGroup
import com.ytfu.yuntaifawu.ui.mseeage.activity.EvaluateDetailsActivity
import com.ytfu.yuntaifawu.ui.mseeage.adaper.EvaluateListAdaper
import com.ytfu.yuntaifawu.ui.mseeage.bean.EvaluateClassBean
import com.ytfu.yuntaifawu.ui.mseeage.bean.Shuju
import com.ytfu.yuntaifawu.ui.mseeage.bean.Type
import com.ytfu.yuntaifawu.ui.mseeage.presenter.EvaluatePresenter
import com.ytfu.yuntaifawu.ui.mseeage.view.EvaluateView

/**
 * @ClassName:      UserEvaluateFragmentNew
 * @Author:         gxy
 * @CreateDate:     2020/8/26
 * @Description:    用户评价
 */
@InjectLayout(loadingLayoutId = R.layout.layout_default_item_skeleton)
class UserEvaluateFragmentNew : BaseEmptyRefreshFragment<Shuju>(), EvaluateView {
    @Autowrite
    private lateinit var evaluatePresenter: EvaluatePresenter

    private var selectType: Type? = null

    companion object {
        private const val KEY_LID = "LID"
        fun newInstance(lid: String): UserEvaluateFragmentNew {
            val args = Bundle()
            args.putString(KEY_LID, lid)
            val fragment = UserEvaluateFragmentNew()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        super.onInitData(savedInstanceState)
        autoRefresh = false
    }

    override fun onSetViewListener() {
        super.onSetViewListener()
        getAdapter().setOnItemClickListener { _, _, position ->
            val lid = getArgumentsValue(KEY_LID, "")
            val id = getAdapter().data[position].id
            val intent = Intent(
                    mContext, EvaluateDetailsActivity::class.java)
            intent.putExtra("lid", lid)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    override fun onSetViewData() {
        super.onSetViewData()
        val lid = getArgumentsValue(KEY_LID, "")
        evaluatePresenter.setEvaluate(lid)
    }

    override fun onCreateAdapter(): BaseQuickAdapter<Shuju, BaseViewHolder> {
        return EvaluateListAdaper()
    }

    override fun onRefreshOrLoadMore(isLoadMore: Boolean) {
        super.onRefreshOrLoadMore(isLoadMore)
        val lid = getArgumentsValue(KEY_LID, "")
        if (isLoadMore) {
            currentPage++
        } else {
            currentPage = 1
        }
        evaluatePresenter.EvaluateList(isLoadMore, lid, selectType?.id.toString(), currentPage)
    }

    // ===Desc:分类成功回调=================================================================
    override fun onEvaluateClass(evaluateClassBean: EvaluateClassBean) {
        hideLoading()
        super.onEvaluateClass(evaluateClassBean)

        val myRadioGroup = MyRadioGroup(mContext)
        val layoutParams = RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.leftMargin = 20
        layoutParams.rightMargin = 20
        layoutParams.topMargin = 15
        layoutParams.bottomMargin = 10
        for ((index, item) in evaluateClassBean.type.withIndex()) {
            val a: Bitmap? = null
            val button = RadioButton(mContext)
            button.id = index
            @SuppressLint("SetTextI18n")
            button.text = item.name + " (" + item.num + ")"
            @Suppress("DEPRECATION")
            button.buttonDrawable = BitmapDrawable(a)
            button.setBackgroundResource(R.drawable.textview_color_selector)
            @Suppress("DEPRECATION") val csl = resources.getColorStateList(R.color.selector_radio_check)
            button.setTextColor(csl)
            myRadioGroup.addView(button, layoutParams)
        }
        myRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectType = evaluateClassBean.type[checkedId]
            evaluatePresenter.refresh()
        }
        val llRoot = LinearLayout(mContext)
        llRoot.orientation = LinearLayout.VERTICAL
        val rgParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        llRoot.addView(myRadioGroup, rgParam)
        val lineView = View(mContext)
        lineView.setBackgroundColor(Color.parseColor("#f2f2f2"))
        val lineParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10)
        lineParam.topMargin = 15
        llRoot.addView(lineView, lineParam)

        val type = evaluateClassBean.type
        if (type.isNotEmpty()) {
            myRadioGroup.check(0)
            selectType = type[0]
            evaluatePresenter.refresh()
        }
        addTopSuspensionView(llRoot, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT))

    }
}
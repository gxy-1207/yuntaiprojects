package com.ytfu.yuntaifawu.ui.complaint.act

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.base.BaseActivity
import com.ytfu.yuntaifawu.base.BasePresenter
import com.ytfu.yuntaifawu.base.BaseView
import com.ytfu.yuntaifawu.ui.complaint.fragment.ComplaintFragment
import com.ytfu.yuntaifawu.utils.LoginHelper
import qiu.niorgai.StatusBarCompat

//@InjectLayout(toolbarLayoutId = R.layout.layout_toolbar_center_title)
class ComplaintClassifyActivity : BaseActivity<BaseView, BasePresenter<BaseView>>() {

    companion object {
        private const val KEY_OWNER = "OWNER"
        private const val KEY_LAWYER = "LAWYER"
        fun start(context: Context, ownerId: String, lawyerId: String?) {
            val bundle = Bundle()
            bundle.putString(KEY_OWNER, ownerId)
            bundle.putString(KEY_LAWYER, lawyerId)
            val starter = Intent(context, ComplaintClassifyActivity::class.java)
            starter.putExtras(bundle)

            context.startActivity(starter)
        }
    }
    //===Desc:================================================================================

    override fun initData() {
        super.initData()
        StatusBarCompat.setStatusBarColor(this, Color.WHITE)
        changeStatusBarTextColor(true)
        setToolbarBackgroud(Color.WHITE)
        setToolbarLeftImage(R.drawable.fanhui_hui) { onBackPressed() }
        setToolbarTextColor(R.id.tv_global_title, Color.parseColor("#333333"))
        setToolbarText(R.id.tv_global_title, R.string.home_complaint)

        val ownerId = getBundleString(KEY_OWNER, LoginHelper.getInstance().loginUserId)
        val lawyerId = getBundleString(KEY_LAWYER, "")
        val fragment = ComplaintFragment.newInstance(true, ownerId, lawyerId)
        showFragment(fragment)
        hideLoading()
    }


}
package com.ytfu.yuntaifawu.ui.complaint.act


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.github.lee.annotation.InjectLayout
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.base.BaseActivity
import com.ytfu.yuntaifawu.base.BasePresenter
import com.ytfu.yuntaifawu.base.BaseView
import com.ytfu.yuntaifawu.ui.complaint.fragment.ComplaintListFragment
import com.ytfu.yuntaifawu.utils.LoginHelper
import qiu.niorgai.StatusBarCompat

/**起诉状列表界面*/
@InjectLayout(R.layout.activity_complaint_list, toolbarLayoutId = R.layout.layout_toolbar_center_title)
class ComplaintListActivity : BaseActivity<BaseView, BasePresenter<BaseView>>() {

    companion object {
        private const val KEY_OWNER = "OWNER"
        private const val KEY_LAWYER = "LAWYER"
        fun starter(context: Context, ownerId: String, lawyerId: String?) {
            val bundle = Bundle()
            bundle.putString(KEY_OWNER, ownerId)
            bundle.putString(KEY_LAWYER, lawyerId)
            val starter = Intent(context, ComplaintListActivity::class.java)
            starter.putExtras(bundle)
            context.startActivity(starter)
        }
    }

    //===Desc:================================================================================


    override fun setViewListener() {
        super.setViewListener()
        //        cv_complaint_list_add.setOnClickListener {
        //            val ownerId = getBundleString(KEY_OWNER, LoginHelper.getInstance().loginUserId)
        //            val lawyerId = getBundleString(KEY_LAWYER, "")
        //            ComplaintClassifyActivity.start(mContext, ownerId, lawyerId)
        //        }
    }

    override fun initData() {
        super.initData()
        StatusBarCompat.setStatusBarColor(this, Color.WHITE)
        changeStatusBarTextColor(true)
        val userLogin = LoginHelper.getInstance().isUserLogin
        val title = if (userLogin) {
            R.string.txt_my_complaint
        } else {
            R.string.txt_his_complaint
        }
        setToolbarText(R.id.tv_global_title, title)
        setToolbarTextColor(R.id.tv_global_title, Color.parseColor("#303030"))
        setToolbarBackgroud(Color.WHITE)
        setToolbarLeftImage(R.drawable.fanhui_hui) { onBackPressed() }

        val ownerId = getBundleString(KEY_OWNER, LoginHelper.getInstance().loginUserId)
        val lawyerId = getBundleString(KEY_LAWYER, "")
        val fragment = ComplaintListFragment.newInstance(0, ownerId, lawyerId)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_complaint_list_content, fragment)
                .commitNowAllowingStateLoss()
    }

}
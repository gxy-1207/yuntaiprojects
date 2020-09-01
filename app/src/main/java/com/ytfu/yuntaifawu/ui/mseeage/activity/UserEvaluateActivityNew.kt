package com.ytfu.yuntaifawu.ui.mseeage.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.core.ui.activity.BaseEmptyActivity
import com.core.ui.ext.getBundleValue
import com.github.lee.annotation.InjectLayout
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.ui.mseeage.fragment.UserEvaluateFragmentNew
import qiu.niorgai.StatusBarCompat

/**
 * @ClassName:      UserEvaluateActivityNew
 * @Author:         gxy
 * @CreateDate:     2020/8/26
 * @Description:    用户评价
 */
@InjectLayout(toolbarLayoutId = R.layout.layout_toolbar_center_title)
class UserEvaluateActivityNew : BaseEmptyActivity() {
    companion object {
        private const val KEY_LID = "LID"
        private const val KEY_TITLE = "TITLE"
        fun start(context: Context, lid: String, title: String) {
            val bundle = Bundle()
            bundle.putString(KEY_LID, lid)
            bundle.putString(KEY_TITLE, title)
            val starter = Intent(context, UserEvaluateActivityNew::class.java)
            starter.putExtras(bundle)
            context.startActivity(starter)
        }
    }

    override fun onSetViewData() {
        super.onSetViewData()
        @Suppress("DEPRECATION")
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.transparent_4c))
        changeStatusBarTextColor(false)
        setToolbarLeftImage(R.drawable.fanhui_bai) { onBackPressed() }
        val title = getBundleValue(KEY_TITLE, "")
        setToolbarText(R.id.tv_global_title, title)
        val lid = getBundleValue(KEY_LID, "")
        val fragment = UserEvaluateFragmentNew.newInstance(lid)
        replaceFragment(fragment)
    }


}
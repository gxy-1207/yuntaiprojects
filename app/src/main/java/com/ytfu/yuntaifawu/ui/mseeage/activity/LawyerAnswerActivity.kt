package com.ytfu.yuntaifawu.ui.mseeage.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.core.ui.activity.BaseEmptyActivity
import com.core.ui.ext.getBundleValue
import com.github.lee.annotation.InjectLayout
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.ui.mseeage.fragment.LawyerAnswerFragment
import extension.color
import qiu.niorgai.StatusBarCompat

/**
 * @ClassName:      LawyerAnswerActivity
 * @Author:         gxy
 * @CreateDate:     2020/8/27
 * @Description:     律师回答列表
 */
@InjectLayout(toolbarLayoutId = R.layout.layout_toolbar_center_title)
class LawyerAnswerActivity : BaseEmptyActivity() {
    companion object {
        private const val KEY_USER_NAME = "USER_NAME"
        private const val KEY_LID = "LID"
        fun start(context: Context, userName: String, lid: String) {
            val bundle = Bundle()
            bundle.putString(KEY_USER_NAME, userName)
            bundle.putString(KEY_LID, lid)
            val starter = Intent(context, LawyerAnswerActivity::class.java)
            starter.putExtras(bundle)
            context.startActivity(starter)
        }
    }

    override fun onSetViewData() {
        super.onSetViewData()
        StatusBarCompat.setStatusBarColor(this, resources.color(R.color.transparent_4c))
        changeStatusBarTextColor(false)
        setToolbarLeftImage(R.drawable.fanhui_bai) { onBackPressed() }
        val userName = getBundleValue(KEY_USER_NAME, "")
        setToolbarText(R.id.tv_global_title, userName)
        val lid = getBundleValue(KEY_LID, "")
        val lawyerAnswerFragment = LawyerAnswerFragment.newInstance(lid)
        replaceFragment(lawyerAnswerFragment)
    }

}
package com.ytfu.yuntaifawu.ui.users.act

import android.content.Context
import android.content.Intent
import com.core.ui.activity.BaseEmptyActivity
import com.github.lee.annotation.InjectLayout
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.ui.users.fragment.SkillsFragmentList
import extension.color
import qiu.niorgai.StatusBarCompat

/**
  * @Author:         gxy
  * @CreateDate:     2020/9/17
  * @Description:     使用技巧列表
 */
@InjectLayout(toolbarLayoutId = R.layout.layout_toolbar_center_title)
class SkillsActivityList:BaseEmptyActivity() {
    companion object{
        fun start(context: Context) {
            val starter = Intent(context, SkillsActivityList::class.java)
            context.startActivity(starter)
        }
    }

    override fun onSetViewData() {
        super.onSetViewData()
        StatusBarCompat.setStatusBarColor(this, resources.color(R.color.transparent_4c))
        changeStatusBarTextColor(false)
        setToolbarLeftImage(R.drawable.fanhui_bai) { onBackPressed() }
        setToolbarText(R.id.tv_global_title,"使用技巧")
        val skillsFragmentList = SkillsFragmentList.newInstance()
        replaceFragment(skillsFragmentList)
    }

}
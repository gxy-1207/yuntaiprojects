package com.ytfu.yuntaifawu.ui.knowledge.act

import android.content.Context
import android.content.Intent
import android.graphics.Color
import com.github.lee.annotation.InjectLayout
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.base.BaseActivity
import com.ytfu.yuntaifawu.base.BasePresenter
import com.ytfu.yuntaifawu.base.BaseView
import com.ytfu.yuntaifawu.ui.knowledge.fragment.LegalKnowledgeFragment
import qiu.niorgai.StatusBarCompat

/**
 *
 *  @Auther  gxy
 *
 *  @Date    2020/8/11
 *
 *  @Des 法律知识
 *
 */
@InjectLayout(R.layout.activity_legal_knowledge, toolbarLayoutId = R.layout.layout_toolbar_center_title)
class LegalKnowledgeActivity : BaseActivity<BaseView, BasePresenter<BaseView>>() {
    companion object {
        fun start(context: Context) {
            val starter = Intent(context, LegalKnowledgeActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initData() {
        super.initData()
        StatusBarCompat.setStatusBarColor(this, Color.WHITE)
        changeStatusBarTextColor(true)
        setToolbarBackgroud(Color.WHITE)
        setToolbarLeftImage(R.drawable.fanhui_hui) {
            onBackPressed()
        }
        @Suppress("DEPRECATION")
        setToolbarTextColor(R.id.tv_global_title, resources.getColor(R.color.textColoe_303030))
        setToolbarText(R.id.tv_global_title, "法律知识")
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_legal_knowledge, LegalKnowledgeFragment.newInstance())
                .commitAllowingStateLoss()
    }
}
package com.ytfu.yuntaifawu.ui.register.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.core.ui.activity.web.BaseWebActivity
import com.core.ui.ext.getBundleValue
import com.github.lee.annotation.InjectLayout
import com.ytfu.yuntaifawu.R

/**
 * @Author:         gxy
 * @CreateDate:     2020/9/1
 * @Description:     webview公共页
 */
@InjectLayout(toolbarLayoutId = R.layout.layout_toolbar_center_title)
class PrivacyAgreementActivity : BaseWebActivity() {

    companion object {
        private const val KEY_URL = "URL"
        private const val KEY_TITLE = "TITLE"
        fun start(context: Context, title: String, url: String) {
            val bundle = Bundle()
            bundle.putString(KEY_TITLE, title)
            bundle.putString(KEY_URL, url)
            val starter = Intent(context, PrivacyAgreementActivity::class.java)
                    .putExtras(bundle)
            context.startActivity(starter)
        }
    }

    override fun onSetViewData() {
        super.onSetViewData()
        setStatusBarColor(resources.getColor(R.color.transparent_half))
        changeStatusBarTextColor(true)
        val title = getBundleValue(KEY_TITLE, "")
        setToolbarBackgroundColor(resources.getColor(R.color.transparent_half))
        setToolbarText(R.id.tv_global_title, title)
        setToolbarTextColor(R.id.tv_global_title, resources.getColor(R.color.textColor_33))
        setToolbarLeftImage(R.drawable.fanhui_hui) { onBackPressed() }
    }

    override fun provideLoadUrl(): String {
        return getBundleValue(KEY_URL, "")
    }
}
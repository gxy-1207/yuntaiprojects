package com.ytfu.yuntaifawu.ui.knowledge.act

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.webkit.CookieSyncManager
import android.webkit.WebSettings
import com.github.lee.annotation.InjectLayout
import com.github.lee.annotation.InjectPresenter
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.base.BaseActivity
import com.ytfu.yuntaifawu.base.BasePresenter
import com.ytfu.yuntaifawu.base.BaseView
import com.ytfu.yuntaifawu.ui.knowledge.bean.KnowledgeDetailsBean
import com.ytfu.yuntaifawu.ui.knowledge.p.KnowledgeDetailsPresenter
import com.ytfu.yuntaifawu.ui.knowledge.v.KnowledgeDetailsView
import kotlinx.android.synthetic.main.activity_knowledge_details.*
import qiu.niorgai.StatusBarCompat

/**
*
*  @Auther  gxy
*
*  @Date    2020/8/11
*
*  @Des 知识详情
*
*/
@InjectLayout(R.layout.activity_knowledge_details,toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(KnowledgeDetailsPresenter::class)
class KnowledgeDetailsActivity : BaseActivity<KnowledgeDetailsView, KnowledgeDetailsPresenter>(),KnowledgeDetailsView{
    companion object{
        private const val KEY_ID = "ID"
        fun start(context: Context,id : String) {
            val bundle = Bundle()
            bundle.putString(KEY_ID,id)
            val starter = Intent(context, KnowledgeDetailsActivity::class.java)
                    .putExtras(bundle)
            context.startActivity(starter)
        }
    }

    override fun initData() {
        super.initData()
        StatusBarCompat.setStatusBarColor(this,Color.WHITE)
        changeStatusBarTextColor(true)
        setToolbarLeftImage(R.drawable.fanhui_hui){
            onBackPressed()
        }
        setToolbarBackgroud(Color.WHITE)
        setToolbarTextColor(R.id.tv_global_title,resources.getColor(R.color.textColoe_303030))
        setToolbarText(R.id.tv_global_title,"法律知识")
        val webSettings = web_view.settings
        webSettings.allowFileAccess = true
//        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN;//把html中的内容放大webview等宽的一列中
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.textSize =WebSettings.TextSize.LARGER
        webSettings.textZoom = 300
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = false
        webSettings.useWideViewPort = true
        webSettings.setSupportMultipleWindows(false)
        //webSettings.loadWithOverviewMode = true
        webSettings.setAppCacheEnabled(true)
        // webSettings.setDatabaseEnabled(true)
        webSettings.domStorageEnabled = true
        @Suppress("DEPRECATION")
        webSettings.javaScriptEnabled = true
        webSettings.setGeolocationEnabled(true)
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE)
        webSettings.setAppCachePath(this.getDir("appcache", 0).path)
        @Suppress("DEPRECATION")
        webSettings.databasePath = this.getDir("databases", 0).path
        webSettings.setGeolocationDatabasePath(this.getDir("geolocation", 0).path)
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        @Suppress("DEPRECATION")
        webSettings.pluginState = WebSettings.PluginState.ON_DEMAND
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        //webSettings.setPreFectch(true)
        CookieSyncManager.createInstance(this)
        CookieSyncManager.getInstance().sync()
        val id = getBundleString(KEY_ID, "")
        presenter.setKnowledgeDetails(id)
    }

    override fun onKnoeledgeSucess(knowledgeDetailsBean: KnowledgeDetailsBean) {
        hideLoading()
        tv_title.text = knowledgeDetailsBean.content.title
        tv_time.text = knowledgeDetailsBean.content.date
        tv_quantity.text = "%s人浏览".format(knowledgeDetailsBean.content.look)
//        val fromHtml = Html.fromHtml(knowledgeDetailsBean.content.content)
        web_view.loadData(knowledgeDetailsBean.content.content,"text/html","utf-8")
    }

    override fun onKnoeledgeFlied() {

    }
}
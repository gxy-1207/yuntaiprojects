package com.ytfu.yuntaifawu.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.IntRange
import androidx.viewpager.widget.ViewPager
import com.baidu.android.common.security.MD5Util
import com.core.ui.activity.BaseEmptyActivity
import com.core.ui.eventbus.EventBusMessage
import com.core.ui.ext.getBundleValue
import com.core.ui.mvp.view.Autowrite
import com.github.lee.annotation.InjectLayout
import com.github.lee.core.utils.hideSoftInput
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.util.XPopupUtils
import com.ytfu.yuntaifawu.BuildConfig
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.app.AppConstant
import com.ytfu.yuntaifawu.constant.CODE_CHANGE_MAIN_PAGE
import com.ytfu.yuntaifawu.constant.CODE_RESET_UNREAD_MSG_COUNT
import com.ytfu.yuntaifawu.im.EmChatManager
import com.ytfu.yuntaifawu.mvp.presenter.UserHomePresenter
import com.ytfu.yuntaifawu.mvp.view.UserHomeView
import com.ytfu.yuntaifawu.ui.adapter.MainPageAdapter
import com.ytfu.yuntaifawu.ui.login.activity.LoginCodeActivity
import com.ytfu.yuntaifawu.ui.updatapk.UpDateApkBean
import com.ytfu.yuntaifawu.utils.ApkUtil
import com.ytfu.yuntaifawu.utils.LoginHelper
import constacne.UiType.CUSTOM
import kotlinx.android.synthetic.main.activity_user_main.*
import listener.OnBtnClickListener
import listener.OnInitUiListener
import listener.UpdateDownloadListener
import model.UiConfig
import model.UpdateConfig
import update.UpdateAppUtils.getInstance

/**用户主页面*/
@InjectLayout(R.layout.activity_user_main)
class UserMainActivity : BaseEmptyActivity(), UserHomeView {
    @Autowrite
    private lateinit var userHomePresenter: UserHomePresenter

    private var lastClickTime = 0L
    private val timeDifference = 2000L

    companion object {
        private const val KEY_POSITION = "POSITION"
        fun start(context: Context, @IntRange(from = 0, to = 4) position: Int = 0) {
            val bundle = Bundle()
            bundle.putInt(KEY_POSITION, position)
            val starter = Intent(context, UserMainActivity::class.java)
            starter.putExtras(bundle)
            context.startActivity(starter)
        }
    }
    //===Desc:================================================================================

    override fun onInitData(savedInstanceState: Bundle?) {
        super.onInitData(savedInstanceState)
        registerEventBus = true
    }

    override fun onSetViewListener() {
        super.onSetViewListener()
        vp_main_content.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                hideSoftInput(this@UserMainActivity)
                switchBottom(position)
                when (position) {
                    2 -> {
                        @Suppress("DEPRECATION")
                        setStatusBarColor(resources.getColor(R.color.transparent_4c))
                        changeStatusBarTextColor(false)
                    }
                    4 -> {
                        setStatusBarColor(Color.parseColor("#44A2F7"))
                        changeStatusBarTextColor(false)
                    }
                    else -> {
                        setStatusBarColor(Color.WHITE)
                        changeStatusBarTextColor(true)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        rg_main_bottom.setOnCheckedChangeListener { _, selectId ->
            val position = when (selectId) {
                R.id.rb_main_home -> 0
                R.id.rb_main_message -> 1
                R.id.rb_main_center -> 2
                R.id.rb_main_adviser -> 3
                R.id.rb_main_user -> 4
                else -> 0
            }
            vp_main_content.currentItem = position

        }

        iv_main_bottom_center.setOnClickListener {
            vp_main_content.currentItem = 2
        }
    }

    override fun onSetViewData() {
        super.onSetViewData()
        // 在Application或者MainActivity中调用，以达到安装成功启动后删除已安装apk
        getInstance().deleteInstalledApk()
        EmChatManager.getInstance().login(LoginHelper.getInstance().loginUserId) {
            log("Login HuanXin fail...")
        }

        vp_main_content.offscreenPageLimit = 5
        vp_main_content.adapter = MainPageAdapter(supportFragmentManager)
        //设置需要显示的界面
        val defaultPosition = getBundleValue(KEY_POSITION, 0)
        vp_main_content.currentItem = defaultPosition

        //设置红点位置
        tv_main_red_point.measure(0, 0)
        val pointWidth = tv_main_red_point.measuredWidth
        val pointHeight = tv_main_red_point.measuredHeight
        val x = XPopupUtils.getWindowWidth(mContext) / 5 * 2 - pointWidth - pointWidth / 2
        rb_main_message.measure(0, 0)
        val y = rb_main_message.measuredHeight - pointHeight

        val params = tv_main_red_point.layoutParams as RelativeLayout.LayoutParams
        params.leftMargin = x
        params.bottomMargin = y
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        tv_main_red_point.requestLayout()
    }

    override fun onResume() {
        super.onResume()
        //检测更新  检测律师认证
        userHomePresenter.checkAttorneyCertified()
        userHomePresenter.checkUpdate()
        //设置未读消息小红点
        val allUnreadCount = EmChatManager.getInstance().allUnreadCount
        setRedPoint(allUnreadCount)
    }

    override fun onBackPressed() {
        val now = System.currentTimeMillis()
        if ((now - lastClickTime) > timeDifference) {
            showToast("再按一次退出程序")
            lastClickTime = now
        } else {
            super.onBackPressed()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val position = getBundleValue(intent, KEY_POSITION, 0)
        vp_main_content.currentItem = position ?: 0
    }

    override fun onDestroy() {
        userHomePresenter.destroy()
        super.onDestroy()
    }
    //===Desc:监听接收到的EventBus消息================================================================================

    override fun receiveEventBusMessage(msg: EventBusMessage) {
        super.receiveEventBusMessage(msg)
        when (msg.what) {
            CODE_CHANGE_MAIN_PAGE -> {
                val position = msg.arg1
                vp_main_content.currentItem = position
            }
            CODE_RESET_UNREAD_MSG_COUNT -> setRedPoint(EmChatManager.getInstance().allUnreadCount)
        }
    }

    //===Desc:================================================================================

    /**底部切换*/
    private fun switchBottom(position: Int) {
        val selectId = when (position) {
            0 -> R.id.rb_main_home
            1 -> R.id.rb_main_message
            2 -> R.id.rb_main_center
            3 -> R.id.rb_main_adviser
            4 -> R.id.rb_main_user
            else -> R.id.rb_main_home
        }
        rg_main_bottom.check(selectId)

    }

    //===Desc:实现View层的回调方法================================================================================
    /**设置未读消息小红点*/
    override fun setRedPoint(count: Int) {
        when {
            count <= 0 -> {
                tv_main_red_point.visibility = View.GONE
            }
            count > 9 -> {
                tv_main_red_point.visibility = View.VISIBLE
                tv_main_red_point.text = "..."
            }
            else -> {
                tv_main_red_point.visibility = View.VISIBLE
                tv_main_red_point.text = count.toString()
            }
        }
    }

    override fun onUserAuthSuccess() {
        XPopup.Builder(mContext)
                .dismissOnTouchOutside(false)
                .dismissOnBackPressed(false)
                .asConfirm(
                        "律师认证",
                        "您的律师认证已审核通过,请点击重新登录按钮切换律师身份。",
                        "",
                        "重新登录",
                        { LoginCodeActivity.startNewTask(mContext) },
                        {},
                        true)
                .show()
    }

    override fun onCheckUpdate(data: UpDateApkBean) {
        super.onCheckUpdate(data)
        val localCode = ApkUtil.getVersionCode()
        val serviceCode = try {
            Integer.parseInt(data.code)
        } catch (e: NumberFormatException) {

            0
        }
        val serviceAllowCode = try {
            Integer.parseInt(data.allow_code)
        } catch (e: NumberFormatException) {
            if (BuildConfig.IS_DEBUG) {
                e.printStackTrace()
            }
            0
        }
        //不显示更新
        if (localCode >= serviceCode) {
            return
        }

        val isForce = localCode < serviceAllowCode

        // UI配置
        val uiConfig = UiConfig()
        uiConfig.uiType = CUSTOM
        uiConfig.customLayoutId = R.layout.pop_update_new
        uiConfig.cancelBtnText = "残忍拒绝"
        uiConfig.updateBtnText = "立即升级"
        // 更新配置
        val updateConfig = UpdateConfig()
        updateConfig.isDebug = AppConstant.DEBUG
        // 自动添加后缀.apk
        val fileName = MD5Util.toMd5(
                (data.url + data.code + data.code_str).toByteArray(), true)

        updateConfig.apkSaveName = fileName
        updateConfig.notifyImgRes = R.mipmap.icon_app_logo
        // 是否强制更新，强制时无取消按钮
        if (isForce) {
            updateConfig.force = true
        }
        getInstance()
                .apkUrl(data.url) // title设置为版本号
                .updateTitle("v" + data.code_str)
                .updateContent(data.miaoshu)
                .uiConfig(uiConfig) // 自定义布局中控件内容填充
                .setOnInitUiListener(object : OnInitUiListener {
                    override fun onInitUpdateUi(view: View?, updateConfig: UpdateConfig, uiConfig: UiConfig) {
                        // 是否强制更新，强制时无取消按钮
                        if (null != view && isForce) {
                            // 隐藏分割线
                            view.findViewById<View>(R.id.view_update_line).visibility = View.GONE
                        }
                    }
                })
                .setCancelBtnClickListener(object : OnBtnClickListener {
                    override fun onClick(): Boolean {
                        return false
                    }
                })
                .setUpdateDownloadListener(
                        object : UpdateDownloadListener {
                            override fun onStart() {
                            }

                            override fun onDownload(progress: Int) {}


                            override fun onFinish() {}
                            override fun onError(e: Throwable) {
                                log("UpdateDownloadListener -> onError() : ${e.message}")
                            }
                        })
                .updateConfig(updateConfig)
                .update()
    }
}


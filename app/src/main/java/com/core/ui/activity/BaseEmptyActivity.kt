package com.core.ui.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.core.ui.callback.IUICallback
import com.core.ui.eventbus.EventBusHandler
import com.core.ui.eventbus.EventBusManager
import com.core.ui.eventbus.EventBusMessage
import com.core.ui.mvp.BasicMVPPresenter
import com.core.ui.mvp.BasicMVPView
import com.core.ui.mvp.view.Autowrite
import com.github.lee.annotation.InjectLayout
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.orhanobut.logger.Logger
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.app.AppConstant
import kotlinx.android.synthetic.main.activity_overlap_toolbar.*
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.EasyPermissions
import qiu.niorgai.StatusBarCompat

open class BaseEmptyActivity : AppCompatActivity(), IUICallback, BasicMVPView,
        EventBusHandler, EasyPermissions.PermissionCallbacks {

    protected lateinit var mContext: Context
    protected lateinit var mInflater: LayoutInflater
    protected var registerEventBus = false

    private val presenters = mutableListOf<BasicMVPPresenter<*>>()


    private var waitDialog: BasePopupView? = null

    /** Toolbar是悬浮还是线性  */
    protected var toolbarOverlap = false

    /** 显示在界面的Toolbar,为null不显示  */
    protected var mToolbar: Toolbar? = null


    protected var loadingView: View? = null
    protected var successView: View? = null
    protected var emptyView: View? = null
    protected var errorView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mInflater = LayoutInflater.from(mContext)
        // Debug模式设置屏幕常亮
        if (AppConstant.DEBUG) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        onInitData(savedInstanceState)

        if (registerEventBus) {
            EventBusManager.register(this)
        }
        //获取注入的layout
        val injectLayout = javaClass.getAnnotation(InjectLayout::class.java)
        if (null == injectLayout) {
            log("Do not have any layout to show at ${javaClass.simpleName}")
            try {
                val clazz = javaClass
                //获取属性字段
                val fields = clazz.declaredFields
                for (item in fields) {
                    item.getAnnotation(Autowrite::class.java) ?: continue
                    item.isAccessible = true
                    @Suppress("UNCHECKED_CAST")
                    val type = item.type as Class<BasicMVPPresenter<BasicMVPView>>
                    val presenterInstance = type.newInstance()
                    item.set(this, presenterInstance)
                    presenterInstance.attachView(this)
                    presenters.add(presenterInstance)
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

            onSetViewListener()
            onSetViewData()
            return
        }

        //获取不同的布局
        val loadingId = injectLayout.loadingLayoutId
        if (loadingId != -1) {
            loadingView = inflateView(loadingId)
        }
        val layoutId = injectLayout.value
        if (layoutId != -1) {
            successView = inflateView(layoutId)
        }
        val emptyId = injectLayout.emptyLayoutId
        if (emptyId != -1) {
            emptyView = inflateView(emptyId)
            emptyView?.setOnClickListener {
                showLoading()
                onRetryLoading()
            }
        }
        val errorId = injectLayout.errorLayoutId
        if (errorId != -1) {
            errorView = inflateView(errorId)
            errorView?.setOnClickListener {
                showLoading()
                onRetryLoading()
            }
        }

        val toolbarId = injectLayout.toolbarLayoutId
        if (toolbarId != -1) {
            mToolbar = inflateToolbar(toolbarId)
        }
        //显示界面
        if (toolbarOverlap) {
            // Toolbar悬浮
            setContentView(R.layout.activity_overlap_toolbar)
        } else {
            setContentView(R.layout.activity_linear_toolbar)
        }
        //设置Toolbar
        if (null == mToolbar) {
            fl_toolbar_container.visibility = View.GONE
        } else {
            //初始化toolbar并添加显示
            fl_toolbar_container.removeAllViews()
            mToolbar?.title = ""
            setSupportActionBar(mToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            fl_toolbar_container.addView(mToolbar)
            fl_toolbar_container.visibility = View.VISIBLE
        }
        //添加布局到容器显示
        fl_base_content.removeAllViews()
        if (null != loadingView) {
            fl_base_content.addView(loadingView)
        }
        if (null != successView) {
            fl_base_content.addView(successView)
        }
        if (null != emptyView) {
            fl_base_content.addView(emptyView)
        }
        if (null != errorView) {
            fl_base_content.addView(errorView)
        }

        //获取注入的Presenter成员变量
        try {
            val clazz = javaClass
            //获取属性字段
            val fields = clazz.declaredFields
            for (item in fields) {
                item.getAnnotation(Autowrite::class.java) ?: continue
                item.isAccessible = true
                @Suppress("UNCHECKED_CAST")
                val type = item.type as Class<BasicMVPPresenter<BasicMVPView>>
                val presenterInstance = type.newInstance()
                item.set(this, presenterInstance)
                presenterInstance.attachView(this)
                presenters.add(presenterInstance)
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        //显示loading
        if (null == loadingView) {
            showSuccess()
        } else {
            showLoading()
        }

        onSetViewListener()
        onSetViewData()
    }

    override fun onDestroy() {
        EventBusManager.unRegister(this)
        hideWait()
        for (item in presenters) {
            item.detachView()
        }
        super.onDestroy()
    }

    //===Desc:================================================================================
    override fun onInitData(savedInstanceState: Bundle?) {

    }

    override fun onSetViewListener() {
    }

    override fun onSetViewData() {
    }

    override fun onRetryLoading() {
    }


    //===Desc:================================================================================
    override fun getBindContext(): Context = mContext

    override fun showLoading() {
        loadingView?.visibility = View.VISIBLE
        successView?.visibility = View.GONE
        emptyView?.visibility = View.GONE
        errorView?.visibility = View.GONE
    }

    override fun hideLoading() {
        loadingView?.visibility = View.GONE
    }

    override fun showSuccess() {
        loadingView?.visibility = View.GONE
        successView?.visibility = View.VISIBLE
        emptyView?.visibility = View.GONE
        errorView?.visibility = View.GONE
    }

    override fun hideSuccess() {
        successView?.visibility = View.GONE
    }

    override fun showEmpty() {
        loadingView?.visibility = View.GONE
        successView?.visibility = View.GONE
        emptyView?.visibility = View.VISIBLE
        errorView?.visibility = View.GONE
    }

    override fun hideEmpty() {
        emptyView?.visibility = View.GONE
    }

    override fun showError() {
        loadingView?.visibility = View.GONE
        successView?.visibility = View.GONE
        emptyView?.visibility = View.GONE
        errorView?.visibility = View.VISIBLE
    }

    override fun hideError() {
        errorView?.visibility = View.GONE
    }

    override fun showWait(cancelOutSite: Boolean) {
        if (null == waitDialog) {
            waitDialog = XPopup.Builder(this)
                    .dismissOnBackPressed(cancelOutSite)
                    .dismissOnTouchOutside(cancelOutSite)
                    .asLoading()
                    .show();
        }
    }

    override fun hideWait(runnable: Runnable?) {
        if (waitDialog?.isShow == true) {
            if (null != runnable) {
                waitDialog?.dismissWith(runnable)
            } else {
                waitDialog?.dismiss()
            }
            waitDialog = null
        }
    }


    override fun showToast(text: String, isLong: Boolean) {
        val toast = Toast.makeText(mContext, null, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        toast.setText(text)
        toast.show()
    }

    override fun showToast(textId: Int, isLong: Boolean) {
        showToast(getString(textId), isLong)
    }

    override fun runMainThread(runnable: Runnable) {
        runOnUiThread(runnable)
    }

    //===Desc:================================================================================

    protected fun log(msg: Any?) {
        if (null == msg) {
            Logger.e("You print msg is null")
            return
        }
        Logger.e(msg.toString())
    }

    protected fun inflateView(@LayoutRes layoutId: Int): View? =
            mInflater.inflate(layoutId, null, false)

    protected fun inflateToolbar(@LayoutRes layoutId: Int): Toolbar? {
        val view = mInflater.inflate(layoutId, null, false)
        if (view is Toolbar) {
            return view
        }
        throw IllegalArgumentException("Can not find toolbar at layout id is $layoutId")
    }

    protected fun getContentView(): FrameLayout =
            fl_base_content

    protected fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_base_content, fragment)
                .commitNowAllowingStateLoss()
    }

    //===Desc:Toolbar相关方法================================================================================
    protected fun setToolbarText(@IdRes viewId: Int, text: String) {
        if (null == mToolbar) {
            return
        }
        mToolbar?.findViewById<TextView>(viewId)?.text = text
    }

    protected fun setToolbarText(@IdRes viewId: Int, @StringRes textId: Int) {
        if (null == mToolbar) {
            return
        }
        mToolbar?.findViewById<TextView>(viewId)?.setText(textId)
    }

    protected fun setToolbarText(@IdRes viewId: Int, text: String, @ColorInt color: Int, size: Float) {
        setToolbarText(viewId, text)
        setToolbarTextColor(viewId, color)
        setToolbarTextSize(viewId, size)
    }

    protected fun setToolbarText(@IdRes viewId: Int, @StringRes textId: Int, @ColorInt color: Int, size: Float) {
        setToolbarText(viewId, textId)
        setToolbarTextColor(viewId, color)
        setToolbarTextSize(viewId, size)
    }

    protected fun setToolbarTextColor(@IdRes viewId: Int, @ColorInt color: Int) {
        if (null == mToolbar) {
            return
        }
        mToolbar?.findViewById<TextView>(viewId)?.setTextColor(color)
    }

    protected fun setToolbarTextSize(@IdRes viewId: Int, size: Float) {
        if (null == mToolbar) {
            return
        }
        mToolbar?.findViewById<TextView>(viewId)?.textSize = size
    }


    protected fun setToolbarBackgroundColor(@ColorInt color: Int) {
        mToolbar?.setBackgroundColor(color)
    }

    protected fun setToolbarLeftImage(
            @DrawableRes drawableId: Int,
            click: View.OnClickListener? = null
    ) {
        mToolbar?.setNavigationIcon(drawableId)
        if (null != click) {
            mToolbar!!.setNavigationOnClickListener(click)
        }
    }


    //===Desc:================================================================================
    /**
     * 更换StatusBar上面文本颜色,只支持黑色和白色
     */
    protected open fun changeStatusBarTextColor(isBlack: Boolean) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            @Suppress("DEPRECATION")
            val visibility = if (isBlack) {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                View.SYSTEM_UI_FLAG_VISIBLE
            }
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = visibility
        }
    }

    protected fun getStatusBarHeight(): Int {
        val resources = resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    protected fun translucentStatusBar(hideStatusBarBackground: Boolean = false) {
        StatusBarCompat.translucentStatusBar(this, hideStatusBarBackground)
    }

    protected fun setStatusBarColor(@ColorInt statusColor: Int, alpha: Int = -1) {
        if (alpha == -1) {
            StatusBarCompat.setStatusBarColor(this, statusColor)
        } else {
            StatusBarCompat.setStatusBarColor(this, statusColor, alpha)
        }
    }

    //===Desc:EventBus================================================================================
    fun obtain(what: Int): EventBusMessage {
        val msg = EventBusMessage()
        msg.what = what
        return msg
    }

    fun postSticky(msg: EventBusMessage) {
        EventBus.getDefault().postSticky(msg)
    }

    fun post(msg: EventBusMessage) {
        EventBus.getDefault().post(msg)
    }


    //===Desc:运行时权限================================================================================
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        //权限拒绝
        //权限是否永久拒绝
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            onPermissionsPermanentlyFail(requestCode, perms)
        } else {
            onPermissionsFail(requestCode, perms)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        //权限通过
        onPermissionsPass(requestCode, perms)
    }

    /**子类复写,自己处理权限不通过*/
    protected open fun onPermissionsFail(requestCode: Int, perms: MutableList<String>) {

    }

    /**子类复写,自己处理权限永久不通过*/
    protected open fun onPermissionsPermanentlyFail(requestCode: Int, perms: MutableList<String>) {

    }

    /**子类复写,自己处理权限通过*/
    protected open fun onPermissionsPass(requestCode: Int, perms: MutableList<String>) {

    }

    /**判断是否拥有权限*/
    protected fun hasPermissions(vararg perms: String): Boolean {
        return EasyPermissions.hasPermissions(this, *perms)
    }

    /**请求权限
     * @param requestCode 请求码
     * @param rationale 请求权限说明
     * @param perms 需要使用的权限
     */
    protected fun requestPermissions(requestCode: Int, rationale: String, vararg perms: String) {
        EasyPermissions.requestPermissions(this, rationale, requestCode, *perms)
    }
    //===Desc:================================================================================

}
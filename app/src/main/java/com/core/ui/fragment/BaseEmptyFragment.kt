package com.core.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.core.ui.callback.IUICallback
import com.core.ui.eventbus.EventBusHandler
import com.core.ui.eventbus.EventBusManager
import com.core.ui.eventbus.EventBusMessage
import com.core.ui.ext.log
import com.core.ui.mvp.BasicMVPPresenter
import com.core.ui.mvp.BasicMVPView
import com.core.ui.mvp.view.Autowrite
import com.ethanhua.skeleton.SkeletonScreen
import com.github.lee.annotation.InjectLayout
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.ytfu.yuntaifawu.R
import kotlinx.android.synthetic.main.fragment_base_empty.*
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.EasyPermissions

/**不做Toolbar处理*/
open class BaseEmptyFragment : Fragment(), IUICallback, BasicMVPView,
        EventBusHandler, EasyPermissions.PermissionCallbacks {

    protected lateinit var mContext: Context
    protected lateinit var mInflater: LayoutInflater

    protected var registerEventBus = false

    private val listPresenters = mutableListOf<BasicMVPPresenter<BasicMVPView>>()


    protected var successView: View? = null
    protected var loadingView: View? = null
    protected var emptyView: View? = null
    protected var errorView: View? = null

    protected var successId: Int = -1
    protected var loadingId: Int = -1
    protected var emptyId: Int = -1
    protected var errorId: Int = -1

    protected var showSkeleton: SkeletonScreen? = null
    private var waitDialog: BasePopupView? = null


    //===Desc:================================================================================


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.mContext = activity!!
        mInflater = LayoutInflater.from(mContext)
        onInitData(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base_empty, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fl_base_content.removeAllViews()
        //注入布局
        val injectLayout = javaClass.getAnnotation(InjectLayout::class.java)
        if (null == injectLayout) {
            log("Do not have layout to show at ${javaClass.simpleName}")
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
                    listPresenters.add(presenterInstance)
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

            if (registerEventBus) {
                EventBusManager.register(this)
            }
            onSetViewListener()
            onSetViewData()
            return
        }
        successId = injectLayout.value
        if (successId != -1) {
            successView = inflaterView(successId)
            fl_base_content.addView(successView)
        }
        loadingId = injectLayout.loadingLayoutId
        if (loadingId != -1) {
            loadingView = inflaterView(loadingId)
            fl_base_content.addView(loadingView)
        }
        emptyId = injectLayout.emptyLayoutId
        if (emptyId != -1) {
            emptyView = inflaterView(emptyId)
            emptyView?.setOnClickListener {
                showLoading()
                onRetryLoading()
            }
            fl_base_content.addView(emptyView)
        }
        errorId = injectLayout.errorLayoutId
        if (errorId != -1) {
            errorView = inflaterView(errorId)
            errorView?.setOnClickListener {
                onRetryLoading()
            }
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
                listPresenters.add(presenterInstance)
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        if (registerEventBus) {
            EventBusManager.register(this)
        }

        showLoading()

        onSetViewListener()
        onSetViewData()

    }

    override fun onDestroy() {
        EventBusManager.unRegister(this)

        //解绑MVP
        for (item in listPresenters) {
            item.detachView()
        }
        listPresenters.clear()
        super.onDestroy()
    }

    //===Desc:================================================================================

    protected fun inflaterView(layoutId: Int): View? =
            mInflater.inflate(layoutId, null, false)

    protected fun getContentView(): FrameLayout =
            fl_base_content

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
            waitDialog = XPopup.Builder(mContext)
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
        activity?.runOnUiThread(runnable)
    }

    //===Desc:================================================================================


    //===Desc:EventBus相关================================================================================
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

    //===Desc:权限相关================================================================================
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
        return EasyPermissions.hasPermissions(mContext, *perms)
    }

    /**请求权限
     *
     * @param requestCode 请求码
     * @param rationale 请求权限说明
     * @param perms 需要使用的权限
     */
    protected fun requestPermissions(requestCode: Int, rationale: String, vararg perms: String) {
        EasyPermissions.requestPermissions(this, rationale, requestCode, *perms)
    }


}
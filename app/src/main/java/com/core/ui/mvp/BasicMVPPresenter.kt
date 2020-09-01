package com.core.ui.mvp

import com.ytfu.yuntaifawu.apis.HttpUtil
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class DataException(val code: Int, msg: String) : RuntimeException(msg) {
}

open class BasicMVPPresenter<V : BasicMVPView> {
    private var viewRef: WeakReference<V>? = null

    fun attachView(view: V) {
        this.viewRef = WeakReference(view)
    }

    fun isAttachView(): Boolean {
        if (null == viewRef) {
            return false
        }
        return null != viewRef?.get()
    }

    open fun detachView() {
        presenterScope.cancel()
        if (isAttachView()) {
            viewRef?.clear()
            viewRef = null
        }
    }


    //===Desc:=====================================================================

    protected fun getView(): V {
        if (null == viewRef) {
            throw IllegalStateException("Please attach a view first")
        }
        return viewRef?.get() ?: throw IllegalStateException("Please attach a view first")
    }


    protected val presenterScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.IO + Job())
    }

    /**
     * 协程发起一个请求,除了 runBlock 运行在 Dispatchers.IO 线程,别的都运行在主线程之中
     * @param run  运行在 Dispatchers.IO 线程
     */
    protected fun <T> requestRemote(
            before: suspend CoroutineScope.() -> Unit = {},
            run: suspend CoroutineScope.() -> T?,
            success: (T) -> Unit = {},
            error: (Throwable) -> Unit = {},
            finish: suspend CoroutineScope.() -> Unit = {}
    ) {
        presenterScope.launch {
            try {
                before()
                val result = run()
                if (null == result) {
                    withContext(Dispatchers.Main) {
                        error(DataException(-1, "服务器响应数据失败，请稍后重试"))
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        success(result)
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    if (e is SocketTimeoutException || e is SocketException || e is UnknownHostException) {
                        error(DataException(-2, "网络请求出现异常，请稍后重试"))
                    } else {
                        val message = e.message
                        if (message.isNullOrEmpty()) {
                            error(DataException(-3, "未知异常"))
                        } else {
                            error(DataException(-4, "ssssss"))
                        }
                    }

                }
            } finally {
                withContext(Dispatchers.Main) { finish() }
            }
        }
    }


    protected fun <T> createService(clazz: Class<T>) =
            HttpUtil.getInstance().getService(clazz)
    
}
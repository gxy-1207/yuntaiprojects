package com.ytfu.yuntaifawu.ui.chatroom.p

import com.ytfu.yuntaifawu.apis.RefundService
import com.ytfu.yuntaifawu.base.BasePresenter
import com.ytfu.yuntaifawu.helper.BaseRxObserver
import com.ytfu.yuntaifawu.ui.chatroom.v.RefundCommitView
import com.ytfu.yuntaifawu.ui.users.bean.MyRefundBean
import io.reactivex.disposables.Disposable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RefundCommitPresenter : BasePresenter<RefundCommitView>() {

    fun refund(userId: String, lawyerId: String, content: String, file: File?) {
//        val ui = RequestBody.create(MediaType.parse("text/plain"), userId)
//        val li = RequestBody.create(MediaType.parse("text/plain"), lawyerId)
//        val c = RequestBody.create(MediaType.parse("text/plain"), content)
        val ui = RequestBody.create("text/plain".toMediaTypeOrNull(), userId)
        val li = RequestBody.create("text/plain".toMediaTypeOrNull(), lawyerId)
        val c = RequestBody.create("text/plain".toMediaTypeOrNull(), content)
        val f = if (file != null) {
//            val rb = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val rb = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            MultipartBody.Part.createFormData("voucher", file.name, rb)
        } else {
            null
        }
        val ob = createService(RefundService::class.java).subRefund(ui, li, c, f)
        requestRemote(ob, object : BaseRxObserver<MyRefundBean>() {
            override fun onSubscribeImpl(d: Disposable?) {
                super.onSubscribeImpl(d)
                view.showProgress()
            }

            override fun onNextImpl(data: MyRefundBean?) {
                if (null == data) {
                    view.showToast("网络请求出现错误,请稍后重试")
                    return
                }
                if (data.code != 200) {
                    view.showToast(data.msg)
                    return
                }
                view.onRefundSuccess()
            }

            override fun onErrorImpl(errorMessage: String?) {
                view.showToast("网络请求出现错误,请稍后重试")
                view.hideProgress()
            }

            override fun onCompleteImpl() {
                super.onCompleteImpl()
                view.hideProgress()
            }
        })


    }
}
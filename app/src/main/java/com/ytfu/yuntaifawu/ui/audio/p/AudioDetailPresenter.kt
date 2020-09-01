package com.ytfu.yuntaifawu.ui.audio.p

import com.ytfu.yuntaifawu.apis.ApiService
import com.ytfu.yuntaifawu.base.BasePresenter
import com.ytfu.yuntaifawu.helper.BaseRxObserver
import com.ytfu.yuntaifawu.ui.audio.v.AudioDetailView
import com.ytfu.yuntaifawu.ui.home.bean.AudioDetailsBean
import com.ytfu.yuntaifawu.ui.home.bean.AudioShouCangBean

class AudioDetailPresenter : BasePresenter<AudioDetailView>() {


    fun getAudioDetail(userId: String, audioId: String) {
        val ob = createService(ApiService::class.java).audioDetail(userId, audioId)
        requestRemote(ob, object : BaseRxObserver<AudioDetailsBean>() {
            override fun onNextImpl(data: AudioDetailsBean?) {
                if (null == data) {
                    view.showEmpty()
                    return
                }
                val status = data.status
                if (status != 1) {
                    view.showError()
                    view.showToast(data.referer)
                    return
                }

                view.onGetAudioDetail(data)
                view.hideLoading()
            }

            override fun onErrorImpl(errorMessage: String?) {
            }
        })


    }

    fun keepAudio(audioId: String) {
        val ob = createService(ApiService::class.java)
                .keep(audioId, 1)
        requestRemote(ob, object : BaseRxObserver<AudioShouCangBean>() {
            override fun onNextImpl(data: AudioShouCangBean?) {
                if (null == data) {
                    view.showToast("应用程序出现错误,请稍后重试")
                    return
                }
                val status = data.status
                if (status != 1) {
                    view.showToast(data.referer)
                    return
                }
                view.showToast("收藏成功")
            }

            override fun onErrorImpl(errorMessage: String?) {
                view.showToast("应用程序出现错误,请稍后重试")
            }
        })
    }

    fun addUpPlay(userId: String, audioId: String) {
        val ob = createService(ApiService::class.java)
                .addUpAudioPlay(userId, audioId, 1)
        requestRemote(ob, object : BaseRxObserver<Any>() {
            override fun onNextImpl(data: Any?) {

            }

            override fun onErrorImpl(errorMessage: String?) {
            }
        })

    }
}

package com.ytfu.yuntaifawu.ui.audio.v

import com.ytfu.yuntaifawu.base.BaseView
import com.ytfu.yuntaifawu.ui.home.bean.AudioDetailsBean

interface AudioDetailView : BaseView {

    fun onGetAudioDetail(data: AudioDetailsBean)

}


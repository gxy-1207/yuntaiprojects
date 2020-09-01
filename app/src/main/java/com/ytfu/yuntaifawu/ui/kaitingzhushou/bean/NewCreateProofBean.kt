package com.ytfu.yuntaifawu.ui.kaitingzhushou.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class NewCreateProofBean(
        @SerializedName("id")
        val id: String = "",
        @SerializedName("referer")
        val referer: String = "",
        @SerializedName("state")
        val state: String = "",
        @SerializedName("status")
        val status: Int = 0
)
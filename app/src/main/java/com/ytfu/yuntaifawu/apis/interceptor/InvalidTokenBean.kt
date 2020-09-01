package com.ytfu.yuntaifawu.apis.interceptor

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class InvalidTokenBean(
        @SerializedName("msg")
        val msg: String = "",
        @SerializedName("referer")
        val referer: String = "",
        @SerializedName("state")
        val state: String = "",
        @SerializedName("status")
        val status: Int = 0
)
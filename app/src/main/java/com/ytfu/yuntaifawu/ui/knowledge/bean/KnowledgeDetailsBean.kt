package com.ytfu.yuntaifawu.ui.knowledge.bean
import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class KnowledgeDetailsBean(
    @SerializedName("content")
    val content: Content = Content(),
    @SerializedName("msg")
    val msg: String = "",
    @SerializedName("referer")
    val referer: String = "",
    @SerializedName("state")
    val state: String = "",
    @SerializedName("status")
    val status: Int = 0
)

@Keep
data class Content(
    @SerializedName("content")
    val content: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("look")
    val look: String = "",
    @SerializedName("title")
    val title: String = ""
)
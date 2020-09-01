package com.ytfu.yuntaifawu.ui.knowledge.bean
import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class LegalKnowledgeBean(
    @SerializedName("list")
    val list: List<LegalKnowledgeItemBean> = listOf(),
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
data class LegalKnowledgeItemBean(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("title")
    val title: String = ""
)
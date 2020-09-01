package com.ytfu.yuntaifawu.ui.mseeage.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class LawyerAnswerBean(
        @SerializedName("code")
        val code: Int,
        @SerializedName("data")
        val `data`: List<LawyerAnswerData>,
        @SerializedName("referer")
        val referer: String,
        @SerializedName("state")
        val state: String,
        @SerializedName("status")
        val status: String
)

@Keep
data class LawyerAnswerData(
        @SerializedName("cid")
        val cid: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("date")
        val date: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("lid")
        val lid: String,
        @SerializedName("picurl")
        val picurl: String,
        @SerializedName("sum")
        val sum: String,
        @SerializedName("uname")
        val uname: String
)
package com.ytfu.yuntaifawu.ui.users.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class SkillsBean(
        @SerializedName("code")
        var code: Int,
        @SerializedName("data")
        var `data`: List<DataSkills>,
        @SerializedName("msg")
        var msg: String
)

@Keep
data class DataSkills(
        @SerializedName("addtime")
        var addtime: String,
        @SerializedName("id")
        var id: String,
        @SerializedName("istop")
        var istop: String,
        @SerializedName("jumpurl")
        var jumpurl: String,
        @SerializedName("rand_type")
        var randType: Int,
        @SerializedName("title")
        var title: String
)
package com.ytfu.yuntaifawu.ui.mseeage.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName

/**
 * @ClassName:      EvaluateBean
 * @Author:         gxy
 * @CreateDate:     2020/8/26
 * @Description:    评价分类
 */
@Keep
data class EvaluateClassBean(
        @SerializedName("referer")
        val referer: String,
        @SerializedName("state")
        val state: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("type")
        val type: List<Type>
)

@Keep
data class Type(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("num")
        val num: String
)
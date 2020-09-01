package com.ytfu.yuntaifawu.ui.mseeage.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


/**
 * @ClassName:      EvaluateListBean
 * @Author:         gxy
 * @CreateDate:     2020/8/26
 * @Description:    评价列表Bean
 */
@Keep
data class EvaluateListBean(
        @SerializedName("category")
        val category: List<Category>,
        @SerializedName("code")
        val code: Int,
        @SerializedName("picurl")
        val picurl: String,
        @SerializedName("referer")
        val referer: String,
        @SerializedName("shuju")
        val shuju: List<Shuju>,
        @SerializedName("state")
        val state: String,
        @SerializedName("status")
        val status: String
)

@Keep
data class Category(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("num")
        val num: String
)

@Keep
data class Shuju(
        @SerializedName("addtime")
        val addtime: String,
        @SerializedName("cid")
        val cid: String,
        @SerializedName("count")
        val count: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("lvshi")
        val lvshi: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("pingjia")
        val pingjia: String,
        @SerializedName("talk")
        val talk: Any,
        @SerializedName("yhaddtime")
        val yhaddtime: String
)
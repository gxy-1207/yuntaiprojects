package com.ytfu.yuntaifawu.ui.complaint.bean

import android.os.Parcelable
import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Keep
data class ComplaintResponse(
        @SerializedName("list")
        val list: List<ComplaintBean> = listOf(),
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
@Parcelize
data class ComplaintBean(
        @SerializedName("beigao_name")
        val beigaoName: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("lsid")
        val lsid: String = "",
        @SerializedName("new_status")
        val newStatus: Int = 0,
        @SerializedName("qingqiu_list")
        val qingqiuList: String = "",
        @SerializedName("uid")
        val uid: String = "",
        @SerializedName("yuangao_name")
        val yuangaoName: String = "",
        @SerializedName("type_name")
        val typeNmae: String = "",
        @SerializedName("href")
        val href: String = "",

        @SerializedName("dailici_type")
        val dailiciType: String = "",
        @SerializedName("qingdan_type")
        val qingdanType: String = ""

) : Parcelable
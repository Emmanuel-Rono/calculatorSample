package com.reemmousa.toTrade.dataStructures.lectures


import com.google.gson.annotations.SerializedName

data class ResponseCourseLectures(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("results")
    val lectures: List<Lecture?>?
)
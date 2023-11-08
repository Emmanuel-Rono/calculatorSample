package com.reemmousa.toTrade.dataStructures.reviews


import com.google.gson.annotations.SerializedName

data class ResponseCourseReview(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("results")
    val reviews: List<Review?>?
)
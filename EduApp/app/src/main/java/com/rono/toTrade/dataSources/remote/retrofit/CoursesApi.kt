package com.reemmousa.toTrade.dataSources.remote.retrofit

import com.reemmousa.toTrade.app.Constants.HEADER_ACCESS
import com.reemmousa.toTrade.app.Constants.HEADER_AUTHORIZATION
import com.reemmousa.toTrade.app.Constants.HEADER_CONTENT_TYPE
import com.reemmousa.toTrade.dataStructures.courses.Course
import com.reemmousa.toTrade.dataStructures.courses.ResponseCoursesList
import com.reemmousa.toTrade.dataStructures.lectures.ResponseCourseLectures
import com.reemmousa.toTrade.dataStructures.reviews.ResponseCourseReview
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface CoursesApi {
    @Headers(*[HEADER_ACCESS, HEADER_CONTENT_TYPE, HEADER_AUTHORIZATION])
    @GET("courses/")
    suspend fun getCoursesList(
        @Query("search") searchString: String?,
        @Query("page") pageNo: Int,
        @Query("price") price: String?
    ): Response<ResponseCoursesList?>?

    @Headers(*[HEADER_ACCESS, HEADER_CONTENT_TYPE, HEADER_AUTHORIZATION])
    @GET("courses/{courseID}")
    suspend fun getCourseDetails(
        @Path("courseID") courseID: String,
    ): Response<Course?>?

    @Headers(*[HEADER_ACCESS, HEADER_CONTENT_TYPE, HEADER_AUTHORIZATION])
    @GET("courses/{courseID}/reviews")
    suspend fun getCourseReviews(
        @Path("courseID") courseID: String,
        @Query("page") pageNo: Int,
    ): Response<ResponseCourseReview?>?

    @Headers(*[HEADER_ACCESS, HEADER_CONTENT_TYPE, HEADER_AUTHORIZATION])
    @GET("courses/{courseID}/public-curriculum-items")
    suspend fun getCourseLectures(
        @Path("courseID") courseID: String,
        @Query("page") pageNo: Int,
    ): Response<ResponseCourseLectures?>?
}
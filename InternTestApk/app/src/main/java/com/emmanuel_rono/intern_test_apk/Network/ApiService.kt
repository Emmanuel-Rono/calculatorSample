package com.emmanuel_rono.intern_test_apk

import retrofit2.http.GET


interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}

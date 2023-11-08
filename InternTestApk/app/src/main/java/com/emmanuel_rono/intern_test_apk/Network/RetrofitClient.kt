package com.emmanuel_rono.intern_test_apk

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val postApiService: PostApiService by lazy {
        retrofit.create(PostApiService::class.java)
    }
}

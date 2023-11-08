package com.emmanuel_rono.intern_test_apk

class PostRepository {
    suspend fun getPosts(): List<Post> {
        return try {
            ApiClient.postApiService.getPosts()
        } catch (e: Exception) {
            // Handle network error
            emptyList()
        }
    }
}

package com.emmanuel_rono.intern_test_apk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class PostViewModel(private val repository: PostRepository) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    fun fetchPosts() {
        viewModelScope.launch {
            val fetchedPosts = repository.getPosts()
            _posts.value = fetchedPosts
        }
    }
}

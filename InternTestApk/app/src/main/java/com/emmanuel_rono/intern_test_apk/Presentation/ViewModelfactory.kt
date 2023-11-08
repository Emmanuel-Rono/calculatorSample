package com.emmanuel_rono.intern_test_apk.Presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emmanuel_rono.intern_test_apk.PostRepository
import com.emmanuel_rono.intern_test_apk.dataViewModel

class PostViewModelFactory(private val repository: PostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(dataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return dataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

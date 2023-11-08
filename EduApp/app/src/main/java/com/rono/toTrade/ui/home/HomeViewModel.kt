package com.reemmousa.toTrade.ui.home

import android.app.Application
import com.reemmousa.toTrade.dataStructures.HomeCategory
import com.reemmousa.toTrade.repository.Repository
import com.reemmousa.toTrade.ui.allCourses.AllCoursesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: Repository,
    application: Application
) : AllCoursesViewModel(repository, application) {

    val homeCategories: List<HomeCategory> = repository.constants.homeCategories

}
package com.reemmousa.toTrade.dataSources.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reemmousa.toTrade.app.Constants.COURSES_TABLE
import com.reemmousa.toTrade.dataStructures.courses.ResponseCoursesList

@Entity(tableName = COURSES_TABLE)
class CoursesEntity(
    var responseCoursesList: ResponseCoursesList
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
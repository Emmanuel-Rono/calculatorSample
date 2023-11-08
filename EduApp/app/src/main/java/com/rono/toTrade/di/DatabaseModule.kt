package com.reemmousa.toTrade.di

import android.content.Context
import androidx.room.Room
import com.reemmousa.toTrade.app.Constants.DATABASE_NAME
import com.reemmousa.toTrade.dataSources.local.room.CoursesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CoursesDatabase {
        return Room.databaseBuilder(
            context,
            CoursesDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideCoursesDao(database: CoursesDatabase) = database.coursesDao()
}
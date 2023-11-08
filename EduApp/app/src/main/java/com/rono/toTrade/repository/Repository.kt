package com.reemmousa.toTrade.repository

import com.reemmousa.toTrade.dataSources.constants.ConstantsDataSource
import com.reemmousa.toTrade.dataSources.dataStore.DataStoreRepo
import com.reemmousa.toTrade.dataSources.local.LocalDataSource
import com.reemmousa.toTrade.dataSources.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource,
    constantsDataSource: ConstantsDataSource,
    dataStoreRepo: DataStoreRepo
) {
    val remote = remoteDataSource
    val constants = constantsDataSource
    val dataStoreRepository = dataStoreRepo
    val local = localDataSource
}
package com.hemp.works.dashboard.account.data.repository

import androidx.lifecycle.LiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.account.data.remote.AccountRemoteDataSource
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository  @Inject constructor(private val remoteDataSource: AccountRemoteDataSource) : BaseRepository() {


    private val _booleanResponse = LiveEvent<Boolean>()
    val booleanResponse: LiveData<Boolean>
        get() = _booleanResponse

    suspend fun logout() {

        getResult { remoteDataSource.logout() }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }
}
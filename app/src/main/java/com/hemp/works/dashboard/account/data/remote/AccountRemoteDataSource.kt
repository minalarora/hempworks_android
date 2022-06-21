package com.hemp.works.dashboard.account.data.remote

import com.hemp.works.base.BaseDataSource
import com.hemp.works.dashboard.home.data.remote.HomeService
import javax.inject.Inject

class AccountRemoteDataSource @Inject constructor(private val service: AccountService): BaseDataSource() {

    suspend fun logout() = getResult { service.logout() }
}
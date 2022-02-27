package com.hemp.works.dashboard.tnl.data.remote

import com.hemp.works.base.BaseDataSource
import javax.inject.Inject

class TNLRemoteDataSource @Inject constructor(private val service: TNLService): BaseDataSource() {

    suspend fun getLiveSessions() = getResult { service.getLiveSessions() }

    suspend fun getNewsLetters() = getResult { service.getNewsLetters() }

    suspend fun getTutorials() = getResult { service.getTutorials() }
}
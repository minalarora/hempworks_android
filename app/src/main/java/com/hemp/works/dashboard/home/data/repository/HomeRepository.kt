package com.hemp.works.dashboard.home.data.repository

import com.hemp.works.base.BaseRepository
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val remoteDataSource: HomeRemoteDataSource) : BaseRepository() {
}
package com.hemp.works.dashboard.search.data.repository

import com.hemp.works.base.BaseRepository
import com.hemp.works.dashboard.search.data.remote.SearchRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(private val remoteDataSource: SearchRemoteDataSource) : BaseRepository() {
}
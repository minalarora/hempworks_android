package com.hemp.works.dashboard.profile.data.repository

import com.hemp.works.base.BaseRepository
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import com.hemp.works.dashboard.profile.data.remote.ProfileRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val remoteDataSource: ProfileRemoteDataSource) : BaseRepository() {
}
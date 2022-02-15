package com.hemp.works.dashboard.prescription.data.repository

import com.hemp.works.base.BaseRepository
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import com.hemp.works.dashboard.prescription.data.remote.PrescriptionRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrescriptionRepository  @Inject constructor(private val remoteDataSource: PrescriptionRemoteDataSource) : BaseRepository()  {
}
package com.hemp.works.dashboard.profile.data.remote

import com.hemp.works.base.BaseDataSource
import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(private val service: ProfileService): BaseDataSource() {
}
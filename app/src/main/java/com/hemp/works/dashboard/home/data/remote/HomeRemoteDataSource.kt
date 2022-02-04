package com.hemp.works.dashboard.home.data.remote

import com.hemp.works.base.BaseDataSource
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(private val service: HomeService): BaseDataSource() {

    suspend fun fetchBanners() = getResult { service.fetchBanners() }

    suspend fun fetchCategories() = getResult { service.fetchCategories() }

}
package com.hemp.works.dashboard.home.data.remote

import com.hemp.works.base.BaseDataSource
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(private val service: HomeService): BaseDataSource() {

    suspend fun fetchBanners() = getResult { service.fetchBanners() }

    suspend fun fetchCategories() = getResult { service.fetchCategories() }

    suspend fun fetchAllProducts() = getResult { service.fetchAllProducts() }

    suspend fun fetchBestSellerProducts() = getResult { service.fetchBestSellerProducts() }

    suspend fun fetchProductsByCategory(category: Long) = getResult { service.fetchProductsByCategory(category) }

}
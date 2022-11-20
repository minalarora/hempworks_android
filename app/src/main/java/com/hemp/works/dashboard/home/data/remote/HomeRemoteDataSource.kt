package com.hemp.works.dashboard.home.data.remote

import com.hemp.works.base.BaseDataSource
import com.hemp.works.dashboard.model.Instagram
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(private val service: HomeService): BaseDataSource() {

    suspend fun fetchBanners() = getResult { service.fetchBanners() }

    suspend fun fetchOffer() = getResult { service.fetchOffer() }

    suspend fun fetchCategories() = getResult { service.fetchCategories() }

    suspend fun fetchAllProducts() = getResult { service.fetchAllProducts() }

    suspend fun fetchAllBlogs() = getResult { service.fetchAllBlogs() }

    suspend fun fetchBestSellerProducts() = getResult { service.fetchBestSellerProducts() }

    suspend fun fetchProductsByCategory(category: Long) = getResult { service.fetchProductsByCategory(category) }

    suspend fun logout() = getResult { service.logout() }

    suspend fun fetchInstagram() = getResult { service.fetchInstagram() }

    suspend fun getPendingAmount() = getResult { service.getPendingAmount() }
}
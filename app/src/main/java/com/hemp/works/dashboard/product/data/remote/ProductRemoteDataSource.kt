package com.hemp.works.dashboard.product.data.remote

import com.hemp.works.base.BaseDataSource
import com.hemp.works.dashboard.home.data.remote.HomeService
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(private val service: ProductService): BaseDataSource() {

    suspend fun getProductById(id: Long) = getResult { service.getProductById(id) }

    suspend fun fetchProductsByCategory(category: Long) = getResult { service.fetchProductsByCategory(category) }


}
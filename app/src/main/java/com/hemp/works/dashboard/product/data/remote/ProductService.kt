package com.hemp.works.dashboard.product.data.remote

import com.hemp.works.dashboard.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("/v1/product/singleproduct")
    suspend fun getProductById(@Query("id") id: Long): Response<Product>

    @GET("/v1/product/category")
    suspend fun fetchProductsByCategory(@Query("category") category: Long): Response<List<Product>>
}
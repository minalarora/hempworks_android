package com.hemp.works.dashboard.home.data.remote

import com.hemp.works.dashboard.model.Banner
import com.hemp.works.dashboard.model.Category
import com.hemp.works.dashboard.model.Product
import com.hemp.works.login.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {

    @GET("/v1/banner")
    suspend fun fetchBanners(): Response<List<Banner>>

    @GET("/v1/category/all")
    suspend fun fetchCategories(): Response<List<Category>>

    @GET("/v1/product/all")
    suspend fun fetchAllProducts(): Response<List<Product>>

    @GET("/v1/product/bestseller")
    suspend fun fetchBestSellerProducts(): Response<List<Product>>

    @GET("/v1/product/category")
    suspend fun fetchProductsByCategory(@Query("category") category: Long): Response<List<Product>>


}
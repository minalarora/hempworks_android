package com.hemp.works.dashboard.home.data.remote

import com.hemp.works.dashboard.model.Banner
import com.hemp.works.dashboard.model.Category
import com.hemp.works.login.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {

    @GET("/v1/banner")
    suspend fun fetchBanners(): Response<List<Banner>>

    @GET("/v1/category/all")
    suspend fun fetchCategories(): Response<List<Category>>
}
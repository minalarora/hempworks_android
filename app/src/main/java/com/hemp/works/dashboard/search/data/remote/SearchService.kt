package com.hemp.works.dashboard.search.data.remote

import com.hemp.works.base.BooleanResponse
import com.hemp.works.dashboard.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/v1/product/search")
    suspend fun searchProducts(@Query("searchkey") searchkey: String): Response<List<Product>>

}
package com.hemp.works.dashboard.account.data.remote

import com.hemp.works.base.BooleanResponse
import retrofit2.Response
import retrofit2.http.GET

interface AccountService {

    @GET("/v1/doctor/logout")
    suspend fun logout(): Response<BooleanResponse>

}
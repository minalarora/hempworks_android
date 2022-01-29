package com.hemp.works.login.data.remote

import com.hemp.works.base.BooleanResponse
import com.hemp.works.login.data.model.Credential
import com.hemp.works.login.data.model.RequestDoctor
import com.hemp.works.login.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    @POST("/v1/login")
    suspend fun login(@Body credential: Credential): Response<User>

    @POST("/v1/doctor/create")
    suspend fun createDoctor(@Body doctor: RequestDoctor): Response<User>

    @GET("/v1/doctor/me")
    suspend fun fetchDoctor(): Response<User>

    @GET("/v1/admin/me")
    suspend fun fetchAdmin(): Response<User>

    @GET("/v1/doctor/updatepassword")
    suspend fun updatePassword(@Query("mobile") mobile: String, @Query("password") password: String): Response<BooleanResponse>

    @GET("/v1/verify/mobile")
    suspend fun verifyMobile(@Query("mobile") mobile: String): Response<BooleanResponse>

    @GET("/v1/verify/otp")
    suspend fun verifyOtp(@Query("mobile") mobile: String, @Query("otp") otp: Int): Response<BooleanResponse>


}
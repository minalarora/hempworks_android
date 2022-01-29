package com.hemp.works.login.data.remote

import com.hemp.works.base.BooleanResponse
import com.hemp.works.base.ImageResponse
import com.hemp.works.login.data.model.Credential
import com.hemp.works.login.data.model.RequestDoctor
import com.hemp.works.login.data.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.util.HashMap

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

    @Multipart
    @POST("/v1/upload/certificate")
    suspend fun uploadCertificate(@Part image: MultipartBody.Part): Response<ImageResponse>


}
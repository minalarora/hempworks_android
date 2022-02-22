package com.hemp.works.dashboard.profile.data.remote

import com.hemp.works.base.BooleanResponse
import com.hemp.works.base.ImageResponse
import com.hemp.works.login.data.model.User
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ProfileService {

    @PATCH("/v1/doctor/update/me")
    suspend fun updateUser(@Body body: HashMap<String,String>) : Response<BooleanResponse>

    @Multipart
    @POST("/v1/upload/image")
    suspend fun uploadProfile(@Part image: MultipartBody.Part): Response<ImageResponse>

    @GET("/v1/verify/mobile")
    suspend fun verifyMobile(@Query("mobile") mobile: String): Response<BooleanResponse>

    @GET("/v1/verify/otp")
    suspend fun verifyOtp(@Query("mobile") mobile: String, @Query("otp") otp: Int): Response<BooleanResponse>

    @GET("/v1/doctor/me")
    suspend fun fetchDoctor(): Response<User>

    @DELETE("/v1/doctor/delete")
    suspend fun deleteDoctor(): Response<BooleanResponse>


}
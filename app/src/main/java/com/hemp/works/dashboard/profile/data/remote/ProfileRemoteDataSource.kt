package com.hemp.works.dashboard.profile.data.remote

import com.google.gson.JsonObject
import com.hemp.works.base.BaseDataSource
import okhttp3.MultipartBody
import org.json.JSONObject
import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(private val service: ProfileService): BaseDataSource() {

    suspend fun updateEmail(email: String) = getResult { service.updateUser(hashMapOf("email" to email)) }

    suspend fun updateMobile(mobile: String) = getResult { service.updateUser(hashMapOf("mobile" to mobile)) }

    suspend fun updateProfile(url: String) = getResult { service.updateUser(hashMapOf("profile" to url)) }

    suspend fun verifyMobile(mobile: String) = getResult { service.verifyMobile(mobile) }

    suspend fun verifyOtp(mobile: String, otp: Int) = getResult { service.verifyOtp(mobile, otp) }

    suspend fun uploadProfile(image: MultipartBody.Part) = getResult { service.uploadProfile(image) }

    suspend fun fetchDoctor() = getResult { service.fetchDoctor() }
}
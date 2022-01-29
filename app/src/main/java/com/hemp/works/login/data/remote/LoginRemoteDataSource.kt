package com.hemp.works.login.data.remote

import com.hemp.works.base.BaseDataSource
import com.hemp.works.base.BooleanResponse
import com.hemp.works.login.data.model.Credential
import com.hemp.works.login.data.model.RequestDoctor
import com.hemp.works.login.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(private val service: LoginService): BaseDataSource() {


    suspend fun login(credential: Credential) = getResult { service.login(credential) }

    suspend fun createDoctor(doctor: RequestDoctor) = getResult { service.createDoctor(doctor) }

    suspend fun updatePassword(mobile: String, password: String) = getResult { service.updatePassword(mobile, password) }

    suspend fun verifyMobile(mobile: String) = getResult { service.verifyMobile(mobile) }

    suspend fun verifyOtp(mobile: String, otp: Int) = getResult { service.verifyOtp(mobile, otp) }

    suspend fun fetchDoctor() = getResult { service.fetchDoctor() }

    suspend fun fetchAdmin() = getResult { service.fetchAdmin() }
}
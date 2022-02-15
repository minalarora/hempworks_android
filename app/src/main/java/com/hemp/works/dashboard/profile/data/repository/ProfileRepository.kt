package com.hemp.works.dashboard.profile.data.repository

import androidx.lifecycle.LiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.base.ImageResponse
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import com.hemp.works.dashboard.profile.data.remote.ProfileRemoteDataSource
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val remoteDataSource: ProfileRemoteDataSource) : BaseRepository() {

    private val _booleanResponse = LiveEvent<Boolean>()
    val booleanResponse: LiveData<Boolean>
        get() = _booleanResponse

    suspend fun updateProfile(image: MultipartBody.Part) {
        getResult(Constants.UNABLE_TO_UPLOAD_IMAGE) { remoteDataSource.uploadProfile(image)}?.let {
            it.data?.let { imageResponse ->
                if (imageResponse.success) {
                    getResult(Constants.UNABLE_TO_UPLOAD_IMAGE) { remoteDataSource.updateProfile(imageResponse.url) }
                        ?.let {
                            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
                        }
                } else {
                    _booleanResponse.postValue(false)
                }
            }
        }
    }

    suspend fun sendOTP(mobile: String) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.verifyMobile(mobile) }
    }

    suspend fun updateMobile(mobile: String, otp: Int) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.verifyOtp(mobile, otp) }?.let {
            it.data?.let { booleanResponse ->
                if (booleanResponse.success) {
                    getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.updateMobile(mobile)}
                        ?.let {
                            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
                        }
                } else {
                    _error.postValue(Constants.INVALID_OTP)
                }
            }
        }
    }

    suspend fun updateEmail(email: String) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.updateEmail(email) }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }



}
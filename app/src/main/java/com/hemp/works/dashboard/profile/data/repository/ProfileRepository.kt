package com.hemp.works.dashboard.profile.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.base.ImageResponse
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import com.hemp.works.dashboard.profile.data.remote.ProfileRemoteDataSource
import com.hemp.works.login.data.model.User
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val remoteDataSource: ProfileRemoteDataSource) : BaseRepository() {

    private val _booleanResponse = LiveEvent<Boolean>()
    val booleanResponse: LiveData<Boolean>
        get() = _booleanResponse

    private val _booleanResponseForDelete = LiveEvent<Boolean>()
    val booleanResponseForDelete: LiveData<Boolean>
        get() = _booleanResponseForDelete

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    suspend fun fetchDoctor() {
        getResult{ remoteDataSource.fetchDoctor() }?.let {
            it.data?.let { user -> _user.postValue(user) }
        }
    }

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
        getResult(Constants.GENERAL_ERROR_MESSAGE, handleLoading = false) { remoteDataSource.verifyMobile(mobile) }
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

    suspend fun deleteDoctor() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.deleteDoctor() }?.let {
            it.data?.let { booleanResponse -> _booleanResponseForDelete.postValue(booleanResponse.success) }
        }
    }


}
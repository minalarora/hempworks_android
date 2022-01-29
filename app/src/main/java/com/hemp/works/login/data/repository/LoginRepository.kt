package com.hemp.works.login.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.*
import com.hemp.works.login.data.model.Credential
import com.hemp.works.login.data.model.User
import com.hemp.works.login.data.remote.LoginRemoteDataSource
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val remoteDataSource: LoginRemoteDataSource) : BaseRepository(){


   private val _user = LiveEvent<User>()
   val user: LiveData<User>
      get() = _user

   private val _booleanResponse = LiveEvent<Boolean>()
   val booleanResponse: LiveData<Boolean>
      get() = _booleanResponse

   private val _imageUrl = LiveEvent<String>()
   val imageUrl: LiveData<String>
      get() = _imageUrl

   suspend fun fetchAdmin() {
      getResult{ remoteDataSource.fetchAdmin() }?.let {
         it.data?.let { user -> _user.postValue(user) }
      }
   }

   suspend fun fetchDoctor() {
      getResult{ remoteDataSource.fetchDoctor() }?.let {
         it.data?.let { user -> _user.postValue(user) }
      }
   }

   suspend fun login(credential: Credential) {

      getResult(Constants.INVALID_USERNAME_OR_PASSWORD) { remoteDataSource.login(credential) }?.let {
         it.data?.let { user -> _user.postValue(user) }
      }
   }

   suspend fun verifyMobile(mobile: String) {
      getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.verifyMobile(mobile) }?.let {
         it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
      }
   }

   suspend fun verifyOtp(mobile: String, otp: Int) {
      getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.verifyOtp(mobile, otp) }?.let {
         it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
      }
   }

   suspend fun uploadCertificate(image: MultipartBody.Part) {
      getResult(Constants.UNABLE_TO_UPLOAD_IMAGE) { remoteDataSource.uploadCertificate(image)}?.let {
            it.data?.let { imageResponse -> _imageUrl.postValue(imageResponse.url)  }
      }
   }



}
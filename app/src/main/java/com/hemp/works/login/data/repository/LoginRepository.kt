package com.hemp.works.login.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.*
import com.hemp.works.login.data.model.Credential
import com.hemp.works.login.data.model.User
import com.hemp.works.login.data.remote.LoginRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val remoteDataSource: LoginRemoteDataSource) : BaseRepository(){


   private val _user = LiveEvent<User>()
   val user: LiveData<User>
      get() = _user


   suspend fun fetchAdmin() {
      getResult{ remoteDataSource.fetchAdmin() }?.let {
         it.data?.let { user ->
            _user.postValue(user)
         } ?: {
            _error.postValue("")
         }
      }
   }

   suspend fun fetchDoctor() {
      getResult{ remoteDataSource.fetchDoctor() }?.let {
         it.data?.let { user ->
            _user.postValue(user)
         } ?: {
            _error.postValue("")
         }
      }
   }

   suspend fun login(credential: Credential) {

      getResult { remoteDataSource.login(credential) }?.let {
         it.data?.let { user ->
            _user.postValue(user)
         } ?: {
            _error.postValue(Constants.GENERAL_ERROR_MESSAGE)
         }
      }
   }

//    val user = fun (credential: Credential) = resultLiveData { remoteDataSource.login(credential) }

//   var _user: MutableLiveData<Result<User>> = MutableLiveData()
//
//   var user: LiveData<Result<User>> = _user
//
//
//      suspend fun getUser (credential: Credential) {
//         getResult {
//            remoteDataSource.login(credential) }?.let {
//            _user.postValue(it)
//         }
//      }








   //val user = fun (credential: Credential) = resultLiveData { remoteDataSource.login(credential) }


//    suspend fun createDoctor(doctor: RequestDoctor) = getResult { service.createDoctor(doctor) }
//
//    suspend fun updatePassword(mobile: String, password: String) = getResult { service.updatePassword(mobile, password) }
//
//    suspend fun verifyMobile(mobile: String) = getResult { service.verifyMobile(mobile) }
//
//    suspend fun verifyOtp(mobile: String, otp: Int) = getResult { service.verifyOtp(mobile, otp) }
}
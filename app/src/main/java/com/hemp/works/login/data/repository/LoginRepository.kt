package com.hemp.works.login.data.repository

import com.hemp.works.login.data.remote.LoginRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val remoteDataSource: LoginRemoteDataSource) {


}
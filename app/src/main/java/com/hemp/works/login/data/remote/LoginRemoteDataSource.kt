package com.hemp.works.login.data.remote

import com.hemp.works.base.BaseDataSource
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(private val service: LoginService): BaseDataSource() {

}
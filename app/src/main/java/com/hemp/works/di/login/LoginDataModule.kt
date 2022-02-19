package com.hemp.works.di.login

import com.hemp.works.login.data.remote.LoginRemoteDataSource
import com.hemp.works.login.data.remote.LoginService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Suppress("unused")
@Module
class LoginDataModule {

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService = retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun provideLoginRemoteDataSource(service: LoginService)
            = LoginRemoteDataSource(service)
}
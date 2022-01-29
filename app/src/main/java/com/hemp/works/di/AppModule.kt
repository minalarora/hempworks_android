package com.hemp.works.di

import android.app.Application
import android.content.Context
import com.hemp.works.base.AppDatabase
import com.hemp.works.login.data.remote.LoginRemoteDataSource
import com.hemp.works.login.data.remote.LoginService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class, NetworkModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application) = AppDatabase.getInstance(app)


    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService = retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun provideLoginRemoteDataSource(service: LoginService)
            = LoginRemoteDataSource(service)

}
package com.hemp.works.di

import android.app.Application
import android.content.Context
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.hemp.works.base.AppDatabase
import com.hemp.works.dashboard.calculator.data.remote.CalculatorRemoteDataSource
import com.hemp.works.dashboard.calculator.data.remote.CalculatorService
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import com.hemp.works.dashboard.home.data.remote.HomeService
import com.hemp.works.dashboard.prescription.data.remote.PrescriptionRemoteDataSource
import com.hemp.works.dashboard.prescription.data.remote.PrescriptionService
import com.hemp.works.dashboard.product.data.remote.ProductRemoteDataSource
import com.hemp.works.dashboard.product.data.remote.ProductService
import com.hemp.works.dashboard.profile.data.remote.ProfileRemoteDataSource
import com.hemp.works.dashboard.profile.data.remote.ProfileService
import com.hemp.works.dashboard.search.data.remote.SearchRemoteDataSource
import com.hemp.works.dashboard.search.data.remote.SearchService
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

    @Provides
    fun provideSnapHelper(): SnapHelper = LinearSnapHelper()

    @Provides
    fun providePagerSnapHelper(): PagerSnapHelper = PagerSnapHelper()

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService = retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun provideLoginRemoteDataSource(service: LoginService)
            = LoginRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideHomeService(retrofit: Retrofit): HomeService = retrofit.create(HomeService::class.java)

    @Singleton
    @Provides
    fun provideHomeRemoteDataSource(service: HomeService)
            = HomeRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideSearchService(retrofit: Retrofit): SearchService = retrofit.create(SearchService::class.java)

    @Singleton
    @Provides
    fun provideSearchRemoteDataSource(service: SearchService)
            = SearchRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService = retrofit.create(ProductService::class.java)

    @Singleton
    @Provides
    fun provideProductRemoteDataSource(service: ProductService)
            = ProductRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideProfileService(retrofit: Retrofit): ProfileService = retrofit.create(ProfileService::class.java)

    @Singleton
    @Provides
    fun provideProfileRemoteDataSource(service: ProfileService)
            = ProfileRemoteDataSource(service)

    @Singleton
    @Provides
    fun providePrescriptionService(retrofit: Retrofit): PrescriptionService = retrofit.create(PrescriptionService::class.java)

    @Singleton
    @Provides
    fun providePrescriptionRemoteDataSource(service: PrescriptionService)
            = PrescriptionRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideCalculatorService(retrofit: Retrofit): CalculatorService = retrofit.create(CalculatorService::class.java)

    @Singleton
    @Provides
    fun provideCalculatorRemoteDataSource(service: CalculatorService)
            = CalculatorRemoteDataSource(service)



}
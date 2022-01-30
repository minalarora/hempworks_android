package com.hemp.works.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.hemp.works.BuildConfig
import com.hemp.works.base.AuthInterceptor
import com.hemp.works.base.Constants
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor, authInterceptor: AuthInterceptor): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()

    @Provides
    fun provideLoggingInterceptor() =
            HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) BODY else NONE }

    @Provides
    fun provideAuthInterceptor(sharedPreferences: SharedPreferences) =
        AuthInterceptor(sharedPreferences)


    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
            GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRetrofitBuilder(okhttpClient: OkHttpClient,
                               converterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()


}
package com.minal.admin.di_coin


import com.app.koyn.data.remote.interceptor.NetworkConnectionInterceptor
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.minal.admin.BuildConfig
import com.minal.admin.data.remote.RestApi
import com.minal.admin.data.remote.RestConstant
import com.minal.admin.utils.NULL_TO_EMPTY_STRING_ADAPTER
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created at 30/03/2020.
 */

val networkModule = module {
    single {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.HEADERS
            HttpLoggingInterceptor.Level.BODY
        }
        else HttpLoggingInterceptor.Level.NONE

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(NetworkConnectionInterceptor(androidContext()))
        httpClient.addInterceptor(ChuckerInterceptor(androidContext()))
        httpClient.addInterceptor(loggingInterceptor)

       // httpClient.addInterceptor(HttpInterceptor(androidContext()))

        httpClient.connectTimeout(RestConstant.API_TIME_OUT.toLong(), TimeUnit.SECONDS)
        httpClient.readTimeout(RestConstant.API_TIME_OUT.toLong(), TimeUnit.SECONDS)
        httpClient.writeTimeout(RestConstant.API_TIME_OUT.toLong(), TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder().build()
            chain.proceed(request)
        }

        val moshi = Moshi.Builder()
            .add(NULL_TO_EMPTY_STRING_ADAPTER)
            .add(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
                .baseUrl(RestConstant.BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build().create(RestApi::class.java)

    }
}

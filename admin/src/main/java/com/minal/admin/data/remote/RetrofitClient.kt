package com.minal.admin.data.remote

import com.minal.admin.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitClient {


    //    const val MainServer = "https://stark-island-35960.herokuapp.com"

//    private val loggingInterceptor = HttpLoggingInterceptor()

    val retrofitClient: Retrofit.Builder by lazy {

        //if there is no instance available... create new one
       val loggingInterceptor =  HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }

        val httpClientBuilder =  OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
//        httpClientBuilder.addInterceptor(object : Interceptor {
//            @Throws(IOException::class)
//            override fun intercept(chain: Interceptor.Chain): Response {
//                val requestBuilder: Request.Builder = chain.request().newBuilder()
//                requestBuilder.header("Content-Type", "application/json")
//                requestBuilder.header("Accept", "application/json")
//                requestBuilder.header("X-API-KEY", "cw00ggcsw4co0g804gcggwo088g4kokgk88sso4s")
//                return chain.proceed(requestBuilder.build())
//            }
//        })

//        loggingInterceptor.apply { loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
//        httpClientBuilder.addInterceptor(loggingInterceptor)

        val httpClient: OkHttpClient = httpClientBuilder.build()

        Retrofit.Builder()
            .baseUrl(RestConstant.BASE_URL)
            .client(httpClient)
//            .addCallAdapterFactory(CoroutineCa)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: RestApi by lazy {
        retrofitClient
            .build()
            .create(RestApi::class.java)
    }

}
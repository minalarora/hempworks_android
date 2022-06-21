package com.hoobio.data.network

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.minal.admin.R
import com.minal.admin.utils.ErrorUtils
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response
import retrofit2.http.HTTP
import java.net.HttpURLConnection


//T is Generic and will store the api status
abstract class DataFetchCall<T>(private val responseLiveData: MutableLiveData<ApiResponse<T>>) :
        KoinComponent {

   /* private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable !is CancellationException) {
            println(throwable.message)
            responseLiveData.postValue(
                    ApiResponse.error(
                            ApiResponse.Error(
                                    500,
                                    app.getString(R.string.error_server_not_reachable)
                            )
                    )
            )
        }
    }
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineExceptionHandler*/


    val app: Application by inject()
    abstract suspend fun createCallAsync(): Response<T>?
    open suspend fun saveResult(resultData: T) {}
    open fun shouldFetchFromDB(): Boolean = false
    open suspend fun loadFromDB(): T? = null


    suspend fun execute() {
        try {
            if (shouldFetchFromDB()) {
                responseLiveData.postValue(ApiResponse.loading())
                val response = loadFromDB()
                if (response != null) {
                    saveResult(response)
                    responseLiveData.postValue(ApiResponse.success(response))
                } else throw Exception()
            } else {
                responseLiveData.postValue(ApiResponse.loading())
                val response = createCallAsync()
                if (response?.body() != null && (response.code() == HttpURLConnection.HTTP_OK || response.code() == HttpURLConnection.HTTP_CREATED)) {
                    saveResult(response.body()!!)
                    responseLiveData.postValue(ApiResponse.success(response.body()!!))
                } else {
                    if (response != null) {
                        if (response.code() != 401) {
                             val message = ErrorUtils.getError(response.code(), response.errorBody())
                            responseLiveData.postValue(
                                    ApiResponse.error(
                                            ApiResponse.Error(
                                                    response.code(),
                                                    message,
                                                    response.errorBody().toString()
                                            )
                                    )
                            )
                        }
                    } else {
                        responseLiveData.postValue(ApiResponse.error(
                                ApiResponse.Error(
                                        0,
                                        "Server error Try Again !!",
                                        ""
                                )))

                    }
                }
            }
        } catch (e: Exception) {
            if (e !is CancellationException) {
                println(e.message)
                responseLiveData.postValue(
                        ApiResponse.error(
                                ApiResponse.Error(
                                        500,
                                        app.getString(R.string.error_server_not_reachable)
                                )
                        )
                )
            }
        }


    }
}
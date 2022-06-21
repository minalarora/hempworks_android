package com.hoobio.data.network

import org.koin.core.KoinComponent


class ApiResponse<T>(val status: Status, val data: T? = null, val error: Error?=null) : KoinComponent {
    companion object {
        fun <T> loading(): ApiResponse<T> {
            return ApiResponse(Status.LOADING)
        }

        fun <T> success(data: T): ApiResponse<T>? {
            return ApiResponse(Status.SUCCESS, data)
        }


        fun <T> error(error: Error?): ApiResponse<T> {
            return ApiResponse(Status.ERROR, error =  error)
        }

    }

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
    }




    class Error(val code: Int, val message: String, val errorBody: String = "")
}
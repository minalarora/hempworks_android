package com.hemp.works.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRepository {

    protected val _error = LiveEvent<String>()
    val error: LiveData<String>
        get() = _error

    protected val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    protected suspend fun <T> getResult( error: String? = null, call: suspend () -> Result<T>) : Result<T>? {

        return try {

            _loading.postValue(true)
            val result = call.invoke()
            _loading.postValue(false)

            if (result.status == Result.Status.SUCCESS && result.data != null) {
                result
            } else {
                _error.postValue(error ?: result.message!!)
                null
            }
        } catch (e: Exception) {
            _error.postValue(error ?: e.message)
            null
        }
    }

}
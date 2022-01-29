package com.hemp.works.base

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel(vararg repositories: BaseRepository): ViewModel()  {

    val loading: MediatorLiveData<Boolean> = MediatorLiveData()

    val error: MediatorLiveData<String> = LiveEvent()

    private val viewModelError: LiveEvent<String> = LiveEvent()

    init {

        repositories.forEach {

            loading.addSource(it.loading) { boolean ->
                loading.postValue(boolean)
            }

            error.addSource(it.error) { msg ->
                error.postValue(msg)
            }
        }

        error.addSource(viewModelError) { msg ->
            error.postValue(msg)
        }
    }

    fun error(msg: String = Constants.GENERAL_ERROR_MESSAGE) {
        viewModelError.postValue(msg)
    }

}
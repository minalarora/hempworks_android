package com.hemp.works.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel(repository: BaseRepository): ViewModel()  {

    val error: LiveData<String> = repository.error

    val loading: LiveData<Boolean> = repository.loading

}
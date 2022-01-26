package com.hemp.works.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface BaseViewModel  {

    val error: SingleLiveEvent<String>
        get() = SingleLiveEvent()

    val _loading: MutableLiveData<Boolean>
        get() = MutableLiveData(false)

    val loading: LiveData<Boolean>
        get() = _loading

}
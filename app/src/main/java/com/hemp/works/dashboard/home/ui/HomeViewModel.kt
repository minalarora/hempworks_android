package com.hemp.works.dashboard.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.home.data.repository.HomeRepository
import com.hemp.works.login.data.repository.LoginRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel(repository) {

    val bannerList = repository.bannerList
    val categoryList = repository.categoryList

    private val _scroll = MutableLiveData<Int>(0)
    val scroll: LiveData<Int> = _scroll

    private var _job: Job? = null

    init {
        viewModelScope.launch {
            repository.fetchHomeData()
        }
    }

    fun startScrolling() {
        _job?.cancel()
        _job = viewModelScope.launch {
            while (true) {
                delay(2000)

                _scroll.value?.let {
                    if (it == 2) _scroll.postValue(0)
                    else _scroll.postValue(it+1)
                }
            }
        }
    }


}
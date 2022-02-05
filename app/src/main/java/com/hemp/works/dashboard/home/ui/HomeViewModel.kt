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
    val bestSellerProductList = repository.bestSellerProductList
    val allProductList = repository.allProductList

    private val _scroll = MutableLiveData<Int>(0)
    val scroll: LiveData<Int> = _scroll
    private val _bannerVisibility = MutableLiveData(false)
    val bannerVisibility: LiveData<Boolean> = _bannerVisibility
    private val _categoryVisibility = MutableLiveData(false)
    val categoryVisibility: LiveData<Boolean> = _categoryVisibility
    private val _bestSellerProductVisibility = MutableLiveData(false)
    val bestSellerProductVisibility: LiveData<Boolean>  = _bestSellerProductVisibility
    private val _allProductVisibility= MutableLiveData(false)
    val allProductVisibility: LiveData<Boolean>  = _allProductVisibility

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
                    if (it == (bannerList.value?.size!! - 1)) _scroll.postValue(0)
                    else _scroll.postValue(it+1)
                }
            }
        }
    }

    fun handleBannerVisibility(isEmpty: Boolean) {
        _bannerVisibility.postValue(!isEmpty)
    }

    fun handleCategoryVisibility(isEmpty: Boolean) {
        _categoryVisibility.postValue(!isEmpty)
    }

    fun handleBSProductVisibility(isEmpty: Boolean) {
        _bestSellerProductVisibility.postValue(!isEmpty)
    }

    fun handleAllProductVisibility(isEmpty: Boolean) {
        _allProductVisibility.postValue(!isEmpty)
    }


}
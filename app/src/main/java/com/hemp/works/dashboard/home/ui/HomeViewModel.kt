package com.hemp.works.dashboard.home.ui

import android.opengl.Visibility
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
    val instagramList = repository.instagramList
    val blogList = repository.blogList
    val booleanResponse = repository.
    booleanResponse
    val imageResponse = repository.imageResponse
    val pendingAmount = repository.pendingAmount

    private val _scroll = MutableLiveData<Int>(0)
    val scroll: LiveData<Int> = _scroll

    private val _bannerVisibility = MutableLiveData(false)
    val bannerVisibility: LiveData<Boolean> = _bannerVisibility

    private val _offerVisibility = MutableLiveData(false)
    val offerVisibility: LiveData<Boolean> = _offerVisibility

    private val _categoryVisibility = MutableLiveData(false)
    val categoryVisibility: LiveData<Boolean> = _categoryVisibility

    private val _bestSellerProductVisibility = MutableLiveData(false)
    val bestSellerProductVisibility: LiveData<Boolean>  = _bestSellerProductVisibility

    private val _allProductVisibility= MutableLiveData(false)
    val allProductVisibility: LiveData<Boolean>  = _allProductVisibility

    private val _instagramVisibility= MutableLiveData(false)
    val instagramVisibility: LiveData<Boolean>  = _instagramVisibility

    private val _blogVisibility= MutableLiveData(false)
    val blogVisibility: LiveData<Boolean>  = _blogVisibility

    private var _job: Job? = null

    init {
        loading.value = true
        viewModelScope.launch {
            repository.fetchHomeData()
        }
    }

    fun startScrolling() {
        _job?.cancel()
        _job = viewModelScope.launch {
            while (true) {
                delay(3000)

                _scroll.value?.let {
                    if (it == (bannerList.value?.size!! - 1)) _scroll.postValue(0)
                    else _scroll.postValue(it+1)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getPendingAmount() {
        viewModelScope.launch {
            repository.fetchPendingAmount()
        }
    }

    fun handleBannerVisibility(isEmpty: Boolean) {
        _bannerVisibility.postValue(!isEmpty)
    }

    fun handleOfferVisibility(visibility: Boolean) {
        _offerVisibility.postValue(visibility)
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

    fun handleInstagramVisibility(isEmpty: Boolean) {
        _instagramVisibility.postValue(!isEmpty)
    }

    fun handleBlogVisibility(isEmpty: Boolean) {
        _blogVisibility.postValue(!isEmpty)
    }

}
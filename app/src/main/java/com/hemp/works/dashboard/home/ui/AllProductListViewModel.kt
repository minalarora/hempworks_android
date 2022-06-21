package com.hemp.works.dashboard.home.ui

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.home.data.repository.HomeRepository
import com.hemp.works.dashboard.model.Product
import com.hemp.works.login.data.repository.LoginRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class AllProductListViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel(repository) {

    val productList = repository.allProductList

    private val _allProductVisibility= MutableLiveData(false)
    val allProductVisibility: LiveData<Boolean> = _allProductVisibility


    fun handleAllProductVisibility(isEmpty: Boolean) {
        _allProductVisibility.postValue(!isEmpty)
    }

    private fun fetchAllProducts() {

        loading.value = true
        viewModelScope.launch {
            repository.fetchAllProducts()
        }
    }

    init {
        fetchAllProducts()
    }

}
package com.hemp.works.dashboard.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.search.data.repository.SearchRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: SearchRepository) : BaseViewModel(repository) {

    val productList = repository.productList

    private var _job: Job? = null

    private val _allProductVisibility= MutableLiveData(false)
    val allProductVisibility: LiveData<Boolean> = _allProductVisibility

    fun search(text: String) {
        if (text.isBlank()) {
            return
        }
        _job?.cancel()
        _job = viewModelScope.launch {
            repository.searchProducts(text)
        }
    }

    fun handleAllProductVisibility(isEmpty: Boolean) {
        _allProductVisibility.postValue(!isEmpty)
    }
}
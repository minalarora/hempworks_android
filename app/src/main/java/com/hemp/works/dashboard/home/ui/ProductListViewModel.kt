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

class ProductListViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel(repository) {

    val productList = repository.productsByCategory.map { modifyListForSearchResult(it) }
    val bannerList = repository.bannerList

    private val _allProductVisibility= MutableLiveData(false)
    val allProductVisibility: LiveData<Boolean> = _allProductVisibility
    private val _scroll = MutableLiveData<Int>(0)
    val scroll: LiveData<Int> = _scroll
    private val _bannerVisibility = MutableLiveData(false)
    val bannerVisibility: LiveData<Boolean> = _bannerVisibility
    private val _title = MutableLiveData("")
    val title: LiveData<String> = _title

    private var _job: Job? = null

    var productId: String? = null

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

    fun handleBannerVisibility(isEmpty: Boolean) {
        _bannerVisibility.postValue(!isEmpty)
    }

    fun handleAllProductVisibility(isEmpty: Boolean) {
        _allProductVisibility.postValue(!isEmpty)
    }

    fun fetchProductsByCategory(category: String) {

        if (!category.isDigitsOnly()) {
            error(Constants.GENERAL_ERROR_MESSAGE)
            return
        }

        viewModelScope.launch {
            repository.fetchProductsByCategory(category.toLong())
        }
    }

    fun setHeader(product: Product) {
        if (productId == null) {
            _title.postValue("Showing results for '${product.categoryname}'")
        } else {
            _title.postValue("Showing results for '${product.title}'")
        }
    }
    private fun modifyListForSearchResult(list: List<Product>) : List<Product> {
      if (productId != null && productId?.isDigitsOnly() == true) {
            Collections.swap(list, 0 , list.indexOfFirst { productId?.toLong() == it.id })
        }
        return list
    }
}
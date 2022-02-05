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
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class ProductListViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel(repository) {

    val productList = repository.productsByCategory.map { modifyListForSearchResult(it) }

    private val _allProductVisibility= MutableLiveData(false)
    val allProductVisibility: LiveData<Boolean> = _allProductVisibility

    var productId: String? = null

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

    private fun modifyListForSearchResult(list: List<Product>) : List<Product> {
      if (productId != null && productId?.isDigitsOnly() == true) {
            Collections.swap(list, 0 , list.indexOfFirst { productId?.toLong() == it.id })
        }
        return list
    }
}
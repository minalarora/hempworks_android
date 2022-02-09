package com.hemp.works.dashboard.product.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.base.ImageResponse
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import com.hemp.works.dashboard.model.Category
import com.hemp.works.dashboard.model.Product
import com.hemp.works.dashboard.product.data.remote.ProductRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

class ProductRepository @Inject constructor(private val remoteDataSource: ProductRemoteDataSource) : BaseRepository() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    private val _productsByCategory = MutableLiveData<List<Product>>()
    val productsByCategory: LiveData<List<Product>>
        get() = _productsByCategory

    suspend fun getProductById(id: Long) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.getProductById(id) }?.let {
            it.data?.let { product -> _product.postValue(product) }
        }
    }

    suspend fun getProductsByCategory(category: Long) {
        getResult(Constants.GENERAL_ERROR_MESSAGE){ remoteDataSource.fetchProductsByCategory(category) }?.let {
            it.data?.let { list -> _productsByCategory.postValue(list) }
        }
    }
}
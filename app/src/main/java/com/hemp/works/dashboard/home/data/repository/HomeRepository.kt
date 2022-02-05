package com.hemp.works.dashboard.home.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import com.hemp.works.dashboard.model.Banner
import com.hemp.works.dashboard.model.Category
import com.hemp.works.dashboard.model.Product
import com.hemp.works.login.data.model.User
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val remoteDataSource: HomeRemoteDataSource) : BaseRepository() {

    private val _bannerList = MutableLiveData<List<Banner>>()
    val bannerList: LiveData<List<Banner>>
        get() = _bannerList

    private val _categoryList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>>
        get() = _categoryList

    private val _bestSellerProductList = MutableLiveData<List<Product>>()
    val bestSellerProductList: LiveData<List<Product>>
        get() = _bestSellerProductList

    private val _allProductList = MutableLiveData<List<Product>>()
    val allProductList: LiveData<List<Product>>
        get() = _allProductList

    private val _productsByCategory = LiveEvent<List<Product>>()
    val productsByCategory: LiveData<List<Product>>
        get() = _productsByCategory

    suspend fun fetchHomeData() {

        coroutineScope {
            _loading.postValue(true)

            val deferredBannerList = async { getResult(handleLoading = false) { remoteDataSource.fetchBanners() }  }
            val deferredCategoryList = async { getResult(handleLoading = false) { remoteDataSource.fetchCategories() } }
            val deferredBestSellerProductList = async { getResult(handleLoading = false) { remoteDataSource.fetchBestSellerProducts() } }
            val deferredAllProductList = async { getResult(handleLoading = false) { remoteDataSource.fetchAllProducts() } }


            deferredBannerList.await()?.let {
                it.data?.let { list -> _bannerList.postValue(list) }
            }
            deferredCategoryList.await()?.let {
                it.data?.let { list -> _categoryList.postValue(list) }
            }
            deferredBestSellerProductList.await()?.let {
                it.data?.let { list -> _bestSellerProductList.postValue(list) }
            }
            deferredAllProductList.await()?.let {
                it.data?.let { list -> _allProductList.postValue(list) }
            }

            _loading.postValue(false)
        }
    }

    suspend fun fetchProductsByCategory(category: Long) {
        getResult(Constants.GENERAL_ERROR_MESSAGE){ remoteDataSource.fetchProductsByCategory(category) }?.let {
            it.data?.let { list -> _productsByCategory.postValue(list) }
        }
    }

}
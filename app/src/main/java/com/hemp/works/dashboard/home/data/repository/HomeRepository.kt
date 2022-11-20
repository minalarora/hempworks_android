package com.hemp.works.dashboard.home.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.base.ImageResponse
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import com.hemp.works.dashboard.model.*
import com.hemp.works.login.data.model.Credential
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

    private val _blogList = MutableLiveData<List<Blog>>()
    val blogList: LiveData<List<Blog>>
        get() = _blogList

    private val _bestSellerProductList = MutableLiveData<List<Product>>()
    val bestSellerProductList: LiveData<List<Product>>
        get() = _bestSellerProductList

    private val _allProductList = MutableLiveData<List<Product>>()
    val allProductList: LiveData<List<Product>>
        get() = _allProductList

    private val _instagramList = MutableLiveData<List<Instagram>>()
    val instagramList: LiveData<List<Instagram>>
        get() = _instagramList

    private val _productsByCategory = LiveEvent<List<Product>>()
    val productsByCategory: LiveData<List<Product>>
        get() = _productsByCategory

    private val _booleanResponse = LiveEvent<Boolean>()
    val booleanResponse: LiveData<Boolean>
        get() = _booleanResponse

    private val _imageResponse = MutableLiveData<ImageResponse>()
    val imageResponse: LiveData<ImageResponse>
        get() = _imageResponse

    private val _pendingAmount = LiveEvent<ResponsePendingAmount>()
    val pendingAmount: LiveData<ResponsePendingAmount>
        get() = _pendingAmount

    suspend fun fetchHomeData() {

        coroutineScope {
            _loading.postValue(true)

            val deferredBannerList = async { getResult(handleLoading = false) { remoteDataSource.fetchBanners() }  }
            val deferredCategoryList = async { getResult(handleLoading = false) { remoteDataSource.fetchCategories() } }
            val deferredBestSellerProductList = async { getResult(handleLoading = false) { remoteDataSource.fetchBestSellerProducts() } }
            val deferredAllProductList = async { getResult(handleLoading = false) { remoteDataSource.fetchAllProducts() } }
            val deferredInstagramList = async { getResult(handleLoading = false) { remoteDataSource.fetchInstagram() } }
            val deferredBlogList = async { getResult(handleLoading = false) { remoteDataSource.fetchAllBlogs() } }
            val deferredOffer = async { getResult(handleLoading = false) { remoteDataSource.fetchOffer() } }


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

            deferredInstagramList.await()?.let {
                it.data?.let { list -> _instagramList.postValue(list) }
            }

            deferredBlogList.await()?.let {
                it.data?.let { list -> _blogList.postValue(list) }
            }

            deferredOffer.await()?.let {
                it.data?.let { offer -> _imageResponse.postValue(offer) }
            }

            _loading.postValue(false)
        }
    }

    suspend fun fetchProductsByCategory(category: Long) {
        getResult(Constants.GENERAL_ERROR_MESSAGE){ remoteDataSource.fetchProductsByCategory(category) }?.let {
            it.data?.let { list -> _productsByCategory.postValue(list) }
        }
    }

    suspend fun fetchAllProducts() {

        getResult(Constants.GENERAL_ERROR_MESSAGE){ remoteDataSource.fetchAllProducts() }?.let {
            it.data?.let { list -> _allProductList.postValue(list) }
        }
    }

    suspend fun fetchPendingAmount() {
        getResult(handleLoading = false){ remoteDataSource.getPendingAmount() }?.let {
            it.data?.let { it -> _pendingAmount.postValue(it) }
        }
    }

    suspend fun logout() {

        getResult { remoteDataSource.logout() }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }

}
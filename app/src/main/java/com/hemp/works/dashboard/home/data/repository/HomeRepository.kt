package com.hemp.works.dashboard.home.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import com.hemp.works.dashboard.model.Banner
import com.hemp.works.dashboard.model.Category
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

    suspend fun fetchHomeData() {

        coroutineScope {
            _loading.postValue(true)

            val deferredBannerList = async { getResult(handleLoading = false) { remoteDataSource.fetchBanners() }  }
            val deferredCategoryList = async { getResult(handleLoading = false) { remoteDataSource.fetchCategories() } }
            //TODO: more async calls

            deferredBannerList.await()?.let {
                it.data?.let { list -> _bannerList.postValue(list) }
            }
            deferredCategoryList.await()?.let {
                it.data?.let { list -> _categoryList.postValue(list) }
            }
            //TODO: more await calls

            _loading.postValue(false)
        }
    }

}
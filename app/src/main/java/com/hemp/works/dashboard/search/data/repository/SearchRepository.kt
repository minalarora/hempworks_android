package com.hemp.works.dashboard.search.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.dashboard.model.Product
import com.hemp.works.dashboard.search.data.remote.SearchRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(private val remoteDataSource: SearchRemoteDataSource) : BaseRepository() {

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>>
        get() = _productList

    suspend fun searchProducts(searchKey: String) {
        getResult{ remoteDataSource.searchProducts(searchKey) }?.let {
            it.data?.let { list -> _productList.postValue(list) }
        }
    }

}
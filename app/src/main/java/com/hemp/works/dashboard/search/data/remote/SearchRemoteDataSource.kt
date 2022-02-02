package com.hemp.works.dashboard.search.data.remote

import com.hemp.works.base.BaseDataSource
import com.hemp.works.dashboard.product.data.remote.ProductService
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(private val service: SearchService): BaseDataSource() {
}
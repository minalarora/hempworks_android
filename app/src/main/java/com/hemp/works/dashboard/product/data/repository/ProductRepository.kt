package com.hemp.works.dashboard.product.data.repository

import com.hemp.works.base.BaseRepository
import com.hemp.works.dashboard.home.data.remote.HomeRemoteDataSource
import com.hemp.works.dashboard.product.data.remote.ProductRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val remoteDataSource: ProductRemoteDataSource) : BaseRepository() {

}
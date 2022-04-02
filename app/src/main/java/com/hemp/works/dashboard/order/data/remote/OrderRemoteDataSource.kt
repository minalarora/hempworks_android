package com.hemp.works.dashboard.order.data.remote

import com.hemp.works.base.BaseDataSource
import javax.inject.Inject

class OrderRemoteDataSource @Inject constructor(private val service: OrderService): BaseDataSource() {

    suspend fun fetchOrders() = getResult { service.fetchOrders() }
}
package com.hemp.works.dashboard.order.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.Order
import com.hemp.works.dashboard.order.data.remote.OrderRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository  @Inject constructor(private val remoteDataSource: OrderRemoteDataSource) : BaseRepository()  {

    private val _orderList = MutableLiveData<List<Order>>()
    val orderList: LiveData<List<Order>>
        get() = _orderList

    suspend fun fetchOrders() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.fetchOrders()}?.let {
            it.data?.let { list -> _orderList.postValue(list) }
        }
    }

}
package com.hemp.works.dashboard.payment.data.remote

import com.hemp.works.base.BaseDataSource
import com.hemp.works.dashboard.address.data.remote.AddressService
import com.hemp.works.dashboard.model.RequestOrder
import javax.inject.Inject

class PaymentRemoteDataSource @Inject constructor(private val service: PaymentService): BaseDataSource()  {

    suspend fun addOrder(order: RequestOrder) = getResult { service.addOrder(order) }

    suspend fun getSingleOrder(id: Long) = getResult { service.getSingleOrder(id) }

    suspend fun requestCancellationForOrder(id: Long) = getResult { service.requestCancellationForOrder(id) }

    suspend fun getAllOrder() = getResult { service.getAllOrder() }
}
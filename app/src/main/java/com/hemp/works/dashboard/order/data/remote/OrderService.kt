package com.hemp.works.dashboard.order.data.remote

import com.hemp.works.dashboard.model.Order
import com.hemp.works.dashboard.model.Prescription
import retrofit2.Response
import retrofit2.http.GET

interface OrderService {

    @GET("/v1/order/all")
    suspend fun fetchOrders(): Response<List<Order>>
}
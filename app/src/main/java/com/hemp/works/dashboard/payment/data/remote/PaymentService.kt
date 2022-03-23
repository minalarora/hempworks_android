package com.hemp.works.dashboard.payment.data.remote

import com.hemp.works.base.BooleanResponse
import com.hemp.works.dashboard.model.Order
import com.hemp.works.dashboard.model.RequestOrder
import com.hemp.works.dashboard.model.Payment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PaymentService {

    @POST("/v1/order")
    suspend fun addOrder(@Body body: RequestOrder) : Response<Payment>

    @GET("/v1/order")
    suspend fun getSingleOrder(@Query("id") orderId: Long) : Response<Order>

    @GET("/v1/order/all")
    suspend fun getAllOrder() : Response<List<Order>>

    @GET("/v1/order/cancel/request")
    suspend fun requestCancellationForOrder(@Query("id") orderId: Long) : Response<BooleanResponse>
}
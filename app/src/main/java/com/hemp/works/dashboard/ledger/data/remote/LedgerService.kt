package com.hemp.works.dashboard.ledger.data.remote

import com.hemp.works.dashboard.model.*
import retrofit2.Response
import retrofit2.http.GET

interface LedgerService {

    @GET("/v1/order/all")
    suspend fun fetchOrders(): Response<List<Order>>

    @GET("/v1/wallethistory")
    suspend fun getWalletHistory() : Response<List<WalletHistory>>

    @GET("/v1/transaction/all")
    suspend fun getPaymentHistory() : Response<List<Transaction>>

    @GET("/v1/credithistory")
    suspend fun getCreditHistory() : Response<List<CreditHistory>>

    @GET("/v1/credithistory/pending")
    suspend fun getPendingAmount(): Response<ResponsePendingAmount>
}
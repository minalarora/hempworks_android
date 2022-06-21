package com.hemp.works.dashboard.wallet.data.remote

import com.hemp.works.dashboard.model.Transaction
import com.hemp.works.dashboard.model.WalletHistory
import retrofit2.Response
import retrofit2.http.GET

interface WalletService {

    @GET("/v1/wallethistory")
    suspend fun getWalletHistory() : Response<List<WalletHistory>>
}
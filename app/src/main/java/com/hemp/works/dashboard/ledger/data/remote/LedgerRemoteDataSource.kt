package com.hemp.works.dashboard.ledger.data.remote

import com.hemp.works.base.BaseDataSource
import javax.inject.Inject

class LedgerRemoteDataSource  @Inject constructor(private val service: LedgerService): BaseDataSource() {

    suspend fun fetchOrders() = getResult { service.fetchOrders() }

    suspend fun getWalletHistory()  = getResult { service.getWalletHistory() }

    suspend fun getPaymentHistory() = getResult { service.getPaymentHistory() }

    suspend fun getCreditHistory() = getResult { service.getCreditHistory() }

    suspend fun getPendingAmount() = getResult { service.getPendingAmount() }

}
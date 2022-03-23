package com.hemp.works.dashboard.credit.data.remote

import com.hemp.works.base.BaseDataSource
import javax.inject.Inject

class CreditRemoteDataSource @Inject constructor(private val service: CreditService): BaseDataSource() {

    suspend fun addCreditAmount(amount: Int) = getResult { service.addCreditAmount(hashMapOf("amount" to amount)) }

    suspend fun getCreditHistory() = getResult { service.getCreditHistory() }
}
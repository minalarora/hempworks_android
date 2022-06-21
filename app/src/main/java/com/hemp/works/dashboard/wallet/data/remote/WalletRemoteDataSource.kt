package com.hemp.works.dashboard.wallet.data.remote

import com.hemp.works.base.BaseDataSource
import javax.inject.Inject

class WalletRemoteDataSource @Inject constructor(private val service: WalletService): BaseDataSource() {

    suspend fun getWalletHistory()  = getResult { service.getWalletHistory() }
}
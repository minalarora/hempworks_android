package com.hemp.works.dashboard.wallet.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.Transaction
import com.hemp.works.dashboard.model.WalletHistory
import com.hemp.works.dashboard.payment.data.remote.PaymentRemoteDataSource
import com.hemp.works.dashboard.wallet.data.remote.WalletRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository  @Inject constructor(private val remoteDataSource: WalletRemoteDataSource) : BaseRepository(){

    private val _walletHistory = MutableLiveData<List<WalletHistory>>()
    val walletHistory: LiveData<List<WalletHistory>>
        get() = _walletHistory

    suspend fun fetchWalletHistory() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.getWalletHistory()
        }?.let{
            it.data?.let { walletHistory -> _walletHistory.postValue(walletHistory) }
        }
    }
}
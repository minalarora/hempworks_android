package com.hemp.works.dashboard.payment.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.Transaction
import com.hemp.works.dashboard.payment.data.remote.PaymentRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentHistoryRepository  @Inject constructor(private val remoteDataSource: PaymentRemoteDataSource) : BaseRepository(){

    private val _paymentHistory = MutableLiveData<List<Transaction>>()
    val paymentHistory: LiveData<List<Transaction>>
        get() = _paymentHistory

    suspend fun fetchPaymentHistory() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.getPaymentHistory()
        }?.let{
            it.data?.let { paymentHistory -> _paymentHistory.postValue(paymentHistory) }
        }
    }
}
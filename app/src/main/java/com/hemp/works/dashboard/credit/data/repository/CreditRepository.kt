package com.hemp.works.dashboard.credit.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.credit.data.remote.CreditRemoteDataSource
import com.hemp.works.dashboard.model.CreditHistory
import com.hemp.works.dashboard.model.Payment
import com.hemp.works.login.data.model.Address
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditRepository @Inject constructor(private val remoteDataSource: CreditRemoteDataSource) : BaseRepository() {

    private val _creditList = MutableLiveData<List<CreditHistory>>()
    val creditList: LiveData<List<CreditHistory>>
        get() = _creditList

    private val _paymentResponse = LiveEvent<Payment>()
    val paymentResponse: LiveData<Payment>
        get() = _paymentResponse

    suspend fun fetchCreditHistory() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.getCreditHistory()
        }?.let{
            it.data?.let { list -> _creditList.postValue(list) }
        }
    }

    suspend fun addCreditAmount(amount: Int) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.addCreditAmount(amount)
        }?.let{
            it.data?.let { payment -> _paymentResponse.postValue(payment) }
        }
    }
}
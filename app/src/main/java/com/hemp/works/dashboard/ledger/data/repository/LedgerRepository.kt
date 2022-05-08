package com.hemp.works.dashboard.ledger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.dashboard.ledger.data.remote.LedgerRemoteDataSource
import com.hemp.works.dashboard.model.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LedgerRepository  @Inject constructor(private val remoteDataSource: LedgerRemoteDataSource) : BaseRepository() {

    private val _orderList = MutableLiveData<List<Order>>()
    val orderList: LiveData<List<Order>>
        get() = _orderList

    private val _walletList = MutableLiveData<List<WalletHistory>>()
    val walletList: LiveData<List<WalletHistory>>
        get() = _walletList

    private val _paymentList = MutableLiveData<List<Transaction>>()
    val paymentList: LiveData<List<Transaction>>
        get() = _paymentList

    private val _creditList = MutableLiveData<List<CreditHistory>>()
    val creditList: LiveData<List<CreditHistory>>
        get() = _creditList

    private val _paymentResponse = MutableLiveData<ResponsePendingAmount>()
    val paymentResponse: LiveData<ResponsePendingAmount>
        get() = _paymentResponse

    suspend fun fetchLedgerData() {

        coroutineScope {
            _loading.postValue(true)

            val deferredOrderList = async { getResult(handleLoading = false) { remoteDataSource.fetchOrders() }  }
            val deferredWalletList = async { getResult(handleLoading = false) { remoteDataSource.getWalletHistory() } }
            val deferredCreditList = async { getResult(handleLoading = false) { remoteDataSource.getCreditHistory() } }
            val deferredPaymentList = async { getResult(handleLoading = false) { remoteDataSource.getPaymentHistory() } }
            val deferredPaymentResponse = async { getResult(handleLoading = false) { remoteDataSource.getPendingAmount() } }

            deferredOrderList.await()?.let {
                it.data?.let { list -> _orderList.postValue(list) }
            }
            deferredWalletList.await()?.let {
                it.data?.let { list -> _walletList.postValue(list) }
            }
            deferredCreditList.await()?.let {
                it.data?.let { list -> _creditList.postValue(list) }
            }
            deferredPaymentList.await()?.let {
                it.data?.let { list -> _paymentList.postValue(list) }
            }

            deferredPaymentResponse.await()?.let {
                it.data?.let { list -> _paymentResponse.postValue(list) }
            }

            _loading.postValue(false)
        }
    }
}
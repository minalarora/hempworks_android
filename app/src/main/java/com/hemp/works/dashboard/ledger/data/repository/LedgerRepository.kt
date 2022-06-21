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

    private val _ledgerList = MutableLiveData<List<Ledger>>()
    val ledgerList: LiveData<List<Ledger>>
        get() = _ledgerList

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

            val localLedgerList: ArrayList<Ledger> = ArrayList();

            deferredOrderList.await()?.let {
                it.data?.let { list ->
                    val expandedList = mutableListOf<Order>()
                    for (orderObject in list) {
                        if (orderObject.order.isNullOrEmpty()) continue
                        for (orderProduct in orderObject.order) {
                            val order = orderObject.copy(order = listOf(orderProduct))
                            expandedList.add(order)
                        }
                    }
                    _orderList.value = expandedList
                    localLedgerList.addAll(expandedList.map { it -> Ledger(it.date, it) })
                }
            }
            deferredWalletList.await()?.let {
                it.data?.let { list ->
                    _walletList.value = list
                    localLedgerList.addAll(list.map { it -> Ledger(it.date, it) })
                }
            }
            deferredCreditList.await()?.let {
                it.data?.let { list ->
                    _creditList.value = list
                    localLedgerList.addAll(list.map { it -> Ledger(it.date, it) })
                }
            }
            deferredPaymentList.await()?.let {
                it.data?.let { list ->
                    _paymentList.value = list
                    localLedgerList.addAll(list.map { it -> Ledger(it.date, it) })
                }
            }

            deferredPaymentResponse.await()?.let {
                it.data?.let { list ->
                    _paymentResponse.value = list
                }
            }

            localLedgerList.sortByDescending { it.date }
            _ledgerList.postValue(localLedgerList)

            _loading.postValue(false)
        }
    }
}
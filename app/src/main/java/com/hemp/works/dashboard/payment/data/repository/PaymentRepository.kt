package com.hemp.works.dashboard.payment.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.address.data.remote.AddressRemoteDataSource
import com.hemp.works.dashboard.model.CreditHistory
import com.hemp.works.dashboard.model.Order
import com.hemp.works.dashboard.model.Payment
import com.hemp.works.dashboard.model.RequestOrder
import com.hemp.works.dashboard.payment.data.remote.PaymentRemoteDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentRepository @Inject constructor(private val remoteDataSource: PaymentRemoteDataSource) : BaseRepository() {

    private val _order = LiveEvent<Order>()
    val order: LiveData<Order>
        get() = _order

    private val _creditHistory = LiveEvent<CreditHistory>()
    val creditHistory: LiveData<CreditHistory>
        get() = _creditHistory

    private val _payment = LiveEvent<Payment>()
    val payment: LiveData<Payment>
        get() = _payment

    suspend fun doOrder(order: RequestOrder) {
        getResult(Constants.PAYMENT_ISSUE) {
            remoteDataSource.addOrder(order)
        }?.let{
            it.data?.let { payment -> _payment.postValue(payment) }
        }
    }

    suspend fun doCreditPayment(amount: Int) {
        getResult(Constants.PAYMENT_ISSUE) {
            remoteDataSource.addCreditPayment(amount)
        }?.let{
            it.data?.let { payment -> _payment.postValue(payment) }
        }
    }

    suspend fun validateOrder() {
        while (true) {
            getResult(Constants.GENERAL_ERROR_MESSAGE, handleLoading = false) {
                delay(3000)
                remoteDataSource.getSingleOrder(payment.value?.id?.toLong() ?: 0)
            }?.let {
                it.data?.let { order -> _order.postValue(order) }
                return
            }
        }
    }

    suspend fun validateCreditHistory() {
        while (true) {
            getResult(Constants.GENERAL_ERROR_MESSAGE, handleLoading = false) {
                delay(3000)
                remoteDataSource.getSingleCreditHistory(payment.value?.id?.toLong() ?: 0)
            }?.let {
                it.data?.let { credit -> _creditHistory.postValue(credit) }
                return
            }
        }
    }


//    private val _orderList = MutableLiveData<List<Order>>()
//    val orderList: LiveData<List<Order>>
//        get() = _orderList
//
//    private val _order = MutableLiveData<Order>()
//    val order: LiveData<Order>
//        get() = _order
//
//    private val _paymentResponse = LiveEvent<Payment>()
//    val paymentResponse: LiveData<Payment>
//        get() = _paymentResponse
//
//    private val _booleanResponse = LiveEvent<Boolean>()
//    val booleanResponse: LiveData<Boolean>
//        get() = _booleanResponse
//
//    suspend fun fetchOrderList() {
//        getResult(Constants.GENERAL_ERROR_MESSAGE) {
//            remoteDataSource.getAllOrder()
//        }?.let{
//            it.data?.let { list -> _orderList.postValue(list) }
//        }
//    }
//
//    suspend fun addOrder(requestOrder: RequestOrder) {
//        getResult(Constants.GENERAL_ERROR_MESSAGE) {
//            remoteDataSource.addOrder(requestOrder)
//        }?.let{
//            it.data?.let { payment -> _paymentResponse.postValue(payment) }
//        }
//    }
//
//    suspend fun getSingleOrder(orderId: Long) {
//        getResult(Constants.GENERAL_ERROR_MESSAGE) {
//            remoteDataSource.getSingleOrder(orderId)
//        }?.let{
//            it.data?.let { order -> _order.postValue(order) }
//        }
//    }
//
//    suspend fun requestCancellationForOrder(orderId: Long) {
//        getResult(Constants.GENERAL_ERROR_MESSAGE) {
//            remoteDataSource.requestCancellationForOrder(orderId)
//        }?.let{
//            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
//        }
//    }
}
package com.hemp.works.dashboard.payment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.BuildConfig
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.model.RequestOrder
import com.hemp.works.dashboard.model.RequestPayment
import com.hemp.works.dashboard.payment.data.repository.PaymentRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class PaymentViewModel @Inject constructor(private val repository: PaymentRepository): BaseViewModel(repository) {

    val order  = repository.order
    val creditHistory = repository.creditHistory
    val payment  = repository.payment

//    private val _endingError = LiveEvent<String>()
//    val endingError: LiveData<String>
//        get() = _endingError

    private val _paymentStatus = MutableLiveData<PaymentStatus>(PaymentStatus.NONE)
    val paymentStatus: LiveData<PaymentStatus>
        get() = _paymentStatus

    val compVisibility: LiveData<Boolean> = Transformations.map(paymentStatus) {
        it != PaymentStatus.NONE
    }

    var requestPayment: RequestPayment? = null

    fun initializePayment() {
        viewModelScope.launch {
            try {
                if (requestPayment?.reason == "ORDER") {

                    val requestOrder = RequestOrder(
                        address = requestPayment?.address!!,
                        payment = requestPayment?.payment!!,
                        totalprice =  if (BuildConfig.DEBUG) 1 else requestPayment?.totalprice!!,
                        discountprice =  if (BuildConfig.DEBUG) 1 else requestPayment?.discountprice!!
                    )
                    repository.doOrder(requestOrder)
                } else {
                    repository.doCreditPayment(if (BuildConfig.DEBUG) 1 else requestPayment?.amount!!)
                }
            } catch (ex: Exception) {
                error(Constants.PAYMENT_ISSUE)
            }
        }
    }


    fun startValidateOrder() {
        viewModelScope.launch {
            repository.validateOrder()
        }
    }

    fun startValidateCredit() {
        viewModelScope.launch {
            repository.validateCreditHistory()
        }
    }


    fun updatePaymentStatus(paymentStatus: PaymentStatus) {
        _paymentStatus.postValue(paymentStatus)
    }
}

sealed class PaymentStatus {
    object NONE : PaymentStatus()
    data class ORDER_PENDING(var amount: Int = 0, var id: String = "") : PaymentStatus()
    data class ORDER_COMPLETED(var amount: Int = 0, var id: String = "") : PaymentStatus()
    data class ORDER_FAILED(var amount: Int = 0, var id: String = "") : PaymentStatus()
    data class CREDIT_PENDING(var amount: Int = 0, var id: String = "") : PaymentStatus()
    data class CREDIT_COMPLETED(var amount: Int = 0, var id: String = "") : PaymentStatus()
    data class CREDIT_FAILED(var amount: Int = 0, var id: String = "") : PaymentStatus()
}

//sealed class Response<out R> {
//
//    data class Success<out T>(val data: T) : Response<T>()
//    data class Error(val exception: Exception, val description:String) : Response<Nothing>()
//    object Loading : Response<Nothing>()
//
//    override fun toString(): String {
//        return when (this) {
//            is Success<*> -> "Success[data=$data]"
//            is Error -> "Error[exception=$exception description=$description]"
//            Loading -> "Loading"
//        }
//    }
//}
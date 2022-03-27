package com.hemp.works.dashboard.payment.ui

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val payment  = repository.creditHistory

    private val _endingError = LiveEvent<String>()
    val endingError: LiveData<String>
        get() = _endingError

    private val _paymentStatus = MutableLiveData<PaymentStatus>(PaymentStatus.NONE)
    val paymentStatus: LiveData<PaymentStatus>
        get() = _paymentStatus



    fun initializePayment(paymentDetails: RequestPayment) {
        viewModelScope.launch {
            try {
                if (paymentDetails.reason == "ORDER") {

                    val requestOrder = RequestOrder(
                        address = paymentDetails.address!!,
                        payment = paymentDetails.payment!!,
                        totalprice =  if (BuildConfig.DEBUG) 1 else paymentDetails.totalprice!!,
                        discountprice =  if (BuildConfig.DEBUG) 1 else paymentDetails.discountprice!!
                    )

                    repository.doOrder(requestOrder)
                } else {

                    repository.doCreditPayment(paymentDetails.amount!!)
                }
            } catch (ex: Exception) {
                _endingError.postValue(Constants.GENERAL_ERROR_MESSAGE)
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

enum class PaymentStatus(var amount: Int = 0, var id: String = "") {
    NONE, ORDER_PENDING, ORDER_COMPLETED, ORDER_FAILED, CREDIT_PENDING, CREDIT_COMPLETED, CREDIT_FAILED;
}

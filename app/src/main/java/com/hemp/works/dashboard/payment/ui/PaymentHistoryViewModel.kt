package com.hemp.works.dashboard.payment.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.model.Transaction
import com.hemp.works.dashboard.payment.data.repository.PaymentHistoryRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PaymentHistoryViewModel @Inject constructor(private val repository: PaymentHistoryRepository): BaseViewModel(repository){

    private val _paymentList: MutableLiveData<List<Transaction>> = Transformations.map(repository.paymentHistory) {
        filterList(it)
    } as MutableLiveData<List<Transaction>>
    val paymentList: LiveData<List<Transaction>> = _paymentList

    private val _paymentListVisibility= MutableLiveData(false)
    val paymentListVisibility: LiveData<Boolean> = _paymentListVisibility

    private val _dateRangeVisibility= MutableLiveData(false)
    val dateRangeVisibility: LiveData<Boolean> = _dateRangeVisibility

    private val _dateRangeText= MutableLiveData("")
    val dateRangeText: LiveData<String> = _dateRangeText


    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd MMM yy");
    private var dateRange: Pair<Date, Date>? = null

    private fun filterList(list: List<Transaction>): List<Transaction> {
        val filteredList = if (dateRange != null) {
            list.filter { order ->
                order.date!!.after(dateRange!!.first) && order.date!!.before(dateRange!!.second)
            }
        } else {
            list
        }

        return filteredList

    }

    fun fetchPaymentHistory() {
        viewModelScope.launch {   repository.fetchPaymentHistory() }
    }

    fun updateDateRange(startDateInMillis: Long, endDateInMillis: Long) {
        if (startDateInMillis < 1 || endDateInMillis < 1) {
            dateRange = null
            _dateRangeVisibility.postValue(false)
        }
        else {
            dateRange = Pair(
                Calendar.getInstance().apply {
                    timeInMillis = startDateInMillis
                    add(Calendar.HOUR, -5)
                }.time,
                Calendar.getInstance().apply {
                    timeInMillis = endDateInMillis
                    add(Calendar.HOUR, 18)
                }.time
            )
            _dateRangeText.postValue("Showing results for " + dateFormat.format(dateRange!!.first) + " - " + dateFormat.format(dateRange!!.second))
            _dateRangeVisibility.postValue(true)
        }
        _paymentList.postValue(filterList(repository.paymentHistory.value!!))
    }


    fun handlePaymentListVisibility(isEmpty: Boolean) {
        _paymentListVisibility.postValue(!isEmpty)
    }

}
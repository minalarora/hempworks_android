package com.hemp.works.dashboard.order.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.model.Order
import com.hemp.works.dashboard.model.Prescription
import com.hemp.works.dashboard.order.data.repository.OrderRepository
import com.hemp.works.dashboard.prescription.data.repository.PrescriptionRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class OrderViewModel @Inject constructor(private val repository: OrderRepository): BaseViewModel(repository){

    private val _orderList: MutableLiveData<List<Order>> = Transformations.map(repository.orderList) {
        filterList(it)
    } as MutableLiveData<List<Order>>
    val orderList: LiveData<List<Order>> = _orderList

    private val _ordersVisibility= MutableLiveData(false)
    val ordersVisibility: LiveData<Boolean> = _ordersVisibility

    private val _dateRangeVisibility= MutableLiveData(false)
    val dateRangeVisibility: LiveData<Boolean> = _dateRangeVisibility

    private val _dateRangeText= MutableLiveData("")
    val dateRangeText: LiveData<String> = _dateRangeText


    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd MMM yy");
    private var dateRange: Pair<Date, Date>? = null

    private fun filterList(list: List<Order>): List<Order> {
        val filteredList = if (dateRange != null) {
            list.filter { order ->
                order.date!!.after(dateRange!!.first) && order.date!!.before(dateRange!!.second)
            }
        } else {
            list
        }
        val expandedList = mutableListOf<Order>()
        for (orderObject in filteredList) {
            if (orderObject.order.isNullOrEmpty()) continue
            for (orderProduct in orderObject.order) {
                val order = orderObject.copy(order = listOf(orderProduct))
                expandedList.add(order)
            }
        }
        return expandedList

    }

    fun fetchOrders() {
        viewModelScope.launch {   repository.fetchOrders() }
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
        _orderList.postValue(filterList(repository.orderList.value!!))
    }


    fun handleOrdersVisibility(isEmpty: Boolean) {
        _ordersVisibility.postValue(!isEmpty)
    }
}
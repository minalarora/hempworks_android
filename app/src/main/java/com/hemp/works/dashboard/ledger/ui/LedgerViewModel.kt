package com.hemp.works.dashboard.ledger.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.home.data.repository.HomeRepository
import com.hemp.works.dashboard.ledger.data.repository.LedgerRepository
import com.hemp.works.dashboard.model.Ledger
import com.hemp.works.dashboard.model.WalletHistory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class LedgerViewModel @Inject constructor(private val repository: LedgerRepository) : BaseViewModel(repository) {


    val paymentResponse = repository.paymentResponse

    private val _ledger: MutableLiveData<List<Ledger>> = Transformations.map(repository.ledgerList) {
        filterList(it)
    } as MutableLiveData<List<Ledger>>
    val ledger: LiveData<List<Ledger>> = _ledger

    private val _listVisibility= MutableLiveData(false)
    val listVisibility: LiveData<Boolean> = _listVisibility

    private val _dateRangeVisibility= MutableLiveData(false)
    val dateRangeVisibility: LiveData<Boolean> = _dateRangeVisibility

    private val _dateRangeText= MutableLiveData("")
    val dateRangeText: LiveData<String> = _dateRangeText

    val dateFormat = SimpleDateFormat(Constants.ONLY_DATE_FORMAT);
    private var dateRange: Pair<Date, Date>? = null

    val dueDate: LiveData<String> = Transformations.map(repository.paymentResponse) {
        "Due Date: " + if ((it.totalamount ?: 0) == 0) "NA" else dateFormat.format(it.date)
    }

    val totalAmount: LiveData<String> = Transformations.map(repository.paymentResponse) {
        "Used Credit Amount: Rs. " + (it.totalamount ?: 0)
    }

    val pendingAmount: LiveData<String> = Transformations.map(repository.paymentResponse) {
        "Pending Amount: Rs. " + (it.pendingamount ?: 0)
    }

    init {
        loading.value = true
        viewModelScope.launch {
            repository.fetchLedgerData()
        }
    }

    fun handleLedgerListVisibility(isEmpty: Boolean) {
        _listVisibility.postValue(!isEmpty)
    }

    private fun filterList(list: List<Ledger>): List<Ledger> {
        val filteredList = if (dateRange != null) {
            list.filter { ledger ->
                ledger.date!!.after(dateRange!!.first) && ledger.date!!.before(dateRange!!.second)
            }
        } else {
            list
        }

        return filteredList

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
        _ledger.postValue(filterList(repository.ledgerList.value!!))
    }

}
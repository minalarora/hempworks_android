package com.hemp.works.dashboard.wallet.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.model.WalletHistory
import com.hemp.works.dashboard.wallet.data.repository.WalletRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WalletViewModel @Inject constructor(private val repository: WalletRepository): BaseViewModel(repository) {

    private val _walletHistory: MutableLiveData<List<WalletHistory>> = Transformations.map(repository.walletHistory) {
        filterList(it)
    } as MutableLiveData<List<WalletHistory>>
    val walletHistory: LiveData<List<WalletHistory>> = _walletHistory

    private val _walletListVisibility= MutableLiveData(false)
    val waletListVisibility: LiveData<Boolean> = _walletListVisibility

    private val _dateRangeVisibility= MutableLiveData(false)
    val dateRangeVisibility: LiveData<Boolean> = _dateRangeVisibility

    private val _dateRangeText= MutableLiveData("")
    val dateRangeText: LiveData<String> = _dateRangeText


    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd MMM yy");
    private var dateRange: Pair<Date, Date>? = null

    private fun filterList(list: List<WalletHistory>): List<WalletHistory> {
        val filteredList = if (dateRange != null) {
            list.filter { order ->
                order.date!!.after(dateRange!!.first) && order.date!!.before(dateRange!!.second)
            }
        } else {
            list
        }

        return filteredList

    }

    fun fetchWalletHistory() {
        viewModelScope.launch {   repository.fetchWalletHistory() }
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
        _walletHistory.postValue(filterList(repository.walletHistory.value!!))
    }


    fun handleWalletListVisibility(isEmpty: Boolean) {
        _walletListVisibility.postValue(!isEmpty)
    }
}
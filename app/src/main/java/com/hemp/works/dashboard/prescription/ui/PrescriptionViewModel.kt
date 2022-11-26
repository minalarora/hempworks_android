package com.hemp.works.dashboard.prescription.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.model.Prescription
import com.hemp.works.dashboard.prescription.data.repository.PrescriptionRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PrescriptionViewModel  @Inject constructor(private val repository: PrescriptionRepository): BaseViewModel(repository) {

    private val _prescriptionsList: MutableLiveData<List<Prescription>> = Transformations.map(repository.prescriptionList) {
       filterList(it)
    } as MutableLiveData<List<Prescription>>
    val prescriptionList: LiveData<List<Prescription>> = _prescriptionsList

    private val _prescriptionVisibility= MutableLiveData(false)
    val prescriptionVisibility: LiveData<Boolean> = _prescriptionVisibility

    private val _dateRangeVisibility= MutableLiveData(false)
    val dateRangeVisibility: LiveData<Boolean> = _dateRangeVisibility

    private val _dateRangeText= MutableLiveData("")
    val dateRangeText: LiveData<String> = _dateRangeText


    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd MMM yy");
    private var description: String? = null
    private var dateRange: Pair<Date, Date>? = null

    private fun filterList(list: List<Prescription>) : List<Prescription> {
       val filteredList = (if (dateRange != null) {
            list.filter { prescription ->
                prescription.date!!.after(dateRange!!.first) && prescription.date!!.before(dateRange!!.second)
            }
        } else {
            list
        }).apply {
             if (!description.isNullOrBlank()) {
                filter { prescription ->
                    prescription.description?.contains(description!!, true) == true
                }
            }
       }

        val expandedList = mutableListOf<Prescription>()
        for (prescriptionObject in filteredList) {
            if (prescriptionObject.prescriptions.isEmpty()) continue
            for (prescriptionMedias in prescriptionObject.prescriptions) {
                val prescription = prescriptionObject.copy(prescription = prescriptionMedias.prescription, type = prescriptionMedias.type)
                expandedList.add(prescription)
            }
        }
        return expandedList
    }

    fun fetchPrescriptions() {
            viewModelScope.launch { repository.fetchPrescriptions() }
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
        _prescriptionsList.postValue(filterList(repository.prescriptionList.value!!))
    }

    fun updateDescription(text: String?) {
        description = text
        _prescriptionsList.postValue(filterList(repository.prescriptionList.value!!))
    }

    fun handlePrescriptionVisibility(isEmpty: Boolean) {
        _prescriptionVisibility.postValue(!isEmpty)
    }
}
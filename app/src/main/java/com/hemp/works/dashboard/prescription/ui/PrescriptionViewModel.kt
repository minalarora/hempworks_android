package com.hemp.works.dashboard.prescription.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.model.Prescription
import com.hemp.works.dashboard.prescription.data.repository.PrescriptionRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class PrescriptionViewModel  @Inject constructor(private val repository: PrescriptionRepository): BaseViewModel() {

    init {
        viewModelScope.launch {   repository.fetchPrescriptions() }
    }

    private val _prescriptionsList: MutableLiveData<List<Prescription>> = Transformations.map(repository.prescriptionList) {
       filterList(it)
    } as MutableLiveData<List<Prescription>>
    val prescriptionList: LiveData<List<Prescription>> = _prescriptionsList

    private val _prescriptionVisibility= MutableLiveData(false)
    val prescriptionVisibility: LiveData<Boolean> = _prescriptionVisibility

    private var description: String? = null
    private var dateRange: Pair<Date, Date>? = null

    private fun filterList(list: List<Prescription>) : List<Prescription> {
       return (if (dateRange != null) {
            list.filter { prescription ->
                prescription.date!!.after(dateRange!!.first) && prescription.date!!.before(dateRange!!.second)
            }
        } else {
            list
        }).apply {
            if (!description.isNullOrBlank()) {
                this.filter { prescription ->
                    prescription.description.contentEquals(description, true)
                }
            } else {
                this
            }
       }
    }

    fun updateDateRange(startDateInMillis: Long, endDateInMillis: Long) {
        if (startDateInMillis < 1 || endDateInMillis < 1) dateRange = null
        else dateRange = Pair(
            Calendar.getInstance().apply { timeInMillis = startDateInMillis }.time,
            Calendar.getInstance().apply { timeInMillis = endDateInMillis }.time
        )
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
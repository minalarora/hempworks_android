package com.hemp.works.dashboard.prescription.ui

import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.prescription.data.repository.PrescriptionRepository
import javax.inject.Inject

class PrescriptionViewModel  @Inject constructor(private val repository: PrescriptionRepository): BaseViewModel() {
}
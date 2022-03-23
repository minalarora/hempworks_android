package com.hemp.works.dashboard.payment.ui

import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.payment.data.repository.PaymentRepository
import javax.inject.Inject

class PaymentViewModel @Inject constructor(private val repository: PaymentRepository): BaseViewModel(repository) {
}
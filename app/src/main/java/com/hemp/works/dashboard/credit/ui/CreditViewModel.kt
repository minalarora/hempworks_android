package com.hemp.works.dashboard.credit.ui

import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.calculator.data.repository.CalculatorRepository
import com.hemp.works.dashboard.credit.data.repository.CreditRepository
import javax.inject.Inject

class CreditViewModel  @Inject constructor(private val repository: CreditRepository): BaseViewModel(repository)  {
}
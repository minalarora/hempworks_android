package com.hemp.works.dashboard.calculator.ui

import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.calculator.data.repository.CalculatorRepository
import javax.inject.Inject

class DosageCalculatorViewModel  @Inject constructor(private val repository: CalculatorRepository): BaseViewModel() {
}
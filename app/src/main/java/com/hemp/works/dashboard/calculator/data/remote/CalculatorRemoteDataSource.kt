package com.hemp.works.dashboard.calculator.data.remote

import com.hemp.works.base.BaseDataSource
import com.hemp.works.dashboard.home.data.remote.HomeService
import com.hemp.works.dashboard.model.CalculatorProduct
import retrofit2.Response
import javax.inject.Inject

class CalculatorRemoteDataSource @Inject constructor(private val service: CalculatorService): BaseDataSource() {
    suspend fun getCalculatorList() = getResult { service.getCalculatorList() }
}
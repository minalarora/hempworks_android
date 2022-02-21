package com.hemp.works.dashboard.calculator.data.remote

import com.hemp.works.dashboard.model.CalculatorProduct
import retrofit2.Response
import retrofit2.http.GET

interface CalculatorService {

    @GET("/v1/dosage/all")
    suspend fun getCalculatorList() : Response<List<CalculatorProduct>>
}
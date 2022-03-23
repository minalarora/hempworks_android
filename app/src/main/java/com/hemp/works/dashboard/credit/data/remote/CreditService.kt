package com.hemp.works.dashboard.credit.data.remote

import com.hemp.works.dashboard.model.CreditHistory
import com.hemp.works.dashboard.model.Payment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CreditService {

    @POST("/v1/credithistory/payment")
    suspend fun addCreditAmount(@Body body: HashMap<String,Int>) : Response<Payment>

    @GET("/v1/credithistory")
    suspend fun getCreditHistory() : Response<List<CreditHistory>>


}
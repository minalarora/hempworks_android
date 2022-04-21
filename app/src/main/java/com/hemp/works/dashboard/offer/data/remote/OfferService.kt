package com.hemp.works.dashboard.offer.data.remote

import com.hemp.works.base.BooleanResponse
import com.hemp.works.dashboard.model.Cart
import com.hemp.works.dashboard.model.Coupon
import com.hemp.works.dashboard.model.RequestProduct
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface OfferService {

    @GET("/v1/coupon/public")
    suspend fun getCouponList() : Response<List<Coupon>>


}
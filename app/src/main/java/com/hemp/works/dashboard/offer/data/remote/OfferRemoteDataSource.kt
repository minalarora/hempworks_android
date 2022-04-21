package com.hemp.works.dashboard.offer.data.remote

import com.hemp.works.base.BaseDataSource
import javax.inject.Inject

class OfferRemoteDataSource @Inject constructor(private val service: OfferService): BaseDataSource() {

    suspend fun getCouponList() = getResult { service.getCouponList() }
}
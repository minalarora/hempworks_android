package com.hemp.works.dashboard.offer.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.Coupon
import com.hemp.works.dashboard.offer.data.remote.OfferRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfferRepository @Inject constructor(private val remoteDataSource: OfferRemoteDataSource) : BaseRepository() {


    private val _coupons = MutableLiveData<List<Coupon>>()
    val coupons: LiveData<List<Coupon>>
        get() = _coupons


    suspend fun fetchCoupons() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.getCouponList()
        }?.let{
            it.data?.let { coupons -> _coupons.postValue(coupons) }
        }
    }


}
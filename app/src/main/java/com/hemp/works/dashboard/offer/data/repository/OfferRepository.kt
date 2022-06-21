package com.hemp.works.dashboard.offer.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.cart.data.remote.CartRemoteDataSource
import com.hemp.works.dashboard.model.Coupon
import com.hemp.works.dashboard.model.RequestProduct
import com.hemp.works.dashboard.offer.data.remote.OfferRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfferRepository @Inject constructor(private val remoteDataSource: OfferRemoteDataSource,
                                            private val cartRemoteDataSource: CartRemoteDataSource) : BaseRepository() {


    private val _coupons = MutableLiveData<List<Coupon>>()
    val coupons: LiveData<List<Coupon>>
        get() = _coupons


    private val _booleanResponse = LiveEvent<Boolean>()
    val booleanResponse: LiveData<Boolean>
        get() = _booleanResponse

    suspend fun addProduct(requestProduct: RequestProduct) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            cartRemoteDataSource.addProduct(requestProduct)
        }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }

    suspend fun addCoupon(coupon: String) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            cartRemoteDataSource.addCoupon(coupon)
        }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }

    suspend fun emptyCart() {
        getResult(Constants.GENERAL_ERROR_MESSAGE, handleLoading = false) {
            cartRemoteDataSource.emptyCart()
        }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }


    suspend fun fetchCoupons() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.getCouponList()
        }?.let{
            it.data?.let { coupons -> _coupons.postValue(coupons) }
        }
    }


}
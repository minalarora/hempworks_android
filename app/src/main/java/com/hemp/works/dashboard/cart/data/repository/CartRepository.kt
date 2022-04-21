package com.hemp.works.dashboard.cart.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.cart.data.remote.CartRemoteDataSource
import com.hemp.works.dashboard.model.Cart
import com.hemp.works.dashboard.model.Coupon
import com.hemp.works.dashboard.model.RequestProduct
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(private val remoteDataSource: CartRemoteDataSource) : BaseRepository() {

    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart>
        get() = _cart

    private val _coupons = MutableLiveData<List<Coupon>>()
    val coupons: LiveData<List<Coupon>>
        get() = _coupons

    private val _booleanResponse = LiveEvent<Boolean>()
    val booleanResponse: LiveData<Boolean>
        get() = _booleanResponse


    suspend fun fetchCart(isFirstTime: Boolean) {
        if (isFirstTime) {
            getResult(Constants.GENERAL_ERROR_MESSAGE) {
                remoteDataSource.removeCoupon()
            }?.let {
                getResult(Constants.GENERAL_ERROR_MESSAGE) {
                    remoteDataSource.getCart()
                }?.let {
                    it.data?.let { cart -> _cart.postValue(cart) }
                }
            }
        } else {
            getResult(Constants.GENERAL_ERROR_MESSAGE) {
                remoteDataSource.getCart()
            }?.let {
                it.data?.let { cart -> _cart.postValue(cart) }
            }
        }
    }

    suspend fun fetchCoupons() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.getCouponList()
        }?.let{
            it.data?.let { coupons -> _coupons.postValue(coupons) }
        }
    }

    suspend fun addProduct(requestProduct: RequestProduct) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.addProduct(requestProduct)
        }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }

    suspend fun addQuantity(requestProduct: RequestProduct) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.addQuantity(requestProduct)
        }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }

    suspend fun deleteProduct(requestProduct: RequestProduct) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.deleteProduct(requestProduct)}?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }

    suspend fun addCoupon(coupon: String) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.addCoupon(coupon)
        }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }

    suspend fun removeCoupon() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.removeCoupon()
        }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }

    suspend fun emptyCart() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.emptyCart()
        }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }
}
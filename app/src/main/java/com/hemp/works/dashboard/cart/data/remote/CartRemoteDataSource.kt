package com.hemp.works.dashboard.cart.data.remote

import com.hemp.works.base.BaseDataSource
import com.hemp.works.dashboard.model.RequestProduct
import javax.inject.Inject

class CartRemoteDataSource @Inject constructor(private val service: CartService): BaseDataSource() {

    suspend fun addProduct(requestProduct: RequestProduct) = getResult { service.addProduct(requestProduct) }

    suspend fun addQuantity(requestProduct: RequestProduct) = getResult { service.addQuantity(requestProduct) }

    suspend fun getCart() = getResult { service.getCart() }

    suspend fun deleteProduct(requestProduct: RequestProduct) = getResult { service.deleteProduct(requestProduct) }

    suspend fun addCoupon(coupon: String) = getResult { service.applyCoupon(hashMapOf("coupon" to coupon.trim())) }

    suspend fun removeCoupon() = getResult { service.applyCoupon(hashMapOf("coupon" to "")) }

    suspend fun emptyCart() = getResult { service.emptyCart() }

    suspend fun getCouponList() = getResult { service.getCouponList() }
}
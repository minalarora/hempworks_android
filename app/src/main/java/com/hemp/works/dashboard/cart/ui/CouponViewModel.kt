package com.hemp.works.dashboard.cart.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.cart.data.repository.CartRepository
import com.hemp.works.dashboard.model.Coupon
import kotlinx.coroutines.launch
import javax.inject.Inject

class CouponViewModel @Inject constructor(private val repository: CartRepository): BaseViewModel(repository) {

    init{
        viewModelScope.launch {
            repository.fetchCoupons()
        }
    }

    val couponsList = Transformations.map(repository.coupons) {
        if (it.isNullOrEmpty()) emptyList<Coupon>() else it
    }

    val couponListVisibility: LiveData<Boolean> = Transformations.map(repository.coupons) {
        it.isNotEmpty()
    }

    val booleanResponse = repository.booleanResponse

    fun addCoupon(coupon: String?) {
        viewModelScope.launch {
            if (coupon.isNullOrBlank()) return@launch
            repository.addCoupon(coupon)
        }
    }

}
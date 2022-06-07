package com.hemp.works.dashboard.offer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.model.Coupon
import com.hemp.works.dashboard.model.RequestProduct
import com.hemp.works.dashboard.offer.data.repository.OfferRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class OfferViewModel @Inject constructor(private val repository: OfferRepository): BaseViewModel(repository) {

    val booleanResponse = repository.booleanResponse

    var currentStage = OfferState.NONE

    var coupon: Coupon? = null

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

    fun addProduct(product: Long, variant: Long, quantity: Int) {
        viewModelScope.launch {
            repository.addProduct(RequestProduct(product, variant, quantity))
        }
        currentStage = OfferState.ADD
    }

    fun addCoupon(coupon: String?) {
        viewModelScope.launch {
            if (coupon.isNullOrBlank()) return@launch
            repository.addCoupon(coupon)
        }
       currentStage =  OfferState.COUPON
    }

    fun emptyCart() {
        viewModelScope.launch {
            repository.emptyCart()
        }
    }

}
enum class OfferState() {
    ADD, COUPON, NONE
}
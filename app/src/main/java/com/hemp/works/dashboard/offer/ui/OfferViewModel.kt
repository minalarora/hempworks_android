package com.hemp.works.dashboard.offer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.model.Coupon
import com.hemp.works.dashboard.offer.data.repository.OfferRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class OfferViewModel @Inject constructor(private val repository: OfferRepository): BaseViewModel(repository) {

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


}
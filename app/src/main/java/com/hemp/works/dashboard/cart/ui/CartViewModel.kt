package com.hemp.works.dashboard.cart.ui

import androidx.core.text.HtmlCompat
import androidx.lifecycle.*
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.UserType
import com.hemp.works.dashboard.cart.data.repository.CartRepository
import com.hemp.works.dashboard.model.CartProduct
import com.hemp.works.dashboard.model.Coupon
import com.hemp.works.dashboard.model.RequestProduct
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel  @Inject constructor(private val repository: CartRepository): BaseViewModel(repository) {

    val cartList = Transformations.map(repository.cart) {
        it.products ?: emptyList()
    }
    val price = Transformations.map(repository.cart) {
        HtmlCompat.fromHtml(
        "<strike>MRP:&ensp; Rs. ${it.totalprice}</strike>  &emsp;  <b> Rs. ${it.discountprice} </b>"
            , HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    val cartListVisibility: LiveData<Boolean> = Transformations.map(repository.cart) {
        it.products?.isNotEmpty()
    }

    val coupon: LiveData<Coupon?> = Transformations.map(repository.cart) {
        it.coupon
    }

    val isCouponApplied: LiveData<Boolean> = Transformations.map(repository.cart) {
        it.coupon != null
    }

    val booleanResponse = repository.booleanResponse


    fun fetchCartDetails() {
        viewModelScope.launch {
            repository.fetchCart()
        }
    }

    fun onCancelCart(cartProduct: CartProduct) {
        viewModelScope.launch {
            repository.deleteProduct(RequestProduct(
                product = cartProduct.productid,
                variant = cartProduct.variantid,
                quantity = cartProduct.quantity
            ))
        }
    }

    fun onQuantityChange(cartProduct: CartProduct) {
        viewModelScope.launch {
            repository.addQuantity(RequestProduct(
                product = cartProduct.productid,
                variant = cartProduct.variantid,
                quantity = cartProduct.quantity
            ))
        }
    }



//    fun addCoupon(coupon: String?) {
//        viewModelScope.launch {
//            if (coupon.isNullOrBlank()) return@launch
//            repository.addCoupon(coupon)
//        }
//    }

    fun removeCoupon() {
        viewModelScope.launch {
            repository.removeCoupon()
        }
    }


}
package com.hemp.works.dashboard.cart.ui

import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.cart.data.repository.CartRepository
import javax.inject.Inject

class CartViewModel  @Inject constructor(private val repository: CartRepository): BaseViewModel(repository) {

}
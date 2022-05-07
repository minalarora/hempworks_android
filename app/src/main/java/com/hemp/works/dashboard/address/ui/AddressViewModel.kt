package com.hemp.works.dashboard.address.ui

import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.address.data.repository.AddressRepository
import com.hemp.works.dashboard.cart.data.repository.CartRepository
import com.hemp.works.login.data.model.Address
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddressViewModel  @Inject constructor(private val repository: AddressRepository, private val cartRepository: CartRepository): BaseViewModel(repository, cartRepository) {

    val addressList = repository.addressList

    val addressListVisibility = Transformations.map(repository.addressList) {
        it.isNotEmpty()
    }

    val booleanResponse = repository.booleanResponse

    val totalprice = cartRepository.cart.value?.totalprice
    val discountprice = cartRepository.cart.value?.discountprice
    val walletprice = cartRepository.cart.value?.walletprice

    fun fetchAddressList() {
        viewModelScope.launch {
            repository.fetchAddressList()
        }
    }

    fun removeAddress(address: Address) {
        viewModelScope.launch {
            repository.removeAddress(address)
        }
    }

}
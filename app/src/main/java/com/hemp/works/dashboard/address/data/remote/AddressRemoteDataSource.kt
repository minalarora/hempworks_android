package com.hemp.works.dashboard.address.data.remote

import com.hemp.works.base.BaseDataSource
import com.hemp.works.login.data.model.Address
import javax.inject.Inject

class AddressRemoteDataSource  @Inject constructor(private val service: AddressService): BaseDataSource() {

    suspend fun getAddressList() = getResult { service.getAddressList() }

    suspend fun addAddress(address: Address) = getResult { service.addAddress(address) }

    suspend fun removeAddress(addressId: Long) = getResult { service.removeAddress(addressId, hashMapOf("active" to false)) }

    suspend fun updateAddress(address: Address) = getResult {
        service.updateAddress(address.id ?: 0L, address.
        apply {
            id = null
            doctor = null
        })
    }
}
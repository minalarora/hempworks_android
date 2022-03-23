package com.hemp.works.dashboard.address.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.BooleanResponse
import com.hemp.works.base.Constants
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.address.data.remote.AddressRemoteDataSource
import com.hemp.works.dashboard.model.CalculatorProduct
import com.hemp.works.login.data.model.Address
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRepository @Inject constructor(private val remoteDataSource: AddressRemoteDataSource) : BaseRepository()  {

    private val _addressList = MutableLiveData<List<Address>>()
    val addressList: LiveData<List<Address>>
        get() = _addressList

    private val _booleanResponse = LiveEvent<Boolean>()
    val booleanResponse: LiveData<Boolean>
        get() = _booleanResponse

    suspend fun fetchAddressList() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.getAddressList()}?.let{
            it.data?.let { list -> _addressList.postValue(list) }
        }
    }

    suspend fun addAddress(address: Address) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.addAddress(address) }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }

    suspend fun removeAddress(address: Address) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.removeAddress(address.id ?: 0) }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }

    suspend fun updateAddress(address: Address) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) {
            remoteDataSource.updateAddress(address) }?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }




}
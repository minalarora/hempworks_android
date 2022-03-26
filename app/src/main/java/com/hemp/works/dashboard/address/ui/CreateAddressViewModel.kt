package com.hemp.works.dashboard.address.ui

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.address.data.repository.AddressRepository
import com.hemp.works.login.data.model.Address
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class CreateAddressViewModel @Inject constructor(private val repository: AddressRepository): BaseViewModel(repository) {

    val booleanResponse = repository.booleanResponse

    private val _address = LiveEvent<Address?>()
    val address: LiveData<Address?>
        get() = _address


    fun fetchAddress(id: String?) {
        try {
            if (id.isNullOrBlank()) _address.postValue(null)

           val address =  repository.addressList.value?.first { it.id == id?.toLong() }
            address?.let {
                _address.postValue(it)
            } ?: _address.postValue(null)

        } catch (ex: Exception) {
            _address.postValue(null)
        }
    }

    fun putAddress( addressLine1: String,
                    addressLine2: String,
                    city: String,
                    state: String,
                    pincode: String,
                    mobile: String) {
        viewModelScope.launch {
            if (TextUtils.isEmpty(addressLine1)) {
              error(Constants.INVALID_FIELDS)
              } else if (TextUtils.isEmpty(city)) {
               error(Constants.INVALID_FIELDS)
              } else if (TextUtils.isEmpty(state) || state == "Select State") {
                  error(Constants.INVALID_FIELDS)
              } else if (pincode.length != 6 || !TextUtils.isDigitsOnly(pincode)) {
                 error(Constants.INVALID_FIELDS)
              } else if (mobile.length != 10 || !TextUtils.isDigitsOnly(mobile)) {
                error(Constants.INVALID_FIELDS)
              } else {
                val address = Address(
                    id = _address.value?.id,
                    address1 = addressLine1,
                    address2 = addressLine2,
                    city = city,
                    state = state,
                    pincode = pincode.toInt(),
                    mobile = mobile
                )

                if (address.id != null) {
                    repository.updateAddress(address)
                } else {
                    repository.addAddress(address)
                }
             }
        }
    }


}
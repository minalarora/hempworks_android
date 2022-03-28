package com.hemp.works.dashboard.address.ui.adapter


import com.hemp.works.dashboard.address.ui.AddressItemClickListener
import com.hemp.works.login.data.model.Address
import java.lang.StringBuilder

class AddressViewModel(private val address: Address, private val listener: AddressItemClickListener) {

    private val titleBuilder = StringBuilder().also { addressBuilder ->
        address.address1?.let { addressBuilder.append("$it")   }
        address.address2?.let { if (it.isNotBlank()) addressBuilder.append(", $it")   }
        address.city?.let { addressBuilder.append(", $it")   }
        address.state?.let { addressBuilder.append(", $it")   }
        address.pincode?.let { addressBuilder.append(" - $it ")   }
    }

    val title = titleBuilder.toString()

    val subTitle = address.mobile ?: ""


    fun deleteAddress() {
        listener.onDeleteClick(address)
    }

    fun editAddress() {
        listener.onEditClick(address)
    }

    fun onAddressClick() {
        listener.onItemClick(address)
    }



}
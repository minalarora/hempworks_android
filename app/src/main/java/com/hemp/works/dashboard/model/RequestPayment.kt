package com.hemp.works.dashboard.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestPayment(
    val address: Long? = null,
    val payment: String? = null, //DIRECT OR CREDIT
    val totalprice: Int? = 0,
    val discountprice: Int? = 0,
    val amount: Int? = 0,
    val reason: String? = null //ORDER OR CREDIT

) : Parcelable



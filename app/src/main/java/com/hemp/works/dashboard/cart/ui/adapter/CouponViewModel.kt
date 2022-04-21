package com.hemp.works.dashboard.cart.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.hemp.works.R
import com.hemp.works.dashboard.model.CartProduct
import com.hemp.works.dashboard.model.Coupon
import java.text.SimpleDateFormat

class CouponViewModel(context: Context, private val coupon: Coupon, isCoupon: Boolean) {

    val title  = coupon.name.toString()

    val subtitle = coupon.description.toString()

    val isCoupon = isCoupon

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd MMM yy");

    val date =
        try {
            context.getString(R.string.expiry_coupon, dateFormat.format(coupon.expiry))
        } catch (ex: Exception) {
            ""
        }

    val dateVisibility = date.isNotBlank()
}
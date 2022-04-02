package com.hemp.works.dashboard.order.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.Order
import com.hemp.works.dashboard.order.ui.OrderItemClickListener
import java.text.SimpleDateFormat

class OrderViewModel(context: Context, private val order: Order, private val listener: OrderItemClickListener) {

    val orderId: String = context.getString(R.string.order_id, order.id?.toString())

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT);
    val date: String = dateFormat.format(order.date!!)

    val status: String = order.order?.get(0)?.status ?: order.status.toString()

    val statusTextColor = when(order.order?.get(0)?.status ?:  order.status.toString()) {
        context.getString(R.string.request_cancellation), context.getString(R.string.canceled) -> ContextCompat.getColor(context, R.color.dark_red)
        context.getString(R.string.completed) -> ContextCompat.getColor(context, R.color.green)
        else -> ContextCompat.getColor(context, R.color.orange_F8AA37)
    }

    val title: String = order.order?.get(0)?.productname.toString()

    val subTitle: String = order.order?.get(0)?.variantname.toString()

    val price: String  = context.getString(R.string.order_price, order.order?.get(0)?.price.toString())

    val quantity: String  = context.getString(R.string.order_quantity, order.order?.get(0)?.quantity.toString())

    fun onProductClick() {
        listener.onOrderClick(order.order?.get(0)?.productid!!)
    }


}
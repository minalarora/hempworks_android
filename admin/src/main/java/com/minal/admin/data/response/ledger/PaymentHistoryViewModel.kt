package com.minal.admin.data.response.ledger

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import com.minal.admin.R
import java.text.SimpleDateFormat

class PaymentHistoryViewModel(context: Context, private val transaction: Transaction) {

    val transactionId: String = context.getString(R.string.transaction_id, transaction.id?.toString())

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd MMM yyyy  hh:mm:ss a");
    val date: String = dateFormat.format(transaction.date!!)

    val status: String = transaction.status.toString()

    val statusTextColor = when(status) {
        context.getString(R.string.canceled), context.getString(R.string.failed) -> ContextCompat.getColor(context, R.color.dark_red)
        context.getString(R.string.success) -> ContextCompat.getColor(context, R.color.green)
        else -> ContextCompat.getColor(context, R.color.orange_F8AA37)
    }

    val title: String = context.getString(R.string.transaction_amount, transaction.amount.toString())

    val subTitle: String = context.getString(R.string.transaction_subtitle, transaction.reason,
        if(transaction.reason == "ORDER") transaction.orderid.toString() else transaction.creditid.toString())




}
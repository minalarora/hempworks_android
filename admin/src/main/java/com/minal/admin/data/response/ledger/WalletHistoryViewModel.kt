package com.minal.admin.data.response.ledger

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import com.minal.admin.R
import java.text.SimpleDateFormat

class WalletHistoryViewModel(context: Context, private val walletHistory: WalletHistory) {

    val walletId: String = if (walletHistory.operation == "add") "Credit Coins Earned" else "Credit Coins Used"

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd MMM yyyy  hh:mm:ss a");
    val date: String = dateFormat.format(walletHistory.date!!)

//    val status: String = walletHistory.operation.toString().uppercase()

    val statusTextColor = when(walletHistory.operation.toString().uppercase()) {
        context.getString(R.string.add) -> ContextCompat.getColor(context, R.color.green)
        else -> ContextCompat.getColor(context, R.color.dark_red)
    }

    val title: String = context.getString(R.string.transaction_amount, walletHistory.amount.toString())

    val subTitle: String = context.getString(
        R.string.transaction_subtitle,
        if (walletHistory.orderid != null) "ORDER" else if (walletHistory.couponid != null) "COUPON" else "NA",
        if (walletHistory.orderid != null) walletHistory.orderid else if (walletHistory.couponid != null) walletHistory.coupon?.name.toString() else "NA")


}
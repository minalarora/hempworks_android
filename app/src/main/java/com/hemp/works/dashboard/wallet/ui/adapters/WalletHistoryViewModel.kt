package com.hemp.works.dashboard.wallet.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.Transaction
import com.hemp.works.dashboard.model.WalletHistory
import java.text.SimpleDateFormat

class WalletHistoryViewModel(context: Context, private val walletHistory: WalletHistory) {

    val walletId: String = context.getString(R.string.wallethistory_id, walletHistory.id?.toString())

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT);
    val date: String = dateFormat.format(walletHistory.date!!)

    val status: String = walletHistory.operation.toString().uppercase()

    val statusTextColor = when(status) {
        context.getString(R.string.add) -> ContextCompat.getColor(context, R.color.green)
        else -> ContextCompat.getColor(context, R.color.dark_red)
    }

    val title: String = context.getString(R.string.transaction_amount, walletHistory.amount.toString())

    val subTitle: String = context.getString(
        R.string.transaction_subtitle,
        if (walletHistory.orderid != null) "ORDER" else if (walletHistory.couponid != null) "COUPON" else "NA",
        if (walletHistory.orderid != null) walletHistory.orderid else if (walletHistory.couponid != null) walletHistory.coupon?.name.toString() else "NA")


}
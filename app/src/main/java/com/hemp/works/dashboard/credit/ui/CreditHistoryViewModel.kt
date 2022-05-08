package com.hemp.works.dashboard.credit.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.CreditHistory
import java.text.SimpleDateFormat

class CreditHistoryViewModel(context: Context, private val creditHistory: CreditHistory) {

    val creditId: String = context.getString(R.string.credithistory_id, creditHistory.id?.toString())

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT);
    val date: String = dateFormat.format(creditHistory.date!!)

    val status: String =
        if (creditHistory.operation == "sub") "DONE"
         else {
            if (creditHistory.paid == true) "PAID" else "PENDING RS." + creditHistory.pendingamount
            }

    val statusTextColor =
        if (creditHistory.operation == "sub") ContextCompat.getColor(
            context,
            R.color.green
        )
        else {
            if (creditHistory.paid == true) ContextCompat.getColor(
                context,
                R.color.green
            ) else ContextCompat.getColor(context, R.color.dark_red)
        }

    val title: String = if (creditHistory.operation == "sub")
        "Repayment of Rs. " + creditHistory.amount + " is done successfully by " + creditHistory.who
    else {
        if (creditHistory.paid == true)
            "Credit of Rs. " + creditHistory.amount + " is used for Order #" + creditHistory.order?.id.toString()
        else
            "Credit of Rs. " + creditHistory.amount + " is used for Order #" + creditHistory.order?.id.toString() +
                    ". Due amount of Rs. " + creditHistory.pendingamount

    }

}
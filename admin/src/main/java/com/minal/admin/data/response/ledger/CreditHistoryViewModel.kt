package com.minal.admin.data.response.ledger

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import com.minal.admin.R
import java.text.SimpleDateFormat

class CreditHistoryViewModel(context: Context, private val creditHistory: CreditHistory) {

    val creditId: String = if (creditHistory.operation == "sub") "Credit Amount Added" else "Credit Amount Used"

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd MMM yyyy  hh:mm:ss a");
    val date: String = dateFormat.format(creditHistory.date!!)

//    val status: String =
//        if (creditHistory.operation == "sub") "ADDED"
//         else "USED"
//
    val statusTextColor =
        if (creditHistory.operation == "sub") ContextCompat.getColor(
            context,
            R.color.green
        )
        else  ContextCompat.getColor(context, R.color.dark_red)


    val title: String = if (creditHistory.operation == "sub")
        "Rs. " + creditHistory.amount + " by " + creditHistory.who
    else {
        if (creditHistory.paid == true)
            "Rs. " + creditHistory.amount + "."
        else
            "Rs. " + creditHistory.amount  +
                    ". Due amount is Rs. " + creditHistory.pendingamount

    }

    val subTitle: String = if (creditHistory.operation != "sub") context.getString(
        R.string.transaction_subtitle,
         "ORDER",
        creditHistory.orderid.toString()) else ""
    //for Order #" + creditHistory.order?.id.toString()

}
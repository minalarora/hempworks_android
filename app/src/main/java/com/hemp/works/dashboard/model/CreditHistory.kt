package com.hemp.works.dashboard.model

import java.util.*

data class CreditHistory(
    val id: Long? = null,
    val credit: Long? = null,
    val operation: String? = null,
    val date: Date? = null,
    val amount: Int? = null,
    val creditamount: Int? = null,
    val pendingamount: Int? = null,
    val paid: Boolean? = null,
    val doctor: String? = null,
    val who: String? = null,
    val orderid: Long? = null,
    val order: Order? = null,
    val transactionid: Long? = null,
    val transaction: Transaction? = null
)
package com.minal.admin.data.response.ledger

import java.util.*

data class Transaction(
    val orderid: Long? = null,
    val creditid: Long? = null,
    val paymentmode: String? = null,
    val referenceid: String? = null,
    val message: String? = null,
    val signature: String? = null,
    val amount: Int? = null,
    val doctorid: String? = null,
    val status: String? = null,
    val reason: String? = null,
    val id: Long? = null,
    val date: Date? = null
)
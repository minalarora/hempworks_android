package com.minal.admin.data.response.ledger

import java.util.*

data class Coupon(
    val id: Long?,
    val name: String,
    val type: String,
    val description: String?,
    val value: String?,
    val expiry: Date?,
    val product: Long?,
    val variant: Long?
)

package com.hemp.works.dashboard.model

data class CartProduct(
    val product: Product? =  null,
    val productid: Long? = null,
    val variantid: Long? = null,
    var quantity: Int? = null
)
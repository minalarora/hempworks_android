package com.hemp.works.dashboard.model

data class Variant(
    var id: Long = 0,
    var size: Long = 0,
    var rate: Long = 0,
    var type: String = "",
    var instock: Boolean = false,
    var product: Long = 0
)

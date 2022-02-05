package com.hemp.works.dashboard.model

data class Product(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var pdf: String? = null,
    var images: List<String> = emptyList(),
    var category: Long = 0,
    var categoryname: String = "",
    var bestseller: Boolean = false,
    var instock: Boolean = false,
    var quantity: Int = 0,
    var variants: List<Variant> = emptyList()
)


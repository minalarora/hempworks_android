package com.minal.admin.data.response


import androidx.databinding.ObservableBoolean
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

 class ResponseProduct(
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
    var variants: List<Variant?> ?= null
){
    data class Variant(
        var id: Long = 0,
        var size: Long = 0,
        var rate: Long = 0,
        var type: String = "",
        var instock: Boolean = false,
        var product: Long = 0
    ) {
        // USING FOR SELECTING THE VARIANT OF PRODUCT
        var isSelected: ObservableBoolean = ObservableBoolean(false)
    }
}
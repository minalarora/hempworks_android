package com.hemp.works.dashboard.model

import androidx.databinding.ObservableBoolean

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

package com.hemp.works.dashboard.model

data class CalculatorProduct(
    var productName: String,
    var variant: List<CalculatorVariant>
)

data class CalculatorVariant(
    var quantity: String?,
    var indication: String?,
    var weight: String?,
    var dosage: String?
)
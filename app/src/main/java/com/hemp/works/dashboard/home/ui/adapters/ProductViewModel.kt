package com.hemp.works.dashboard.home.ui.adapters

import android.content.Context
import androidx.core.content.ContextCompat
import com.hemp.works.R
import com.hemp.works.dashboard.model.Product

class ProductViewModel(context: Context, private val product: Product) {

    val title = product.title
    val image = product.images[0]

    val subTitle = when {
        !product.instock -> context.getString(R.string.out_of_stock)
        product.bestseller -> context.getString(R.string.best_seller)
        else -> ""
    }

    val subTitleVisibility: Boolean = when {
        !product.instock -> true
        product.bestseller -> true
        else -> false
    }

    val subTitleBackgroundColor = when {
        !product.instock -> ContextCompat.getColor(context, R.color.dark_red)//#8B0000
        else -> ContextCompat.getColor(context, R.color.green_title)
    }

    val price: String = context.getString(R.string.product_price_from, product.variants.minOf { it.rate })

}
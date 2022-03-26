package com.hemp.works.dashboard.cart.ui.adapter


import androidx.databinding.ObservableField
import com.hemp.works.dashboard.cart.ui.CartItemClickListener
import com.hemp.works.dashboard.model.CartProduct


class CartViewModel(private val cartProduct: CartProduct, private val listener: CartItemClickListener) {

    val image = if(cartProduct.product?.images?.isNotEmpty() == true) cartProduct.product?.images?.get(0)
                else ""

    val title  = cartProduct.product?.title.toString()

    val subTitle: String = cartProduct.product?.variants?.find { variant ->
                            variant.id == cartProduct.variantid }?.let {
            return@let it.size.toString() + " " + it.type
    } ?: ""

    val price: String = cartProduct.product?.variants?.find { variant ->
        variant.id == cartProduct.variantid }?.let {
        return@let "Rs. " + it.rate.toString()
    } ?: ""

    var quantity: ObservableField<String> = ObservableField(cartProduct.quantity.toString())

    fun increaseQuantity() {
        quantity.get()?.let {
            val newQuantity = it.toInt() + (cartProduct.product?.quantity ?: 1)
            quantity.set(newQuantity.toString())
            listener.onQuantityChange(cartProduct.copy(quantity = newQuantity))
        }
    }

    fun decreaseQuantity() {
        quantity.get()?.let {
            if (cartProduct.product?.quantity != it.toInt()) {
                val newQuantity = it.toInt() - (cartProduct.product?.quantity ?: 1)
                quantity.set(newQuantity.toString())
                listener.onQuantityChange(cartProduct.copy(quantity = newQuantity))
            }
        }
    }

    fun deleteProduct() {
        listener.onCancelClick(cartProduct)
    }

    fun onProductClick() {
        listener.onItemClick(cartProduct)
    }

}
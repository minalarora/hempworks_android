package com.hemp.works.dashboard.product.ui

import android.content.Context
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.lifecycle.*
import com.hemp.works.R
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.UserType
import com.hemp.works.dashboard.model.Variant
import com.hemp.works.dashboard.product.data.repository.ProductRepository
import com.hemp.works.login.data.model.Doctor
import com.hemp.works.login.data.model.User
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class ProductViewModel @Inject constructor(private val repository: ProductRepository) : BaseViewModel(repository) {

    fun loadProduct(user: Doctor?, userType: UserType, id: String, category: String) {
        this.user = user
        this.userType = userType
        productId = id.toLong()
        viewModelScope.launch {
            val deferredProduct = async {  repository.getProductById(id.toLong()) }
            val deferredCategoryProducts = async {  repository.getProductsByCategory(category.toLong()) }
            deferredProduct.await()
            deferredCategoryProducts.await()
        }
    }

    fun selectVariant(variant: Variant) {
            unSelectVariant()
            variant.isSelected.set(true)
            selectedVariant = variant
            _price.postValue(updatePrice(product.value?.variants ?: emptyList()))
    }

    private fun discountedPrice(price: Long, percentage: Double): Int {
       return (price - (price * percentage / 100)).roundToInt()
    }

    private fun unSelectVariant() {
        selectedVariant?.isSelected?.set(false)
        selectedVariant = null
    }

    private fun updatePrice(list: List<Variant>) =
        HtmlCompat.fromHtml(if (userType == UserType.APPROVED) {
            if (selectedVariant != null){
                val initialPercentage = user?.discount?.find { it.price == 0 }?.percentage
                if (initialPercentage == 0.0 || initialPercentage == null) {
                    "Price:&ensp;Rs. ${selectedVariant?.rate.toString()}"
                } else {
                    "<strike>MRP:&ensp; Rs. ${selectedVariant?.rate.toString()}</strike>  <br>  Price:&ensp; Rs. ${discountedPrice(selectedVariant?.rate!!, initialPercentage)} &emsp; <font color='#8B0000'>Save ${initialPercentage}% </font>"
                }
            }
            else  {
                val initialPercentage = user?.discount?.find { it.price == 0 }?.percentage
                if (initialPercentage == 0.0 || initialPercentage == null) {
                    "Price:&ensp;Rs. ${list[0].rate.toString()}"
                } else {
                    "<strike>MRP:&ensp; Rs. ${list[0].rate}</strike> <br> Price:&ensp; Rs. ${discountedPrice(list[0].rate, initialPercentage)} &emsp; <font color='#8B0000'>Save ${initialPercentage}%</font>"
                }
            }
        } else {
            if (selectedVariant != null) "Price:&ensp; Rs. ${selectedVariant?.rate.toString()}"
            else "Price:&ensp; Rs. ${list[0].rate.toString()}"
        }, HtmlCompat.FROM_HTML_MODE_LEGACY)

    fun increaseQuantity() {
        _quantity.value?.let {
            _quantity.postValue((it.toInt() + defaultQuantity).toString())
        }
    }

    fun decreaseQuantity() {
        _quantity.value?.let {
            if (defaultQuantity != it.toInt()) {
                _quantity.postValue((it.toInt() - defaultQuantity).toString())
            }
        }
    }
    val product = repository.product
    val productsByCategory = repository.productsByCategory.map { it.filter { product -> product.id != productId } }
    var user: Doctor? = null
    var userType: UserType? = null
    var productId: Long = 0
    private var selectedVariant: Variant? = null
    private var defaultQuantity: Int = 1

    val productTitle: LiveData<String> = Transformations.map(repository.product) {
        it.title
    }

    val productImages: LiveData<List<String>> = Transformations.map(repository.product) {
        it.images
    }

    val productImagesVisibility: LiveData<Boolean> = Transformations.map(repository.product) {
        it.images.isNotEmpty()
    }

    val sizes: LiveData<List<Variant>> =  Transformations.map(repository.product) {
        selectVariant(it.variants[0])
        it.variants
    }

    private val _price: MutableLiveData<Spanned> = Transformations.map(repository.product) {
        updatePrice(it.variants)
    } as MutableLiveData<Spanned>
    val price: LiveData<Spanned> = _price


    private val _quantity: MutableLiveData<String> = Transformations.map(repository.product) {
        defaultQuantity = it.quantity
        it.quantity.toString()
    } as MutableLiveData<String>
    val quantity: LiveData<String> = _quantity

    val description: LiveData<Spanned> = Transformations.map(repository.product) {
        HtmlCompat.fromHtml(it.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    val pdfVisibility: LiveData<Boolean> = Transformations.map(repository.product) {
        !it.pdf.isNullOrBlank()
    }
    val productsListVisibility: LiveData<Boolean> = Transformations.map(productsByCategory) {
        it.isNotEmpty()
    }

    val buttonVisibility: LiveData<Boolean> = Transformations.map(repository.product) {
        it.instock
    }

}

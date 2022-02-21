package com.hemp.works.dashboard.calculator.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.calculator.data.repository.CalculatorRepository
import com.hemp.works.dashboard.model.CalculatorProduct
import com.hemp.works.dashboard.model.CalculatorVariant
import kotlinx.coroutines.launch
import javax.inject.Inject

class DosageCalculatorViewModel  @Inject constructor(private val repository: CalculatorRepository): BaseViewModel(repository) {

    private val _state: MutableLiveData<STATE> = MutableLiveData(STATE.PRODUCT)
    val state: LiveData<STATE> = _state

    var selectedProduct: CalculatorProduct? = null

    init {
        viewModelScope.launch {
            repository.fetchCalculatorList()
        }
    }
    private val _productList: MutableLiveData<List<String>> = Transformations.map(repository.calculatorList) {
        filterProductList(it)
    } as MutableLiveData<List<String>>
    val productList: LiveData<List<String>> = _productList

    private fun filterProductList(list: List<CalculatorProduct>) : List<String>{
        return list.map { calculatorProduct -> calculatorProduct.productName }
    }

    fun onProductSelected(product: String) {
        selectedProduct = CalculatorProduct(productName = product, variant = emptyList())
        _typeList.value = filterTypeList(repository.calculatorList.value!!)
        _state.value = STATE.TYPE
    }

    private val _typeList: MutableLiveData<List<String>> = Transformations.map(repository.calculatorList) {
        filterTypeList(it)
    } as MutableLiveData<List<String>>
    val typeList: LiveData<List<String>> = _typeList

    private fun filterTypeList(list: List<CalculatorProduct>): List<String> {
        return selectedProduct?.productName?.let { productName ->
            return list.find{ calculatorProduct -> calculatorProduct.productName == productName }?.let {
                return it.variant.map  { variant -> variant.quantity!! }.toSet().toList()
            } ?: emptyList()
        } ?: emptyList()
    }

    fun onTypeSelected(type: String) {
        selectedProduct?.apply {
            variant = listOf(CalculatorVariant(quantity = type, dosage = null, indication = null, weight = null))
        }
        _indicationList.value = filterIndicationList(repository.calculatorList.value!!)
        _state.value = STATE.INDICATION
    }

    private val _indicationList: MutableLiveData<List<String>> = Transformations.map(repository.calculatorList) {
        filterIndicationList(it)
    } as MutableLiveData<List<String>>
    val indicationList: LiveData<List<String>> = _indicationList

    private fun filterIndicationList(list: List<CalculatorProduct>): List<String> {
        return selectedProduct?.productName?.let { productName ->
            return list.find{ calculatorProduct -> calculatorProduct.productName == productName }?.let {
                return it.variant.filter { variant -> variant.quantity == selectedProduct?.variant?.get(0)?.quantity }
                    .map  { variant -> variant.indication!! }.toSet().toList()
            } ?: emptyList()
        } ?: emptyList()
    }

    fun onIndicationSelected(indication: String) {
        selectedProduct?.apply {
            variant[0].indication = indication
        }
        _weightList.value = filterWeightList(repository.calculatorList.value!!)
        _state.value = STATE.WEIGHT
    }

    private val _weightList: MutableLiveData<List<String>> = Transformations.map(repository.calculatorList) {
        filterWeightList(it)
    } as MutableLiveData<List<String>>
    val weightList: LiveData<List<String>> = _weightList

    private fun filterWeightList(list: List<CalculatorProduct>): List<String> {
        return selectedProduct?.productName?.let { productName ->
            return list.find{ calculatorProduct -> calculatorProduct.productName == productName }?.let {
                return it.variant.filter { variant -> variant.quantity == selectedProduct?.variant?.get(0)?.quantity && variant.indication == selectedProduct?.variant?.get(0)?.indication}
                    .map  { variant -> variant.weight!! }.toSet().toList()
            } ?: emptyList()
        } ?: emptyList()
    }

    fun onWeightSelected(weight: String) {
        selectedProduct?.apply {
            variant[0].weight = weight
        }
        _dosage.value = filterDosageResult(repository.calculatorList.value!!)
        _state.value = STATE.PRODUCT
        selectedProduct = null
    }

    private val _dosage: LiveEvent<String> = LiveEvent()
    val dosage: LiveData<String> = _dosage

    private fun filterDosageResult(list: List<CalculatorProduct>): String {
        return selectedProduct?.productName?.let { productName ->
            return list.find{ calculatorProduct -> calculatorProduct.productName == productName }?.let {
                return it.variant.find { variant ->
                    variant.quantity == selectedProduct?.variant?.get(0)?.quantity &&
                            variant.indication == selectedProduct?.variant?.get(0)?.indication &&
                            variant.weight == selectedProduct?.variant?.get(0)?.weight
                }?.dosage ?: ""
            } ?: ""
        } ?: ""
    }

}

enum class STATE(val value: Int){
    PRODUCT(0), TYPE(1), INDICATION(2), WEIGHT(3), DOSAGE(4)
}
package com.hemp.works.dashboard.product.ui

import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.product.data.repository.ProductRepository
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val repository: ProductRepository) : BaseViewModel(repository) {
}
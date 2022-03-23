package com.hemp.works.dashboard.address.ui

import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.address.data.repository.AddressRepository
import javax.inject.Inject

class AddressViewModel  @Inject constructor(private val repository: AddressRepository): BaseViewModel(repository) {
}
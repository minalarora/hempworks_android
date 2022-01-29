package com.hemp.works.login.ui.viewmodel

import com.hemp.works.base.BaseViewModel
import com.hemp.works.login.data.repository.LoginRepository
import javax.inject.Inject

class CreateViewModel @Inject constructor(private val repository: LoginRepository) :  BaseViewModel(repository) {

    val imageUrl = repository.imageUrl


}
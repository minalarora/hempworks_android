package com.hemp.works.login.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.login.data.model.Credential
import com.hemp.works.login.data.repository.LoginRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForgetPasswordViewModel @Inject constructor(private val repository: LoginRepository) : BaseViewModel(repository) {



}
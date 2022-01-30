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

    val booleanResponse = repository.booleanResponse
    lateinit var mobile: String

    fun updatePassword(password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            error(Constants.PASSWORD_MISMATCH)
            return
        }
        if (password.isBlank()) {
            error(Constants.INVALID_PASSWORD)
            return
        }

        viewModelScope.launch {
            repository.updatePassword(mobile, password)
        }
    }


}
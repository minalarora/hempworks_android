package com.hemp.works.login.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.login.data.repository.LoginRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: LoginRepository) : BaseViewModel(repository) {

    val user = repository.user

    fun onLogin(username: String, password: String) {

        viewModelScope.launch {

        }
    }
}
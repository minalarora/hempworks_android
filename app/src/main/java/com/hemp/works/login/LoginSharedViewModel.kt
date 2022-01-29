package com.hemp.works.login

import androidx.lifecycle.ViewModel
import com.hemp.works.base.BaseViewModel
import com.hemp.works.login.data.model.Credential
import com.hemp.works.login.data.repository.LoginRepository
import javax.inject.Inject

class LoginSharedViewModel @Inject constructor(private val repository: LoginRepository) : BaseViewModel(repository) {

}
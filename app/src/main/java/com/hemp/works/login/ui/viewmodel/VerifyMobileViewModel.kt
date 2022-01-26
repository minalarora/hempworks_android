package com.hemp.works.login.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.hemp.works.base.BaseViewModel
import com.hemp.works.login.data.repository.LoginRepository
import javax.inject.Inject

class VerifyMobileViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel(), BaseViewModel {
}
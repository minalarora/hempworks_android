package com.hemp.works.dashboard.account.ui

import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.account.data.repository.AccountRepository
import com.hemp.works.dashboard.home.data.repository.HomeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel @Inject constructor(private val repository: AccountRepository) : BaseViewModel(repository){

    val booleanResponse = repository.booleanResponse

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
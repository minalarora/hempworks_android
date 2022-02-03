package com.hemp.works.dashboard.home.ui

import com.hemp.works.base.BaseViewModel
import com.hemp.works.dashboard.home.data.repository.HomeRepository
import com.hemp.works.login.data.repository.LoginRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel(repository) {


}
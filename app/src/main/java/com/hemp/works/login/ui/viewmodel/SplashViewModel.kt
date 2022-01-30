package com.hemp.works.login.ui.viewmodel

import android.content.Context
import android.os.Looper
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.*
import com.hemp.works.login.data.repository.LoginRepository
import com.hemp.works.utils.PreferenceManagerUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.logging.Handler
import javax.inject.Inject

class SplashViewModel
@Inject constructor(private val repository: LoginRepository, context: Context)
    : BaseViewModel(repository) {

        private val userType = PreferenceManagerUtil.getString(context, Constants.USER_TYPE)

        val user = repository.user

        init {
            if (userType?.equals(Constants.DOCTOR) == true) {
                viewModelScope.launch {
                    repository.fetchDoctor()
                }
            }
            else  {
                viewModelScope.launch {
                    repository.fetchAdmin()
                }
            }
        }

}
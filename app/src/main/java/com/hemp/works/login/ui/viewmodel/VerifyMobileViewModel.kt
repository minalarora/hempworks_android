package com.hemp.works.login.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.base.LiveEvent
import com.hemp.works.login.data.repository.LoginRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class VerifyMobileViewModel @Inject constructor(private val repository: LoginRepository) : BaseViewModel(repository) {

    val booleanResponse = repository.booleanResponse

    private val _isMobileState: MutableLiveData<Boolean> = MutableLiveData(true)
    val isMobileState: LiveData<Boolean> = _isMobileState

    private val _goAhead: LiveEvent<Boolean> = LiveEvent()
    val goAhead: LiveData<Boolean> = _goAhead

    lateinit var reason: String

    fun verifyMobile(mobile: String) {

        if (mobile.length < 10) {
            error(Constants.INVALID_MOBILE)
            return
        }
        viewModelScope.launch {
            repository.verifyMobile(mobile)
        }
    }

    fun verifyOtp(mobile: String, otp: String) {

        if (otp.length < 4) {
            error(Constants.INVALID_MOBILE)
            return
        }
        viewModelScope.launch {
            repository.verifyOtp(mobile, otp.toInt())
        }
    }

    fun handleBooleanResponse(boolean: Boolean) {
        if (reason == Constants.REASON_CREATE_ACCOUNT) {
            if (isMobileState.value == true) {
                if (boolean) error(Constants.USER_ALREADY_EXIST) else _isMobileState.postValue(false)
            } else {
                if (boolean) _goAhead.postValue(true) else error(Constants.INVALID_OTP)
            }
        } else {
            if (isMobileState.value == true) {
                if (!boolean) error(Constants.USER_NOT_FOUND) else _isMobileState.postValue(false)
            } else {
                if (boolean) _goAhead.postValue(true) else error(Constants.INVALID_OTP)
            }
        }
    }

}
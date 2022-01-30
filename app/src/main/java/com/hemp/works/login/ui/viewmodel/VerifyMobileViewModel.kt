package com.hemp.works.login.ui.viewmodel

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.hemp.works.R
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.base.LiveEvent
import com.hemp.works.login.data.repository.LoginRepository
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class VerifyMobileViewModel @Inject constructor(context: Context, private val repository: LoginRepository) : BaseViewModel(repository) {

    val booleanResponse = repository.booleanResponse

    private val _isMobileState: MutableLiveData<Boolean> = MutableLiveData(true)
    val isMobileState: LiveData<Boolean> = _isMobileState

    private val _goAhead: LiveEvent<Boolean> = LiveEvent()
    val goAhead: LiveData<Boolean> = _goAhead

    lateinit var reason: String

    private val _titleText: MutableLiveData<String> = MutableLiveData()
    val titleText: LiveData<String> = _titleText

    private val _resendOtpText: MutableLiveData<String> = MutableLiveData()
    val resendOtpText: LiveData<String> = _resendOtpText

    private val createAccountMessage  =  context.getString(R.string.enter_mobile_number)
    private val updatePasswordMessage = context.getString(R.string.update_password_message)
    private val otpMessage  = context.getString(R.string.enter_otp)

    var canResendOTP: Boolean = false

    fun verifyMobile(mobile: String) {

        if (mobile.length < 10) {
            error(Constants.INVALID_MOBILE)
            return
        }
        viewModelScope.launch {
            _isMobileState.postValue(true)
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
                if (boolean) error(Constants.USER_ALREADY_EXIST) else {
                    _isMobileState.postValue(false)
                    startResendOTPService()
                }
            } else {
                if (boolean) _goAhead.postValue(true) else error(Constants.INVALID_OTP)
            }
        } else {
            if (isMobileState.value == true) {
                if (!boolean) error(Constants.USER_NOT_FOUND) else {
                    _isMobileState.postValue(false)
                    startResendOTPService()
                }
            } else {
                if (boolean) _goAhead.postValue(true) else error(Constants.INVALID_OTP)
            }
        }
    }

    private fun startResendOTPService() {
        canResendOTP = false
        _titleText.postValue(otpMessage)
        object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                _resendOtpText.postValue("Resend OTP in " + millisUntilFinished / 1000 + " secs")
            }

            override fun onFinish() {
                _resendOtpText.postValue("Resend OTP")
                canResendOTP = true
            }
        }.start()
    }

    fun updateTitle() {
        if (reason == Constants.REASON_CREATE_ACCOUNT) {
            _titleText.postValue(createAccountMessage)
        }  else {
            _titleText.postValue(updatePasswordMessage)
        }
    }

}
package com.hemp.works.dashboard.profile.ui

import android.content.Context
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.profile.data.repository.ProfileRepository
import com.hemp.works.login.data.model.Doctor
import com.hemp.works.utils.FileUtils
import com.hemp.works.utils.PreferenceManagerUtil
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class ProfileViewModel  @Inject constructor(private val repository: ProfileRepository, context: Context): BaseViewModel(repository) {

    val booleanResponse = repository.booleanResponse
    val booleanResponseForDelete = repository.booleanResponseForDelete
    private val localDoctor =  PreferenceManagerUtil.getDoctor(context)

    val doctor: LiveData<Doctor> =  Transformations.map(repository.user) {
        if (it.doctor != null) it.doctor
        else localDoctor
    }

    var fileName: String = ""
    var file: File? = null

    init {
       fetchDoctor()
    }

    private fun uploadImage() {
        if (file == null) {
            error(Constants.UPLOAD_IMAGE)
            return
        }
        viewModelScope.launch {
            repository.updateProfile(prepareFilePart(FileUtils.SERVER_IMAGE_KEY_NAME, file!!))
        }
    }

    private fun prepareFilePart(partName: String, outputFile: File): MultipartBody.Part {
        val requestFile = RequestBody.create(FileUtils.SERVER_FILE_TYPE.toMediaTypeOrNull(), outputFile)
        return MultipartBody.Part.createFormData(partName, fileName, requestFile)
    }

    fun updateImage(file: File) {
        if (file.name.isNotBlank()) {
            this.file = file
            if (file.name.contains(FileUtils.JPG_FILE)) {
                fileName = file.name
            } else {
                fileName = file.name + FileUtils.JPG_FILE
            }
            uploadImage()
        }
    }

    fun sendOTP(mobile: String) {

        if (mobile.length < 10 && !mobile.isDigitsOnly()) {
            error(Constants.INVALID_MOBILE)
            return
        }
        viewModelScope.launch {
            repository.sendOTP(mobile)
        }
    }

    fun updateMobile(mobile: String, otp: String) {
        if (otp.length < 4 && !otp.isDigitsOnly()) {
            error(Constants.INVALID_OTP)
            return
        }
        viewModelScope.launch {
            repository.updateMobile(mobile, otp.toInt())
        }
    }

    fun updateEmail(email: String) {
        if (email.isNullOrBlank()) {
            error(Constants.INVALID_EMAIL)
            return
        }
        viewModelScope.launch {
            repository.updateEmail(email)
        }
    }

    fun fetchDoctor() {
        viewModelScope.launch {
            repository.fetchDoctor()
        }
    }

    fun deleteDoctor() {
        viewModelScope.launch {
            repository.deleteDoctor()
        }
    }
}
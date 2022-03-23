package com.hemp.works.login.ui.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.login.data.model.Address
import com.hemp.works.login.data.model.RequestDoctor
import com.hemp.works.login.data.repository.LoginRepository
import com.hemp.works.utils.FileUtils.JPG_FILE
import com.hemp.works.utils.FileUtils.SERVER_FILE_TYPE
import com.hemp.works.utils.FileUtils.SERVER_IMAGE_KEY_NAME
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class CreateViewModel @Inject constructor(private val repository: LoginRepository) :  BaseViewModel(repository) {

    val imageUrl = repository.imageResponse
    val doctor = repository.user

    private val _isFile: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFile: LiveData<Boolean> = _isFile

    var fileName: String = ""
    var file: File? = null

    lateinit var mobile: String

    fun removeImage() {
        fileName = ""
        file = null
        _isFile.postValue(false)

    }

    fun createDoctor(
        name: String,
        clinic: String,
        email: String,
        password: String,
        confirmPassword: String,
        addressLine1: String,
        addressLine2: String,
        city: String,
        state: String,
        pincode: String
    ) {
        if (TextUtils.isEmpty(name)) {
            error(Constants.INVALID_FIELDS)
        } else if (TextUtils.isEmpty(clinic)) {
            error(Constants.INVALID_FIELDS)
        } else if (TextUtils.isEmpty(email)) {
            error(Constants.INVALID_FIELDS)
        } else if (password != confirmPassword) {
            error(Constants.PASSWORD_MISMATCH)
        } else if (password.isBlank()) {
            error(Constants.INVALID_PASSWORD)
        } else if (TextUtils.isEmpty(addressLine1)) {
            error(Constants.INVALID_FIELDS)
        } else if (TextUtils.isEmpty(city)) {
            error(Constants.INVALID_FIELDS)
        } else if (TextUtils.isEmpty(state) || state == "Select State") {
            error(Constants.INVALID_FIELDS)
        } else if (pincode.length != 6 || !TextUtils.isDigitsOnly(pincode)) {
            error(Constants.INVALID_FIELDS)
        } else if (TextUtils.isEmpty(imageUrl.value?.url ?: "") || isFile.value == false){
            error(Constants.UPLOAD_IMAGE)
        } else {
            val address = Address(
                address1 = addressLine1,
                address2 = addressLine2,
                city = city,
                state = state,
                pincode = pincode.toInt(),
                mobile = mobile
            )

            val doctor = RequestDoctor(
                name = name,
                email = email,
                mobile = mobile,
                clinic = clinic,
                password = password,
                address = address,
                certificate = imageUrl.value?.url
            )

            viewModelScope.launch {
                repository.createDoctor(doctor)
            }
        }

    }

    private fun uploadImage() {
        if (file == null) {
            error(Constants.UPLOAD_IMAGE)
            return
        }
        viewModelScope.launch {
            repository.uploadCertificate(prepareFilePart(SERVER_IMAGE_KEY_NAME, file!!))
        }
    }

    fun saveImage(file: File) {
        if (file.name.isNotBlank()) {
            this.file = file
            if (file.name.contains(JPG_FILE)) {
                fileName = file.name
            } else {
                fileName = file.name + JPG_FILE
            }
            _isFile.postValue(true)
            uploadImage()
        }
    }

    private fun prepareFilePart(partName: String, outputFile: File): MultipartBody.Part {
        val requestFile = RequestBody.create(MediaType.parse(SERVER_FILE_TYPE), outputFile)
        return MultipartBody.Part.createFormData(partName, fileName, requestFile)
    }


}
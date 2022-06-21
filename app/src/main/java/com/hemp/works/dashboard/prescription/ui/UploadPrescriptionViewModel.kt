package com.hemp.works.dashboard.prescription.ui

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.Prescription
import com.hemp.works.dashboard.prescription.data.repository.PrescriptionRepository
import com.hemp.works.utils.FileUtils
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UploadPrescriptionViewModel  @Inject constructor(private val repository: PrescriptionRepository): BaseViewModel(repository) {

    val mediaUrl = repository.imageResponse
    val booleanResponse = repository.booleanResponse

    private val _isFile: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFile: LiveData<Boolean> = _isFile

    var fileName: String = ""
    var fileType: String = ""
    var file: File? = null

    fun removeMedia() {
        fileName = ""
        fileType = ""
        file = null
        _isFile.postValue(false)
    }

    private fun uploadImage() {
        if (file == null) {
            error(Constants.UPLOAD_IMAGE)
            return
        }
        viewModelScope.launch {
            repository.uploadImage(prepareFilePart(FileUtils.SERVER_IMAGE_KEY_NAME, file!!))
        }
    }

    private fun uploadPDF() {
        if (file == null) {
            error(Constants.UPLOAD_PDF)
            return
        }
        viewModelScope.launch {
            repository.uploadPDF(prepareFilePart(FileUtils.SERVER_PDF_KEY_NAME, file!!))
        }
    }

    fun createPrescription(text: String) {
        if (TextUtils.isEmpty(text)) {
            error(Constants.INVALID_FIELDS)
        } else if (TextUtils.isEmpty(mediaUrl.value?.url ?: "") || isFile.value == false || fileType.isBlank()){
            error(Constants.UPLOAD_PRESCRIPTION)
        } else {
            val prescription = Prescription(
                description = text,
                prescription = mediaUrl.value?.url,
                type = fileType
            )

            viewModelScope.launch {
                repository.createPrescription(prescription)
            }
        }
    }

    fun saveMedia(file: File, fileType: String) {
        if (file.name.isNotBlank()) {
            this.file = file
            this.fileType = fileType
            if (fileType == FileUtils.IMAGE_TYPE) {
                if (file.name.contains(FileUtils.JPG_FILE)) {
                    fileName = file.name
                } else {
                    fileName = file.name + FileUtils.JPG_FILE
                }
                _isFile.postValue(true)
                uploadImage()
            } else {
                if (file.name.contains(FileUtils.PDF_FILE)) {
                    fileName = file.name
                } else {
                    fileName = file.name + FileUtils.PDF_FILE
                }
                _isFile.postValue(true)
                uploadPDF()
            }
        }
    }

    private fun prepareFilePart(partName: String, outputFile: File): MultipartBody.Part {
        val requestFile = RequestBody.create(FileUtils.SERVER_FILE_TYPE.toMediaTypeOrNull(), outputFile)
        return MultipartBody.Part.createFormData(partName, fileName, requestFile)
    }


}
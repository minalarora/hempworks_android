package com.hemp.works.dashboard.prescription.ui

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hemp.works.base.BaseViewModel
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.Prescription
import com.hemp.works.dashboard.model.PrescriptionMedia
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

    var fileNames: ArrayList<String> = arrayListOf()
    var fileTypes: ArrayList<String> = arrayListOf()
    var files: ArrayList<File> = arrayListOf()

    fun removeMedia() {
        fileNames.clear()
        fileTypes.clear()
        files.clear()
        _isFile.postValue(false)
    }

    private fun uploadMedia() {
        if (files.isEmpty()) {
            error(Constants.UPLOAD_PDF)
            return
        }

        viewModelScope.launch {
            val multipartList = ArrayList<Pair<MultipartBody.Part, String>>()
            for (i in 0 until files.size) {
                val multipart = if (fileTypes[i] == "image") {
                    prepareFilePart(FileUtils.SERVER_IMAGE_KEY_NAME, files[i], fileNames[i])
                } else {
                    prepareFilePart(FileUtils.SERVER_PDF_KEY_NAME, files[i], fileNames[i])
                }
                multipartList.add(Pair(multipart, fileTypes[i]))
            }
            repository.uploadFiles(multipartList)
        }
    }


    fun createPrescription(text: String) {
        if (TextUtils.isEmpty(text)) {
            error(Constants.INVALID_FIELDS)
        } else if (mediaUrl.value.isNullOrEmpty() || isFile.value == false || fileTypes.any { it.isBlank() }){
            error(Constants.UPLOAD_PRESCRIPTION)
        } else {

            val prescriptions  = arrayListOf<PrescriptionMedia>()

            mediaUrl.value?.forEach {
                if (it.success && it.url.substring(it.url.length - 3) == "pdf") {
                    prescriptions.add(PrescriptionMedia(it.url, "pdf"))
                } else if (it.success) {
                    prescriptions.add(PrescriptionMedia(it.url, "image"))
                }
            }
            val prescription = Prescription(
                description = text,
                prescription = null,
                type = null,
                prescriptions = prescriptions
            )

            viewModelScope.launch {
                repository.createPrescription(prescription)
            }
        }
    }

    fun saveMedia(filesAndFileTypes: ArrayList<Pair<File, String>>) {

        if (filesAndFileTypes.isNotEmpty()) {
            this.files = filesAndFileTypes.map { it.first } as ArrayList<File>
            this.fileTypes = filesAndFileTypes.map { it.second } as ArrayList<String>
            this.fileNames = filesAndFileTypes.map {
                return@map if (it.second == FileUtils.IMAGE_TYPE) {
                    if (it.first.name.contains(FileUtils.JPG_FILE)) {
                        it.first.name
                    } else {
                        it.first.name + FileUtils.JPG_FILE
                    }
                } else {
                    if (it.first.name.contains(FileUtils.PDF_FILE)) {
                        it.first.name
                    } else {
                        it.first.name + FileUtils.PDF_FILE
                    }
                }
            } as ArrayList<String>

            _isFile.postValue(true)

            uploadMedia()
        }
    }

    private fun prepareFilePart(partName: String, outputFile: File, outputFileName: String): MultipartBody.Part {
        val requestFile = RequestBody.create(FileUtils.SERVER_FILE_TYPE.toMediaTypeOrNull(), outputFile)
        return MultipartBody.Part.createFormData(partName, outputFileName, requestFile)
    }


}
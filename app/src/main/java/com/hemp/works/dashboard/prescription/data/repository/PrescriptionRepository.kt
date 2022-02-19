package com.hemp.works.dashboard.prescription.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hemp.works.base.BaseRepository
import com.hemp.works.base.Constants
import com.hemp.works.base.ImageResponse
import com.hemp.works.base.LiveEvent
import com.hemp.works.dashboard.model.Prescription
import com.hemp.works.dashboard.prescription.data.remote.PrescriptionRemoteDataSource
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrescriptionRepository  @Inject constructor(private val remoteDataSource: PrescriptionRemoteDataSource) : BaseRepository()  {

    private val _booleanResponse = LiveEvent<Boolean>()
    val booleanResponse: LiveData<Boolean>
        get() = _booleanResponse

    private val _imageResponse = LiveEvent<ImageResponse>()
    val imageResponse: LiveData<ImageResponse>
        get() = _imageResponse

    private val _prescriptionList = MutableLiveData<List<Prescription>>()
    val prescriptionList: LiveData<List<Prescription>>
        get() = _prescriptionList

    suspend fun uploadImage(image: MultipartBody.Part) {
        getResult(Constants.UNABLE_TO_UPLOAD_IMAGE) { remoteDataSource.uploadPrescriptionImage(image)}?.let {
            it.data?.let { imageResponse -> _imageResponse.postValue(imageResponse)  }
        }
    }

    suspend fun uploadPDF(pdf: MultipartBody.Part) {
        getResult(Constants.UNABLE_TO_UPLOAD_PDF) { remoteDataSource.uploadPrescriptionPDF(pdf)}?.let {
            it.data?.let { imageResponse -> _imageResponse.postValue(imageResponse)  }
        }
    }

    suspend fun createPrescription(prescription: Prescription) {
        getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.createPrescription(prescription)}?.let {
            it.data?.let { booleanResponse -> _booleanResponse.postValue(booleanResponse.success) }
        }
    }

    suspend fun fetchPrescriptions() {
        getResult(Constants.GENERAL_ERROR_MESSAGE) { remoteDataSource.fetchPrescriptions()}?.let {
            it.data?.let { list -> _prescriptionList.postValue(list) }
        }
    }
}
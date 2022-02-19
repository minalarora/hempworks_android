package com.hemp.works.dashboard.prescription.data.remote

import com.hemp.works.base.BaseDataSource
import com.hemp.works.dashboard.model.Prescription
import okhttp3.MultipartBody
import javax.inject.Inject

class PrescriptionRemoteDataSource @Inject constructor(private val service: PrescriptionService): BaseDataSource() {

    suspend fun uploadPrescriptionPDF(pdf: MultipartBody.Part) = getResult { service.uploadPrescriptionPDF(pdf) }

    suspend fun uploadPrescriptionImage(image: MultipartBody.Part) = getResult { service.uploadPrescriptionImage(image) }

    suspend fun createPrescription(prescription: Prescription) = getResult { service.createPrescription(prescription) }

    suspend fun fetchPrescriptions() = getResult { service.fetchPrescriptions() }
}
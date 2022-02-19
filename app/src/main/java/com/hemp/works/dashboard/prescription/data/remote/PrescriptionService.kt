package com.hemp.works.dashboard.prescription.data.remote

import com.hemp.works.base.BooleanResponse
import com.hemp.works.base.ImageResponse
import com.hemp.works.dashboard.model.Prescription
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part

interface PrescriptionService {

    @POST("/v1/upload/pdf")
    suspend fun uploadPrescriptionPDF(@Part pdf: MultipartBody.Part): Response<ImageResponse>

    @POST("/v1/upload/image")
    suspend fun uploadPrescriptionImage(@Part image: MultipartBody.Part): Response<ImageResponse>

    @POST("/v1/prescription")
    suspend fun createPrescription(@Body body: Prescription): Response<BooleanResponse>

    @GET("/v1/prescription/all")
    suspend fun fetchPrescriptions(): Response<List<Prescription>>


}
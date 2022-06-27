package com.minal.admin.data.remote

import com.minal.admin.data.request.*
import com.minal.admin.data.response.*
import retrofit2.Response
import retrofit2.http.*

interface RestApi {

    @GET(RestConstant.API_PRODUCT_ALL)
    suspend fun getProduct(@Header("Authorization") token: String) : Response<ArrayList<ResponseProduct>>

    @POST(RestConstant.API_CREATE_COUPON)
    suspend fun createCoupon(@Header("Authorization") token: String,@Body obj: RequestCreateCoupon): Response<ResponseCreateCoupon>

    @GET(RestConstant.API_DOCTOR_LIST)
    suspend fun getDoctors(@Header("Authorization") token: String,
    @Query ("status") status:String) : Response<ArrayList<ResponseAllDoctors>>

    @GET(RestConstant.API_SINGLE_DOCTOR)
    suspend fun getDoctorDetail(@Header("Authorization") token: String,
                           @Query ("id") id:String?) : Response<ResponseDoctorDetail>

    @GET(RestConstant.API_ORDER_LIST)
    suspend fun getOrderList(@Header("Authorization") token: String,
                                @Query ("doctor") doctor:String?) : Response<ArrayList<OrderList>>

    @GET(RestConstant.API_ORDER_SINGLE)
    suspend fun getSingleOrder(@Header("Authorization") token: String,
                                @Query ("id") id:String?) : Response<ResponseSingleOrder>

    @GET(RestConstant.API_PRESCRIPTION_LIST)
    suspend fun getPresList(@Header("Authorization") token: String,
                                @Query ("doctor") doctor:String?) : Response<ArrayList<ResponsePresList>>

    @PATCH(RestConstant.API_DOCTOR_UPDATE_STATUS)
    suspend fun getUpdateStatus(@Header("Authorization") token: String, @Query ("id") id:String?,
                                @Body obj:RequestDocUpStatus) : Response<ResponseDoctorStatus>

    @POST(RestConstant.API_ADD_MANUAL_CREDIT)
    suspend fun addCreditManual(@Header("Authorization") token: String,@Body obj: ReqAddCreditManual): Response<RespCreditManual>

    @POST(RestConstant.API_ORDER_MANUAL)
    suspend fun orderManual(@Header("Authorization") token: String,@Body obj: ReqAddManualOrder): Response<RespManualOrder>

    @GET(RestConstant.API_CHAT_LIST)
    suspend fun getChatList(@Header("Authorization") token: String) : Response<ArrayList<ResponseChatList>>

    @GET(RestConstant.API_CHAT_ADMIN)
    suspend fun getSingleChat(@Header("Authorization") token: String,@Path ("id") id:String?) : Response<RespSingleChatList>

    @POST(RestConstant.API_CHAT_MESSAGE)
    suspend fun sendMsg(@Header("Authorization") token: String,@Body obj: RequestSendMsg): Response<ResponseSendMsg>

    @GET(RestConstant.API_ALL_ORDER)
    suspend fun getAllOrder(@Header("Authorization") token: String) : Response<ArrayList<OrderList>>

}
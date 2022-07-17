package com.minal.admin.data.remote

import com.minal.admin.data.request.*
import com.minal.admin.data.response.*
import okhttp3.MultipartBody
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

    @PATCH(RestConstant.API_ORDER_UPDATE)
    suspend fun updateOrder(@Header("Authorization") token: String?, @Query("id") id: String?,
                            mRequestOrderUpdate: RequestOrderUpdate):Response<ResponseOrderUpdate>


    @GET(RestConstant.API_WALLET_HISTORY)
    suspend fun walletHistory(@Header("Authorization") token: String,@Query ("id") id:String?): Response<ResponseWalletHistory>

    @GET(RestConstant.API_TRANSACTION_ALL)
    suspend fun transactionAll(@Header("Authorization") token: String,@Query ("id") id:String?): Response<ResponseTransactionAll>

    @GET(RestConstant.API_CREDIT_HISTORY)
    suspend fun creditHistory(@Header("Authorization") token: String,@Query ("id") id:String?): Response<ResponseCreditHistory>

    @GET(RestConstant.API_CREDIT_HISTORY_PENDIG)
    suspend fun creditHistoryPending(@Header("Authorization") token: String,@Query ("id") id:String?): Response<ResponseCreditHistoryPending>

    @POST(RestConstant.API_CREATE_BANNER)
    suspend fun createBanner(@Header("Authorization") token: String?,@Body mRequestCreateBanner: RequestCreateBanner): Response<ResponseCreateBanner>

    @POST(RestConstant.API_BLOG)
    suspend fun createBlog(@Header("Authorization") token: String?,@Body mRequestBlog: RequestBlog): Response<ResponseBlog>

    @POST(RestConstant.API_LIVE_SESSION)
    suspend fun liveSession(@Header("Authorization") token: String?,@Body mRequestLiveSession: RequestLiveSession): Response<ResponseLiveSession>

    @POST(RestConstant.API_NEWS_PAPER)
    suspend fun newsLetter(@Header("Authorization") token: String?,@Body mRequestNewsLetter: RequestNewsLetter): Response<ResponseNewsLetter>

    @POST(RestConstant.API_TUTORIAL)
    suspend fun tutorial(@Header("Authorization") token: String?,@Body mRequestTutorial: RequestTutorial): Response<ResponseTutorial>

    @Multipart
    @POST(RestConstant.API_UPLOAD_IMAGE)
    suspend fun uploadImage(@Header("Authorization") token: String,@Part file: MultipartBody.Part): Response<ResponseUploadImage>


}
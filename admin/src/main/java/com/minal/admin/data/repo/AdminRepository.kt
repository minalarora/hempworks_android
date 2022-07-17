package com.minal.admin.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hoobio.data.network.ApiResponse
import com.hoobio.data.network.DataFetchCall
import com.minal.admin.base.BaseRepository
import com.minal.admin.data.remote.RestApi
import com.minal.admin.data.remote.Result
import com.minal.admin.data.remote.RetrofitClient
import com.minal.admin.data.remote.SafeApiRequest
import com.minal.admin.data.request.*
import com.minal.admin.data.response.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

class AdminRepository(private val apiClient: RestApi = RetrofitClient.apiInterface) : SafeApiRequest {

    private val _orderList = MutableLiveData<List<OrderList>>()
    val orderList: LiveData<List<OrderList>>
        get() = _orderList

    suspend fun getProduct(token:String): Result<ArrayList<ResponseProduct>>{

        return  safeApiCall(call = { apiClient.getProduct(token) })

    }

    suspend fun createCoupon(token: String,obj: RequestCreateCoupon): Result<ResponseCreateCoupon>
    {
        return safeApiCall(call = {apiClient.createCoupon(token,obj)})
    }

    suspend fun getDoctors(token: String,obj: RequestAllDoctors): Result<ArrayList<ResponseAllDoctors>>
    {
        return safeApiCall(call = {apiClient.getDoctors(token,obj.status)})
    }

    suspend fun getDoctorDetail(token: String,obj: RequestSingleDoctor): Result<ResponseDoctorDetail>
    {
        return safeApiCall(call = {apiClient.getDoctorDetail(token,obj.id)})
    }

    suspend fun getOrderList(token: String,obj: RequestOrderList): Result<ArrayList<OrderList>>
    {
        return safeApiCall(call = {apiClient.getOrderList(token,obj.doctor)})
    }

    suspend fun getSingleOrder(token: String,obj: RequestSingleOrder): Result<ResponseSingleOrder>
    {
        return safeApiCall(call = {apiClient.getSingleOrder(token,obj.id)})
    }

    suspend fun getPresList(token: String,obj: RequestPresList): Result<ArrayList<ResponsePresList>>
    {
        return safeApiCall(call = {apiClient.getPresList(token,obj.doctor)})
    }

    suspend fun getUpdateStatus(token: String,id:String?,obj: RequestDocUpStatus): Result<ResponseDoctorStatus>
    {
        return safeApiCall(call = {apiClient.getUpdateStatus(token,id,obj)})
    }

    suspend fun addCreditManual(token: String,obj: ReqAddCreditManual): Result<RespCreditManual>
    {
        return safeApiCall(call = {apiClient.addCreditManual(token,obj)})
    }

    suspend fun orderManual(token: String,obj: ReqAddManualOrder): Result<RespManualOrder>
    {
        return safeApiCall(call = {apiClient.orderManual(token,obj)})
    }

    suspend fun chatList(token: String): Result<ArrayList<ResponseChatList>>
    {
        return safeApiCall(call = {apiClient.getChatList(token)})
    }

    suspend fun singleChat(token: String,id:String): Result<RespSingleChatList>
    {
        return safeApiCall(call = {apiClient.getSingleChat(token,id)})
    }

    suspend fun sendMsg(token: String,obj:RequestSendMsg): Result<ResponseSendMsg>
    {
        return safeApiCall(call = {apiClient.sendMsg(token,obj)})
    }

    suspend fun getAllOrder(token: String): Result<ArrayList<OrderList>>
    {
        return safeApiCall(call = {apiClient.getAllOrder(token)})
    }

    suspend fun updateOrder(token: String?,id: String?,mRequestOrderUpdate: RequestOrderUpdate): Result<ResponseOrderUpdate>
    {
        return safeApiCall(call = {apiClient.updateOrder(token,id,mRequestOrderUpdate)})
    }

    suspend fun walletHistory(token: String,id: String): Result<ResponseWalletHistory>
    {
        return safeApiCall(call = {apiClient.walletHistory(token,id)})
    }

    suspend fun transactionAll(token: String,id: String): Result<ResponseTransactionAll>
    {
        return safeApiCall(call = {apiClient.transactionAll(token,id)})
    }

    suspend fun creditHistory(token: String,id: String): Result<ResponseCreditHistory>
    {
        return safeApiCall(call = {apiClient.creditHistory(token,id)})
    }

    suspend fun creditHistoryPending(token: String,id: String): Result<ResponseCreditHistoryPending>
    {
        return safeApiCall(call = {apiClient.creditHistoryPending(token,id)})
    }

    suspend fun createBanner(token: String?,mRequestCreateBanner: RequestCreateBanner): Result<ResponseCreateBanner>
    {
        return safeApiCall(call = {apiClient.createBanner(token,mRequestCreateBanner)})
    }

    suspend fun createBlog(token: String?,mRequestBlog: RequestBlog): Result<ResponseBlog>
    {
        return safeApiCall(call = {apiClient.createBlog(token,mRequestBlog)})
    }

    suspend fun liveSession(token: String?,mRequestLiveSession: RequestLiveSession): Result<ResponseLiveSession>
    {
        return safeApiCall(call = {apiClient.liveSession(token,mRequestLiveSession)})
    }

    suspend fun newsLetter(token: String?,mRequestNewsLetter: RequestNewsLetter): Result<ResponseNewsLetter>
    {
        return safeApiCall(call = {apiClient.newsLetter(token,mRequestNewsLetter)})
    }

    suspend fun tutorial(token: String?,mRequestTutorial: RequestTutorial): Result<ResponseTutorial>
    {
        return safeApiCall(call = {apiClient.tutorial(token,mRequestTutorial)})
    }

    var mediaFile: RequestBody? = null
    var body: MultipartBody.Part? = null
    suspend fun uploadImage(token: String,mFile: File): Result<ResponseUploadImage>
    {
        mediaFile = mFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        body = MultipartBody.Part.createFormData("image", mFile.name, mediaFile!!)
        return safeApiCall(call = {apiClient.uploadImage(token,body!!)})
    }


}
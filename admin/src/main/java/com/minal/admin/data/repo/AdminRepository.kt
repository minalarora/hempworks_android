package com.minal.admin.data.repo

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
import retrofit2.Response

class AdminRepository(private val apiClient: RestApi = RetrofitClient.apiInterface) : SafeApiRequest {

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
}